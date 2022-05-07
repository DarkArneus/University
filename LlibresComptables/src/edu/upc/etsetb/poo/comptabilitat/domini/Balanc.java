/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.domini;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class Balanc {
   
    private List<Partida> actiu;
    private List<Partida> passiu;
    private List<Partida> patrimoni;
    private double totalActiu;
    private double totalPassiu;
    private double totalPatrimoni;
    
    public Balanc(){
        this.actiu = new LinkedList<>();
        this.passiu = new LinkedList<>();
        this.patrimoni = new LinkedList<>();
        totalActiu = 0;
        totalPassiu = 0;
        totalPatrimoni = 0;
    }
    
    /**
     * Retorna un String amb la informació del balanç (ES DONA FET). El format es mostra
     * a l'enunciat.
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder text = new StringBuilder();
        text.append("Actiu:\n");
        Collections.sort(actiu);
        for (Partida p:this.actiu){
            text.append(p).append("\n");
        }
        text.append(String.format("%-24s%10.2f\n\n","Total actiu =",this.totalActiu));
        text.append("Patrimoni:\n");
        Collections.sort(patrimoni);
        for (Partida p:this.patrimoni){
            text.append(p).append("\n");
        }
        text.append(String.format("%-24s%10.2f\n\n","Total patrimoni =",this.totalPatrimoni));
        text.append("Passiu:\n");
        Collections.sort(passiu);
        for (Partida p:this.passiu){
            text.append(p).append("\n");
        }
        text.append(String.format("%-24s%10.2f\n","Total passiu =",this.totalPassiu));
        text.append(String.format("%-24s%10.2f\n\n","Passiu + Patrimoni =",this.totalPassiu+this.totalPatrimoni));
        return text.toString();
    }

    public List<Partida> getActiu() {
        return actiu;
    }

    public List<Partida> getPassiu() {
        return passiu;
    }

    public List<Partida> getPatrimoni() {
        return patrimoni;
    }

    public double getTotalActiu() {
        return totalActiu;
    }

    public double getTotalPassiu() {
        return totalPassiu;
    }

    public double getTotalPatrimoni() {
        return totalPatrimoni;
    }
    public void afegeixActiu(Partida p){
        actiu.add(p);
        totalActiu += p.getSaldo();
    }
    public void afegeixPassiu(Partida p){
        passiu.add(p);
        totalPassiu += p.getSaldo();
    }
    public void afegeixPatrimoni(Partida p){
        patrimoni.add(p);
        totalPatrimoni += p.getSaldo();
    }
    
}
