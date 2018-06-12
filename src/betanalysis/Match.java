package betanalysis;

import java.util.Date;



public class Match implements Comparable{
    private Team HT;
    private Team AT;
    private Championship championship;
    private byte HTG;
    private byte ATG;
    private String strResult;
    private Date date;
    
    public Match(Team HT, Team AT, Championship championship, byte HTG, byte ATG, String strResult, Date result){
        this.HT = HT;
        this.AT = AT;
        this.championship = championship;
        this.HTG = HTG;
        this.ATG = ATG;
        this.strResult = strResult;
        this.date = result;
    }
    
    public Date getDatum(){
        return date;
    }

    public Team getHazai() {
        return HT;
    }

    public Team getVendeg() {
        return AT;
    }

    public byte getHTG() {
        return HTG;
    }

    public byte getATG() {
        return ATG;
    }

    public String getStrEredmeny() {
        int intResult;
        switch (strResult) {
            case "HT": intResult=0; break;
        }
        
        return strResult;
    }
    
    public String toString(){
        return getDatum() + " " + championship.getName() + " " + getHazai() + "-" 
                          + getVendeg() + " " + getHTG() + ":" + getATG();
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Match)
            return AT == ((Match)o).getVendeg() && HT == 
                    ((Match)o).getHazai() && date == ((Match)o).getDatum();
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Date thisDate = this.date;
        Date anotherDate = ((Match)o).getDatum();
        return (thisDate.getTime() < anotherDate.getTime() ? -1 : 
               (thisDate.getTime() == anotherDate.getTime() ? 0 : 1));
    }            
    
}


