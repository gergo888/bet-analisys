package betanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class Championship implements Comparable{
    private String strName;
    private String strSeason;
    private Date dateStarting = new Date();
    //meccs
    private ArrayList<String> arrTeams = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrPoints = new ArrayList();
    private ArrayList<ArrayList<Byte>> arrGF = new ArrayList(); //Lőtt
    private ArrayList<ArrayList<Byte>> arrGA = new ArrayList(); //Kapott
    //tabella
    private ArrayList<ArrayList<Integer>> arrPosition = new ArrayList();    
    private ArrayList<ArrayList<Integer>> arrPointsAccumulated = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrGFaccumulated = new ArrayList();
    private ArrayList<ArrayList<Integer>> arrGAaccumulated = new ArrayList();
    
    public Championship(String strName, String strSeason){
        this.strName = strName;
        this.strSeason = strSeason;
    }
    
    public String getName(){
        return strName;
    }
    
    public String getSeason(){
        return strSeason;
    }
    
    public void addTeam(Team team){
        if(!arrTeams.contains(team)){
            arrTeams.add(team.getName());
            arrPoints.add(new ArrayList());
            arrPointsAccumulated.add(new ArrayList());
            arrGF.add(new ArrayList());
            arrGFaccumulated.add(new ArrayList());
            arrGA.add(new ArrayList());
            arrGAaccumulated.add(new ArrayList());
            arrPosition.add(new ArrayList());
        }
    }    
    
    public void setStartingDate(Date dateStarting){
        this.dateStarting.setTime(dateStarting.getTime());
    }
    
    public Date getStartingDate(){
        return dateStarting;
    }
    
    public int pointCalc(String HV, Match match){
        // H hazai
        // V vendég
        int point = 0;
        String result;
        
        if (HV=="H"){
            if (match.getHTG() > match.getATG()){
                point = 3;
            }
        }
        if (HV=="V"){
            if (match.getHTG() < match.getATG()){
                point = 3;
            }
        }
        if(match.getHTG() == match.getATG()){
            point = 1;
        }
        return point;
    }
    
    public void addMatch(Match match){
        int homeTeamIndex = arrTeams.indexOf(match.getHazai().getName());
        int awayTeamIndex = arrTeams.indexOf(match.getVendeg().getName());
        //Pontok
        arrPoints.get(homeTeamIndex).add(pointCalc("H", match));
        arrPoints.get(awayTeamIndex).add(pointCalc("V", match));
        //Lőtt gólok GF
        arrGF.get(homeTeamIndex).add(match.getHTG());
        arrGF.get(awayTeamIndex).add(match.getATG());
        //Kapott gólok GA
        arrGA.get(homeTeamIndex).add(match.getATG());
        arrGA.get(awayTeamIndex).add(match.getHTG());                
    }
    
    public void leagueTableMaking(){
        int round;
        int pointSum;        
        int GFsum;
        int GAsum;
        ArrayList<TeamRound> arrTeamRound = new ArrayList();
        
        for (int i = 0; i < arrTeams.size(); i++) {
            round = arrPoints.get(i).size();
            pointSum = 0;
            GFsum = 0;
            GAsum = 0;
            for (int j = 0; j < round; j++) {
                pointSum += arrPoints.get(i).get(j);
                arrPointsAccumulated.get(i).add(j, pointSum);
                GFsum += arrGF.get(i).get(j);
                arrGFaccumulated.get(i).add(j, GFsum);
                GAsum += arrGA.get(i).get(j);
                arrGAaccumulated.get(i).add(j, GAsum);
                arrTeamRound.add(new TeamRound(GFsum - GAsum, pointSum, i, j));
            }
        }
        for (int i = 0; i < arrTeamRound.size(); i++) {
            System.out.println(arrTeamRound.get(i));
        }
        for (int i = 0; i < arrTeams.size(); i++) {
            round = arrPoints.get(i).size();
            for (int j = 0; j < round; j++) {
                arrPosition.get(i).add(positionCalc(arrTeamRound, i, j));
            }
        }
    }
    
    private int positionCalc(ArrayList<TeamRound> arrTeamRound, int teamIndex, int roundIndex){
        ArrayList<TeamRound> arrSelectedRound = new ArrayList<>();
        for (int i = 0; i < arrTeamRound.size(); i++) {
            if(arrTeamRound.get(i).roundIndex==roundIndex){
                arrSelectedRound.add(arrTeamRound.get(i));
            }
        }
        Collections.sort(arrSelectedRound);
        int index=0;
        for (int i = 0; i < arrSelectedRound.size(); i++) {
            if(arrSelectedRound.get(i).teamIndex==teamIndex)
                index=i;
        }
        System.out.println(index);
        return index;
    }
    
    public ArrayList<String> getTeams(){
        return arrTeams;
    }
    
    @Override
    public String toString(){
        String strText = strName + " " + strSeason + "\n"
                + arrTeams + "\n"
                + arrPoints + "\n"
                + arrPointsAccumulated + "\n"
                + arrGF + "\n"
                + arrGFaccumulated + "\n"
                + arrGA + "\n"
                + arrGAaccumulated + "\n"
                + arrPosition;
        return strText;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Championship)
            return (((Championship)o).getName()== null ? 
                    getName() == null : ((Championship)o).getName().equals(getName())) 
                    && (((Championship)o).getSeason() == null ? getSeason() == 
                    null : ((Championship)o).getSeason().equals(getSeason()));
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.strName);
        hash = 47 * hash + Objects.hashCode(this.strSeason);
        return hash;
    }

    @Override
    public int compareTo(Object o){
        Date thisDate = dateStarting;
        Date anotherDate = ((Championship)o).getStartingDate();
        return (thisDate.getTime() < anotherDate.getTime() ? -1 :
               (thisDate.getTime() == anotherDate.getTime() ? 0 : 1));
    }
    
    class TeamRound implements Comparable<Object>{
        int goalDifference;
        int points;
        int teamIndex;
        int roundIndex;
        
        public TeamRound(int goalDifference, int points, int teamIndex, int roundIndex){
            this.goalDifference = goalDifference;
            this.points = points;
            this.teamIndex = teamIndex;
            this.roundIndex = roundIndex;
        }

        @Override
        public int compareTo(Object o) {
            TeamRound thisTeamRound = this;
            TeamRound anotherTeamRound = (TeamRound)o;
            int intResult = 0;
            
            if (this.points != anotherTeamRound.points){
                return this.points<anotherTeamRound.points?-1:1;
            }
            else{
                if(this.goalDifference!=anotherTeamRound.goalDifference){
                    return this.goalDifference<anotherTeamRound.goalDifference?-1:1;
                }
                else{
                    return 0;
                }
            }
        }
        
        @Override
        public String toString(){
            return "teamIndex: " + teamIndex + ", points: " + points + ", GD: " + goalDifference + ", round: " + roundIndex;
        }
    }
}