/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course.data.refresh;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author austinloza
 */
public class FileFormatter {
    public FileFormatter(){
        System.out.print("Taking raw files and converting them to nice files...\n");
    }
    
    public static String findRating(String[] profname) throws Exception{
        File rates = new File("ProfRate.data");
        
        File dontHave = new File("ProfDepartments.txt");
        ArrayList<String> ignore = new ArrayList();
        Scanner dont = new Scanner(dontHave);
        dont.useDelimiter("\\s+|\\n+");
        while(dont.hasNext()){
            String nextUp = dont.next();
            ignore.add(nextUp.replaceAll("\\n+", "").toUpperCase());
        }
        
        Scanner ratingFind = new Scanner(rates);
        ArrayList<String> matches = new ArrayList();
        //String output = "";
        int counter = 0;
        
        while(ratingFind.hasNextLine()){
            //System.out.println(counter++);
            String point = ratingFind.nextLine();
            
            if(point.equals(profname[0].toUpperCase().replaceAll("\\s", ""))){
                matches.add(point);
            }
        }
        ArrayList<String> finalMatches = new ArrayList();
        while(!matches.isEmpty()){
            int depth = 0;
            Scanner poinParse = new Scanner(matches.get(matches.size()-1));
                while(poinParse.hasNext()){
                    String select = poinParse.next();
                    if(!ignore.contains(select.toUpperCase())){
                        if(depth == 0){
                            //if(select.startsWith(firstLetter.toUpperCase())){
                                finalMatches.add(matches.get(matches.size()-1));
                            //}
                        }
                        depth++;
                        //System.out.println(select);
                    }
                }
            //System.out.println(matches.get(matches.size()-1));
            matches.remove(matches.size()-1);
        }
        
        while(!finalMatches.isEmpty()){
            return finalMatches.get(finalMatches.size()-1).split("\\s+")[finalMatches.get(finalMatches.size()-1).split("\\s+").length-1];
            //finalMatches.remove(finalMatches.size()-1);
        }
        
        
        return "N.S";
    }
    
    public void createRatingNumbered(String department) throws Exception{
        File departmentCourses = new File(department.replaceAll("\\s+|\\t+", "").toLowerCase() + "RAW.txt");
        Scanner courseScanner = new Scanner(departmentCourses);
        courseScanner.useDelimiter("\\s+|,");
        
        ArrayList<String[]> recall = new ArrayList();
        
        PrintWriter deptCourseList = new PrintWriter(department.replaceAll("\\s+|\\t+","").toLowerCase()+"ratingnumbered.txt");
        int counter = 1;
        while(courseScanner.hasNextLine()){
            
            boolean exit = false;
            String nextData = courseScanner.nextLine();
            String[] pair = new String[2];
            String[] full = new String[4];
            pair[0] = nextData.split(",")[0];
            pair[1] = nextData.split(",")[1];
            full[0] = pair[0];
            full[1] = pair[1];
            full[2] = nextData.split(",")[2];
            full[3] = nextData.split(",")[3];
            for(int i = 0;i < recall.size(); i++){
                if(recall.get(i)[0].equals(pair[0]) && recall.get(i)[1].equals(pair[1])){
                    exit = true;
                    break;
                }
            }
            if (exit == true) continue;
            recall.add(pair);
            //String[] full1Split = full[1].split("\\s+");
            //System.out.println(full[1]);
            if(full[1].split("\\s").length > 1)
                deptCourseList.println("#"+counter+","+full[0]+"," + full[1].split("\\s+",2)[0]+","+full[1].split("\\s+",2)[1]+"," + full[2]+"," + full[3].toUpperCase()+","+findRating(full[1].split("\\s+")));
            else
              deptCourseList.println("#"+counter+","+full[0]+"," + full[1]+"," + full[2]+"," + full[3].toUpperCase()+","+findRating(full[1].split("\\s+")));  
            counter++;
               
        }
        deptCourseList.close();
    }
    
    public void createRating(String department) throws Exception{
        File departmentCourses = new File(department.replaceAll("\\s+|\\t+", "").toLowerCase() + "RAW.txt");
        Scanner courseScanner = new Scanner(departmentCourses);
        courseScanner.useDelimiter("\\s+|,");
        
        ArrayList<String[]> recall = new ArrayList();
        
        PrintWriter deptCourseList = new PrintWriter(department.replaceAll("\\s+|\\t+","").toLowerCase()+"rating.txt");
        
        while(courseScanner.hasNextLine()){
            
            boolean exit = false;
            String nextData = courseScanner.nextLine();
            String[] pair = new String[2];
            String[] full = new String[4];
            pair[0] = nextData.split(",")[0];
            pair[1] = nextData.split(",")[1];
            full[0] = pair[0];
            full[1] = pair[1];
            full[2] = nextData.split(",")[2];
            full[3] = nextData.split(",")[3];
            for(int i = 0;i < recall.size(); i++){
                if(recall.get(i)[0].equals(pair[0]) && recall.get(i)[1].equals(pair[1])){
                    exit = true;
                    break;
                }
            }
            if (exit == true) continue;
            recall.add(pair);
            if(full[1].split("\\s").length > 1)
                deptCourseList.println(full[0]+"," + full[1].split("\\s+",2)[0]+","+full[1].split("\\s+",2)[1]+"," + full[2]+"," + full[3].toUpperCase()+","+findRating(full[1].split("\\s+")));
             else
                deptCourseList.println(full[0]+"," + full[1]+"," + full[2]+"," + full[3].toUpperCase()+","+findRating(full[1].split("\\s+")));
        }
        deptCourseList.close();
    }
    
    public void createDptCourseList(String department) throws Exception{
        File departmentCourses = new File(department.replaceAll("\\s+|\\t+", "").toLowerCase() + "RAW.txt");
        Scanner courseScanner = new Scanner(departmentCourses);
        courseScanner.useDelimiter("\\s|,|\\n");
        ArrayList courseNames = new ArrayList();
        PrintWriter deptCourseList = new PrintWriter(department.replaceAll("\\s+|\\t+","").toLowerCase()+".txt");
        int counter = 1;
        while(courseScanner.hasNext()){
            String nextCourse = courseScanner.next();
            if(nextCourse.contains(department.toUpperCase())){
                if(!courseNames.contains(nextCourse)){
                    deptCourseList.println("#"+counter+","+nextCourse);
                    counter++;
                    courseNames.add(nextCourse);
                }
            }
        }
        deptCourseList.close();
    }
    
    
    
}
