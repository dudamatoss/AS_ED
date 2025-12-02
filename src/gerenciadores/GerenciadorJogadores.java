package gerenciadores;

import models.Jogador;
import estruturas.NoCasa;
import java.util.ArrayList;
import java.util.List;

// Gerenciador de jogadores usando ArrayList para operações CRUD eficientes
public class GerenciadorJogadores {
    private List<Jogador> jogadores;
    private int proximoId;
    
    public GerenciadorJogadores() {
        this.jogadores = new ArrayList<>();
        this.proximoId = 1;
        inicializarDadosPreCadastrados(1500.0); // Saldo padrão
    }
    
    // Inicializa dados pré-cadastrados para facilitar testes
    private void inicializarDadosPreCadastrados(double saldoInicial) {
        // Cadastra 4 jogadores pré-definidos
        String[] nomesJogadores = {"Juliano", "Eduarda", "João", "Isadora"};
        
        for (String nome : nomesJogadores) {
            cadastrar(nome, saldoInicial, false); // false = não exibe mensagem
        }
    }
    
    // Cadastra um novo jogador
    public boolean cadastrar(String nome, double saldoInicial) {
        return cadastrar(nome, saldoInicial, true);
    }
    
    // Cadastra um novo jogador (versão com controle de mensagem)
    private boolean cadastrar(String nome, double saldoInicial, boolean exibirMensagem) {
        if (jogadores.size() >= 6) {
            if (exibirMensagem) {
                System.out.println("Limite máximo de 6 jogadores atingido!");
            }
            return false;
        }
        
        Jogador novoJogador = new Jogador(proximoId++, nome, saldoInicial);
        jogadores.add(novoJogador);
        if (exibirMensagem) {
            System.out.println("Jogador cadastrado com sucesso!");
        }
        return true;
    }
    
    // Lista todos os jogadores
    public void listar() {
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        
        System.out.println("\n=== LISTA DE JOGADORES ===");
        for (Jogador jogador : jogadores) {
            System.out.println(jogador.toString());
        }
        System.out.println("Total: " + jogadores.size() + " jogadores\n");
    }
    
    // Busca um jogador por ID
    public Jogador buscarPorId(int id) {
        for (Jogador jogador : jogadores) {
            if (jogador.getId() == id) {
                return jogador;
            }
        }
        return null;
    }
    
    // Atualiza dados de um jogador
    public boolean atualizar(int id, String nome, double saldo) {
        Jogador jogador = buscarPorId(id);
        if (jogador == null) {
            System.out.println("Jogador não encontrado!");
            return false;
        }
        
        jogador.setNome(nome);
        jogador.setSaldo(saldo);
        System.out.println("Jogador atualizado com sucesso!");
        return true;
    }
    
    // Remove um jogador
    public boolean remover(int id) {
        Jogador jogador = buscarPorId(id);
        if (jogador == null) {
            System.out.println("Jogador não encontrado!");
            return false;
        }
        
        jogadores.remove(jogador);
        System.out.println("Jogador removido com sucesso!");
        return true;
    }
    
    // Retorna a lista de jogadores
    public List<Jogador> getJogadores() {
        return jogadores;
    }
    
    // Retorna o número de jogadores cadastrados
    public int getQuantidade() {
        return jogadores.size();
    }
    
    // Valida se há pelo menos 2 jogadores cadastrados
    public boolean validarMinimo() {
        return jogadores.size() >= 2;
    }
    
    // Inicializa as posições dos jogadores no tabuleiro
    public void inicializarPosicoes(NoCasa casaInicio) {
        for (Jogador jogador : jogadores) {
            jogador.setPosicaoAtual(casaInicio);
        }
    }
    
    // Retorna lista de jogadores não falidos
    public List<Jogador> getJogadoresAtivos() {
        List<Jogador> ativos = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            if (!jogador.isFalido()) {
                ativos.add(jogador);
            }
        }
        return ativos;
    }
}

