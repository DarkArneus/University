/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.domini;

import edu.upc.etsetb.poo.comptabilitat.domini.comptes.Compte;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.ComptePatrimoni;
import java.util.Map;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class Informe {
    
    private Balanc balanc;
    private double resultat;
    private Compte resultats;
    
    public Informe(){
        this.resultats = new ComptePatrimoni("Resultats");
        this.balanc = new Balanc();
    }
     /**
     * Retorna un String amb la informació de l'informe (ES DONA FET). Vegeu l'enunciat.
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder text = new StringBuilder();
        text.append("RESULTAT:\n   ").append(String.format("%.2f",this.resultat));
        if (this.resultat>=0){
            text.append(" (beneficis)\n");
        }
        else {
            text.append(" (pèrdues)\n");
        }
        text.append("\nBALANÇ:\n");
        text.append(this.balanc.toString());
        return text.toString();
    }
    
    public void calculaInforme(Map<String,Compte> llibreMajor){
        for(Compte c: llibreMajor.values()){
            if(!c.getDebit().isEmpty() && !c.getCredit().isEmpty())
                c.salda(this);
            
        }
        this.resultat = this.resultats.getSaldoCreditor();
        this.resultats.salda(this);
    }

    public double getResultat() {
        return resultat;
    }

    public Balanc getBalanc() {
        return balanc;
    }

    public Compte getResultats() {
        return resultats;
    }
    
    
}
