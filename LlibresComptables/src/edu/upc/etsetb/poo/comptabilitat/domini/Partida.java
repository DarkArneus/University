/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.domini;

import edu.upc.etsetb.poo.comptabilitat.interficie.InterficieUsuari;
import java.util.List;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class Partida {
    
    private static List<String> cataleg = InterficieUsuari.carregaCataleg();
    private double saldo;
    private String titolCompte;
       
    public Partida(String titolCompte, double saldo){
        this.titolCompte = titolCompte;
        this.saldo = saldo;
    }
    /**
     * Retorna un String amb la informació de la partida (EL DONEM FET).
     * @return 
     */
    @Override
    public String toString() {
        return String.format("   %-20s\t%10.2f",titolCompte,saldo);
    }

    public double getSaldo() {
        return saldo;
    }
    
    public int compareTo(Partida other){
        if(this.cataleg.indexOf(this.titolCompte)<this.cataleg.indexOf(other.titolCompte))
            return -1;
        
        else if(this.cataleg.indexOf(this.titolCompte)>this.cataleg.indexOf(other.titolCompte))
            return 1;
        
        return 0;
    }
}
