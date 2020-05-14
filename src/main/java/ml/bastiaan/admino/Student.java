// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public enum Sex {
        MALE("Male"), FEMALE("Female"), OTHER("Other");

        private String name;

        private Sex(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    };

    private static int numberCounter = 1;

    private int number;
    private String firstName;
    private String lastName;
    private Sex sex;

    private String studyName;
    private String className;
    private List<Subject> passedSubjects;
    private List<Subject> failedSubjects;

    public Student(String firstName, String lastName, Sex sex, String studyName, String className) {
        number = numberCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;

        this.studyName = studyName;
        this.className = className;
        passedSubjects = new ArrayList<Subject>();
        failedSubjects = new ArrayList<Subject>();
    }

    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getStudyName() {
        return studyName;
    }

    public String getClassName() {
        return className;
    }

    public List<Subject> getPassedSubjects() {
        return passedSubjects;
    }

    public void addPassedSubject(Subject subject) {
        passedSubjects.add(subject);
    }

    public List<Subject> getFailedSubjects() {
        return failedSubjects;
    }

    public void addFailedSubject(Subject subject) {
        failedSubjects.add(subject);
    }

    public void print() {
        Log.info("Student:");
        Log.info("  First name = " + firstName);
        Log.info("  Last name = " + lastName);
        Log.info("  Sex = " + sex);

        Log.info("");
        Log.info("  Passed subjects:");
        for (Subject subject : passedSubjects) {
            Log.info("    " + subject);
        }

        Log.info("");
        Log.info("  Failed subjects:");
        for (Subject subject : failedSubjects) {
            Log.info("    " + subject);
        }
    }
}
