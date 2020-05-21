// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class App implements Runnable {
    private static App instance = null;

    private App() {}

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        JFrame frame = new JFrame("The Administration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        frame.add(tabs);

        // Student Tab
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Bastiaan", "van der Plaat", Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jaco", "de Jong", Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jan", "Jansen", Sex.OTHER, "Informatica", "INF1A"));
        students.add(new Student("Lisa", "de Lange", Sex.FEMALE, "Informatica", "INF1A"));
        students.add(new Student("Michiel", "de Korte", Sex.MALE, "Technische Informatica", "TI1B"));

        StudentTableModel studentTableModel = new StudentTableModel(students);
        JTable studentTable = new JTable(studentTableModel);
        studentTable.setDefaultEditor(Sex.class, new SexCellEditor());
        tabs.addTab("Students", new JScrollPane(studentTable));

        // Subject Tab
        List <Subject> subjects = new ArrayList<Subject>();
        subjects.add(new Subject("Wiskunde 1", 1));
        subjects.add(new Subject("Wiskunde 2", 1));
        subjects.add(new Subject("Wiskunde 3", 1));
        subjects.add(new Subject("Programeren 1", 1));
        subjects.add(new Subject("Programeren 2", 1));
        subjects.add(new Subject("Programeren 3", 1));
        subjects.add(new Subject("Programeren 4", 1));
        subjects.add(new Subject("Netwerken 1", 1));
        subjects.add(new Subject("Netwerken 2", 2));
        subjects.add(new Subject("Stage", 3));
        subjects.add(new Subject("Afsturen", 4));

        SubjectTableModel subjectTableModel = new SubjectTableModel(subjects);
        JTable subjectTable = new JTable(subjectTableModel);
        tabs.addTab("Subjects", new JScrollPane(subjectTable));

        frame.setVisible(true);
    }
}
