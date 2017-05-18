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
public class Course {

    public Time time;
    public String id;
    public Professor professor;
    public boolean priority;

    public Course(Time time, String id, Professor professor, boolean priority) {
        this.time = time;
        this.id = id;
        this.professor = professor;
        this.priority = priority;
    }

        //returns true if there is overlap
    public boolean courseOverlap(Course compared) { //checks if course objects have overlapping times or are ide
        if (compared.getTime().timeOverlap(this.time)) return true;
        if (id.compareTo(compared.getID()) == 0) return true;
        return false;
        //include case when course includes .1 .2 .3
    }

    public Time getTime() {
        return time;
    }

    public String getID() {
        return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean getPriority() {
        return priority;
    }

    public String toString() {
        String result = "";
        result += this.id;
        result += ", ";
        result += this.professor.toString();
        result += ", ";
        result += this.time.toString();

        return result;
    }

}
