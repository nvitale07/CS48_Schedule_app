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
public class Menu {

    private List<Course> courses;
    private int desired;
    private int optimizations;
    Schedule [] schedules;
    int schedulesRequested;
    int priorities;    // number of classes with priority 
    int validSchedules;

    public Menu(){
        courses = new ArrayList<Course>();
        desired = 0;
        optimizations = 0;
        schedulesRequested = 0;
        priorities = 0;
    }


public void addCourse(String course,String last,String other,String days,String time,String rating,int priority) {
        desired++;
        //convert priority integer to boolean
        boolean priorityParameter = true;
        if (priority == 0) {
            priorityParameter = false;
        }
        else priorities ++;

        //calculate end and begin doubles
        double begin = 0;
        double end = 0;
        int count = 0;
        for (int x=1; x<time.length(); x++) {
            if (time.substring(x,x+1).compareTo(":") == 0) {
                begin = Double.parseDouble(time.substring(0,x));
                begin += ((Double.parseDouble(time.substring(x+1,x+3)))/60.0);
                if (time.substring(x+3,x+4).compareTo("P") == 0 && time.substring(0,2).compareTo("12") != 0) {
                    begin += 12.0;
                }
                count = x+6;
                break;
            }
        }

        // calculate end double
        for (int x=count; x<time.length(); x++) {
            if (time.substring(x,x+1).compareTo(":") == 0) {
                end = Double.parseDouble(time.substring(count,x));
                end += ((Double.parseDouble(time.substring(x+1,x+3)))/60.0);
                if (time.substring(x+3,x+4).compareTo("P") == 0 && time.substring(count,count+2).compareTo("12") != 0) {
                    end += 12.0;
                }
                break;
            }
        }
        Time t = new Time(begin,end,days);
        Professor p = new Professor(last,other,Double.parseDouble(rating));
        Course c = new Course(t,course,p,priorityParameter);
        sortedAdd(c);
    }

public void sortedAdd(Course course) {
        int temp = 0;
        if (courses.size() == 0) {
            courses.add(course);
            return;
        }
            
            //adds to the proper rating spot (unless its a priority class)
            
        for (Course x : courses) {
            if (course.getProfessor().getRating() > x.getProfessor().getRating()
                    && !x.getPriority()) {
                break;
            }
            temp++;
        }
        courses.add(temp,course);
        
        // account for priority
        for (int a = temp; a > 0; a--){
            if (!courses.get(a).getPriority()) break;
            if (!courses.get(a-1).getPriority() || courses.get(a-1).getProfessor().getRating() 
                   < courses.get(a).getProfessor().getRating()) {
                Course c = courses.get(a);
                courses.set(a, courses.get(a-1));
                courses.set(a-1,c);
            }
             
        }    
        
    }

    public void deleteCourse(Course course) {
        courses.remove(course);
    }

    public void setDesired(int desired) {
        this.desired = desired;
    }

    public void changePriority(Course course, boolean priority) {
        course.setPriority(priority);
    }

    public void optimize(int d){
        desired = d; // only classes that are not prioritized
                       
        if (desired == 3) optimize3();
        if (desired == 4) optimize4();
        if (desired == 5) optimize5();

    }

    public void printOptimized(){
            // if no more valid schedules
        if (schedules[schedulesRequested] == null) {
            System.out.println("No Possible Valid Schedules");
            return;
        }

        System.out.println(schedules[schedulesRequested]);
        System.out.println();
        schedulesRequested++;
    }
    
    public void optimize3(){
        schedules = new Schedule[3];
        //int total=0;
        validSchedules = 0;
        
            //handles priorities at the beginning of the array
        int xStopValue = priorities;
        int yStopValue = priorities;
        if (xStopValue == 0) xStopValue = courses.size()-2;
        if (yStopValue < 2) yStopValue = courses.size()-1;
        for (int x=0; x<xStopValue; x++){
            for (int y=x+1; y < yStopValue;y++){
                for (int z=y+1; z<courses.size(); z++){

                    if (val3(courses.get(x),courses.get(y),courses.get(z))){

                        Schedule tempSchedule = new Schedule();
                        tempSchedule.add(courses.get(x));
                        tempSchedule.add(courses.get(y));
                        tempSchedule.add(courses.get(z));
                        
                        addOptimized(tempSchedule);
                    }
                }
            } // end y for loop
        }// end x for loop

    }

    public void optimize4(){
        schedules = new Schedule[3];
        //int total=0;
        validSchedules = 0;
        
            //handles priorities at the beginning of the array
        int wStopValue = priorities;
        int xStopValue = priorities;
        int yStopValue = priorities;
        if (wStopValue == 0) wStopValue = courses.size()-3;
        if (xStopValue < 2) xStopValue = courses.size()-2;
        if (yStopValue < 3) yStopValue = courses.size()-1;
        for (int w=0; w<wStopValue; w++){
        for (int x=w+1; x<xStopValue; x++){
            for (int y=x+1; y < yStopValue;y++){
                for (int z=y+1; z<courses.size(); z++){

                    if (val4(courses.get(w),courses.get(x),courses.get(y),courses.get(z))){

                        Schedule tempSchedule = new Schedule();
                        tempSchedule.add(courses.get(w));
                        tempSchedule.add(courses.get(x));
                        tempSchedule.add(courses.get(y));
                        tempSchedule.add(courses.get(z));
                        
                        addOptimized(tempSchedule);
                    }
                }
            } // end y for loop
        }// end x for loop
        }   // end w for loop

    }

    public void optimize5(){
        schedules = new Schedule[3];
        int total=0;
        validSchedules = 0;
        
            //handles priorities at the beginning of the array
        int uStopValue = priorities;
        int wStopValue = priorities;
        int xStopValue = priorities;
        int yStopValue = priorities;
        if (priorities == 0) 
            uStopValue = courses.size()-4; 
        if (priorities < 2) 
            wStopValue = courses.size()-3;
        if (priorities < 3) 
            xStopValue = courses.size()-2;
        if (priorities < 4) 
            yStopValue = courses.size()-1;
        for (int u=0; u<uStopValue; u++){
        for (int w=u+1; w<wStopValue; w++){
        for (int x=w+1; x<xStopValue; x++){
            for (int y=x+1; y < yStopValue;y++){
                for (int z=y+1; z<courses.size(); z++){

                    if (val5(courses.get(u),courses.get(w),courses.get(x),courses.get(y),courses.get(z))){

                        Schedule tempSchedule = new Schedule();
                        tempSchedule.add(courses.get(u));
                        tempSchedule.add(courses.get(w));
                        tempSchedule.add(courses.get(x));
                        tempSchedule.add(courses.get(y));
                        tempSchedule.add(courses.get(z));
                        
                        addOptimized(tempSchedule);
                    }
                }
            } // end y for loop
        }// end x for loop
        }   // end w for loop
        }


    }

    public void addOptimized(Schedule tempSchedule){
        // haven't filled valid schedule array
                        if(validSchedules < 3){
                            schedules[validSchedules] = tempSchedule;
                            validSchedules++;
                            if (validSchedules>2) sortOptimizedSchedules();
                            if (validSchedules==2 && schedules[0].totalRating()
                                    < schedules[1].totalRating()){
                                Schedule t = schedules[0];
                                schedules[0] = schedules[1];
                                schedules[1] = t;
                            }
                        } // haven't filled valid schedules yet

                        else if (schedules[2].totalRating()
                                    < tempSchedule.totalRating()){
                            schedules[2]=tempSchedule;
                            sortOptimizedSchedules();
                        }
        
    }
  
    public void sortOptimizedSchedules(){
            // selection sort of the small array of optimized schedules
        for (int i = 0; i < schedules.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < schedules.length; j++){
                if (schedules[j].totalRating() > schedules[i].totalRating())
                    index = j;
            }
            Schedule t = schedules[index]; 
            schedules[index] = schedules[i];
            schedules[i] = t;
        }
        
    }

    public boolean val3(Course a, Course b, Course c){
        if (a.courseOverlap(b) || b.courseOverlap(c) || a.courseOverlap(c))
            return false;
        return true;
    }
    public boolean val4(Course a, Course b, Course c, Course d){
        Course [] A = new Course[4];
        A[0]=a; 
        A[1]=b;
        A[2]=c;
        A[3]=d;
        for (int h = 0; h<3;h++){
            for (int k = h+1; k<4;k++){
                if (A[h].courseOverlap(A[k])) return false;
            }
        }
        return true;
    }
    
    public boolean val5(Course a, Course b, Course c, Course d, Course e){
        Course [] A = new Course[5];
        A[0]=a; 
        A[1]=b;
        A[2]=c;
        A[3]=d;
        A[4]=e;
        for (int h = 0; h<4;h++){
            for (int k = h+1; k<5;k++){
                if (A[h].courseOverlap(A[k])) return false;
            }
        }
        return true;
    }
    
    public void printCourses() {
        List<String> printedCourses = new ArrayList<String>();
        System.out.println("Classes Added:");
        for (Course temp : courses) {
            if (!printedCourses.contains(temp.getID())) {
                if (temp.getPriority()) {
                    System.out.println(temp.getID() + "(Priority)");
                    printedCourses.add(temp.getID());
                }
                else {
                    System.out.println(temp.getID());
                    printedCourses.add(temp.getID());
                }
            }
        }
    }
}