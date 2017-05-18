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
public class Schedule {
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private int courseCount;
    private int totalRating;

    public Schedule(){
        courseCount = 0;
        totalRating = 0;
    }

    public int totalRating(){
        return totalRating;
    }

    public void add(Course c){
        courseCount++;
        if (courseCount ==1)
            course1 = c;
        if (courseCount ==2)
            course2 = c;
        if (courseCount ==3)
            course3 = c;
        if (courseCount ==4)
            course4 = c;
        if (courseCount ==5)
            course5 = c;
        totalRating += c.getProfessor().getRating();
    }


    public String toString() {
        String result = "";
        result += this.course1.toString();
        result += "\n";
        result += this.course2.toString();
        result += "\n";
        result += this.course3.toString();

        if (this.courseCount > 3) {
            result += "\n";
            result += this.course4.toString();
        }

        if (this.courseCount > 4) {
            result += "\n";
            result += this.course5.toString();
        }

        return result;
    }

}
