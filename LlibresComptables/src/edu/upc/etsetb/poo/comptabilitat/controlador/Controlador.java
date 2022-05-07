package edu.upc.etsetb.poo.comptabilitat.controlador;

import edu.upc.etsetb.poo.comptabilitat.domini.Assentament;
import edu.upc.etsetb.poo.comptabilitat.domini.ComptabilitatException;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.Compte;
import edu.upc.etsetb.poo.comptabilitat.domini.FilaAssentament;
import edu.upc.etsetb.poo.comptabilitat.domini.Informe;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.CompteActiu;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.CompteDespeses;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.CompteIngressos;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.ComptePassiu;
import edu.upc.etsetb.poo.comptabilitat.domini.comptes.ComptePatrimoni;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * El Controlador gestiona la informació del llibreMajor i el llibreDiari de la
 * comptabilitat. El llibre Major és un contenidor que guarda tots els comptes,
 * indexats pel seu titol. El llibre Diari és un contenidor que guarda tots els
 * Assentaments indexats per refMoviment.
 *
 * @authors Pol Massana y Arnau Sola
 */
public class Controlador {
    /** 
     * Llibre de comptes.
     */
    private Map<String, Compte> llibreMajor;
    /**
     * Llibre d'assentaments.
     */
    private Map<Integer, Assentament> llibreDiari;

    /**
     * Constructor. Inicialitza els dos contenidors.
     */
    private Informe informe;
    
    public Controlador() {
        this.llibreMajor = new HashMap<>();
        this.llibreDiari = new HashMap<>();
        this.informe = new Informe();
    }

    /**
     * Afegeix el compte titol al llibreMajor.
     *
     * @param titol
     * @param tipus
     */
    public void afegeixCompte(String titol, String tipus) throws ComptabilitatException {
        switch (tipus) {
            case "Actiu":
                {
                    CompteActiu a = new CompteActiu(titol);
                    llibreMajor.put(titol,a);
                    break;
                }
            case "Passiu":
                {
                    ComptePassiu a = new ComptePassiu(titol);
                    llibreMajor.put(titol,a);
                    break;
                }
            case "Patrimoni":
                {
                    ComptePatrimoni a = new ComptePatrimoni(titol);
                    llibreMajor.put(titol,a);
                    break;
                }
            case "Ingressos":
                {
                    CompteIngressos a = new CompteIngressos(titol);
                    llibreMajor.put(titol,a);
                    break;
                }
            case "Despeses":
                {
                    CompteDespeses a = new CompteDespeses(titol);
                    llibreMajor.put(titol,a);
                    break;
                }
            default:
                throw new ComptabilitatException("El tipus de compte "+tipus+" no existeix!");
        }
        
    }

    /**
     * Crea un nou assentament amb la data i el concepte que rep com a
     * parametres i l'afegeix al llibre Diari. Retorna la referència de
     * l'assentament (refMoviment).
     *
     * @param data
     * @param concepte
     * @return el número de l'assentament.
     */
    public int nouAssentament(String data, String concepte) {
        Assentament a = new Assentament(data, concepte);
        llibreDiari.put(a.getRefMoviment(),a);
        return a.getRefMoviment();
    }

    /**
     * Afegeix una FilaAssentament nova al debit de l'assentament
     * refMoviment. Si el compte del nou assentament no existeix en el llibre
     * major, l'afegeix. Després agafa l'assentament refMoviment del llibre
     * Diari i, si existeix, afegeix en el debit de l'assentament una nova fila
     * amb el compte i l'import que hem rebut com a paràmetres.
     *
     * @param refMoviment
     * @param compte
     * @param importe
     */
    public void afegeixADebit(int refMoviment, String compte, double importe) {
        FilaAssentament fa = new FilaAssentament(compte, importe);
        if (llibreMajor.containsKey(compte) == false){
            System.err.println("El compte "+compte+" no existeix");
        }
        if(llibreDiari.containsKey(refMoviment)){
            llibreDiari.get(refMoviment).afageixADebit(fa);
        }
    }

    /**
     * Afegeix una FilaAssentament nova al credit de l'assentament
     * refMoviment. Si el compte del nou assentament no existeix en el llibre
     * major, l'afegeix. Després agafa l'assentament refMoviment del llibre
     * Diari i, si existeix, afegeix en el credit de l'assentament una nova fila
     * (amb el compte i l'import que hem rebut com a paràmetres).
     *
     * @param refMoviment
     * @param compte
     * @param importe
     */
    public void afegeixACredit(int refMoviment, String compte, double importe) {
        FilaAssentament fa = new FilaAssentament(compte, importe);
        if (llibreMajor.containsKey(compte) == false){
            System.err.println("El compte "+compte+" no existeix");
        }
        if(llibreDiari.containsKey(refMoviment)){
            llibreDiari.get(refMoviment).afageixACredit(fa);
        }
    }

    /**
     * Retorna una String amb el text de l'assentament refMoviment. Si
     * l'assentament no existeix mostra el missatge "L'assentament
     * refMoviment no existeix"
     *
     * @param refMoviment
     * @return
     */
    public String getTextAssentament(int refMoviment) {
        String s = new String();
        if (llibreDiari.containsKey(refMoviment) == false){
            System.out.println("L'assentament "+refMoviment+" no existeix");
            return "";
        }
        else{
            s = llibreDiari.get(refMoviment).toString();
            return s;
        }
    }

    /**
     * Verifica que l'assentament refMoviment és correcte.
     *
     * @param refMoviment
     * @return
     */
    public boolean verificaAssentament(int refMoviment) {
        return llibreDiari.get(refMoviment).verifica();
    }

    /**
     * Elimina l'assentament refMoviment del llibre Diari.
     * Si l'assentament no existeix mostra el missatge
     * "Error en l'argument: no existeix cap assentament amb aquest
     * identificador."
     * Si l'assentament refMoviment ja s'ha anotat no l'esborra i mostra el
     * missatge "Em sap greu, aquest assentament no es pot eliminar. Ja s'ha
     * anotat al llibre Major." 
     *
     * @param refMoviment
     */
    public void eliminaAssentament(int refMoviment) throws ComptabilitatException {
        if(llibreDiari.containsKey(refMoviment) == true){
            if(llibreDiari.get(refMoviment).isAnotat() == true){
                throw new ComptabilitatException("Em sap greu, aquest assentament no es pot eliminar. Ja s'ha anotat al llibre Major.");
            }
            else{
                llibreDiari.remove(refMoviment);
            }
        }
        else{
            throw new ComptabilitatException("Error en l'argument: no existeix cap assentament amb identificador "+refMoviment+"!");
        }
    }

    /**
     * Retorna el text del compte titol. Agafa el compte titol del llibre Major
     * i retorna el seu text (toString()). Si no hi ha cap compte amb aquest
     * titol escriu el missatge: "El compte titol no existeix".
     *
     * @param titol
     * @return
     */
    public String getTextCompte(String titol) {
        if(llibreMajor.containsKey(titol) == true){
            String s = new String();
            s = llibreMajor.get(titol).toString();
            return s;
        }
        else{
            System.out.println("El compte "+titol+" no existeix");
        }
        return "";
    }

    /**
     * Elimina el compte titol del llibre Major.
     *
     * @param titol
     */
    public void eliminaCompte(String titol) {
        llibreMajor.remove(titol);
    }

    /**
     * Actualitza els comptes del llibre Major amb la informació que hi ha en
     * els assentaments del llibre diari. Si un assentament ha estat anotat
     * anteriorment no el repeteix. L'actualització s'ha de fer seguint els
     * passos següents: 1. Per cada assentament del llibre Diari 2. Si
     * l'assentament no s'ha anotat anteriorment. 3. Per cada fila del debit de
     * l'assentament. 3.1. Si el compte que indica la fila existeix en el llibre
     * major 3.2. Carrega el numero d'assentament i l'import en e compte. 4. Per
     * cada fila del credit de l'asentament 4.1. Si el compte que indica la fila
     * existeix en el llibre major. 4.2. Abona el numero d'assentament i
     * l'import en el compte.
     *
     * A més, el mètode ha de comptabilitzar l'import total que s'ha carregat i
     * l'import total que s'ha abonat, i comprovar que son iguals. En cas
     * contrari ha de mostrar el missatge: "Ull, totalCredit != totalDebit".
     */
    public void actualitzaComptes() throws ComptabilitatException {
        for(Assentament llibreDiari:llibreDiari.values()){
            if(llibreDiari.isAnotat() == false){
                for (FilaAssentament debit : llibreDiari.getDebit()) {
                    if(llibreMajor.containsKey(debit.getCompte()) == true){
                      llibreMajor.get(debit.getCompte()).carrega(llibreDiari.getRefMoviment(),debit.getImporte());
                    }
                }
                for (FilaAssentament credit : llibreDiari.getCredit()) {
                   if(llibreMajor.containsKey(credit.getCompte()) == true){
                      llibreMajor.get(credit.getCompte()).abona(llibreDiari.getRefMoviment(),credit.getImporte());
                    } 
                }
                llibreDiari.setAnotat(true);
            }
            if (llibreDiari.verifica()==false)
                throw new ComptabilitatException("Ull, totalCredit != totalDebit");
               
        }
    }
        

    /**
     * Retorna una String amb tota la informació del llibre Major EL DONEM FET.
     *
     * @return
     */
    public String llibreMajorToString() {
        StringBuilder text = new StringBuilder("");
        text.append("LLIBRE MAJOR\n");
        for (Compte c : this.llibreMajor.values()) {
            text.append(c).append("\n");
        }
        text.append("\n");
        return text.toString();
    }

    /**
     * Retorna una String amb tota la informació del llibre Diari EL DONEM FET.
     *
     * @return
     */
    public String llibreDiariToString() {
        StringBuilder text = new StringBuilder("");
        text.append("LLIBRE DIARI\n");
        String linia = String.format("%s|%62s|%10s\n", "Ref.  Debit", "", "Credit");
        text.append(linia);
        for (Assentament c : this.llibreDiari.values()) {
            text.append(c).append("\n");
        }
        text.append("\n");
        return text.toString();
    }


    /**
     * getter
     * @return 
     */
    public Map<String, Compte> getLlibreMajor() {
        return llibreMajor;
    }

    /**
     * getter
     * @return 
     */
    public Map<Integer, Assentament> getLlibreDiari() {
        return llibreDiari;
    }
       /**
     * NOU (S'utilitza només per fer proves)(EL DONEMM FET).
     * A la segona part aquest mètode ja no 
     * s'utilitza, però pot servir per fer proves mentre no tinguem fets 
     * els mètodes que llegeixen de fitxer.
     */
    public void carregaComptesHabituals() throws ComptabilitatException {
        this.afegeixCompte("Caixa","Actiu");
        this.afegeixCompte("Bancs","Actiu");
        this.afegeixCompte("Fiances","Actiu");
        this.afegeixCompte("Lloguers","Despeses");
        this.afegeixCompte("Material","Actiu");
        this.afegeixCompte("Clients","Actiu");
        this.afegeixCompte("Proveidors","Passiu");
        this.afegeixCompte("Bestretes","Actiu");
        this.afegeixCompte("Equipament","Actiu");
        this.afegeixCompte("Capital","Patrimoni");
        this.afegeixCompte("Creditors","Passiu");
        this.afegeixCompte("Ingressos","Ingressos");
        this.afegeixCompte("Despeses","Despeses");
    }

    /** NOU (EL DONEM FET): Llegeix un assentament de l'Scanner lector i l'afegeix al llibreDiari.
     * El format d'un assentament en el fitxer de prova es mostra a l'enunciat.
     * Aquest mètode ES DONA FET.
     * @param lector
     * @throws ComptabilitatException 
     */
    public void carregaAssentament(Scanner lector) throws ComptabilitatException {
        String data = lector.nextLine();
        String concepte = lector.nextLine();
        Assentament asst = new Assentament(data, concepte);
        int nDebit = Integer.parseInt(lector.nextLine());
        for (int i = 0; i < nDebit; i++) {
            String fila = lector.nextLine();
            String[] parts = fila.split("; ");
            asst.afageixADebit(new FilaAssentament(parts[0], Double.parseDouble(parts[1])));
        }
        int nCredit = Integer.parseInt(lector.nextLine());
        for (int i = 0; i < nCredit; i++) {
            String fila = lector.nextLine();
            String[] parts = fila.split(";");
            asst.afageixACredit(new FilaAssentament(parts[0], Double.parseDouble(parts[1])));
        }
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);
        lector.nextLine();
    }
        public void carregaAssentamentsDeProva() throws ComptabilitatException {
        Assentament asst = new Assentament("1/2/2021", "Aportació de capital.");
        asst.afageixADebit(new FilaAssentament("Bancs", 5000));
        asst.afageixACredit(new FilaAssentament("Capital", 5000));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("3/2/2021", "Pagament lloguer i fiança.");
        asst.afageixADebit(new FilaAssentament("Fiances", 500));
        asst.afageixADebit(new FilaAssentament("Lloguers", 200));
        asst.afageixACredit(new FilaAssentament("Bancs", 700));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("5/2/2021", "Materia d'oficina a crèdit.");
        asst.afageixADebit(new FilaAssentament("Material", 900));
        asst.afageixACredit(new FilaAssentament("Proveidors", 900));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("7/2/2021", "Serveis prestats, parcialment a compte.");
        asst.afageixADebit(new FilaAssentament("Bancs", 1000));
        asst.afageixADebit(new FilaAssentament("Clients", 1500));
        asst.afageixACredit(new FilaAssentament("Ingressos", 2500));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("9/2/2021", "Bestreta a un treballador.");
        asst.afageixADebit(new FilaAssentament("Bestretes", 300));
        asst.afageixACredit(new FilaAssentament("Bancs", 300));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("11/2/2021", "Compra d'equipament de telefonia.");
        asst.afageixADebit(new FilaAssentament("Equipament", 1200));
        asst.afageixACredit(new FilaAssentament("Bancs", 1200));
        if (!asst.verifica()){ 
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("13/2/2021", "Pagament al proveidor de material.");
        asst.afageixADebit(new FilaAssentament("Proveidors", 900));
        asst.afageixACredit(new FilaAssentament("Bancs", 900));
        if (!asst.verifica()) {
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("15/2/2021", "Cobrament del client.");
        asst.afageixADebit(new FilaAssentament("Bancs", 1500));
        asst.afageixACredit(new FilaAssentament("Clients", 1500));
        if (!asst.verifica()) {
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);

        asst = new Assentament("17/2/2021", "Compra de material a credit.");
        asst.afageixADebit(new FilaAssentament("Material", 750));
        asst.afageixACredit(new FilaAssentament("Proveidors", 750));
        if (!asst.verifica()) {
            throw new ComptabilitatException("L'assentament no quadra!");
        }
        this.llibreDiari.put(asst.getRefMoviment(), asst);
    }
    public String tancaComptabilitat(){
      this.informe.calculaInforme(this.llibreMajor);
      return this.informe.toString();
    }
}
