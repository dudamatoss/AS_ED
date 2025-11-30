package jogo;

import estruturas.Pilha;
import modelos.Carta;
import modelos.TipoAcaoCarta;
import java.util.Random;

/**
 * Classe que gerencia o baralho de cartas de Sorte/Revés usando Pilha
 */
public class Baralho {
    private Pilha pilhaCartas;
    private Carta[] todasCartas;
    private Random random;
    
    public Baralho() {
        this.pilhaCartas = new Pilha();
        this.random = new Random();
        criarCartas();
        embaralhar();
    }
    
    /**
     * Cria o conjunto mínimo de 16 cartas
     */
    private void criarCartas() {
        todasCartas = new Carta[] {
            // Cartas de ganhar dinheiro
            new Carta("Você ganhou na loteria! Receba R$ 500,00", TipoAcaoCarta.GANHAR_DINHEIRO, 500),
            new Carta("Herança inesperada! Receba R$ 300,00", TipoAcaoCarta.GANHAR_DINHEIRO, 300),
            new Carta("Reembolso de imposto! Receba R$ 200,00", TipoAcaoCarta.GANHAR_DINHEIRO, 200),
            new Carta("Prêmio de sorteio! Receba R$ 150,00", TipoAcaoCarta.GANHAR_DINHEIRO, 150),
            
            // Cartas de perder dinheiro
            new Carta("Multa de trânsito! Pague R$ 100,00", TipoAcaoCarta.PERDER_DINHEIRO, 100),
            new Carta("Despesas médicas! Pague R$ 200,00", TipoAcaoCarta.PERDER_DINHEIRO, 200),
            new Carta("Reparo em casa! Pague R$ 150,00", TipoAcaoCarta.PERDER_DINHEIRO, 150),
            new Carta("Imposto atrasado! Pague R$ 250,00", TipoAcaoCarta.PERDER_DINHEIRO, 250),
            
            // Cartas de movimento
            new Carta("Avance 3 casas!", TipoAcaoCarta.AVANCAR_CASAS, 3),
            new Carta("Avance 5 casas!", TipoAcaoCarta.AVANCAR_CASAS, 5),
            new Carta("Retroceda 2 casas!", TipoAcaoCarta.RETROCEDER_CASAS, 2),
            new Carta("Retroceda 3 casas!", TipoAcaoCarta.RETROCEDER_CASAS, 3),
            
            // Cartas especiais
            new Carta("Vá para o Início e receba seu salário!", TipoAcaoCarta.IR_PARA_INICIO, 0, "INICIO"),
            new Carta("Você foi preso! Vá para a Prisão!", TipoAcaoCarta.IR_PARA_PRISAO, 0, "PRISAO"),
            
            // Cartas de interação com outros jogadores
            new Carta("Aniversário! Todos pagam R$ 50,00 para você", TipoAcaoCarta.RECEBER_TODOS, 50),
            new Carta("Festa surpresa! Pague R$ 30,00 para cada jogador", TipoAcaoCarta.PAGAR_TODOS, 30),
            
            // Cartas extras para completar mais de 16
            new Carta("Bônus de produtividade! Receba R$ 100,00", TipoAcaoCarta.GANHAR_DINHEIRO, 100),
            new Carta("Doação para caridade! Pague R$ 80,00", TipoAcaoCarta.PERDER_DINHEIRO, 80),
            new Carta("Avance 2 casas!", TipoAcaoCarta.AVANCAR_CASAS, 2),
            new Carta("Retroceda 1 casa!", TipoAcaoCarta.RETROCEDER_CASAS, 1)
        };
    }
    
    /**
     * Embaralha as cartas usando algoritmo Fisher-Yates
     */
    public void embaralhar() {
        // Cria uma cópia do array para embaralhar
        Carta[] cartasEmbaralhadas = todasCartas.clone();
        
        // Algoritmo Fisher-Yates para embaralhar
        for (int i = cartasEmbaralhadas.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = cartasEmbaralhadas[i];
            cartasEmbaralhadas[i] = cartasEmbaralhadas[j];
            cartasEmbaralhadas[j] = temp;
        }
        
        // Limpa a pilha atual e empilha as cartas embaralhadas
        pilhaCartas = new Pilha();
        for (Carta carta : cartasEmbaralhadas) {
            pilhaCartas.empilhar(carta);
        }
    }
    
    /**
     * Puxa uma carta do topo da pilha
     */
    public Carta puxarCarta() {
        Carta carta = pilhaCartas.desempilhar();
        
        // Se a pilha estiver vazia, embaralha novamente
        if (pilhaCartas.estaVazia()) {
            System.out.println("Baralho esgotado! Embaralhando novamente...");
            embaralhar();
        }
        
        return carta;
    }
    
    /**
     * Retorna o tamanho atual da pilha
     */
    public int getTamanhoPilha() {
        return pilhaCartas.getTamanho();
    }
}

