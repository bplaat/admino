// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Student {
    private static int idCounter = 1;

    private final int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String studyName;
    private String className;
    private final List<Grade> grades;

    public Student(String firstName, String lastName, Sex sex, String studyName, String className) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.studyName = studyName;
        this.className = className;
        grades = new ArrayList<Grade>();
    }

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
            JSONObject jsonGrade = new JSONObject();
            jsonGrade.put("subject-id", grade.getSubject().getId());
            jsonGrade.put("grade", grade.getGrade());
            jsonGrades.put(jsonGrade);
        }
        jsonStudent.put("grades", jsonGrades);

        return jsonStudent;
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

    public List<Grade> getGrades() {
        return grades;
    }

    public boolean addGrade(Subject subject, float grade) {
        for (Grade otherGrade : grades) {
            if (otherGrade.getSubject().getCode().equals(subject.getCode())) {
                return false;
            }
        }
        grades.add(new Grade(subject, grade));
        return true;
    }

    public void removeGrade(int index) {
        grades.remove(index);
    }
}
