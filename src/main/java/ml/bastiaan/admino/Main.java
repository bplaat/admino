// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Log.info("The Administration System");

        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Bastiaan", "van der Plaat", Student.Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jaco", "de Jong", Student.Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jan", "Jansen", Student.Sex.OTHER, "Informatica", "INF1A"));
        students.add(new Student("Lisa", "de Lange", Student.Sex.FEMALE, "Informatica", "INF1A"));
        students.add(new Student("Michiel", "de Korte", Student.Sex.MALE, "Technische Informatica", "TI1B"));

        students.forEach(Student::print);
    }
}
