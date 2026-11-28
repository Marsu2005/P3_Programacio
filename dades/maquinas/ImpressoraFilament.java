package maquinas;
public class ImpressoraFilament extends Maquina {
    private static final int DURADA_MAXIMA = 24; // hores
    private static final double PREU_HORA = 1.5; // €/hora
    private static final double PREU_MATERIAL = 0.05; // €/gram   
    
    private double amplada;             // mm
    private double profunditat;         // mm
    private double altura;              // mm
    private double diametreFilament;    // mm

    /**
     * Constructor de la classe ImpressoraFilament
     * @param id codi únic de la impressora
     * @param model model de la impressora
     * @param lab laboratori on es troba la impressora
     * @param amplada amplada de la impressora en mm
     * @param profunditat profunditat de la impressora en mm
     * @param altura altura de la impressora en mm
     * @param diametreFilament diàmetre del filament en mm
     */
    public ImpressoraFilament(String id, String model, String lab, double amplada, double profunditat, double altura, double diametreFilament) {
        super(id, model, lab);
        this.amplada = amplada;
        this.profunditat = profunditat;
        this.altura = altura;
        this.diametreFilament = diametreFilament;
    }

    /**
     * Getters de la classe ImpressoraFilament
     * @return amplada, profunditat, altura i diàmetre del filament de la impressora
     */
    public double getAmplada() { return amplada; }
    public double getProfunditat() { return profunditat; }
    public double getAltura() { return altura; }
    public double getDiametreFilament() { return diametreFilament; }

    /**
     * Mètode per obtenir el volum de la impressora en mm³
     * @return volum de la impressora en mm³
     */
    public double getVolum() {
        return amplada * profunditat * altura;
    }

    /**
     * Mètode per calcular el cost de la impressora
     * @param hores nombre d'hores d'ús
     * @param quantitatMaterial quantitat de material utilitzat
     * @return cost de la impressora
     */
    public double calcularCost(double hores, double quantitatMaterial) {
        return (hores * PREU_HORA) + (quantitatMaterial * PREU_MATERIAL);
    }

    /**
     * Mètode per obtenir la durada màxima de la impressora
     * @return durada màxima de la impressora
     */
    public double getDuradaMaxima() {
        return DURADA_MAXIMA;
    }

    /**
     * Línia per al fitxer de text, atributs separats per ';'
     * @return informació de la impressora en format String
     */
    @Override
    public String toFitxer() {
        return super.toFitxer() + ";" + amplada + ";" + profunditat + ";" + altura + ";" + diametreFilament;
    }

    /*public void validarTreball(double hores, double quantitatMaterial) throws TreballNoValidException {
        if (hores <= 0 || hores > DURADA_MAXIMA) {
            throw new TreballNoValidException("El nombre d'hores ha d'estar entre 0 i " + DURADA_MAXIMA + " hores.");
        }
        if (quantitatMaterial <= 0) {
            throw new TreballNoValidException("La quantitat de material ha de ser positiva.");
        }
    } */

}