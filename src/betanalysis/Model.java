package betanalysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Model {
    ArrayList<Championship> arrChampionship = new ArrayList<>();
    
    public Model(){
        fileImport();
    }
    
    public void fileImport(){
        ArrayList<File> arrFiles = csvList();
        String strReading;
        String[][] strTable;
        ArrayList<Team> arrTeams = new ArrayList<>();
        ArrayList<Match> arrMatches = new ArrayList<>();
        
        System.out.println("Found files:");
        for (int i = 0; i < arrFiles.size(); i++) {
            System.out.println(arrFiles.get(i));
        }
        System.out.println("\nimporting csv files:");
        for (int i = 0; i < arrFiles.size(); i++) {
            System.out.println(arrFiles.get(i) + " reading...");
            strReading = csvReading(arrFiles.get(i));
            strTable = csvTableMaking(strReading);
            
            System.out.println("creating championship...");
            arrChampionship.add(championshipCreating(strTable));
            
            System.out.println("creating teams...");
            System.out.println("creating matches...");
            System.out.println("makeing league table...");
            System.out.println("DONE");
            System.out.println("---------------------------------------------");
        }
    }
    
    public void runTest(){
        ArrayList<File> arrFiles = csvList();
        System.out.println(arrFiles.get(0).getName());
        
        String strReading = csvReading(arrFiles.get(0));
        
        String[][] strTable = csvTableMaking(strReading);
        
        System.out.println("0. " + strTable[0][0]);
        System.out.println("1. " + strTable[0][1]);
        System.out.println("2. " + strTable[0][2]);
        System.out.println("3. " + strTable[0][3]);
        System.out.println("4. " + strTable[0][4]);
        System.out.println("5. " + strTable[0][5]);
        System.out.println("6. " + strTable[0][6]);
        
        //Bajnoksag
        Championship championship = championshipCreating(strTable);
        System.out.println(championship);
        //csapat
        ArrayList<Team> arrTeams = arrTeamsCreting(strTable);
        System.out.println(arrTeams);
        //meccs
        ArrayList<Match> arrMatches = arrMatchesCreating(strTable, championship);
        System.out.println(arrMatches);
        //Bajnokság módosít
            //csapat
        for (int i = 0; i < arrTeams.size(); i++) {
            championship.addTeam(arrTeams.get(i));
        }
            //meccs
        for (int i = 0; i < arrTeams.size(); i++) {
            championship.addMatch(arrMatches.get(i));
        }
        // Tablella
        championship.leagueTableMaking();
        System.out.println(championship);
    }
    
    public ArrayList<File> csvList(){
        File fFolder = new File("./csv");
        ArrayList<File> arrCSVs = new ArrayList<>();
        for (int i = 0; i < fFolder.list().length; i++) {
            arrCSVs.add(new File("./csv/" + fFolder.list()[i]));
        }
        return arrCSVs;                
    }

    public String csvReading(File file){
        String strOutput="";
        
        try{
            FileReader fr = new FileReader(file);
            while (fr.ready()) {                
                strOutput += (char)fr.read();
            }
            fr.close();
        }
        catch(IOException e){
            System.out.println("I/O error: " + e.getMessage());
        }
        return strOutput;
    }
    
    public String[][] csvTableMaking(String csvReading) {
        int R = 0;
        int C = 1;
        String[][] arrTable;
        String[] arrPiece;
        int pc = 0;
        
        for (int i = 0; i < csvReading.length(); i++) {
            if(R==0 && csvReading.charAt(i)==','){
                C++;
            }
            if(csvReading.charAt(i)=='\n'){
                R++;
            }
        }                
        arrTable = new String[R][C];
        arrPiece = csvReading.split(",|\n");        
        for (int s = 0; s < R; s++) {
            for (int o = 0; o < C; o++) {
                arrTable[s][o] = arrPiece[pc++];
            }
        }
        return arrTable;
    }
    
    public Date dateParseing(String strDate){
        String[] strPiece = strDate.split("/");
        Date date = new Date(100 + Integer.parseInt(strPiece[2]),
                             Integer.parseInt(strPiece[1]),
                             Integer.parseInt(strPiece[0]));
        return date;
    }

    public Championship championshipCreating(String[][] csvTable){
        ArrayList<Date> arrDate = new ArrayList<>();        
        long min = Long.MAX_VALUE;
        long value;
        long max = Long.MIN_VALUE;        
        Date openingDate;
        Date closeingDate;
        String strSeason;
        String strName;
        Championship championship;

        for (int i = 1; i < csvTable.length; i++) {
            arrDate.add(dateParseing(csvTable[i][1]));
        }
        
        for (int i = 0; i < arrDate.size(); i++) {
            value = arrDate.get(i).getTime();
            if(value < min){
                min = value;
            }
        }
        openingDate = new Date(min);
        
        for (int i = 0; i < arrDate.size(); i++) {
            value = arrDate.get(i).getTime();
            if(value > max){
                max = value;
            }
        }
        closeingDate = new Date(max);
        
        strSeason = String.valueOf(openingDate.getYear()) + "/" 
                + String.valueOf(closeingDate.getYear());
        strName = csvTable[1][0];        
        championship = new Championship(strName, strSeason);
        championship.setStartingDate(openingDate);
        
        return championship;
    }
    
    public ArrayList<Team> arrTeamsCreting(String[][] csvTable){
        ArrayList<Team> arrTeams = new ArrayList<>();
        ArrayList<String> arrNames = new ArrayList<>();
        String strHome;
        String strAway;
        
        for (int i = 1; i < csvTable.length; i++) {
            strHome = csvTable[i][2];
            strAway = csvTable[i][3];
            if(!arrNames.contains(strHome)){
                arrNames.add(strHome);
            }
            if(!arrNames.contains(strAway)){
                arrNames.add(strAway);
            }
        }
        
        for (int i = 0; i < arrNames.size(); i++) {
            arrTeams.add(new Team(arrNames.get(i)));
        }
        return arrTeams;
    }    
    
    public ArrayList<Match> arrMatchesCreating(String[][] csvTable, Championship championship){
        Date dateMatch;
        Team teamHome;
        Team teamAway;
        byte byteHTG;
        byte byteATG;
        String strResult;
        ArrayList<Match> arrMatches = new ArrayList<>();
        
        for (int i = 1; i < csvTable.length; i++) {
            arrMatches.add(new Match(new Team(csvTable[i][2]), 
                                   new Team(csvTable[i][3]), 
                                   championship, 
                                   Byte.parseByte(csvTable[i][4]), 
                                   Byte.parseByte(csvTable[i][5]), 
                                   csvTable[i][6], 
                                   dateParseing(csvTable[i][1])));
        }        
        return arrMatches;                
    }
    
    //ESEMÉNYEK SZŰRÉSE, KALKULÁCIÓK
    public ArrayList<Match> filter(ArrayList<Match> arrInputMatches){
        //Helyezés
        //Odds
        //EV
        //Team
        return null;
    }
    
    public double EVcalc(){
        return 0d;
        
    }
    
    public float WinCalc(){
        return 0f;
    }
        
    
    //OBJEKTUMOK MENTÉSE FILEBA
    public void objSaving(){
        
    }
        
    //OBJEKTUMOK BETÖLTÉSE FILEBÓL
    public ArrayList<File> objFileList(){
        return null;
    }
    
    public Object objLoading(){
        return null;
    }        
}