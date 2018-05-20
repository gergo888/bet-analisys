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
public class teszt {    
    public static void main(String[] args) {
        Csapat cs1 = new Csapat("FTC");
        Csapat cs2 = new Csapat("MTK");
        ArrayList<Csapat> arrCsapat = new ArrayList<>();
        arrCsapat.add(cs1);
        arrCsapat.add(cs2);
        System.out.println(arrCsapat.contains(new Csapat("FTC")));
        System.out.println(cs1.equals(cs2));
    }
}
