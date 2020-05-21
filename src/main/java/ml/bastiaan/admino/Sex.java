// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

public enum Sex {
    MALE("Male"), FEMALE("Female"), OTHER("Other");

    private String name;

    private Sex(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
