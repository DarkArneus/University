package edu.upc.etsetb.poo.comptabilitat.domini;

import java.util.LinkedList;
import java.util.List;

/**
 * Gestiona la informació relativa a un Assentament.
 * Un assentament té els atributs següents: un número (refMoviment) 
 * que és la referència de l'assentament. Dos llistes (debit i credit) 
 * formades cada una per files (FilaAssentament). Dos atributs totalDebit
 * i totalCredit amb el valor actual del que s'ha anotat a cada llista, 
 * respectivament. 
 * La data, el concepte de l'assentament, i un boolea (anotat) que indica
 * si l'assentament ja s'ha anotat al llibre major o encara no.
 * A més té un atribut static (numAssentaments) que indica quants Assentaments
 * s'han creat fins ara, que s'actualitza quan s'invoca el constructor.
 * @authors Pol Massana y Arnau Sola
 */
public class Assentament {
    private static int numAssentaments = 0;
    private int refMoviment;
    private List<FilaAssentament> debit;
    private List<FilaAssentament> credit;
    double totalCredit;
    double totalDebit;
    private String data;
    private String concepte;
    private boolean anotat; // true si l'assentament s'ha anotat al llibre major
    
    /**
     * Constructor. Inicialitza els atributs de l'assentament amb la data
     * i el concepte que rep com a paràmetres. La referència de l'assentament
     * (refMoviment) és el número d'assentaments que s'han creat fins
     * ara (numAssentaments), començant per 1. Inicialitza les llistes, i 
     * inicialitza els totals a zero. Anotat d'entrada és false.
     * @param data
     * @param concepte 
     */
    public Assentament(String data,String concepte){
        numAssentaments++;
        this.refMoviment = numAssentaments;
        this.debit = new LinkedList<>();
        this.credit = new LinkedList<>();
        this.totalCredit = 0;
        this.totalDebit = 0;
        this.data = data;
        this.concepte = concepte;
        this.anotat = false;
        
    }
    
    /**
     * Afegeix la fila que rep com a paràmetre a la llista del 
     * debit. Actualitza totalDebit amb l'import que indica la fila
     * @param fila 
     */
    public void afageixADebit(FilaAssentament fila){
        this.debit.add(fila);
        this.totalDebit += fila.importe;
    }

    /** 
     * Afegeix la fila que rep com a paràmetre a la llista del credit.
     * Actualitza totalCredit amb l'import que indica la fila.
     * @param fila 
     */
    public void afageixACredit(FilaAssentament fila){
        this.credit.add(fila);
        this.totalCredit += fila.importe;
    }

    /**
     * getter
     * @return 
     */
    public List<FilaAssentament> getDebit() {
        return debit;
    }

    /**
     * getter
     * @return 
     */
    public List<FilaAssentament> getCredit() {
         return credit;
    }

    /**
     * Getter. Retorna la referència de l'assentament (refMoviment).
     * @return 
     */
    public int getRefMoviment() {
        return refMoviment;
    }

    /**
     * getter boolea.
     * @return 
     */
    public boolean isAnotat() {
        return anotat;
    }

    /**
     * setter
     * @param anotat 
     */
    public void setAnotat(boolean anotat) {
        this.anotat = anotat;
    }

    /**
     * Retorna true si totalDebit == totalCredit.
     * @return 
     */
    public boolean verifica(){
        if (totalDebit == totalCredit){
            return true;
        }
        else{
           return false; 
        }
        
    }
    
    /**
     * Retorna una String amb la informació de l'assentament EL DONEM FET. 
     * La primera linia és la referència de l'assentament (refMoviment), la data
     * i el concepte. A continuació venen les files del dèbit amb l'import, 
     * una barra vertical i el titol del compte. Despres venen les files 
     * del credit amb el titol del compte, una barra vertical i l'import. 
     * De manera que a l'esquerra apareixen tots els imports del dèbit
     * i a la dreta apareixen tots els imports del crèdit. 
     * Fixa't com fem servir el metode String.format() per formatejar el text 
     * a l'estil del printf() de C.
     * @return String
     */
    @Override
    public String toString(){
        StringBuilder assentament = new StringBuilder();
        String linia = String.format("(%d)- %s %s\n",this.refMoviment,this.data,this.concepte);
        assentament.append(linia);
        for (FilaAssentament fila:this.debit){
            linia = String.format("%10.2f | %-60s |\n",fila.getImporte(),fila.getCompte());
            assentament.append(linia);
        }
        for (FilaAssentament fila:this.credit){
            linia = String.format("%10s | a   %-56s |%10.2f\n","",fila.getCompte(),fila.getImporte());
            assentament.append(linia);
        }
        return assentament.toString();
    }
    
    /**
     * getter
     * @return 
     */
    public static int getNumAssentaments() {
        return numAssentaments;
    }

    /**
     * getter
     * @return 
     */
    public double getTotalCredit() {
       return totalCredit;
    }

    /**
     * getter
     * @return 
     */
    public double getTotalDebit() {
       return totalDebit;
    }

    /**
     * getter
     * @return 
     */
    public String getData() {
       return data;
    }

    /**
     * getter
     * @return 
     */
    public String getConcepte() {
       return concepte;
    }

    /** 
     * Necessari per la verificació automàtica EL DONEM FET.
     */
    public static void resetNumAssentaments(){
        Assentament.numAssentaments=0;
    }
    
    /**
     * NOU: Retorna un String amb la informació de l'assentament en el format 
     * adequat per desar en un fitxer (EL DONEM FET).
     * @return 
     */
    public String desa(){ 
        StringBuilder assentament = new StringBuilder();
        String linia = String.format("%s\n%s\n",this.data,this.concepte);
        assentament.append(linia);
        assentament.append(this.debit.size()).append("\n");
        for (FilaAssentament fila:this.debit){
            assentament.append(fila.getCompte()).append("; ").append(fila.getImporte()).append("\n");
        }
        assentament.append(this.credit.size()).append("\n");
        for (FilaAssentament fila:this.credit){
            assentament.append(fila.getCompte()).append("; ").append(fila.getImporte()).append("\n");
        }
        return assentament.toString();
    }
}
