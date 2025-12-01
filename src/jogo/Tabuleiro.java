package jogo;

import estruturas.ListaCircular;
import estruturas.NoCasa;
import modelos.Casa;
import modelos.Imovel;
import modelos.TipoCasa;
import gerenciadores.GerenciadorImoveis;
import java.util.List;
import java.util.Random;

// Classe que gerencia o tabuleiro usando Lista Ligada Circular
public class Tabuleiro {
    private ListaCircular casas;
    private Random random;
    
    public Tabuleiro() {
        this.casas = new ListaCircular();
        this.random = new Random();
    }
    
    // Inicializa o tabuleiro com as casas especiais e os imóveis
    public void inicializar(GerenciadorImoveis gerenciadorImoveis) {
        List<Imovel> imoveis = gerenciadorImoveis.getImoveis();
        
        // Adiciona casa de início
        casas.adicionar(new Casa(TipoCasa.INICIO, "Início"));
        
        // Distribui imóveis e casas especiais ao longo do tabuleiro
        int indiceImovel = 0;
        int totalCasas = imoveis.size() + 5; // imóveis + casas especiais
        
        for (int i = 1; i < totalCasas; i++) {
            if (i == totalCasas / 4) {
                // Casa de Imposto (25% do caminho)
                casas.adicionar(new Casa(TipoCasa.IMPOSTO, "Imposto"));
            } else if (i == totalCasas / 2) {
                // Casa de Prisão (50% do caminho)
                casas.adicionar(new Casa(TipoCasa.PRISAO, "Prisão"));
            } else if (i == (totalCasas * 3) / 4) {
                // Casa de Restituição (75% do caminho)
                casas.adicionar(new Casa(TipoCasa.RESTITUICAO, "Restituição"));
            } else if (i == totalCasas - 2) {
                // Casa de Sorte/Revés (próximo ao fim)
                casas.adicionar(new Casa(TipoCasa.SORTE_REVES, "Sorte/Revés"));
            } else if (indiceImovel < imoveis.size()) {
                // Adiciona imóvel
                Imovel imovel = imoveis.get(indiceImovel++);
                casas.adicionar(new Casa(TipoCasa.IMOVEL, imovel.getNome(), imovel));
            }
        }
        
        // Garante que há pelo menos uma casa de Sorte/Revés
        if (casas.getTamanho() < 10) {
            casas.adicionar(new Casa(TipoCasa.SORTE_REVES, "Sorte/Revés"));
        }
    }
    
    // Retorna a lista circular de casas
    public ListaCircular getCasas() {
        return casas;
    }
    
    // Retorna a casa de início
    public NoCasa getCasaInicio() {
        return casas.encontrarCasaInicio();
    }
    
    // Retorna a casa de prisão
    public NoCasa getCasaPrisao() {
        return casas.encontrarCasaPrisao();
    }
    
    // Simula o lançamento de dois dados
    public int lancarDados() {
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int soma = dado1 + dado2;
        System.out.println("Dados: " + dado1 + " + " + dado2 + " = " + soma);
        return soma;
    }
    
    // Verifica se os dados são duplos (iguais)
    public boolean saoDadosDuplos() {
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        boolean duplos = dado1 == dado2;
        System.out.println("Tentativa de sair da prisão - Dados: " + dado1 + " + " + dado2 + 
                          (duplos ? " (DUPLOS!)" : ""));
        return duplos;
    }
    
    // Lista todas as casas do tabuleiro
    public void listarCasas() {
        casas.listarCasas();
    }
}

