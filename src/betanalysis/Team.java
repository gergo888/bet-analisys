package betanalysis;

import java.util.Objects;

public class Team{
    private String strName;
    
    public Team (String strName){
        this.strName = strName;        
    }
    
    public String getName(){
        return strName;
    }
    
    @Override
    public String toString(){
        return getName();
    }

    @Override
    public boolean equals (Object o) {
        if (o instanceof Team)
            return this.strName == ((Team)o).getName();
        return false;
    }

    @Override
    public int hashCode(){
        return strName.hashCode();
    }

}