/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course.catalog.access;

import java.util.Scanner;

import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.examples.HtmlToPlainText;



public class CourseCatalogAccess {
    
public static boolean isUpperCase(String s)
{
    for (int i=0; i<s.length(); i++)
    {
        if (!Character.isUpperCase(s.charAt(i)))
        {
            return false;
        }
    }
    return true;
}

    
    public static void main(String[] args) throws Exception {
           Document newDoc = Jsoup.connect("https://my.sa.ucsb.edu/Public/curriculum/coursesearch.aspx").get();
           
           HtmlToPlainText test = new HtmlToPlainText();
           Scanner parsing = new Scanner(test.getPlainText(newDoc));
           while(parsing.hasNext()){
               String read = parsing.next();
               if(read.equals("FALL")) break;
               if(isUpperCase(read) || (read.contains("&") && read.length() != 1)){
                   System.out.print(read + " "); 
                   String nexRead = parsing.next();
                   if(isUpperCase(nexRead))
                       System.out.println(nexRead);
                   else
                       System.out.print("\n");
                   
                
               }
           }
           
    }
}
    

