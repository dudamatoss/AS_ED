package modelos;

/**
 * Classe que representa uma carta de Sorte/Revés
 */
public class Carta {
    private String descricao;
    private TipoAcaoCarta tipoAcao;
    private double valor; // valor monetário se aplicável
    private String destinoEspecial; // ex: "PRISAO", "INICIO"
    
    public Carta(String descricao, TipoAcaoCarta tipoAcao, double valor) {
        this.descricao = descricao;
        this.tipoAcao = tipoAcao;
        this.valor = valor;
        this.destinoEspecial = null;
    }
    
    public Carta(String descricao, TipoAcaoCarta tipoAcao, double valor, String destinoEspecial) {
        this.descricao = descricao;
        this.tipoAcao = tipoAcao;
        this.valor = valor;
        this.destinoEspecial = destinoEspecial;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public TipoAcaoCarta getTipoAcao() {
        return tipoAcao;
    }
    
    public void setTipoAcao(TipoAcaoCarta tipoAcao) {
        this.tipoAcao = tipoAcao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public String getDestinoEspecial() {
        return destinoEspecial;
    }
    
    public void setDestinoEspecial(String destinoEspecial) {
        this.destinoEspecial = destinoEspecial;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}

