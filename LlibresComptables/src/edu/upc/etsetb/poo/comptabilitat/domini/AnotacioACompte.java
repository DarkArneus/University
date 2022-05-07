package edu.upc.etsetb.poo.comptabilitat.domini;

/**
 * Guarda la informació corresponent a una Anotacio en un compte.
 * L'anotació consisteix en un enter (refMoviment), 
 * que és el número de l'operació, i un double que és la quantitat 
 * anotada al compte.
 * @authors Pol Massana y Arnau Sola
 */
public class AnotacioACompte {
    private final int refMoviment;
    private final double quantitat;
    
    /** Constructor.
     * Incicialitza la referència de l'operació i la quantitat anotada
     * al compte.
     * @param ref
     * @param quantitat 
     */
    public AnotacioACompte(int ref,double quantitat){
        this.refMoviment = ref;
        this.quantitat = quantitat;
    }

    /**
     * Getter. Retorna la referencia de l'operacio.
     * @return 
     */
    public int getRefMoviment() {
        return refMoviment;
    }

    /**
     * Getter. Retorna la quantitat.
     * @return 
     */
    public double getQuantitat() {
        return quantitat;
    }

    
}
