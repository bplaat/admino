// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import org.json.JSONException;
import org.json.JSONObject;

public class Subject {
    private static int idCounter = 1;

    private final int id;
    private String code;
    private int year;

    public Subject(String code, int year) {
        this.id = idCounter++;
        this.code = code;
        this.year = year;
    }

    public Subject(int id, String code, int year) {
        idCounter = id + 1;
        this.id = id;
        this.code = code;
        this.year = year;
    }

    public static Subject fromJSON(JSONObject jsonSubject) throws JSONException {
        return new Subject(
            jsonSubject.getInt("id"),
            jsonSubject.getString("code"),
            jsonSubject.getInt("year")
        );
    }

    public JSONObject toJSON() {
        JSONObject jsonSubject = new JSONObject();
        jsonSubject.put("id", id);
        jsonSubject.put("code", code);
        jsonSubject.put("year", year);
        return jsonSubject;
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

    public String toString() {
        return code;
    }
}
