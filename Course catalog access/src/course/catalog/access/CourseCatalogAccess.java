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
           //Document doc;
            //doc = Jsoup.connect("https://my.sa.ucsb.edu/Public/curriculum/coursesearch.aspx").data("ctl00_pageContent_courseList", "ART").data("ctl00_pageContent_quarterList", "FALL 2017").data("ctl00_pageContent_dropDownCourseLevels","Undergraduate").data("__VIEWSTATEGENERATOR","0D1CB3EA").data("__EVENTVALIDATION","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").data("_VIEWSTATE","/wEPDwUKMTE5NDUyMzA1NGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFHmN0bDAwJHBhZ2VDb250ZW50JHNlYXJjaEJ1dHRvbiMXcs5WOcLuCqd2f2EYWjl2uGGDaZGlB69ZJIyOmiWT").userAgent("Mozilla").followRedirects(true).post().get();
           PrintWriter writer = new PrintWriter("Majors.data", "UTF-8");
            
           System.out.println(newDoc.select("center tr"));
           
           HtmlToPlainText test = new HtmlToPlainText();
           Scanner parsing = new Scanner(test.getPlainText(newDoc));
           
           
           while(parsing.hasNext()){
               String read = parsing.next();
               if(read.equals("FALL")) break;
               if(isUpperCase(read) || (read.contains("&") && read.length() != 1)){
                   writer.print(read + " "); 
                   String nexRead = parsing.next();
                   if(isUpperCase(nexRead))
                       writer.println(nexRead);
                   else
                       writer.print("\n");
                   
                
               }
           }
           writer.close();
           File openMe = new File("Majors.data");
           Scanner newparsing = new Scanner(openMe);
           while(newparsing.hasNext()){
               
           }
           
    }
}
    

