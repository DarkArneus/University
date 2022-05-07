/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.domini.comptes;

import edu.upc.etsetb.poo.comptabilitat.domini.Informe;
import edu.upc.etsetb.poo.comptabilitat.domini.Partida;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class ComptePatrimoni extends Compte{
    
    public ComptePatrimoni(String titol){
        super(titol);
    }
    
    @Override
    public void salda(Informe inf){
        double saldo = this.getSaldoCreditor();
        this.carrega(0,saldo);
        Partida partida = new Partida(this.titol,saldo);
        inf.getBalanc().getPatrimoni().add(partida);
    }
}
