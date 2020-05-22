// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

// The sex enum
public enum Sex {
    // The difrent sexes
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    UNKOWN("?");

    // A sex prop also has a name
    private String name;

    // The constructor saves the name
    private Sex(String name) {
        this.name = name;
    }

    // When converted to a string return name (for JComboBox)
    public String toString() {
        return name;
    }
}
