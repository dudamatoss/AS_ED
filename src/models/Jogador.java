package models;

import estruturas.NoCasa;
import java.util.ArrayList;
import java.util.List;

// Classe que representa um jogador no jogo
public class Jogador {
    private int id;
    private String nome;
    private double saldo;
    private NoCasa posicaoAtual;
    private List<Imovel> propriedades;
    private boolean falido;
    private int rodadasNaPrisao;
    private boolean preso;
    
    public Jogador(int id, String nome, double saldoInicial) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldoInicial;
        this.propriedades = new ArrayList<>();
        this.falido = false;
        this.rodadasNaPrisao = 0;
        this.preso = false;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
        if (this.saldo < 0) {
            this.saldo = 0;
        }
    }
    
    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }
    
    public void subtrairSaldo(double valor) {
        this.saldo -= valor;
        if (this.saldo < 0) {
            this.saldo = 0;
        }
    }
    
    public NoCasa getPosicaoAtual() {
        return posicaoAtual;
    }
    
    public void setPosicaoAtual(NoCasa posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }
    
    public List<Imovel> getPropriedades() {
        return propriedades;
    }
    
    public void adicionarPropriedade(Imovel imovel) {
        if (!propriedades.contains(imovel)) {
            propriedades.add(imovel);
            imovel.setDono(this);
        }
    }
    
    public void removerPropriedade(Imovel imovel) {
        propriedades.remove(imovel);
        imovel.setDono(null);
    }
    
    public double calcularPatrimonio() {
        double valorImoveis = propriedades.stream()
            .mapToDouble(Imovel::getValorCompra)
            .sum();
        return saldo + valorImoveis;
    }
    
    public boolean isFalido() {
        return falido;
    }
    
    public void setFalido(boolean falido) {
        this.falido = falido;
    }
    
    public int getRodadasNaPrisao() {
        return rodadasNaPrisao;
    }
    
    public void setRodadasNaPrisao(int rodadasNaPrisao) {
        this.rodadasNaPrisao = rodadasNaPrisao;
    }
    
    public void incrementarRodadasNaPrisao() {
        this.rodadasNaPrisao++;
    }
    
    public boolean isPreso() {
        return preso;
    }
    
    public void setPreso(boolean preso) {
        this.preso = preso;
    }
    
    @Override
    public String toString() {
        return "Jogador: " + nome + " | Saldo: R$ " + String.format("%.2f", saldo) + 
               " | Propriedades: " + propriedades.size() + 
               " | Patrimônio: R$ " + String.format("%.2f", calcularPatrimonio());
    }
}

