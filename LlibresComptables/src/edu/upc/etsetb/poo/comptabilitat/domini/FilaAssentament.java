package edu.upc.etsetb.poo.comptabilitat.domini;

/**
 * Gestiona la informacio d'una fila d'un assentament.
 * Una fila d'un assentament consisteix en el titol d'un compte i el seu
 * saldo o import.
 * @authors Pol Massana y Arnau Sola
 */
public class FilaAssentament {
    String compte;
    double importe;
    
    /**
     * Constructor.
     * @param compte
     * @param importe 
     */
    public FilaAssentament(String compte,double importe){
        this.compte = compte;
        this.importe = importe;
    }

    /**
     * getter
     * @return 
     */
    public String getCompte() {
        return compte;
    }

    /**
     * getter
     * @return 
     */
    public double getImporte() {
        return importe;
    }
    
}
