package edu.upc.etsetb.poo.comptabilitat.domini.comptes;

import edu.upc.etsetb.poo.comptabilitat.domini.AnotacioACompte;
import edu.upc.etsetb.poo.comptabilitat.domini.Informe;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Gestiona la informació relativa a un Compte.
 * En un compte hi desem dos llistes d'anotacions (AnotacioACompte): el
 * debit i el crèdit. A més, el compte té un títol i dos atributs més,
 * totalDebit i totalCredit que memoritzen l'import total del que
 * s'ha carregat al debit i el que s'ha abonat al credit, respectivament.
 * @authors Pol Massana y Arnau Sola
 */
public abstract class Compte extends Object{
    protected String titol;
    protected double totalDebit;
    protected double totalCredit;
    protected List<AnotacioACompte> debit;
    protected List<AnotacioACompte> credit;

    /**
     * Constructor.
     * Inicialitza tots els atributs (el titol el rep com a paràmetre).
     * @param titol 
     */
    public Compte(String titol){
        this.titol = titol;
        this.totalDebit = 0;
        this.totalCredit = 0;
        this.debit = new LinkedList<>();
        this.credit = new LinkedList<>();
    }
    
    /**
     * Getter.
     * @return 
     */
    public String getTitol() {
        return titol;
    }
    
    /**
     * Retorna totalDebit - totalCredit
     * @return 
     */
    public double getSaldoDeutor() {
        return totalDebit - totalCredit;
    }

    /**
     * Retorna totalCredit - totalDebit
     * @return 
     */
    public double getSaldoCreditor() {
        return totalCredit - totalDebit;
    }
    
    /** 
     * Crea una AnotacioACompte amb la referència i la quantitat que
     * rep com a paràmetres, i l'afegeix al debit del compte.
     * Actualitza totalDebit.
     * @param refMoviment
     * @param quantitat 
     */
    public void carrega(int refMoviment,double quantitat){
        AnotacioACompte a = new AnotacioACompte(refMoviment, quantitat);
        debit.add(a);
        totalDebit += quantitat;
    }
    
    /**
     * Crea una AnotacioACompte amb la referència i la quantitat que
     * rep com a paràmetres, i l'afegeix al credit del compte.
     * Actualitza totalCredit.
     * @param refMoviment
     * @param quantitat 
     */
    public void abona(int refMoviment,double quantitat){
        AnotacioACompte c = new AnotacioACompte(refMoviment, quantitat);
        credit.add(c);
        totalCredit += quantitat;
    }
    
    /**
     * Retorna una String amb totes les anotacions de debit i credit a 
     * 2 columnes EL DONEM FET. Mostra el titol del compte, una linia horitzontal,
     * les anotacions del dèbit i el crèdit separades per una barra vertical,
     * una altra línia horitzontal i, al final, si el saldoDeutor es positiu,
     * el posa sota la columna de l'esquerra. En cas contrari, posa el saldo
     * creditor sota la columna de la dreta.
     * Fixa't en el metode String.format() per formatejar
     * el text, a l'estil del printf() de C.
     * @return String. 
     */
    @Override
    public String toString(){
        StringBuilder cadena = new StringBuilder();
        Iterator<AnotacioACompte> debits = this.debit.iterator();
        Iterator<AnotacioACompte> credits = this.credit.iterator();
        cadena.append(this.titol).append("\n");
        cadena.append("-----------------------------------\n");
        while (debits.hasNext()&&credits.hasNext()){
            AnotacioACompte debit = debits.next();
            AnotacioACompte credit = credits.next();
            String linia = String.format("(%3d) %10.2f | %10.2f (%3d)\n",
                 debit.getRefMoviment(),debit.getQuantitat(),
                 credit.getQuantitat(),credit.getRefMoviment());
            cadena.append(linia);
        }
        while (debits.hasNext()){
            AnotacioACompte debit = debits.next();
            String linia = String.format("(%3d) %10.2f |\n",
                 debit.getRefMoviment(),debit.getQuantitat());
            cadena.append(linia);
        }
        while (credits.hasNext()){
            AnotacioACompte credit = credits.next();
            String linia = String.format("%17s| %10.2f (%3d)\n","",
                  credit.getQuantitat(),credit.getRefMoviment());
            cadena.append(linia);
        }
        cadena.append("-----------------------------------\n");
        if (this.getSaldoDeutor()>0){
            String linia = String.format("      %10.2f |\n",
                 this.getSaldoDeutor());
            cadena.append(linia);            
        }
        else {
            String linia = String.format("%17s| %10.2f \n","",
                  this.getSaldoCreditor());
            cadena.append(linia);
        }
        cadena.append("\n");
        return cadena.toString();
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
    public double getTotalCredit() {
        return totalCredit;
    }

    /**
     * getter
     * @return 
     */
    public List<AnotacioACompte> getDebit() {
        return debit;
    }

    /**
     * getter
     * @return 
     */
    public List<AnotacioACompte> getCredit() {
        return credit;
    }
    
    public boolean teAnotacions(){
        if(debit.isEmpty() && credit.isEmpty())
            return false;
        
        return true;
    }
    
    public abstract void salda(Informe inf);
}
