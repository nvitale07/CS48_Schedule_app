package course.catalog.access;

import java.util.Scanner;

import java.io.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.select.Elements;



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
            //Connect to the course catalog
           String cataLink = "https://my.sa.ucsb.edu/Public/curriculum/coursesearch.aspx";
           Document catalog = Jsoup.connect(cataLink).get();
           PrintWriter deptWriter = new PrintWriter("Departments.data", "UTF-8"); //Creates a file to write the departments to
           
           
           HtmlToPlainText textifyCatalog = new HtmlToPlainText();//To convert the HTML to a nice plaintext
           Scanner pageParser = new Scanner(textifyCatalog.getPlainText(catalog)); //Takes the plaintext as an instream and parses it
           int deptNumber = 1; //To number and identify which department is which
           
           //Parses through text and extracts 
           while(pageParser.hasNext()){
               String read = pageParser.next();//Puts next read token into a string
               
               if(read.equals("FALL")) break;//If the parser gets to the point in the page where the quarter drop down menu is
               
               if(isUpperCase(read) || (read.contains("&") && read.length() != 1)){ //if the token read is uppercase or it contains an and is longer than one
                   deptWriter.print("#"+ deptNumber + "," + read); //Write the department number and the course
                   String nextRead = pageParser.next(); //Get the next token
                   deptNumber++; //Increase the department number
                   
                   if(isUpperCase(nextRead)){ //Checks if the next token is uppercase
                       deptWriter.println("_"+ nextRead); //writes the next token into the file
                   }
                   else{
                       deptWriter.print("\n"); //Writes a newline character in the file
                        }
                   
                
               }
           }
           deptWriter.close();//Closes the department file
           
           PrintWriter deptWriter2 = new PrintWriter("dpt.data", "UTF-8");
           Elements elm = catalog.select("select[name=ctl00$pageContent$courseList] option");
           for (Element e : elm){
               deptWriter2.println(e.attr("value"));
           }
           deptWriter2.close();
           
           //Access of the individual course information
           
           //Below is the hellhole of this project~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
           
           File dptReference = new File("dpt.data"); //Opens the departments data file
           Scanner department = new Scanner(dptReference);
           
           String quarter = "20174";//will become changable on next iteration
           
           Response recons = Jsoup.connect(cataLink).method(Method.GET).execute();
           Document catalogueFile = recons.parse();
           
            Element hiddenData = catalogueFile.select("input[id=__VIEWSTATE]").first();
            String viewState = hiddenData.attr("value");
            
            hiddenData = catalogueFile.select("input[id=__VIEWSTATEGENERATOR]").first();
            String viewStateGenerator= hiddenData.attr("value");
            
            hiddenData = catalogueFile.select("input[id=__EVENTVALIDATION]").first();
            String eventValidation = hiddenData.attr("value");
            
            

           
           
           
           //while(department.hasNext()){//While there exists something still in the section of the page unread
               String dep = department.nextLine();
               recons = Jsoup.connect(cataLink)
                    .data("__VIEWSTATE", viewState)
                    .data("__VIEWSTATEGENERATOR", viewStateGenerator)
                    .data("__EVENTVAILDATION", eventValidation)
                    .data("ctl00$pageContent$courseList", "CMPSC")
                    .data("ctl00$pageContent$quarterList", "20174")
                    .data("ctl00$pageContent$dropDownCourseLevels", "All")
                    .data("ctl00$pageContent$searchButton.x", "99")
                    .data("ctl00$pageContent$searchButton.y", "99")
                    .cookies(recons.cookies())
                    .userAgent("Mozilla")
                    .maxBodySize(0)
                    .timeout(60000)
                    .method(Method.POST)
                    .execute();
               
               catalogueFile = recons.parse();
               //scrap me
               Scanner scan = new Scanner(catalogueFile.text());
               while(scan.hasNext()){
                   System.out.println(scan.nextLine());
               }
               //end scrap
               Elements courseRow = catalogueFile.select("tr.CourseInfoRow");
               
               for(Element row: courseRow){
                   Elements profs = row.select("td:nth-child(6)");
		    Elements courseID = row.select("td:nth-child(2)");
		    Elements courseTitle = row.select("td:nth-child(3)");
		    Elements days = row.select("td:nth-child(7)");
		    Elements times = row.select("td:nth-child(8)");
		    		
		    String id = courseID.text().split("Click")[0];
		    String title = courseTitle.text().split("Click")[0];
		    String professorName = profs.text();
		    String day = days.text();
		    String time = times.text();
		    
                    System.out.println(title +" "+professorName+" "+day+" "+time+" ");
		   
               }
               
               
           //}

    }
}
    

