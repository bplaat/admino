// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

public class Subject {
    private String code;
    private float grade;
    private int year;

    public Subject(String code, float grade, int year) {
        this.code = code;
        this.grade = grade;
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public float getGrade() {
        return grade;
    }

    public int getYear() {
        return year;
    }
}
