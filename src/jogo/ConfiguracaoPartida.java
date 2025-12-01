package jogo;

// Classe que armazena as configurações da partida
public class ConfiguracaoPartida {
    private double saldoInicial;
    private double salario;
    private int maxRodadas;
    
    public ConfiguracaoPartida() {
        // Valores padrão
        this.saldoInicial = 1500.0;
        this.salario = 200.0;
        this.maxRodadas = 50;
    }
    
    public double getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    
    public double getSalario() {
        return salario;
    }
    
    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    public int getMaxRodadas() {
        return maxRodadas;
    }
    
    public void setMaxRodadas(int maxRodadas) {
        this.maxRodadas = maxRodadas;
    }
}

