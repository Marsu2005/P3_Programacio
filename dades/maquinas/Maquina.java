package maquinas;
public abstract class Maquina {
    private String id;
    private String model;
    private String lab;

    /**
     * Constructor de la classe Maquina
     * @param id codi únic de la màquina
     * @param model model de la màquina
     * @param lab laboratir on es troba la màquina
     */
    public Maquina(String id, String model, String lab) {
        this.id = id;
        this.model = model;
        this.lab = lab;
    }

    /**
     * Getters de la classe Maquina
     * @return id, model i lab de la màquina
     */
    public String getId() { return id; }
    public String getModel() { return model; }
    public String getLab() { return lab; }


    /**
     * Mètode abstracte per calcular el cost de la màquina
     * @param hores hores d'ús de la màquina
     * @param quantitatMaterial quantitat de material utilitzat
     * @return cost total de la màquina
     */
    public abstract double calcularCost(double hores, double quantitatMaterial);


    /**
     * Mètode abstracte per obtenir la durada màxima de la màquina
     * @return durada màxima de la màquina
     */
    public abstract double getDuradaMaxima();

    @Override
    /**
     * Mètode toString per mostrar la informació de la màquina
     * @return informació de la màquina en format String
     */
    public String toString() {
        return "Maquina{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", lab='" + lab + '\'' +
                '}';
    }


    /**
     * Mètode per obtenir una línia de text amb la informació de la màquina per guardar en un fitxer
     * @return informació de la màquina en format String per fitxer
     */
    public String toFitxer() {
        return id + "," + model + "," + lab;
    }
    // He pensado que estaría bien una función que te devuelva una línia para ponerla directamente en el fichero

    // He pensado que podría ser útil tener un método para validar el trabajo antes de calcular el costo. Por ahora, lo dejo comentado.
    // public abstract void validarTreball(double hores, double quantitatMaterial) throws TreballNoValidException;
}
