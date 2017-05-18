package rateprof.page.access;

import java.util.Scanner;

import java.io.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.select.Elements;



public class RateMyProfAccess {

    
    public static void main(String[] args) throws Exception {
           Document professor = Jsoup.connect("http://www.ratemyprofessors.com/campusRatings.jsp?sid=1077").get();
           PrintWriter RatingWriter = new PrintWriter("ProfRate.data", "UTF-8");
           HtmlToPlainText textifier = new HtmlToPlainText();
           Scanner profParse = new Scanner(textifier.getPlainText(professor));
           double rating = 0;
           
           while(profParse.hasNext()){
               String read = profParse.next();
           }
           RatingWriter.close();
           
    }
}
    

