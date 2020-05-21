// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App.getInstance());
    }

//     public static List<Student> getStudentsWhichPassedSubject(List<Student> students, Subject subject) {
//         return students.stream().filter(student -> student.getPassedSubjects().contains(subject))
//             .collect(Collectors.toList());
//     }

//     public static List<Subject> getSubjectsByStudent(Student student) {
//         return Stream.concat(student.getPassedSubjects().stream(), student.getFailedSubjects().stream())
//             .collect(Collectors.toList());
//     }
}
