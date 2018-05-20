package betanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class Bajnoksag implements Comparable{
    private String strNev;
    private String strEvad;
    private Date dKezdo = new Date();
    //meccs
    private ArrayList<String> arrCsapatok = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrPontok = new ArrayList();
    private ArrayList<ArrayList<Byte>> arrLott = new ArrayList();
    private ArrayList<ArrayList<Byte>> arrKapott = new ArrayList();
    //tabella
    private ArrayList<ArrayList<Integer>> arrPozicio = new ArrayList();    
    private ArrayList<ArrayList<Integer>> arrPontokKummulalt = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrLottKummulalt = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrKapottKummulalt = new ArrayList();
    
    public Bajnoksag(String strNev, String strEvad){
        this.strNev = strNev;
        this.strEvad = strEvad;
    }
    
    public String getNev(){
        return strNev;
    }
    
    public String getEvad(){
        return strEvad;
    }
    
    public void addCsapat(Csapat csapat){
        if(!arrCsapatok.contains(csapat)){
            arrCsapatok.add(csapat.getNev());
            arrPontok.add(new ArrayList());
            arrPontokKummulalt.add(new ArrayList());
            arrLott.add(new ArrayList());
            arrLottKummulalt.add(new ArrayList());
            arrKapott.add(new ArrayList());
            arrKapottKummulalt.add(new ArrayList());
            arrPozicio.add(new ArrayList());
        }
    }    
    
    public void setKezdoDatum(Date dKezdo){
        this.dKezdo.setTime(dKezdo.getTime());
    }
    
    public Date getKezdoDatum(){
        return dKezdo;
    }
    
    public int pontSzamitas(String HV, Meccs meccs){
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
    
    public void addMeccs(Meccs meccs){
        int HazaiCsapatIndex = arrCsapatok.indexOf(meccs.getHazai().getNev());
        int VendegCsapatIndex = arrCsapatok.indexOf(meccs.getVendeg().getNev());
        //Pontok
        arrPontok.get(HazaiCsapatIndex).add(pontSzamitas("H", meccs));
        arrPontok.get(VendegCsapatIndex).add(pontSzamitas("V", meccs));
        //Lőtt
        arrLott.get(HazaiCsapatIndex).add(meccs.getHTG());
        arrLott.get(VendegCsapatIndex).add(meccs.getATG());
        //Kapott
        arrKapott.get(HazaiCsapatIndex).add(meccs.getATG());
        arrKapott.get(VendegCsapatIndex).add(meccs.getHTG());                
    }
    
    public void tabellaKészít(){
        int fordulo;
        int pontOsszeg;        
        int lottOsszeg;
        int kapottOsszeg;
        ArrayList<csapatFordulo> arrCsapatFordulo = new ArrayList();
        
        for (int i = 0; i < arrCsapatok.size(); i++) {
            fordulo = arrPontok.get(i).size();
            pontOsszeg = 0;
            lottOsszeg = 0;
            kapottOsszeg = 0;
            for (int j = 0; j < fordulo; j++) {
                pontOsszeg += arrPontok.get(i).get(j);
                arrPontokKummulalt.get(i).add(j, pontOsszeg);
                lottOsszeg += arrLott.get(i).get(j);
                arrLottKummulalt.get(i).add(j, lottOsszeg);
                kapottOsszeg += arrKapott.get(i).get(j);
                arrKapottKummulalt.get(i).add(j, kapottOsszeg);
                arrCsapatFordulo.add(new csapatFordulo(lottOsszeg-kapottOsszeg, pontOsszeg, i, j));
            }
        }
        for (int i = 0; i < arrCsapatFordulo.size(); i++) {
            System.out.println(arrCsapatFordulo.get(i));
        }
        for (int i = 0; i < arrCsapatok.size(); i++) {
            fordulo = arrPontok.get(i).size();
            for (int j = 0; j < fordulo; j++) {
                arrPozicio.get(i).add(pozicio(arrCsapatFordulo, i, j));
            }
        }
    }
    
    private int pozicio(ArrayList<csapatFordulo> arrCsapatFordulo, int csapatIndex, int forduloIndex){
        ArrayList<csapatFordulo> valogatottTomb = new ArrayList<>();
        for (int i = 0; i < arrCsapatFordulo.size(); i++) {
            if(arrCsapatFordulo.get(i).forduloIndex==forduloIndex){
                valogatottTomb.add(arrCsapatFordulo.get(i));
            }
        }
        Collections.sort(valogatottTomb);
        int index=0;
        for (int i = 0; i < valogatottTomb.size(); i++) {
            if(valogatottTomb.get(i).csapatIndex==csapatIndex)
                index=i;
        }
        System.out.println(index);
        return index;
    }
    
    public ArrayList<String> getCsapatok(){
        return arrCsapatok;
    }
    
    @Override
    public String toString(){
        String strText = strNev + " " + strEvad + "\n"
                + arrCsapatok + "\n"
                + arrPontok + "\n"
                + arrPontokKummulalt + "\n"
                + arrLott + "\n"
                + arrLottKummulalt + "\n"
                + arrKapott + "\n"
                + arrKapottKummulalt + "\n"
                + arrPozicio;
        return strText;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Bajnoksag)
            return (((Bajnoksag)o).getNev() == null ? getNev() == null : ((Bajnoksag)o).getNev().equals(getNev())) && (((Bajnoksag)o).getEvad() == null ? getEvad() == null : ((Bajnoksag)o).getEvad().equals(getEvad()));
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.strNev);
        hash = 47 * hash + Objects.hashCode(this.strEvad);
        return hash;
    }

    @Override
    public int compareTo(Object o){
        Date thisDate = dKezdo;
        Date anotherDate = ((Bajnoksag)o).getKezdoDatum();
        return (thisDate.getTime()<anotherDate.getTime() ? -1 :
               (thisDate.getTime()==anotherDate.getTime() ? 0 : 1));
    }
    
    class csapatFordulo implements Comparable<Object>{
        int golKulonbseg;
        int pontok;
        int csapatIndex;
        int forduloIndex;
        
        public csapatFordulo(int golKulonbseg, int pontok, int csapatIndex, int forduloIndex){
            this.golKulonbseg = golKulonbseg;
            this.pontok = pontok;
            this.csapatIndex = csapatIndex;
            this.forduloIndex = forduloIndex;
        }

        @Override
        public int compareTo(Object o) {
            csapatFordulo thisCSF = this;
            csapatFordulo anotherCSF = (csapatFordulo)o;
            int eredmeny = 0;
            if (this.pontok != anotherCSF.pontok){
                return this.pontok<anotherCSF.pontok?-1:1;
            }
            else{
                if(this.golKulonbseg!=anotherCSF.golKulonbseg){
                    return this.golKulonbseg<anotherCSF.golKulonbseg?-1:1;
                }
                else{
                    return 0;
                }
            }
        }
        
        @Override
        public String toString(){
            return "csapatIndex: " + csapatIndex + ", pontok: " + pontok + ", golkulonbseg: " + golKulonbseg + ", fordulo: " + forduloIndex;
        }
    }
}