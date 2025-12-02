package models;

// Classe que representa um imóvel no jogo
public class Imovel {
    private int id;
    private String nome;
    private double valorCompra;
    private double valorAluguel;
    private Jogador dono;
    
    public Imovel(int id, String nome, double valorCompra, double valorAluguel) {
        this.id = id;
        this.nome = nome;
        this.valorCompra = valorCompra;
        this.valorAluguel = valorAluguel;
        this.dono = null;
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
    
    public double getValorCompra() {
        return valorCompra;
    }
    
    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }
    
    public double getValorAluguel() {
        return valorAluguel;
    }
    
    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }
    
    public Jogador getDono() {
        return dono;
    }
    
    public void setDono(Jogador dono) {
        this.dono = dono;
    }
    
    public boolean temDono() {
        return dono != null;
    }
    
    @Override
    public String toString() {
        String donoStr = dono != null ? " - Dono: " + dono.getNome() : " - Disponível";
        return "ID: " + id + " | " + nome + " | Compra: R$ " + valorCompra + 
               " | Aluguel: R$ " + valorAluguel + donoStr;
    }
}

