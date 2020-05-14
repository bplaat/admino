// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Student> getStudentsWhichPassedSubject(List<Student> students, Subject subject) {
        return students.stream().filter(student -> student.getPassedSubjects().contains(subject))
            .collect(Collectors.toList());
    }

    public static List<Subject> getSubjectsByStudent(Student student) {
        return Stream.concat(student.getPassedSubjects().stream(), student.getFailedSubjects().stream())
            .collect(Collectors.toList());
    }


    public static float getAvaregeGradeBySubject(List<Student> students, Subject subject) {
        return 0f;
    }

    public static void main(String[] args) {
        Log.info("The Administration System");

        List <Subject> subjects = new ArrayList<Subject>();
        subjects.add(new Subject("Wiskunde 1", 1));
        subjects.add(new Subject("Wiskunde 2", 1));
        subjects.add(new Subject("Wiskunde 3", 1));
        subjects.add(new Subject("Programeren 1", 1));
        subjects.add(new Subject("Programeren 2", 1));
        subjects.add(new Subject("Programeren 3", 1));
        subjects.add(new Subject("Programeren 4", 1));
        subjects.add(new Subject("Netwerken 2", 2));
        subjects.add(new Subject("Stage", 3));

        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Bastiaan", "van der Plaat", Student.Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jaco", "de Jong", Student.Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jan", "Jansen", Student.Sex.OTHER, "Informatica", "INF1A"));
        students.add(new Student("Lisa", "de Lange", Student.Sex.FEMALE, "Informatica", "INF1A"));
        students.add(new Student("Michiel", "de Korte", Student.Sex.MALE, "Technische Informatica", "TI1B"));

        Log.info("All students:");
        for (Student student : students) {
            student.print();
        }

        Log.info("");

        Subject subject = new Subject("Wiskunde 1", 1);
        Log.info("All students which pased " + subject.getCode() + ":");
        List<Student> students2 = getStudentsWhichPassedSubject(students, subject);
        for (Student student : students2) {
            student.print();
        }
    }
}
