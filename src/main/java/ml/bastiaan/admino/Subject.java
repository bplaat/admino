// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

public class Subject {
    private String code;
    private int year;
    private float grade;

    public Subject(String code, int year) {
        this.code = code;
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public int getYear() {
        return year;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public float getGrade() {
        return grade;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Subject)) {
            return false;
        }

        Subject subject = (Subject)object;
        return code.equals(subject.getCode()) && year == subject.getYear();
    }

    public String toString() {
        return "Code = " + code + ", Year = " + year;
    }
}
