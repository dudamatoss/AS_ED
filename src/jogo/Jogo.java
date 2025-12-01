package jogo;

import estruturas.NoCasa;
import modelos.*;
import gerenciadores.GerenciadorImoveis;
import gerenciadores.GerenciadorJogadores;
import java.util.List;
import java.util.Scanner;

// Classe principal que gerencia a lógica do jogo
public class Jogo {
    private Tabuleiro tabuleiro;
    private Baralho baralho;
    private GerenciadorJogadores gerenciadorJogadores;
    private GerenciadorImoveis gerenciadorImoveis;
    private ConfiguracaoPartida configuracao;
    private Scanner scanner;
    private int rodadaAtual;
    
    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.baralho = new Baralho();
        this.gerenciadorJogadores = new GerenciadorJogadores();
        this.gerenciadorImoveis = new GerenciadorImoveis();
        this.configuracao = new ConfiguracaoPartida();
        this.scanner = new Scanner(System.in);
        this.rodadaAtual = 0;
    }
    
    // Inicia uma nova partida
    public void iniciarPartida() {
        if (!validarInicio()) {
            System.out.println("Não é possível iniciar o jogo. Verifique os requisitos mínimos.");
            return;
        }
        
        // Inicializa o tabuleiro
        tabuleiro.inicializar(gerenciadorImoveis);
        
        // Inicializa posições dos jogadores
        NoCasa casaInicio = tabuleiro.getCasaInicio();
        gerenciadorJogadores.inicializarPosicoes(casaInicio);
        
        rodadaAtual = 0;
        System.out.println("\n=== PARTIDA INICIADA ===");
        System.out.println("Saldo inicial: R$ " + configuracao.getSaldoInicial());
        System.out.println("Salário por volta: R$ " + configuracao.getSalario());
        System.out.println("Rodadas máximas: " + configuracao.getMaxRodadas());
        System.out.println("========================\n");
        
        executarPartida();
    }
    
    // Valida se o jogo pode ser iniciado
    private boolean validarInicio() {
        if (!gerenciadorImoveis.validarMinimo()) {
            System.out.println("É necessário cadastrar pelo menos 10 imóveis!");
            return false;
        }
        if (!gerenciadorJogadores.validarMinimo()) {
            System.out.println("É necessário cadastrar pelo menos 2 jogadores!");
            return false;
        }
        return true;
    }
    
    // Executa o loop principal da partida
    private void executarPartida() {
        List<Jogador> jogadores = gerenciadorJogadores.getJogadores();
        
        while (rodadaAtual < configuracao.getMaxRodadas()) {
            rodadaAtual++;
            System.out.println("\n========== RODADA " + rodadaAtual + " ==========");
            
            boolean jogoTerminou = false;
            
            for (Jogador jogador : jogadores) {
                if (jogador.isFalido()) {
                    continue;
                }
                
                System.out.println("\n--- Vez de: " + jogador.getNome() + " ---");
                System.out.println("Saldo atual: R$ " + String.format("%.2f", jogador.getSaldo()));
                System.out.println("Posição: " + jogador.getPosicaoAtual().getCasa().getNome());
                
                // Verifica se o jogador está preso
                if (jogador.isPreso()) {
                    processarPrisao(jogador);
                    continue;
                }
                
                // Processa a jogada
                processarJogada(jogador);
                
                // Verifica condições de fim de jogo
                List<Jogador> ativos = gerenciadorJogadores.getJogadoresAtivos();
                if (ativos.size() <= 1) {
                    jogoTerminou = true;
                    break;
                }
            }
            
            if (jogoTerminou) {
                break;
            }
        }
        
        encerrarPartida();
    }
    
    // Processa jogada: lança dados, move jogador e verifica interações
    private void processarJogada(Jogador jogador) {
        NoCasa posicaoAnterior = jogador.getPosicaoAtual();
        
        System.out.println("\nPressione ENTER para lançar os dados...");
        scanner.nextLine();
        
        int resultadoDados = tabuleiro.lancarDados();
        NoCasa novaPosicao = tabuleiro.getCasas().avancar(posicaoAnterior, resultadoDados);
        jogador.setPosicaoAtual(novaPosicao);
        
        System.out.println("Você parou em: " + novaPosicao.getCasa().getNome());
        
        // Verifica se passou pelo início
        if (tabuleiro.getCasas().passouPeloInicio(posicaoAnterior, novaPosicao) || 
            novaPosicao.getCasa().getTipo() == TipoCasa.INICIO) {
            jogador.adicionarSaldo(configuracao.getSalario());
            System.out.println("Você completou uma volta! Recebeu R$ " + 
                             configuracao.getSalario() + " de salário.");
        }
        
        // Processa a interação com a casa
        processarCasa(jogador, novaPosicao);
    }
    
    // Processa interação com a casa (compra, aluguel, imposto, etc.)
    private void processarCasa(Jogador jogador, NoCasa casaAtual) {
        Casa casa = casaAtual.getCasa();
        TipoCasa tipo = casa.getTipo();
        
        switch (tipo) {
            case INICIO:
                // Já processado no método processarJogada
                break;
                
            case IMOVEL:
                processarImovel(jogador, casa);
                break;
                
            case IMPOSTO:
                processarImposto(jogador);
                break;
                
            case RESTITUICAO:
                processarRestituicao(jogador);
                break;
                
            case PRISAO:
                // Apenas passa pela prisão, não prende
                System.out.println("Você passou pela Prisão. Continue sua jornada!");
                break;
                
            case SORTE_REVES:
                processarSorteReves(jogador);
                break;
        }
    }
    
    // Processa compra de imóvel ou pagamento de aluguel
    private void processarImovel(Jogador jogador, Casa casa) {
        Imovel imovel = casa.getImovel();
        
        if (imovel == null) {
            System.out.println("Erro: Imóvel não encontrado nesta casa.");
            return;
        }
        
        if (imovel.temDono()) {
            if (imovel.getDono().getId() == jogador.getId()) {
                System.out.println("Este imóvel já é seu!");
            } else {
                double aluguel = imovel.getValorAluguel();
                if (jogador.getSaldo() >= aluguel) {
                    jogador.subtrairSaldo(aluguel);
                    imovel.getDono().adicionarSaldo(aluguel);
                    System.out.println("Você pagou R$ " + String.format("%.2f", aluguel) + 
                                     " de aluguel para " + imovel.getDono().getNome() + ".");
                } else {
                    System.out.println("Saldo insuficiente para pagar o aluguel!");
                    falirJogador(jogador);
                }
            }
        } else {
            System.out.println("Imóvel disponível: " + imovel.getNome());
            System.out.println("Valor de compra: R$ " + String.format("%.2f", imovel.getValorCompra()));
            System.out.println("Valor de aluguel: R$ " + String.format("%.2f", imovel.getValorAluguel()));
            
            if (jogador.getSaldo() >= imovel.getValorCompra()) {
                System.out.print("Deseja comprar? (s/n): ");
                String resposta = scanner.nextLine().toLowerCase();
                
                if (resposta.equals("s") || resposta.equals("sim")) {
                    jogador.subtrairSaldo(imovel.getValorCompra());
                    jogador.adicionarPropriedade(imovel);
                    System.out.println("Parabéns! Você comprou " + imovel.getNome() + "!");
                }
            } else {
                System.out.println("Saldo insuficiente para comprar este imóvel.");
            }
        }
    }
    
    // Calcula e cobra imposto (5% do patrimônio)
    private void processarImposto(Jogador jogador) {
        double patrimonio = jogador.calcularPatrimonio();
        double imposto = patrimonio * 0.05; // 5% do patrimônio
        
        System.out.println("Você deve pagar 5% de imposto sobre seu patrimônio.");
        System.out.println("Patrimônio: R$ " + String.format("%.2f", patrimonio));
        System.out.println("Imposto: R$ " + String.format("%.2f", imposto));
        
        if (jogador.getSaldo() >= imposto) {
            jogador.subtrairSaldo(imposto);
            System.out.println("Imposto pago com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para pagar o imposto!");
            falirJogador(jogador);
        }
    }
    
    // Paga restituição (10% do salário)
    private void processarRestituicao(Jogador jogador) {
        double restituicao = configuracao.getSalario() * 0.10; // 10% do salário
        jogador.adicionarSaldo(restituicao);
        System.out.println("Você recebeu R$ " + String.format("%.2f", restituicao) + 
                         " de restituição!");
    }
    
    // Puxa carta do baralho e aplica efeito
    private void processarSorteReves(Jogador jogador) {
        System.out.println("Você parou em Sorte/Revés! Puxando uma carta...");
        Carta carta = baralho.puxarCarta();
        System.out.println("\n>>> " + carta.getDescricao() + " <<<\n");
        
        aplicarEfeitoCarta(jogador, carta);
    }
    
    // Aplica o efeito de uma carta
    private void aplicarEfeitoCarta(Jogador jogador, Carta carta) {
        TipoAcaoCarta tipo = carta.getTipoAcao();
        double valor = carta.getValor();
        
        switch (tipo) {
            case GANHAR_DINHEIRO:
                jogador.adicionarSaldo(valor);
                System.out.println("Você recebeu R$ " + String.format("%.2f", valor) + "!");
                break;
                
            case PERDER_DINHEIRO:
                if (jogador.getSaldo() >= valor) {
                    jogador.subtrairSaldo(valor);
                    System.out.println("Você pagou R$ " + String.format("%.2f", valor) + "!");
                } else {
                    System.out.println("Saldo insuficiente!");
                    falirJogador(jogador);
                }
                break;
                
            case IR_PARA_INICIO:
                jogador.setPosicaoAtual(tabuleiro.getCasaInicio());
                jogador.adicionarSaldo(configuracao.getSalario());
                System.out.println("Você foi para o Início e recebeu seu salário!");
                break;
                
            case IR_PARA_PRISAO:
                jogador.setPosicaoAtual(tabuleiro.getCasaPrisao());
                jogador.setPreso(true);
                jogador.setRodadasNaPrisao(0);
                System.out.println("Você foi preso!");
                break;
                
            case AVANCAR_CASAS:
                int casas = (int) valor;
                NoCasa novaPosicao = tabuleiro.getCasas().avancar(jogador.getPosicaoAtual(), casas);
                jogador.setPosicaoAtual(novaPosicao);
                System.out.println("Você avançou " + casas + " casas!");
                processarCasa(jogador, novaPosicao);
                break;
                
            case RETROCEDER_CASAS:
                int retrocesso = (int) valor;
                NoCasa posicaoRetrocedida = tabuleiro.getCasas().retroceder(
                    jogador.getPosicaoAtual(), retrocesso);
                jogador.setPosicaoAtual(posicaoRetrocedida);
                System.out.println("Você retrocedeu " + retrocesso + " casas!");
                processarCasa(jogador, posicaoRetrocedida);
                break;
                
            case PAGAR_TODOS:
                List<Jogador> todosJogadores = gerenciadorJogadores.getJogadores();
                double totalPagar = valor * (todosJogadores.size() - 1);
                if (jogador.getSaldo() >= totalPagar) {
                    jogador.subtrairSaldo(totalPagar);
                    for (Jogador outro : todosJogadores) {
                        if (outro.getId() != jogador.getId() && !outro.isFalido()) {
                            outro.adicionarSaldo(valor);
                        }
                    }
                    System.out.println("Você pagou R$ " + String.format("%.2f", valor) + 
                                     " para cada jogador!");
                } else {
                    System.out.println("Saldo insuficiente!");
                    falirJogador(jogador);
                }
                break;
                
            case RECEBER_TODOS:
                List<Jogador> todos = gerenciadorJogadores.getJogadores();
                double totalReceber = valor * (todos.size() - 1);
                jogador.adicionarSaldo(totalReceber);
                for (Jogador outro : todos) {
                    if (outro.getId() != jogador.getId() && !outro.isFalido()) {
                        if (outro.getSaldo() >= valor) {
                            outro.subtrairSaldo(valor);
                        } else {
                            falirJogador(outro);
                        }
                    }
                }
                System.out.println("Você recebeu R$ " + String.format("%.2f", valor) + 
                                 " de cada jogador!");
                break;
        }
    }
    
    // Processa tentativa de sair da prisão (dados duplos)
    private void processarPrisao(Jogador jogador) {
        jogador.incrementarRodadasNaPrisao();
        int tentativas = jogador.getRodadasNaPrisao();
        
        System.out.println("Você está preso! Tentativa " + tentativas + " de 3");
        
        if (tentativas >= 3) {
            // Terceira tentativa - liberta automaticamente
            jogador.setPreso(false);
            jogador.setRodadasNaPrisao(0);
            System.out.println("Você foi libertado após 3 tentativas, mas não pode jogar neste turno.");
            return;
        }
        
        System.out.println("Pressione ENTER para tentar sair (dados duplos)...");
        scanner.nextLine();
        
        if (tabuleiro.saoDadosDuplos()) {
            // Libertado!
            jogador.setPreso(false);
            jogador.setRodadasNaPrisao(0);
            System.out.println("Parabéns! Você saiu da prisão!");
            
            // Joga os dados novamente para avançar
            int resultadoDados = tabuleiro.lancarDados();
            NoCasa novaPosicao = tabuleiro.getCasas().avancar(jogador.getPosicaoAtual(), resultadoDados);
            jogador.setPosicaoAtual(novaPosicao);
            System.out.println("Você parou em: " + novaPosicao.getCasa().getNome());
            processarCasa(jogador, novaPosicao);
        } else {
            System.out.println("Você não conseguiu sair. Ficará preso mais uma rodada.");
        }
    }
    
    // Declara jogador como falido e libera propriedades
    private void falirJogador(Jogador jogador) {
        jogador.setFalido(true);
        jogador.setSaldo(0);
        
        // Libera todas as propriedades
        for (Imovel imovel : jogador.getPropriedades()) {
            imovel.setDono(null);
        }
        jogador.getPropriedades().clear();
        
        System.out.println("\n*** " + jogador.getNome() + " FALIU! ***\n");
    }
    
    // Encerra partida e declara vencedor (maior patrimônio)
    private void encerrarPartida() {
        System.out.println("\n========== PARTIDA ENCERRADA ==========");
        
        List<Jogador> jogadores = gerenciadorJogadores.getJogadores();
        Jogador vencedor = null;
        double maiorPatrimonio = -1;
        
        for (Jogador jogador : jogadores) {
            if (!jogador.isFalido()) {
                double patrimonio = jogador.calcularPatrimonio();
                System.out.println(jogador.getNome() + ": R$ " + 
                                 String.format("%.2f", patrimonio));
                
                if (patrimonio > maiorPatrimonio) {
                    maiorPatrimonio = patrimonio;
                    vencedor = jogador;
                }
            }
        }
        
        if (vencedor != null) {
            System.out.println("\n*** VENCEDOR: " + vencedor.getNome().toUpperCase() + " ***");
            System.out.println("Patrimônio: R$ " + String.format("%.2f", maiorPatrimonio));
        } else {
            System.out.println("\nNenhum vencedor encontrado.");
        }
        
        System.out.println("========================================\n");
    }
    
    // Getters
    public GerenciadorJogadores getGerenciadorJogadores() {
        return gerenciadorJogadores;
    }
    
    public GerenciadorImoveis getGerenciadorImoveis() {
        return gerenciadorImoveis;
    }
    
    public ConfiguracaoPartida getConfiguracao() {
        return configuracao;
    }
}

