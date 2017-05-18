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
           Document professor = Jsoup.connect("http://www.ratemyprofessors.com/search.jsp?queryBy=teacherName&queryoption=HEADER&query=ucsb&facetSearch=true").get();
           Response resp = Jsoup.connect("http://www.ratemyprofessors.com/search.jsp?queryBy=teacherName&queryoption=HEADER&query=ucsb&facetSearch=true").followRedirects(false).execute();
                   
           PrintWriter RatingWriter = new PrintWriter(new FileOutputStream(new File("ProfRate.data")));
           HtmlToPlainText textifier = new HtmlToPlainText();
           Scanner profParse = new Scanner(textifier.getPlainText(professor));
           double rating = 0;
           for(int i = 1; i < 134; ++i){
           Scanner linkparse = new Scanner(professor.select("li[class=listing PROFESSOR] a").toString());
           while(linkparse.hasNext()){
               RatingWriter = new PrintWriter(new FileOutputStream(new File("ProfRate.data"), true));
               String posslink = linkparse.next();
               if(posslink.startsWith("href")){
                    String link = posslink.substring(6, posslink.length()-2);
                    
                    if(link.contains("tid=1266378") || link.contains("tid=1289651")){//too many redirects so avoiding these
                        RatingWriter.close();
                        continue;
                    }
                    
                    professor = Jsoup.connect("http://www.ratemyprofessors.com" + link).get();
                    
                    
                    String sub = professor.select("div[class=result-title]").text();
                    if(sub.length() == 0){
                        String printr = professor.select("div[class=subject]").text();
                        System.out.print(printr.toUpperCase()+" ");
                        RatingWriter.print(printr.toUpperCase()+" ");
                        
                        printr = professor.select("div[class=rate-info hidden-md] div[class=name]").text();
                        System.out.println(printr.toUpperCase()+" NS");
                        RatingWriter.println(printr.toUpperCase()+" NS");
                        RatingWriter.close();
                        continue;
                        
                    }
                    String printme = sub.substring(17, sub.length()-90);
                    System.out.print(printme.toUpperCase()+" ");
                    RatingWriter.print(printme.toUpperCase()+" ");
                    
                    printme = professor.select("span[class=pfname]").text();
                    System.out.print(printme.toUpperCase());
                    RatingWriter.print(printme.toUpperCase());
                    
                    printme = professor.select("span[class=plname]").text();
                    System.out.print(printme.toUpperCase()+" ");
                    RatingWriter.print(printme.toUpperCase()+" ");
                    
                    printme = professor.select("div[class=grade]").text();
                    System.out.println(printme.substring(0,3));
                    RatingWriter.println(printme.substring(0,3));
                    RatingWriter.close();
               }
           }
           
           System.out.println("Page "+i);
           professor = Jsoup.connect("http://www.ratemyprofessors.com/search.jsp?query=ucsb&queryoption=HEADER&stateselect=&country=&dept=&queryBy=teacherName&facetSearch=true&amp;schoolName=&offset="+ (i*20) +"&max=20").followRedirects(false).get();
           }
           
           RatingWriter.close();
           
    }
}
    

