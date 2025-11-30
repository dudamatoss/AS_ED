import interface_jogo.MenuPrincipal;

/**
 * Classe principal do jogo Banco Imobiliário
 * 
 * @author Sistema
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   JOGO DE TABULEIRO ESTRATÉGICO");
        System.out.println("   Banco Imobiliário - Versão Java");
        System.out.println("========================================");
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibirMenu();
    }
}
