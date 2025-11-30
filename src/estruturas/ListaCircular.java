package estruturas;

import modelos.Casa;

/**
 * Implementação de Lista Ligada Circular para representar o tabuleiro
 */
public class ListaCircular {
    private NoCasa inicio;
    private NoCasa fim;
    private int tamanho;
    
    public ListaCircular() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }
    
    /**
     * Adiciona uma casa ao final da lista
     */
    public void adicionar(Casa casa) {
        NoCasa novoNo = new NoCasa(casa);
        
        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
            fim.setProximo(inicio); // torna circular
        } else {
            fim.setProximo(novoNo);
            fim = novoNo;
            fim.setProximo(inicio); // mantém circularidade
        }
        tamanho++;
    }
    
    /**
     * Retorna o nó inicial (casa de início)
     */
    public NoCasa getInicio() {
        return inicio;
    }
    
    /**
     * Retorna o nó final
     */
    public NoCasa getFim() {
        return fim;
    }
    
    /**
     * Retorna o tamanho da lista
     */
    public int getTamanho() {
        return tamanho;
    }
    
    /**
     * Avança um número específico de casas a partir de um nó
     */
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
    
    /**
     * Retrocede um número específico de casas a partir de um nó
     * Em uma lista circular, retroceder N casas é equivalente a avançar (tamanho - N) casas
     */
    public NoCasa retroceder(NoCasa atual, int casas) {
        if (atual == null) {
            return inicio;
        }
        
        // Em uma lista circular, retroceder N casas é o mesmo que avançar (tamanho - N) casas
        int casasParaAvancar = tamanho - (casas % tamanho);
        return avancar(atual, casasParaAvancar);
    }
    
    /**
     * Verifica se passou pelo início (completou uma volta)
     * Verifica se ao avançar de posicaoAnterior para posicaoAtual, passou pelo nó de início
     */
    public boolean passouPeloInicio(NoCasa posicaoAnterior, NoCasa posicaoAtual) {
        if (posicaoAnterior == null || posicaoAtual == null || inicio == null) {
            return false;
        }
        
        // Se a posição atual é o início, não passou (já está nele)
        if (posicaoAtual == inicio) {
            return false;
        }
        
        // Percorre da posição anterior até a atual
        NoCasa noAtual = posicaoAnterior;
        while (noAtual != posicaoAtual) {
            noAtual = noAtual.getProximo();
            // Se encontrou o início durante o percurso, passou por ele
            if (noAtual == inicio) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Encontra o nó que contém a casa de início
     */
    public NoCasa encontrarCasaInicio() {
        if (inicio == null) {
            return null;
        }
        
        NoCasa atual = inicio;
        do {
            if (atual.getCasa().getTipo() == modelos.TipoCasa.INICIO) {
                return atual;
            }
            atual = atual.getProximo();
        } while (atual != inicio);
        
        return inicio; // retorna início como padrão
    }
    
    /**
     * Encontra o nó que contém a casa de prisão
     */
    public NoCasa encontrarCasaPrisao() {
        if (inicio == null) {
            return null;
        }
        
        NoCasa atual = inicio;
        do {
            if (atual.getCasa().getTipo() == modelos.TipoCasa.PRISAO) {
                return atual;
            }
            atual = atual.getProximo();
        } while (atual != inicio);
        
        return null;
    }
    
    /**
     * Lista todas as casas do tabuleiro
     */
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

