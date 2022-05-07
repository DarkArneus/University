/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.domini.comptes;

import edu.upc.etsetb.poo.comptabilitat.domini.Informe;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class CompteIngressos extends Compte{
    
    public CompteIngressos(String titol){
        super(titol);
    }
    
    @Override
    public void salda(Informe inf){
        double saldo = this.getSaldoCreditor();
        this.carrega(0,saldo);
        inf.getResultats().abona(0,saldo);
    }
}
