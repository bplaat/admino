// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// The student data class
public class Student {
    // The internal student id counter
    private static int idCounter = 1;

    // The student data fields
    private final int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String studyName;
    private String className;
    private final List<Grade> grades;

    // Create a student and generate a new id
    public Student(String firstName, String lastName, Sex sex, String studyName, String className) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.studyName = studyName;
        this.className = className;
        grades = new ArrayList<Grade>();
    }

    // Create a student with a id (for data loading)
    public Student(int id, String firstName, String lastName, Sex sex, String studyName, String className) {
        idCounter = id + 1;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.studyName = studyName;
        this.className = className;
        grades = new ArrayList<Grade>();
    }

    // Create a student object from a json object
    public static Student fromJSON(JSONObject jsonStudent, List<Subject> subjects) throws JSONException {
        Student student = new Student(
            jsonStudent.getInt("id"),
            jsonStudent.getString("first-name"),
            jsonStudent.getString("last-name"),
            Sex.valueOf(jsonStudent.getString("sex")),
            jsonStudent.getString("study-name"),
            jsonStudent.getString("class-name")
        );

        JSONArray jsonGrades = jsonStudent.getJSONArray("grades");
        for (int i = 0; i < jsonGrades.length(); i++) {
            JSONObject jsonGrade = jsonGrades.getJSONObject(i);

            // Get the subject by subject id
            Subject gradeSubject = null;
            int gradeSubjectId = jsonGrade.getInt("subject-id");
            for (Subject subject : subjects) {
                if (subject.getId() == gradeSubjectId) {
                    gradeSubject = subject;
                    break;
                }
            }

            if (gradeSubject != null) {
                student.addGrade(gradeSubject, jsonGrade.getFloat("grade"));
            }

            else {
                Log.warning("Grade subject id: " + gradeSubjectId + " not found!");
            }
        }

        return student;
    }

    // Convert a student to a json object
    public JSONObject toJSON() {
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", id);
        jsonStudent.put("first-name", firstName);
        jsonStudent.put("last-name", lastName);
        jsonStudent.put("sex", sex);
        jsonStudent.put("study-name", studyName);
        jsonStudent.put("class-name", className);

        JSONArray jsonGrades = new JSONArray();
        for (Grade grade : grades) {
            jsonGrades.put(grade.toJSON());
        }
        jsonStudent.put("grades", jsonGrades);

        return jsonStudent;
    }

    // Get a student id
    public int getId() {
        return id;
    }

    // Get a student first name
    public String getFirstName() {
        return firstName;
    }

    // Set a student first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Get a student last name
    public String getLastName() {
        return lastName;
    }

    //  Set a student last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //  Get a student sex
    public Sex getSex() {
        return sex;
    }

    // Set a student sex
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    // Get a student study name
    public String getStudyName() {
        return studyName;
    }

    // Set a student study name
    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    // Get a student class name
    public String getClassName() {
        return className;
    }

    // Set a student class name
    public void setClassName(String className) {
        this.className = className;
    }

    // Get a student grades
    public List<Grade> getGrades() {
        return grades;
    }

    // A add grade to a student when possible returns when successeded
    public boolean addGrade(Subject subject, float grade) {
        for (Grade otherGrade : grades) {
            if (otherGrade.getSubject().getCode().equals(subject.getCode())) {
                return false;
            }
        }
        grades.add(new Grade(subject, grade));
        return true;
    }

    // Remove a student grade by index
    public void removeGrade(int index) {
        grades.remove(index);
    }
}
