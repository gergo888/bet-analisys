package betanalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javafx.print.Collation;


public class BajnoksagHiba implements Comparable{
    String strNev;
    String strEvad;
    HashSet<Team> hCsapatok = new HashSet();
    HashMap<Team, ArrayList> csapatPontok = new HashMap();
    Date dKezdo = new Date();
    
    public BajnoksagHiba(String strNev, String strEvad){
        this.strNev = strNev;
        this.strEvad = strEvad;
    }
    
    public String getNev(){
        return strNev;
    }
    
    public String getEvad(){
        return strEvad;
    }
    
    public void addCsapat(Team nev){
        hCsapatok.add(nev);
    }    
    
    public void setKezdoDatum(Date dKezdo){
        this.dKezdo.setTime(dKezdo.getTime());
    }
    
    public Date getKezdoDate(){
        return dKezdo;
    }
    
    public int pontSzamitas(String HV, Match meccs){
        int pont = 0;
        String eredmeny;
        
        if (HV=="H"){
            if (meccs.getHTG() > meccs.getATG()){
                pont = 3;
            }
        }
        if (HV=="V"){
            if (meccs.getHTG() < meccs.getATG()){
                pont = 3;
            }
        }
        if(meccs.getHTG() == meccs.getATG()){
            pont = 1;
        }
        return pont;
    }
    
    public void addMeccs(Match meccs){
        int hazaiPont = pontSzamitas("H", meccs);
        int vendegPont = pontSzamitas("V", meccs);
        
        ArrayList arrPontHazai = new ArrayList();                
        ArrayList arrPontVendeg = new ArrayList();        
        
        //HAZAI
        
        arrPontHazai.add(5);
        csapatPontok.put(new Team("FTC"), arrPontHazai);
        
        //VENDÉG
        Team vendeg = meccs.getVendeg();
        if (csapatPontok.containsKey(new Team(vendeg.getName()))){
            System.out.println("rfrewfrewf");
            arrPontVendeg = csapatPontok.get(new Team(vendeg.getName()));
        }
        arrPontVendeg.add(6);
        csapatPontok.put(new Team(vendeg.getName()), arrPontVendeg);        
    }
    
    public HashSet<Team> getCsapatok(){
        return hCsapatok;
    }
    
    @Override
    public String toString(){
        String strText = strNev + " " + strEvad + "\n" + hCsapatok + "\n" + csapatPontok;        
        return strText;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof BajnoksagHiba)
            return ((BajnoksagHiba)o).getNev() == getNev() && ((BajnoksagHiba)o).getEvad() == getEvad();
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Date thisDate = dKezdo;
        Date anotherDate = ((BajnoksagHiba)o).getKezdoDate();
        return thisDate.getTime()<anotherDate.getTime() ? -1 :
               thisDate.getTime()==anotherDate.getTime() ? 0 : 1;
    }
    
}