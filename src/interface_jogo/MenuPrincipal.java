package interface_jogo;

import jogo.Jogo;
import modelos.Imovel;
import modelos.Jogador;
import java.util.Scanner;

/**
 * Classe que gerencia os menus e a interface do usuário
 */
public class MenuPrincipal {
    private Jogo jogo;
    private Scanner scanner;
    
    public MenuPrincipal() {
        this.jogo = new Jogo();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Exibe o menu principal
     */
    public void exibirMenu() {
        int opcao;
        
        do {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1. Gerenciar Imóveis");
            System.out.println("2. Gerenciar Jogadores");
            System.out.println("3. Configurações da Partida");
            System.out.println("4. Iniciar Partida");
            System.out.println("5. Dados Pré-cadastrados (Teste Rápido)");
            System.out.println("0. Sair");
            System.out.println("====================================");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer
            
            switch (opcao) {
                case 1:
                    menuImoveis();
                    break;
                case 2:
                    menuJogadores();
                    break;
                case 3:
                    menuConfiguracao();
                    break;
                case 4:
                    jogo.iniciarPartida();
                    break;
                case 5:
                    carregarDadosPreCadastrados();
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    /**
     * Menu de gerenciamento de imóveis
     */
    private void menuImoveis() {
        int opcao;
        
        do {
            System.out.println("\n========== GERENCIAR IMÓVEIS ==========");
            System.out.println("1. Cadastrar Imóvel");
            System.out.println("2. Listar Imóveis");
            System.out.println("3. Atualizar Imóvel");
            System.out.println("4. Remover Imóvel");
            System.out.println("0. Voltar");
            System.out.println("=======================================");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    cadastrarImovel();
                    break;
                case 2:
                    jogo.getGerenciadorImoveis().listar();
                    break;
                case 3:
                    atualizarImovel();
                    break;
                case 4:
                    removerImovel();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    /**
     * Cadastra um novo imóvel
     */
    private void cadastrarImovel() {
        System.out.print("Nome do imóvel: ");
        String nome = scanner.nextLine();
        
        System.out.print("Valor de compra: R$ ");
        double valorCompra = scanner.nextDouble();
        
        System.out.print("Valor de aluguel: R$ ");
        double valorAluguel = scanner.nextDouble();
        scanner.nextLine();
        
        jogo.getGerenciadorImoveis().cadastrar(nome, valorCompra, valorAluguel);
    }
    
    /**
     * Atualiza um imóvel
     */
    private void atualizarImovel() {
        jogo.getGerenciadorImoveis().listar();
        System.out.print("ID do imóvel a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Novo valor de compra: R$ ");
        double valorCompra = scanner.nextDouble();
        
        System.out.print("Novo valor de aluguel: R$ ");
        double valorAluguel = scanner.nextDouble();
        scanner.nextLine();
        
        jogo.getGerenciadorImoveis().atualizar(id, nome, valorCompra, valorAluguel);
    }
    
    /**
     * Remove um imóvel
     */
    private void removerImovel() {
        jogo.getGerenciadorImoveis().listar();
        System.out.print("ID do imóvel a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        jogo.getGerenciadorImoveis().remover(id);
    }
    
    /**
     * Menu de gerenciamento de jogadores
     */
    private void menuJogadores() {
        int opcao;
        
        do {
            System.out.println("\n========== GERENCIAR JOGADORES ==========");
            System.out.println("1. Cadastrar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Atualizar Jogador");
            System.out.println("4. Remover Jogador");
            System.out.println("0. Voltar");
            System.out.println("==========================================");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    cadastrarJogador();
                    break;
                case 2:
                    jogo.getGerenciadorJogadores().listar();
                    break;
                case 3:
                    atualizarJogador();
                    break;
                case 4:
                    removerJogador();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    /**
     * Cadastra um novo jogador
     */
    private void cadastrarJogador() {
        System.out.print("Nome do jogador: ");
        String nome = scanner.nextLine();
        
        double saldoInicial = jogo.getConfiguracao().getSaldoInicial();
        System.out.println("Saldo inicial será: R$ " + saldoInicial);
        
        jogo.getGerenciadorJogadores().cadastrar(nome, saldoInicial);
    }
    
    /**
     * Atualiza um jogador
     */
    private void atualizarJogador() {
        jogo.getGerenciadorJogadores().listar();
        System.out.print("ID do jogador a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Novo saldo: R$ ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();
        
        jogo.getGerenciadorJogadores().atualizar(id, nome, saldo);
    }
    
    /**
     * Remove um jogador
     */
    private void removerJogador() {
        jogo.getGerenciadorJogadores().listar();
        System.out.print("ID do jogador a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        jogo.getGerenciadorJogadores().remover(id);
    }
    
    /**
     * Menu de configurações
     */
    private void menuConfiguracao() {
        int opcao;
        
        do {
            System.out.println("\n========== CONFIGURAÇÕES ==========");
            System.out.println("Saldo inicial: R$ " + jogo.getConfiguracao().getSaldoInicial());
            System.out.println("Salário por volta: R$ " + jogo.getConfiguracao().getSalario());
            System.out.println("Rodadas máximas: " + jogo.getConfiguracao().getMaxRodadas());
            System.out.println("===================================");
            System.out.println("1. Alterar saldo inicial");
            System.out.println("2. Alterar salário");
            System.out.println("3. Alterar rodadas máximas");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    System.out.print("Novo saldo inicial: R$ ");
                    double saldo = scanner.nextDouble();
                    scanner.nextLine();
                    jogo.getConfiguracao().setSaldoInicial(saldo);
                    System.out.println("Saldo inicial atualizado!");
                    break;
                case 2:
                    System.out.print("Novo salário: R$ ");
                    double salario = scanner.nextDouble();
                    scanner.nextLine();
                    jogo.getConfiguracao().setSalario(salario);
                    System.out.println("Salário atualizado!");
                    break;
                case 3:
                    System.out.print("Novo número de rodadas: ");
                    int rodadas = scanner.nextInt();
                    scanner.nextLine();
                    jogo.getConfiguracao().setMaxRodadas(rodadas);
                    System.out.println("Rodadas máximas atualizadas!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    /**
     * Carrega dados pré-cadastrados para teste rápido
     */
    private void carregarDadosPreCadastrados() {
        System.out.println("\nCarregando dados pré-cadastrados...");
        
        // Limpa dados existentes
        jogo = new Jogo();
        
        // Cadastra imóveis
        String[] nomesImoveis = {
            "Avenida Atlântica", "Avenida Paulista", "Rua das Flores", "Praça Central",
            "Avenida Copacabana", "Rua do Comércio", "Avenida Beira-Mar", "Rua Principal",
            "Avenida dos Bandeirantes", "Rua das Palmeiras", "Avenida Central", "Rua do Sol",
            "Avenida das Acácias", "Rua das Rosas", "Avenida do Mar", "Rua da Praia",
            "Avenida das Estrelas", "Rua do Céu", "Avenida dos Jardins", "Rua das Águas"
        };
        
        double[] valoresCompra = {
            500, 600, 300, 400, 550, 350, 450, 380, 420, 480,
            320, 390, 410, 370, 440, 360, 430, 400, 460, 340
        };
        
        for (int i = 0; i < nomesImoveis.length; i++) {
            double aluguel = valoresCompra[i] * 0.3; // 30% do valor de compra
            jogo.getGerenciadorImoveis().cadastrar(nomesImoveis[i], valoresCompra[i], aluguel);
        }
        
        // Cadastra jogadores
        String[] nomesJogadores = {"Alice", "Bob", "Carlos", "Diana"};
        double saldoInicial = jogo.getConfiguracao().getSaldoInicial();
        
        for (String nome : nomesJogadores) {
            jogo.getGerenciadorJogadores().cadastrar(nome, saldoInicial);
        }
        
        System.out.println("Dados pré-cadastrados carregados com sucesso!");
        System.out.println("- " + jogo.getGerenciadorImoveis().getQuantidade() + " imóveis");
        System.out.println("- " + jogo.getGerenciadorJogadores().getQuantidade() + " jogadores");
    }
}

