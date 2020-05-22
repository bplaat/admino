// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import org.json.JSONException;
import org.json.JSONObject;

// The subject data class
public class Subject {
    // The internal subject id counter
    private static int idCounter = 1;

    // The subject data fields
    private final int id;
    private String code;
    private int year;

    // Create a subject and generate a new id
    public Subject(String code, int year) {
        this.id = idCounter++;
        this.code = code;
        this.year = year;
    }

    // Create a subject with an id (when loading the saved data back)
    public Subject(int id, String code, int year) {
        idCounter = id + 1;
        this.id = id;
        this.code = code;
        this.year = year;
    }

    // Load a subject data class by using a json object
    public static Subject fromJSON(JSONObject jsonSubject) throws JSONException {
        return new Subject(
            jsonSubject.getInt("id"),
            jsonSubject.getString("code"),
            jsonSubject.getInt("year")
        );
    }

    // Convert the subject to a json object (for storage)
    public JSONObject toJSON() {
        JSONObject jsonSubject = new JSONObject();
        jsonSubject.put("id", id);
        jsonSubject.put("code", code);
        jsonSubject.put("year", year);
        return jsonSubject;
    }

    // Get the subjects id
    public int getId() {
        return id;
    }

    // Get the subjects code name
    public String getCode() {
        return code;
    }

    // Set the subjects code name
    public void setCode(String code) {
        this.code = code;
    }

    // Get the subjects year
    public int getYear() {
        return year;
    }

    // Set the subjects year
    public void setYear(int year) {
        this.year = year;
    }

    // Give the name when converted to a string (for the JComboBox)
    public String toString() {
        return code;
    }
}
