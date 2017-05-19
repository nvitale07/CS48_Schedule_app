/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course.data.refresh;

import java.io.File;
import java.util.Scanner;


/**
 *
 * @author austinloza
 */
public class Tester {
    public static void main(String[] args) throws Exception{
        RefreshData x = new RefreshData();
        //x.RateMyProfUpdate();
        File departments = new File("dpt.data");
        Scanner read = new Scanner(departments);
        FileFormatter y = new FileFormatter();
        while(read.hasNextLine()){
        
        y.createRating(read.nextLine().replaceAll("\\s+|\\n+||\\t+", "").toLowerCase());
        }
        
        
    }
}
