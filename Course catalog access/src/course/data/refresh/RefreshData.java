package course.data.refresh;

import java.util.Scanner;

import java.io.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.select.Elements;



public class RefreshData {
    
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

    public static void curriculumSearch(PrintWriter export,String courseDpt, String quarter, String courseLvl) throws Exception {
                    String catalogURL = "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
		    Response curriculumTables = Jsoup.connect(catalogURL).method(Method.GET).execute();
		    Document curriculumTableDocument = curriculumTables.parse();
                    Element hiddenData = curriculumTableDocument.select("input[id=__VIEWSTATE]").first();
		    String viewState = hiddenData.attr("value");
		    hiddenData = curriculumTableDocument.select("input[id=__VIEWSTATEGENERATOR]").first();
		    String viewStateGenerator= hiddenData.attr("value");
		    hiddenData = curriculumTableDocument.select("input[id=__EVENTVALIDATION]").first();
		    String eventValidation = hiddenData.attr("value");
		    curriculumTables = Jsoup.connect(catalogURL)
		    		.data("__VIEWSTATE", viewState)
		    		.data("__VIEWSTATEGENERATOR", viewStateGenerator)
		    		.data("__EVENTVALIDATION", eventValidation)
		    		.data("ctl00$pageContent$courseList", courseDpt)
		    		.data("ctl00$pageContent$quarterList", quarter)
		    		.data("ctl00$pageContent$dropDownCourseLevels", courseLvl)
		    		.data("ctl00$pageContent$searchButton.x", "99")
		    		.data("ctl00$pageContent$searchButton.y", "99")
		    		.cookies(curriculumTables.cookies() )
		    		.maxBodySize(0)
                                .timeout(60000)
		    		.method(Method.POST)
		    		.execute();
		    
		    curriculumTableDocument = curriculumTables.parse();
                    
                    
                    
                    Elements courseInfo = curriculumTableDocument.select("tr.CourseInfoRow");
                    Scanner spaceFilter;
      
		    for (Element row : courseInfo) {
		    		Elements profs = row.select("td:nth-child(6)");
		    		Elements courseNumber = row.select("td:nth-child(2)");
		    		Elements courseTitle = row.select("td:nth-child(3)");
		    		Elements courseDay = row.select("td:nth-child(7)");
		    		Elements courseTime = row.select("td:nth-child(8)");
		    		
		    		String courNum = courseNumber.text().split("Click")[0];
		    		String title = courseTitle.text().split("Click")[0];
		    		String professorName = profs.text();
		    		String day = courseDay.text();
		    		String time = courseTime.text();
                                
                                    //if(!professorName.equals("T B A")){
                                        System.out.println(courNum.replaceAll("\\s+", "")+","+professorName+","+day.replaceAll("\\s+", "")+","+time.replaceAll("\\s+", ""));
                                        export.println(courNum.replaceAll("\\s+", "")+","+professorName+","+day.replaceAll("\\s+", "")+","+time.replaceAll("\\s+", ""));
                                    //}
                                   //else{
                                        //System.out.println(courNum.replaceAll("\\s+", "")+","+professorName+","+day.replaceAll("\\s+", "")+","+time.replaceAll("\\s+", ""));
                                        //export.println(courNum.replaceAll("\\s+", "")+","+"TBA"+","+day.replaceAll("\\s+", "")+","+time.replaceAll("\\s+", ""));
                                        //}
                                }
                                    
           
    }
    
    public static void RateMyProf() throws Exception {
           String searchURL = "http://www.ratemyprofessors.com/search.jsp?queryBy=teacherName&queryoption=HEADER&query=ucsb&facetSearch=true";
           String iterateThrURL = "http://www.ratemyprofessors.com";
           Document professor = Jsoup.connect(searchURL).get();
                   
           PrintWriter RatingWriter = new PrintWriter(new FileOutputStream(new File("ProfRate.data")));
           
           for(int i = 1; i < 134; ++i){
           Scanner searchParse = new Scanner(professor.select("li[class=listing PROFESSOR] a").toString());
           while(searchParse.hasNext()){
               RatingWriter = new PrintWriter(new FileOutputStream(new File("ProfRate.data"), true));
               String possibleLink = searchParse.next();
               if(possibleLink.startsWith("href")){
                    String professorLink = possibleLink.substring(6, possibleLink.length()-2);
                    
                    if(professorLink.contains("tid=1266378") || professorLink.contains("tid=1289651")){//too many redirects so avoiding these
                        RatingWriter.close();
                        continue;
                    }
                    
                    professor = Jsoup.connect(iterateThrURL + professorLink).get();
                    
                    
                    String profDepartment = professor.select("div[class=result-title]").text();
                    if(profDepartment.length() == 0){
                        String professorInfo = professor.select("div[class=subject]").text();
                        System.out.print(professorInfo.toUpperCase()+" ");
                        RatingWriter.print(professorInfo.toUpperCase()+" ");
                        
                        professorInfo = professor.select("div[class=rate-info hidden-md] div[class=name]").text();
                        System.out.println(professorInfo.toUpperCase()+" NS");
                        RatingWriter.println(professorInfo.toUpperCase()+" NS");
                        RatingWriter.close();
                        continue;
                        
                    }
                    String profInfo = profDepartment.substring(17, profDepartment.length()-90);
                    System.out.print(profInfo.toUpperCase()+" ");
                    RatingWriter.print(profInfo.toUpperCase()+" ");
                    
                    profInfo = professor.select("span[class=pfname]").text();
                    System.out.print(profInfo.toUpperCase());
                    RatingWriter.print(profInfo.toUpperCase());
                    
                    profInfo = professor.select("span[class=plname]").text();
                    System.out.print(profInfo.toUpperCase()+" ");
                    RatingWriter.print(profInfo.toUpperCase()+" ");
                    
                    profInfo = professor.select("div[class=grade]").text();
                    System.out.println(profInfo.substring(0,3));
                    RatingWriter.println(profInfo.substring(0,3));
                    RatingWriter.close();
               }
           }
           
           System.out.println("Page "+i);
           professor = Jsoup.connect("http://www.ratemyprofessors.com/search.jsp?query=ucsb&queryoption=HEADER&stateselect=&country=&dept=&queryBy=teacherName&facetSearch=true&amp;schoolName=&offset="+ (i*20) +"&max=20").followRedirects(false).get();
           }
           
           RatingWriter.close();
           
    }
    
    public static void enumerateDepartments() throws Exception{
                    //Connect to the course catalog
           String catalogURL = "https://my.sa.ucsb.edu/Public/curriculum/coursesearch.aspx";
           Document catalogDepartments = Jsoup.connect(catalogURL).get();
           PrintWriter departmentWriter = new PrintWriter("Departments.data", "UTF-8"); //Creates a file to write the departments to
           
           Scanner pageParser = new Scanner(catalogDepartments.text()); //Takes the plaintext as an instream and parses it
           int deptNumber = 1; //To number and identify which department is which
           
           //Parses through text and extracts 
           while(pageParser.hasNext()){
               String read = pageParser.next();//Puts next read token into a string
               
               if(read.equals("FALL")) break;//If the parser gets to the point in the page where the quarter drop down menu is
               
               if(isUpperCase(read) || (read.contains("&") && read.length() != 1)){ //if the token read is uppercase or it contains an and is longer than one
                   departmentWriter.print("#"+ deptNumber + "," + read); //Write the department number and the course
                   String nextRead = pageParser.next(); //Get the next token
                   deptNumber++; //Increase the department number
                   if(isUpperCase(nextRead)){ //Checks if the next token is uppercase
                       departmentWriter.println("_"+ nextRead); //writes the next token into the file
                   }
                   else{
                       departmentWriter.print("\n"); //Writes a newline character in the file
                    }
               }
           }
           departmentWriter.close();//Closes the department file
           
           PrintWriter deptWriter2 = new PrintWriter("dpt.data", "UTF-8");//Gets department data as it shows up on the page
           Elements elm = catalogDepartments.select("select[name=ctl00$pageContent$courseList] option");
           for (Element e : elm){
               deptWriter2.println(e.attr("value"));
           }
           deptWriter2.close();
    }

    
    public static void main(String[] args) throws Exception {
        enumerateDepartments();
        File departments = new File("dpt.data");
        Scanner dptReader = new Scanner(departments);
        while(dptReader.hasNext()){
            String department = dptReader.nextLine();
            
            String departmentNoSpace = department.replaceAll("\\t+|\\s+", "").toLowerCase();
            
            
            String filename = departmentNoSpace + "RAW.txt";
            PrintWriter depCourses = new PrintWriter(new FileOutputStream(new File(filename)), true);
            curriculumSearch(depCourses,department,"20174","Undergraduate");
            depCourses.close();
        }
        

    }
}
    

