/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Darian
 */
public class MyFile {

    public static ArrayList myFileReader(String fname) {

        ArrayList<String> myList = new ArrayList();
        try {
            File file = new File(fname);
            BufferedReader br = new BufferedReader(new FileReader(fname));
            String st;
            while ((st = br.readLine()) != null) {
                myList.add(st);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myList;
    }
    
    public static void myFileWriter(ArrayList<String> list, String fname){
        try {
            File file = new File(fname);
            PrintWriter pw = new PrintWriter(fname);
            for(int i = 0; i < list.size(); i++){
                pw.println(list.get(i));
            }
            pw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
