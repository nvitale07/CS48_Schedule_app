/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduledterminal;

import java.util.*;
import java.io.*;

/**
 *
 * @author Nick
 */
public class ScheduledTerminal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Menu menu = new Menu();
        
        int loop = 1;
        int coursesAdded = 0;
        List<String> coursesAddedList = new ArrayList<String>();
        
        while (loop == 1) {
        Scanner reader = new Scanner(new File("subjectsloaded.txt"));
        while (reader.hasNext()){
            String data = reader.nextLine();
            System.out.println(data);      
        }
        
        System.out.println("Enter Subject Number: ");
        Scanner reader2 = new Scanner(System.in);
        int subjectNumber = reader2.nextInt();
        
        System.out.println(); //linespace
        
        reader.close();
        reader = new Scanner(new File("subjectsloaded.txt"));
        String subjectID = "";
        String shortSubjectID = "";
        while (reader.hasNext()){
            String data = reader.nextLine();
            if (subjectNumber < 10){
                if (data.substring(1,2).compareTo(Integer.toString(subjectNumber)) == 0) {
                    shortSubjectID = data.substring(3);
                    shortSubjectID = shortSubjectID.toLowerCase();
                    shortSubjectID = shortSubjectID.replaceAll(" ","");
                    subjectID = data.substring(3) + ".txt";
                    subjectID = subjectID.toLowerCase();
                    subjectID = subjectID.replaceAll(" ","");
                    break;
                }
            }
            else if (subjectNumber >= 10){
                if (data.substring(1,3).compareTo(Integer.toString(subjectNumber)) == 0) {
                    shortSubjectID = data.substring(4);
                    shortSubjectID = shortSubjectID.toLowerCase();
                    shortSubjectID = shortSubjectID.replaceAll(" ","");
                    subjectID = data.substring(4) + ".txt";
                    subjectID = subjectID.toLowerCase();
                    subjectID = subjectID.replaceAll(" ","");
                    break;
                }
            }
        }
        
        reader.close();
        reader = new Scanner (new File(subjectID));
        while (reader.hasNext()){
            String data = reader.nextLine();
            System.out.println(data);      
        }
        
        System.out.println("Enter Class Number: ");
        int subjectNumber2 = reader2.nextInt();
        
        reader.close();
        reader = new Scanner(new File(subjectID));
        String courseID = "";
        String shortCourseID = "";
        String numberedCourseID = "";
        while (reader.hasNext()){
            String data = reader.nextLine();
            if (subjectNumber2 < 10){
                if (data.substring(1,2).compareTo(Integer.toString(subjectNumber2)) == 0) {
                    courseID = shortSubjectID + "rating.txt";
                    courseID = courseID.toLowerCase();
                    courseID = courseID.replaceAll(" ","");
                    shortCourseID = data.substring(3);
                    shortCourseID = shortCourseID.toLowerCase();
                    shortCourseID = shortCourseID.replaceAll(" ","");
                    numberedCourseID = subjectID + "ratingnumbered.txt";
                    numberedCourseID = numberedCourseID.toLowerCase();
                    numberedCourseID = numberedCourseID.replaceAll(" ","");
                    break;
                }
            }
            else if (subjectNumber2 >= 10){
                if (data.substring(1,3).compareTo(Integer.toString(subjectNumber2)) == 0) {
                    courseID = shortSubjectID + "rating.txt";
                    courseID = courseID.toLowerCase();
                    courseID = courseID.replaceAll(" ","");
                    shortCourseID = data.substring(4);
                    shortCourseID = shortCourseID.toLowerCase();
                    shortCourseID = shortCourseID.replaceAll(" ","");
                    numberedCourseID = subjectID + "ratingnumbered.txt";
                    numberedCourseID = numberedCourseID.toLowerCase();
                    numberedCourseID = numberedCourseID.replaceAll(" ","");
                    break;
                }
            }
        }
        
        if (coursesAddedList.contains(shortCourseID.toLowerCase())) {
            System.out.println();
            System.out.println("You have already added this class. Please add a different class.");
            System.out.println();
            continue;
        }
        
        System.out.println();//linespace
        
        //get data to pass to addCourse()
        System.out.println("Would you like to prioritize " + shortCourseID.toUpperCase() + "?");
        System.out.println("Give a class priority if it must be included on your optimized schedule. Enter yes or no: ");
        Scanner priorityScanner = new Scanner(System.in);
        String temp = priorityScanner.nextLine();
        int priority = 1;
        if (temp.toLowerCase().contains("no")) {
            priority = 0;
        }
        
        String all = "";
        List<String> split;
        String course = "";
        String last = "";
        String other = "";
        String days = "";
        String time = "";
        String rating = "";
        int length = shortCourseID.length();
        
        reader.close();
        reader = new Scanner(new File(courseID));
        while (reader.hasNext()){
            String data = reader.nextLine();
            all = data;
            split = Arrays.asList(all.split(","));              
            course = split.get(0);
            if (course.toLowerCase().equals(shortCourseID)) {
                last = split.get(1);
                other = split.get(2);
                days = split.get(3);
                time = split.get(4);
                rating = split.get(5);
                if (rating.contains("NS")) {
                    rating = "2.0";
                }
                menu.addCourse(course,last,other,days,time,rating,priority);
                coursesAdded++;
                if (!coursesAddedList.contains(course.toLowerCase())) {                
                    coursesAddedList.add(course.toLowerCase());
                    System.out.println();
                    menu.printCourses();
                    System.out.println();
                }
                if (priority == 1) break;
            }
        }
        
        if (coursesAdded == 20) {
            System.out.println("You have added the maximum number of classes.");
            System.out.println();
            break;
        }
            // make it so that it doesn't prompt this when under 3 classes
        String temp2 = "yes";
        if (coursesAddedList.size() >= 3) {
            System.out.println("Would you like to add another course? Enter yes or no: ");
            Scanner continueScanner = new Scanner(System.in);
            temp2 = continueScanner.nextLine();
            System.out.println();
        }
        if (temp2.toLowerCase().contains("no")) {
            if (coursesAddedList.size() < 3) {
                System.out.println("You must add at least 3 classes. Please add another class.");
                System.out.println();
            }
            else {
                loop = 0;
            }
        }
        }
        
        int ensure = 0;
        int desiredClasses = 0;
        while (ensure == 0) {
            System.out.println("How many classes would you like to take?");
            System.out.println("This is the number of classes that will appear on your optimized schedule. Enter a value: ");
            Scanner desiredScanner = new Scanner(System.in);
            desiredClasses = desiredScanner.nextInt();
            if (desiredClasses < 3 || desiredClasses > 5) {
                System.out.println();
                System.out.println("You must take either 3, 4, or 5 classes.");
                System.out.println();
            }
            else if (desiredClasses > coursesAddedList.size()) {
                System.out.println();
                System.out.println("You cannot take more classes than you have added.");
                System.out.println();
            }    
            else {   
                ensure = 1;
            }
        }
        
        System.out.println();
        System.out.println("Your schedule will now be optimized...");
        
        menu.optimize(desiredClasses);
        
        Scanner nextSchedule;
        nextSchedule = new Scanner(System.in);
        int next = 1;
        for (int x = 0;next == 1; x++){
            System.out.println();
            menu.printOptimized();
            if (x==2) break;
            System.out.println("\nHappy with your schedule? (Enter 0 to terminate)");
            System.out.println("If not, enter 1 to view the next optimal schedule");
            next=2;
            while (next != 0 && next != 1){
                next = nextSchedule.nextInt();
                if (next == 0) return;
            }
        }
        
    }
        
}
