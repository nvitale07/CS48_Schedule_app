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

    public Menu(){
        courses = new ArrayList<Course>();
        desired = 0;
        optimizations = 0;
        schedulesRequested = 0;
    }


public void addCourse(String course,String last,String other,String days,String time,String rating,int priority) {
        desired++;
        //convert priority integer to boolean
        boolean priorityParameter = true;
        if (priority == 0) {
            priorityParameter = false;
        }

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
        for (Course x : courses) {
            if (course.getProfessor().getRating() >= x.getProfessor().getRating()) {
                courses.add(temp,course);
                return;
            }
            temp++;
        }
        courses.add(course);
        return;
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

    public void optimize(int desire){
        desired = desire;
        if (desired ==3) optimize3();
        if (desired ==4) optimize4();
        if (desired ==5) optimize5();

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
        Course lowest = courses.get(0);
        int total=0;
        int validSchedules = 0;

        for (int x=0; x<courses.size()-2; x++){
            for (int y=x+1; y < courses.size()-1;y++){
                for (int z=y+1; z<courses.size(); z++){

                    if (val3(courses.get(x),courses.get(y),courses.get(z))){

                        Schedule tempSchedule = new Schedule();
                        tempSchedule.add(courses.get(x));
                        tempSchedule.add(courses.get(y));
                        tempSchedule.add(courses.get(z));

                        if(validSchedules < 3){
                            schedules[validSchedules] = tempSchedule;
                            validSchedules++;
                                //check if 1 and 2 are in the right order
                            if (validSchedules == 2 && schedules[0].totalRating()
                                    <schedules[1].totalRating()){
                                Schedule t = schedules[0];
                                schedules[0] = schedules[1];
                                schedules[1] = t;
                            }
                            else if (validSchedules == 3)
                                sortOptimizedSchedules();
                        } // haven't filled valid schedules yet

                        else if (schedules[2].totalRating()
                                    < tempSchedule.totalRating()){
                            schedules[2]=tempSchedule;
                            sortOptimizedSchedules();
                        }



                    }

                }
            } // end y for loop
        }// end x for loop

    }

    public void optimize4(){


    }

    public void optimize5(){


    }

    public void sortOptimizedSchedules(){
        if (schedules[2].totalRating() > schedules[1].totalRating()){
            Schedule t = schedules[2];
            schedules[2] = schedules[1];
            schedules[1] = t;
        }
        if (schedules[1].totalRating() > schedules[0].totalRating()){
            Schedule t = schedules[0];
            schedules[0] = schedules[1];
            schedules[1] = t;
        }
    }

    public boolean val3(Course a, Course b, Course c){
        if (a.courseOverlap(b) || b.courseOverlap(c) || a.courseOverlap(c))
            return false;
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