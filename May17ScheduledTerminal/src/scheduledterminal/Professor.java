/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduledterminal;

/**
 *
 * @author Nick
 */
public class Professor {

    private String last; //last name of professor
    private String other; //first and middle name (initial) of professor
    private double rating; //professor rating

    public Professor(String last, String other, double rating) {
        this.last = last;
        this.other = other;
        this.rating = rating;
    }

    public String getLast() {
        return last;
    }

    public String getOther() {
        return other;
    }

    public double getRating() {
        return rating;
    }

    public String toString() {
        String result = "";
        result += this.last;
        result += " ";
        result += this.other;
        result += " (";
        result += Double.toString(this.rating);
        result += ")";
        return result;
    }

}
