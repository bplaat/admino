// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import org.json.JSONObject;

// The grade data class
public class Grade {
    private final Subject subject;
    private float grade;

    // Grade constructor with subject and grate
    public Grade(Subject subject, float grade) {
        this.subject = subject;
        this.grade = grade;
    }

    // Convert a grade item to a json object
    public JSONObject toJSON() {
        JSONObject jsonGrade = new JSONObject();
        jsonGrade.put("subject-id", subject.getId());
        jsonGrade.put("grade", grade);
        return jsonGrade;
    }

    // Get a grate subject
    public Subject getSubject() {
        return subject;
    }

    // Get a grade grade
    public float getGrade() {
        return grade;
    }

    // Set a grade grade
    public void setGrade(float grade) {
        this.grade = grade;
    }
}
