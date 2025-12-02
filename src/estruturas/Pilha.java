package estruturas;

import models.Carta;

// Pilha LIFO (Last-In, First-Out) para gerenciar cartas de Sorte/Revés
// Última carta empilhada é a primeira a ser retirada
public class Pilha {
    private NoPilha topo;
    private int tamanho;
    
    // Nó interno da pilha
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
    
    // Adiciona carta no topo da pilha (push)
    public void empilhar(Carta carta) {
        NoPilha novoNo = new NoPilha(carta);
        novoNo.setProximo(topo);
        topo = novoNo;
        tamanho++;
    }
    
    // Remove e retorna carta do topo (pop)
    public Carta desempilhar() {
        if (estaVazia()) {
            return null;
        }
        
        Carta carta = topo.getCarta();
        topo = topo.getProximo();
        tamanho--;
        return carta;
    }
    
    // Retorna carta do topo sem remover (peek)
    public Carta verTopo() {
        if (estaVazia()) {
            return null;
        }
        return topo.getCarta();
    }
    
    // Verifica se a pilha está vazia
    public boolean estaVazia() {
        return topo == null;
    }
    
    // Retorna o tamanho da pilha
    public int getTamanho() {
        return tamanho;
    }
    
    // Adiciona todas as cartas de um array na pilha
    public void empilharTodas(Carta[] cartas) {
        for (Carta carta : cartas) {
            empilhar(carta);
        }
    }
}

