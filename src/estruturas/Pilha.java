package estruturas;

import modelos.Carta;

/**
 * Implementação de Pilha (LIFO) para gerenciar as cartas de Sorte/Revés
 */
public class Pilha {
    private NoPilha topo;
    private int tamanho;
    
    /**
     * Classe interna que representa um nó da pilha
     */
    private static class NoPilha {
        private Carta carta;
        private NoPilha proximo;
        
        public NoPilha(Carta carta) {
            this.carta = carta;
            this.proximo = null;
        }
        
        public Carta getCarta() {
            return carta;
        }
        
        public NoPilha getProximo() {
            return proximo;
        }
        
        public void setProximo(NoPilha proximo) {
            this.proximo = proximo;
        }
    }
    
    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }
    
    /**
     * Adiciona uma carta no topo da pilha (push)
     */
    public void empilhar(Carta carta) {
        NoPilha novoNo = new NoPilha(carta);
        novoNo.setProximo(topo);
        topo = novoNo;
        tamanho++;
    }
    
    /**
     * Remove e retorna a carta do topo da pilha (pop)
     */
    public Carta desempilhar() {
        if (estaVazia()) {
            return null;
        }
        
        Carta carta = topo.getCarta();
        topo = topo.getProximo();
        tamanho--;
        return carta;
    }
    
    /**
     * Retorna a carta do topo sem removê-la (peek)
     */
    public Carta verTopo() {
        if (estaVazia()) {
            return null;
        }
        return topo.getCarta();
    }
    
    /**
     * Verifica se a pilha está vazia
     */
    public boolean estaVazia() {
        return topo == null;
    }
    
    /**
     * Retorna o tamanho da pilha
     */
    public int getTamanho() {
        return tamanho;
    }
    
    /**
     * Adiciona todas as cartas de um array na pilha
     */
    public void empilharTodas(Carta[] cartas) {
        for (Carta carta : cartas) {
            empilhar(carta);
        }
    }
}

