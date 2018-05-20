package betanalysis;

import java.util.Date;



public class Meccs implements Comparable{
    private Csapat hazai;
    private Csapat vendeg;
    private Bajnoksag bajnoksag;
    private byte HTG;
    private byte ATG;
    private String strEredmeny;
    private Date datum;
    
    public Meccs(Csapat hazai, Csapat vendeg, Bajnoksag bajnoksag, byte HTG, byte ATG, String strEredmeny, Date datum){
        this.hazai = hazai;
        this.vendeg = vendeg;
        this.bajnoksag = bajnoksag;
        this.HTG = HTG;
        this.ATG = ATG;
        this.strEredmeny = strEredmeny;
        this.datum = datum;
    }
    
    public Date getDatum(){
        return datum;
    }

    public Csapat getHazai() {
        return hazai;
    }

    public Csapat getVendeg() {
        return vendeg;
    }

    public byte getHTG() {
        return HTG;
    }

    public byte getATG() {
        return ATG;
    }

    public String getStrEredmeny() {
        int intEredmeny;
        switch (strEredmeny) {
            case "HT": intEredmeny=0; break;
        }
        
        return strEredmeny;
    }
    
    public String toString(){
        return getDatum() + " " + bajnoksag.getNev() + " " + getHazai() + "-" 
                          + getVendeg() + " " + getHTG() + ":" + getATG();
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Meccs)
            return vendeg == ((Meccs)o).getVendeg() && hazai == 
                    ((Meccs)o).getHazai() && datum == ((Meccs)o).getDatum();
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Date thisDate = this.datum;
        Date anotherDate = ((Meccs)o).getDatum();
        return (thisDate.getTime() < anotherDate.getTime() ? -1 : 
               (thisDate.getTime() == anotherDate.getTime() ? 0 : 1));
    }            
    
}


