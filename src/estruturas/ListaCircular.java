package estruturas;

import models.Casa;

// Lista Ligada Circular para representar o tabuleiro
// Cada nó aponta para o próximo, formando um ciclo infinito
public class ListaCircular {
    private NoCasa inicio;
    private NoCasa fim;
    private int tamanho;
    
    public ListaCircular() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }
    
    // Adiciona uma casa ao final da lista (antes do início)
    public void adicionar(Casa casa) {
        NoCasa novoNo = new NoCasa(casa);
        
        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
            fim.setProximo(inicio);
        } else {
            fim.setProximo(novoNo);
            fim = novoNo;
            fim.setProximo(inicio);
        }
        tamanho++;
    }

    // Retorna o nó inicial
    public NoCasa getInicio() {
        return inicio;
    }
    
    // Retorna o nó final
    public NoCasa getFim() {
        return fim;
    }
    
    // Retorna o tamanho da lista
    public int getTamanho() {
        return tamanho;
    }
    
    // Avança N casas a partir da posição atual (retorna ao início automaticamente)
    public NoCasa avancar(NoCasa atual, int casas) {
        if (atual == null) {
            return inicio;
        }
        
        NoCasa noAtual = atual;
        for (int i = 0; i < casas; i++) {
            noAtual = noAtual.getProximo();
        }
        return noAtual;
    }
    
    // Retrocede N casas (equivalente a avançar tamanho - N)
    public NoCasa retroceder(NoCasa atual, int casas) {
        if (atual == null) {
            return inicio;
        }
        int casasParaAvancar = tamanho - (casas % tamanho);
        return avancar(atual, casasParaAvancar);
    }
    
    // Verifica se passou pelo início ao avançar de posicaoAnterior para posicaoAtual
    public boolean passouPeloInicio(NoCasa posicaoAnterior, NoCasa posicaoAtual) {
        if (posicaoAnterior == null || posicaoAtual == null || inicio == null) {
            return false;
        }
        if (posicaoAtual == inicio) {
            return false;
        }
        NoCasa noAtual = posicaoAnterior;
        while (noAtual != posicaoAtual) {
            noAtual = noAtual.getProximo();
            if (noAtual == inicio) {
                return true;
            }
        }
        return false;
    }
    
    // Encontra o nó que contém a casa de início
    public NoCasa encontrarCasaInicio() {
        if (inicio == null) {
            return null;
        }
        
        NoCasa atual = inicio;
        do {
            if (atual.getCasa().getTipo() == models.TipoCasa.INICIO) {
                return atual;
            }
            atual = atual.getProximo();
        } while (atual != inicio);
        
        return inicio; // retorna início como padrão
    }
    
    // Encontra o nó que contém a casa de prisão
    public NoCasa encontrarCasaPrisao() {
        if (inicio == null) {
            return null;
        }
        
        NoCasa atual = inicio;
        do {
            if (atual.getCasa().getTipo() == models.TipoCasa.PRISAO) {
                return atual;
            }
            atual = atual.getProximo();
        } while (atual != inicio);
        
        return null;
    }
    
    // Lista todas as casas do tabuleiro
    public void listarCasas() {
        if (inicio == null) {
            System.out.println("Tabuleiro vazio!");
            return;
        }
        
        NoCasa atual = inicio;
        int contador = 1;
        do {
            System.out.println(contador + ". " + atual.getCasa().toString());
            atual = atual.getProximo();
            contador++;
        } while (atual != inicio);
    }
}

