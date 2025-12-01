package estruturas;

import modelos.Casa;

// Classe que representa um nó da lista ligada circular
public class NoCasa {
    private Casa casa;
    private NoCasa proximo;
    
    public NoCasa(Casa casa) {
        this.casa = casa;
        this.proximo = null;
    }
    
    public Casa getCasa() {
        return casa;
    }
    
    public void setCasa(Casa casa) {
        this.casa = casa;
    }
    
    public NoCasa getProximo() {
        return proximo;
    }
    
    public void setProximo(NoCasa proximo) {
        this.proximo = proximo;
    }
}

