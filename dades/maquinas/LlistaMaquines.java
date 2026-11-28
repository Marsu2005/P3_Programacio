package maquinas;
public class LlistaMaquines {
    private Maquina[] llista;
    private int numMaquines;

    /**
     * Constructor de la classe LlistaMaquines
     * @param capacitat capacitat de la llista
     */
    public LlistaMaquines(int capacitat) {
        llista = new Maquina[capacitat];
        numMaquines = 0;
    }

    /**
     * Afegeix una màquina a la llista
     * @param maquina màquina a afegir
     */
    public void afegirMaquina(Maquina maquina) {
        if (numMaquines < llista.length) {
            llista[numMaquines] = maquina;
            numMaquines++;
        } else {
            System.out.println("No es pot afegir més màquines. La llista està plena.");
        }
    }
}