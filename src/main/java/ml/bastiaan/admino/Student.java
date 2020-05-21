// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static int idCounter = 1;

    private final int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String studyName;
    private String className;
    private final List<Subject> passedSubjects;
    private final List<Subject> failedSubjects;

    public Student(int id, String firstName, String lastName, Sex sex, String studyName, String className) {
        idCounter = id + 1;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.studyName = studyName;
        this.className = className;
        passedSubjects = new ArrayList<Subject>();
        failedSubjects = new ArrayList<Subject>();
    }

    public Student(String firstName, String lastName, Sex sex, String studyName, String className) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.studyName = studyName;
        this.className = className;
        passedSubjects = new ArrayList<Subject>();
        failedSubjects = new ArrayList<Subject>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
}
