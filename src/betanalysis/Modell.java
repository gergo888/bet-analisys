package betanalysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Modell {
    ArrayList<Bajnoksag> arrBajnoksag = new ArrayList<>();
    
    public Modell(){
        fileImport();
    }
    
    public void fileImport(){
        ArrayList<File> talaltFileok = csvLista();
        String beolvasas;
        String[][] tablazat;
        ArrayList<Csapat> arrCsapat = new ArrayList<>();
        ArrayList<Meccs> arrMeccs = new ArrayList<>();

        
        System.out.println("A talált fileok listája:");
        for (int i = 0; i < talaltFileok.size(); i++) {
            System.out.println(talaltFileok.get(i));
        }
        System.out.println("\ncsv fileok importálása:");
        for (int i = 0; i < talaltFileok.size(); i++) {
            System.out.println(talaltFileok.get(i) + " beolvasása...");
            beolvasas = csvBeolvas(talaltFileok.get(i));
            tablazat = csvTablazat(beolvasas);
            
            System.out.println("bajnokság létrehozása...");
            arrBajnoksag.add(bajnoksagLetrehoz(tablazat));
            
            System.out.println("csapatok létrehozása...");
            System.out.println("meccsek létrehozása...");
            System.out.println("tabella készítése...");
            System.out.println("KÉSZ");
            System.out.println("---------------------------------------------");
        }
    }
    
    public void fut(){
        ArrayList<File> fileok = csvLista();
        System.out.println(fileok.get(0).getName());
        
        String beolvasas = csvBeolvas(fileok.get(0));
        
        String[][] tablazat = csvTablazat(beolvasas);
        
        System.out.println("0. " + tablazat[0][0]);
        System.out.println("1. " + tablazat[0][1]);
        System.out.println("2. " + tablazat[0][2]);
        System.out.println("3. " + tablazat[0][3]);
        System.out.println("4. " + tablazat[0][4]);
        System.out.println("5. " + tablazat[0][5]);
        System.out.println("6. " + tablazat[0][6]);
        
        //Bajnoksag
        Bajnoksag bajnoksag = bajnoksagLetrehoz(tablazat);
        System.out.println(bajnoksag);
        //csapat
        ArrayList<Csapat> arrCsapat = csapatArrLétrehoz(tablazat);
        System.out.println(arrCsapat);
        //meccs
        ArrayList<Meccs> arrMeccs = meccsArrLétrehoz(tablazat, bajnoksag);
        System.out.println(arrMeccs);
        //Bajnokság módosít
            //csapat
        for (int i = 0; i < arrCsapat.size(); i++) {
            bajnoksag.addCsapat(arrCsapat.get(i));
        }
            //meccs
        for (int i = 0; i < arrMeccs.size(); i++) {
            bajnoksag.addMeccs(arrMeccs.get(i));
        }
        // Tablella
        bajnoksag.tabellaKészít();
        System.out.println(bajnoksag);
        
    }
    
    public ArrayList<File> csvLista(){
        File mappa = new File("./csv");
        ArrayList<File> arrCSVs = new ArrayList<>();
        for (int i = 0; i < mappa.list().length; i++) {
            arrCSVs.add(new File("./csv/" + mappa.list()[i]));
        }
        return arrCSVs;                
    }

    public String csvBeolvas(File file){
        String kimenet="";
        try{
            FileReader fr = new FileReader(file);
            while (fr.ready()) {                
                kimenet += (char)fr.read();
            }
            fr.close();
        }
        catch(IOException e){
            System.out.println("I/O hiba" + e.getMessage());
        }
        return kimenet;
    }
    
    public String[][] csvTablazat(String csvBeolvasas) {
        int S = 0;
        int O = 1;
        
        for (int i = 0; i < csvBeolvasas.length(); i++) {
            if(S==0 && csvBeolvasas.charAt(i)==','){
                O++;
            }
            if(csvBeolvasas.charAt(i)=='\n'){
                S++;
            }
        }
                
        String[][] arrTablazat= new String[S][O];
        String[] darab = csvBeolvasas.split(",|\n");
        
        int db = 0;
        for (int s = 0; s < S; s++) {
            for (int o = 0; o < O; o++) {
                arrTablazat[s][o]=darab[db++];
            }
        }
        return arrTablazat;
    }
    
    public Date datumParsolas(String strDate){
        String[] strDarab = strDate.split("/");
        Date date = new Date(100 + Integer.parseInt(strDarab[2]),
                             Integer.parseInt(strDarab[1]),
                             Integer.parseInt(strDarab[0]));
        return date;
    }

    public Bajnoksag bajnoksagLetrehoz(String[][] csvTablazat){
        ArrayList<Date> arrDate = new ArrayList<>();        
        long min = Long.MAX_VALUE;
        long ertek;
        long max = Long.MIN_VALUE;        
        Date nyitoDatum;
        Date zaroDatum;
        String strEvad;
        String strNev;
        Bajnoksag bajnoksag;

        for (int i = 1; i < csvTablazat.length; i++) {
            arrDate.add(datumParsolas(csvTablazat[i][1]));
        }
        for (int i = 0; i < arrDate.size(); i++) {
            ertek = arrDate.get(i).getTime();
            if(ertek < min){
                min = ertek;
            }
        }
        nyitoDatum = new Date(min);
        
        for (int i = 0; i < arrDate.size(); i++) {
            ertek = arrDate.get(i).getTime();
            if(ertek > max){
                max = ertek;
            }
        }
        zaroDatum = new Date(max);
        
        strEvad = String.valueOf(nyitoDatum.getYear()) + "/" 
                + String.valueOf(zaroDatum.getYear());
        strNev = csvTablazat[1][0];        
        bajnoksag = new Bajnoksag(strNev, strEvad);
        bajnoksag.setKezdoDatum(nyitoDatum);
        
        return bajnoksag;
    }
    
    public ArrayList<Csapat> csapatArrLétrehoz(String[][] csvTablazat){
        ArrayList<Csapat> arrCsapat=new ArrayList<>();
        ArrayList<String> arrNevek = new ArrayList<>();
        String hazai;
        String vendeg;
        
        for (int i = 1; i < csvTablazat.length; i++) {
            hazai = csvTablazat[i][2];
            vendeg = csvTablazat[i][3];
            if(!arrNevek.contains(hazai)){
                arrNevek.add(hazai);
            }
            if(!arrNevek.contains(vendeg)){
                arrNevek.add(vendeg);
            }
        }
        
        for (int i = 0; i < arrNevek.size(); i++) {
            arrCsapat.add(new Csapat(arrNevek.get(i)));
        }
        return arrCsapat;
    }
    
    
    public ArrayList<Meccs> meccsArrLétrehoz(String[][] csvTablazat, Bajnoksag bajnoksag){
        Date meccsDatuma;
        Csapat hazai;
        Csapat vendeg;
        byte HTG;
        byte ATG;
        String strEredmeny;
        ArrayList<Meccs> arrMeccs = new ArrayList<>();
        
        for (int i = 1; i < csvTablazat.length; i++) {
            arrMeccs.add(new Meccs(new Csapat(csvTablazat[i][2]), 
                                   new Csapat(csvTablazat[i][3]), 
                                   bajnoksag, 
                                   Byte.parseByte(csvTablazat[i][4]), 
                                   Byte.parseByte(csvTablazat[i][5]), 
                                   csvTablazat[i][6], 
                                   datumParsolas(csvTablazat[i][1])));
        }        
        return arrMeccs;                
    }
        
    public ArrayList<Meccs> szures(){
        //Helyezés
        //Odds
        //EV
    }
    
    public double EVcalc(){
        
    }
    
    public float WinPercentage(){
        
    }
        
    
    //OBJEKTUMOK MENTÉSE FILEBA
    public objMentes(){
        
    }
        
    //OBJEKTUMOK BETÖLTÉSEFILEBÓL
    public ArrayList<File> objFieLista(){
        
    }
    
    public objBetoltes(){
        
    }        
}