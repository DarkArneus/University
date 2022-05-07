/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.poo.comptabilitat.interficie;

import edu.upc.etsetb.poo.comptabilitat.controlador.Controlador;
import edu.upc.etsetb.poo.comptabilitat.domini.Assentament;
import edu.upc.etsetb.poo.comptabilitat.domini.ComptabilitatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Pol Massana y Arnau Sola
 */
public class InterficieUsuari {
    
    private Controlador contr;
    private Scanner teclat;
    
    public InterficieUsuari(){
        this.contr = new Controlador();
        this.teclat = new Scanner(System.in);
    }
    
    public static void main(String[] args) throws ComptabilitatException{
        InterficieUsuari iu = new InterficieUsuari();
        iu.start();
    }
    private void actualitzaComptes() throws ComptabilitatException{
        System.out.println("Actualitza comptes...:");
        contr.actualitzaComptes();
    }
    public static List<String> carregaCataleg(){
        String s = "data/catalegPartides.txt";
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(s);
        }catch(FileNotFoundException ex){
            System.err.println(ex);
            exit(1);
        }
        Scanner entrada = new Scanner(fis);
        String linia = "";
        List<String> cat = new ArrayList<>();
        while(entrada.hasNext()){
            linia = entrada.nextLine();
            String[] partides = linia.split(", ");
            cat.add(partides[0]);
        }
        entrada.close();
        return cat;
    }
    private void carregaComptabilitat() throws ComptabilitatException{
        this.carregaComptesDelCataleg();
        this.carregaLlibreDiari();
    }
    private void carregaComptesDelCataleg()throws ComptabilitatException{
        String s = "data/catalegPartides.txt";
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(s);
        }catch(FileNotFoundException ex){
            System.err.println(ex);
            exit(1);
        }
        Scanner entrada = new Scanner(fis);
        String linia = "";
        List<String> cat = new ArrayList<>();
        while(entrada.hasNext()){
            linia = entrada.nextLine();
            String[] partides = linia.split(", ");
            contr.afegeixCompte(partides[0], partides[1]);
        }
        entrada.close();
    }
    private void carregaLlibreDiari()throws ComptabilitatException{
        String s = "data/llibreDiari.txt";
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(s);
        }catch(FileNotFoundException ex){
            System.err.println(ex);
            exit(1);
        }
        Scanner entrada = new Scanner(fis);
        while(entrada.hasNext()){
            this.contr.carregaAssentament(entrada);
        }
        entrada.close();
    }
    private void desaComptabilitat(){
        String s = "data/llibreDiari.txt";
        FileOutputStream fis = null;
        try{
            fis = new FileOutputStream(s);
        }catch(FileNotFoundException ex){
            System.err.println(ex);
            exit(1);
        }
        PrintStream sortida = new PrintStream(fis);
        for(Assentament a:contr.getLlibreDiari().values())
            a.desa();
        
        sortida.close();
    }
    private void eliminaAssentament()throws ComptabilitatException{
        System.out.println("Elimina assentament...:");
        System.out.println("Num assentament?");
        int n = teclat.nextInt();
        teclat.skip("\n");
        if(contr.getLlibreDiari().containsKey(n)){
            if(!contr.getLlibreDiari().get(n).isAnotat()){
                contr.eliminaAssentament(n);
            }
            else{
                throw new ComptabilitatException(" Em sap greu, aquest assentament no es pot eliminar.Ja s'ha anotat al llibre Major");
            }
        }
        else{
            throw new ComptabilitatException("no existeix cap assentament indentificador"+n+"!");
        }
    }
        /**
     * (EL DONEM FET) Aquest mètode és necessari per fer la prova automàtica.
     * @param nomFitxer 
     */
    public void entradaDesDeFitxer(String nomFitxer){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(nomFitxer);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            exit(1);
        }
        teclat = new Scanner(fis); 
    }
    private void executaComanda(String opcio)throws ComptabilitatException{
        switch (opcio){
           
            case "aj":
                this.mostraOpcions();
                break;
            
            case "na":
                this.nouAssentament();
                break;
            
            case "ma":
                this.mostraAssentament();
                break;
                
            case "ea":
                this.eliminaAssentament();
                break;
           
            case "md":
                this.mostraLlibreDiari();
                break;
                
            case "ac":
                this.actualitzaComptes();
                break;
                
            case "mc":
                this.mostraCompte();
                break;
                
            case "mm":
                this.mostraLlibreMajor();
                break;    
                
            case "tc":
                this.tancaComptabilitat();
                break;    
                
            default:
                throw new ComptabilitatException("Comanda errònia");
      }  
    }
    private String mostraAssentament(){
        System.out.println("Mostra assentament...:");
        System.out.println("Num assentament?");
        int n = teclat.nextInt();
        teclat.skip("\n");
        if(contr.getLlibreDiari().containsKey(n)){
            System.out.println(contr.getTextAssentament(n));
        }
        return contr.getTextAssentament(n);
    }
    private String mostraCompte()throws ComptabilitatException{
        System.out.println("Mostra compte...:");
        System.out.println("Titol?");
        String c = teclat.nextLine();
        if(contr.getLlibreMajor().containsKey(c)){
            System.out.println(contr.getTextCompte(c));
        }
        return contr.getTextCompte(c);
    }
    private String mostraLlibreDiari(){
        System.out.println(contr.llibreDiariToString());
        return contr.llibreDiariToString();
    }
    private String mostraLlibreMajor()throws ComptabilitatException{
        System.out.println(contr.llibreMajorToString()); 
        return contr.llibreMajorToString();
    }
    private void mostraOpcions(){
        System.out.println("Opcions:");
        System.out.println("    aj, ajuda");
        System.out.println("    na, nou assentament");
        System.out.println("    ma, mostra un assentament");
        System.out.println("    ea, elimina assentament");
        System.out.println("    md mostra el llibre diari");
        System.out.println("    ac, actualitza comptes");
        System.out.println("    mc, mostra un compte");
        System.out.println("    mm, mostra el llibre major");
        System.out.println("    tc, tanca la comptabilitat");
        System.out.println("    fi, fi");
    }
    private void nouAssentament()throws ComptabilitatException{
        System.out.println("Nou assentament...:");
        System.out.println("Concepte?");
        String c = teclat.nextLine();
        System.out.println("Data?");
        String d = teclat.nextLine();
        int na = contr.nouAssentament(d, c);
        String comptesd = new String(" ");
        while(!comptesd.equals("")){
            System.out.println("Compte debit?");
            comptesd = teclat.nextLine();
            if(!comptesd.equals("")){
                System.out.println("Import?");
                int imp = teclat.nextInt();
                teclat.skip("\n");
                contr.afegeixADebit(na, comptesd, imp);
            }
        }
        String comptesc = new String(" ");
        while(!comptesc.equals("")){
            System.out.println("Compte credit?");
            comptesc = teclat.nextLine();
            if(!comptesc.equals("")){
                System.out.println("Import?");
                int imp = teclat.nextInt();
                teclat.skip("\n");
                contr.afegeixACredit(na, comptesc, imp);
            }
        }
        if(!contr.verificaAssentament(na))
            throw new ComptabilitatException("L'assentament no quadra!");
        
    }
    private void start() throws ComptabilitatException{
        this.carregaComptabilitat();
        System.out.println("Hola!");
        System.out.println("Opcions:");
        this.mostraOpcions();
        String opcio = new String();
        while(!opcio.equals("fi")){
            opcio = teclat.nextLine();
            if(!opcio.equals("fi")){
                this.executaComanda(opcio);
                System.out.println("Introdueix una opcio: ");
            }
        }
        System.out.println("Adeu!");
    }
    private void tancaComptabilitat()throws ComptabilitatException{
        contr.actualitzaComptes();
        System.out.println(contr.tancaComptabilitat());
    }
}
