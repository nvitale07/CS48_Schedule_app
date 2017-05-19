/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduledterminal;

import java.io.*;

/** 
 *
 * @author Nick
 */
public class Time {

    private double begin; //begin time of class
    private double end; //end time of class
    private String days; //days on which class occurs

    public Time(double begin, double end, String days) {
        this.begin = begin;
        this.end = end;
        this.days = days;
    }
        
        // return true if there is a time overlap
    public boolean timeOverlap(Time compared) { //checks if time objects have overlapping times
        for (int x=0; x<days.length(); x++){
            for (int y=0; y<compared.getDays().length();y++){
                if (days.substring(x,x+1).compareTo(compared.getDays().substring(y,y+1)) == 0){
                    if (compared.getBegin() >= begin && compared.getBegin() < end) {
                        return true;
                    }
                    if (compared.getEnd() > begin && compared.getEnd() <= end) {
                        return true;
                    }
                    
                }
            }
        }
        return false;
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }

    public String getDays() {
        return days;
    }

    public String toString() {
        double begin = this.begin;
        double end = this.end;

        int beginHours = (int) begin;
        int endHours = (int) end;

        Boolean beginPM = false;
        Boolean endPM = false;

        if (beginHours > 12) {
            beginHours = beginHours - 12;
            begin = begin - 12;
            beginPM = true;
        }
        if (beginHours == 12) beginPM = true;
        if (endHours > 12) {
            endHours = endHours - 12;
            end = end - 12;
            endPM = true;
        }
        if (endHours == 12) endPM = true;
        int beginMinutes = (int) ((begin - beginHours) * 60);
        int endMinutes = (int) ((end - endHours) * 60);

        String result = "";
        result += this.days;
        result += ", ";

        result += Integer.toString(beginHours);
        result += ":";
        if (beginMinutes < 10)
            result += Integer.toString(0);
        result += Integer.toString(beginMinutes);
        if (beginPM)
            result += "PM";
        else
            result += "AM";

        result += "-";

        result += Integer.toString(endHours);
        result += ":";
        if (endMinutes < 10)
            result += Integer.toString(0);
        result += Integer.toString(endMinutes);
        if (endPM)
            result += "PM";
        else
            result += "AM";

        return result;
    }
}
