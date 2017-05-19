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
    
    public static String findRating(String lastname) throws Exception{
        File rates = new File("ProfRate.data");
        Scanner ratingFind = new Scanner(rates);
        ArrayList<String> matches = new ArrayList();
        //String output = "";
        
        
        while(ratingFind.hasNextLine()){
            String point = ratingFind.nextLine();
            System.out.println(point);
            for(int i = 0; i < point.split("\\s").length; i++){
                if(point.split("\\s")[i].equals(lastname.toUpperCase().replaceAll("\\s", ""))){
                    System.out.println("match!");
                    matches.add(point);
                }
            }
        }
        if(matches.isEmpty()){
            return "NS";
        }
        else if(matches.size() == 1){
            int size = matches.get(0).split("\\s+").length;
            return matches.get(0).split("\\s")[size-1];
        }
        else{
            return "";
        }
        
        
        //return output;
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
            deptCourseList.println("#"+counter+","+full[0]+"," + full[1]+"," + full[2]+"," + full[3]);
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
            String rating = findRating("maass");
            deptCourseList.println(full[0]+"," + full[1]+"," + full[2]+"," + full[3]+","+rating);
               
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
