package gerenciadores;

import modelos.Imovel;
import java.util.ArrayList;
import java.util.List;

// Gerenciador de imóveis usando ArrayList para operações CRUD eficientes
public class GerenciadorImoveis {
    private List<Imovel> imoveis;
    private int proximoId;
    
    public GerenciadorImoveis() {
        this.imoveis = new ArrayList<>();
        this.proximoId = 1;
        inicializarDadosPreCadastrados();
    }
    
    // Inicializa dados pré-cadastrados para facilitar testes
    private void inicializarDadosPreCadastrados() {
        // Cadastra 15 imóveis pré-definidos
        String[] nomesImoveis = {
            "Avenida Atlântica", "Avenida Paulista", "Rua das Flores", 
            "Praça Central", "Avenida Copacabana", "Rua do Comércio", 
            "Avenida Beira-Mar", "Rua Principal", "Avenida dos Bandeirantes", 
            "Rua das Palmeiras", "Avenida Central", "Rua do Sol",
            "Avenida das Acácias", "Rua das Rosas", "Avenida do Mar"
        };
        
        double[] valoresCompra = {
            500, 600, 300, 400, 550, 350, 450, 380, 420, 480,
            320, 390, 410, 370, 440
        };
        
        for (int i = 0; i < nomesImoveis.length; i++) {
            double aluguel = valoresCompra[i] * 0.3; // 30% do valor de compra
            cadastrar(nomesImoveis[i], valoresCompra[i], aluguel, false); // false = não exibe mensagem
        }
    }
    
    // Cadastra um novo imóvel
    public boolean cadastrar(String nome, double valorCompra, double valorAluguel) {
        return cadastrar(nome, valorCompra, valorAluguel, true);
    }
    
    // Cadastra um novo imóvel (versão com controle de mensagem)
    private boolean cadastrar(String nome, double valorCompra, double valorAluguel, boolean exibirMensagem) {
        if (imoveis.size() >= 40) {
            if (exibirMensagem) {
                System.out.println("Limite máximo de 40 imóveis atingido!");
            }
            return false;
        }
        
        Imovel novoImovel = new Imovel(proximoId++, nome, valorCompra, valorAluguel);
        imoveis.add(novoImovel);
        if (exibirMensagem) {
            System.out.println("Imóvel cadastrado com sucesso!");
        }
        return true;
    }
    
    // Lista todos os imóveis cadastrados
    public void listar() {
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel cadastrado.");
            return;
        }
        
        System.out.println("\n=== LISTA DE IMÓVEIS ===");
        for (Imovel imovel : imoveis) {
            System.out.println(imovel.toString());
        }
        System.out.println("Total: " + imoveis.size() + " imóveis\n");
    }
    
    // Busca um imóvel por ID
    public Imovel buscarPorId(int id) {
        for (Imovel imovel : imoveis) {
            if (imovel.getId() == id) {
                return imovel;
            }
        }
        return null;
    }
    
    // Atualiza dados de um imóvel
    public boolean atualizar(int id, String nome, double valorCompra, double valorAluguel) {
        Imovel imovel = buscarPorId(id);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return false;
        }
        
        imovel.setNome(nome);
        imovel.setValorCompra(valorCompra);
        imovel.setValorAluguel(valorAluguel);
        System.out.println("Imóvel atualizado com sucesso!");
        return true;
    }
    
    // Remove um imóvel
    public boolean remover(int id) {
        Imovel imovel = buscarPorId(id);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return false;
        }
        
        if (imovel.temDono()) {
            System.out.println("Não é possível remover um imóvel que possui dono!");
            return false;
        }
        
        imoveis.remove(imovel);
        System.out.println("Imóvel removido com sucesso!");
        return true;
    }
    
    // Retorna a lista de imóveis
    public List<Imovel> getImoveis() {
        return imoveis;
    }
    
    // Retorna o número de imóveis cadastrados
    public int getQuantidade() {
        return imoveis.size();
    }
    
    // Valida se há pelo menos 10 imóveis cadastrados
    public boolean validarMinimo() {
        return imoveis.size() >= 10;
    }
}

