package betanalysis;

import java.util.Objects;

public class Csapat{
    private String strNev;
    
    public Csapat (String strNev){
        this.strNev = strNev;        
    }
    
    public String getNev(){
        return strNev;
    }
    
    @Override
    public String toString(){
        return getNev();
    }

    @Override
    public boolean equals (Object o) {
        if (o instanceof Csapat)
            return this.strNev == ((Csapat)o).getNev();
        return false;
    }

    @Override
    public int hashCode(){
        return strNev.hashCode();
    }

}