package modelos;

/**
 * Classe que representa uma casa no tabuleiro
 */
public class Casa {
    private TipoCasa tipo;
    private Imovel imovel; // null se não for um imóvel
    private String nome;
    
    public Casa(TipoCasa tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
        this.imovel = null;
    }
    
    public Casa(TipoCasa tipo, String nome, Imovel imovel) {
        this.tipo = tipo;
        this.nome = nome;
        this.imovel = imovel;
    }
    
    public TipoCasa getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoCasa tipo) {
        this.tipo = tipo;
    }
    
    public Imovel getImovel() {
        return imovel;
    }
    
    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        if (imovel != null) {
            return nome + " (" + tipo + ") - Valor: R$ " + imovel.getValorCompra();
        }
        return nome + " (" + tipo + ")";
    }
}

