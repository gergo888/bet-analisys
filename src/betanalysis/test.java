/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betanalysis;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gergo
 */
public class test {    
    public static void main(String[] args) {
        Team cs1 = new Team("FTC");
        Team cs2 = new Team("MTK");
        ArrayList<Team> arrCsapat = new ArrayList<>();
        arrCsapat.add(cs1);
        arrCsapat.add(cs2);
        System.out.println(arrCsapat.contains(new Team("FTC")));
        System.out.println(cs1.equals(cs2));
    }
}
