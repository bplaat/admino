// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

public class Subject {
    private static int idCounter = 1;

    private final int id;
    private String code;
    private int year;

    public Subject(String code, int year) {
        id = idCounter++;
        this.code = code;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
