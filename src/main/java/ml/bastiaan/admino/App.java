// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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

        JPanel root = new JPanel(new GridBagLayout());

        JScrollPane rootScrollPane = new JScrollPane(root);
        rootScrollPane.setBorder(null);
        frame.add(rootScrollPane);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(640, 480));
        Insets insets = new Insets(4, 4, 4, 4);
        root.add(tabs, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));

        // Student Tab
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Bastiaan", "van der Plaat", Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jaco", "de Jong", Sex.MALE, "Technische Informatica", "TI1E"));
        students.add(new Student("Jan", "Jansen", Sex.OTHER, "Informatica", "INF1A"));
        students.add(new Student("Lisa", "de Lange", Sex.FEMALE, "Informatica", "INF1A"));
        students.add(new Student("Michiel", "de Korte", Sex.MALE, "Technische Informatica", "TI1B"));

        StudentTableModel studentTableModel = new StudentTableModel(students);

        JPanel studentTab = new JPanel(new BorderLayout());

        JTable studentTable = new JTable(studentTableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setDefaultEditor(Sex.class, new SexCellEditor());
        studentTab.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel studentButtons = new JPanel();
        studentTab.add(studentButtons, BorderLayout.PAGE_END);

        JButton studentAddButton = new JButton("Add new student");
        studentAddButton.addActionListener((ActionEvent event) -> {
            students.add(new Student("?", "?", Sex.OTHER, "?", "?"));
            studentTableModel.fireTableDataChanged();
        });
        studentButtons.add(studentAddButton);

        JButton studentRemoveButton = new JButton("Remove selected student");
        studentRemoveButton.addActionListener((ActionEvent event) -> {
            students.remove(studentTable.getSelectedRow());
            studentTableModel.fireTableDataChanged();
        });
        studentButtons.add(studentRemoveButton);

        tabs.addTab("Students", studentTab);

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

        JPanel subjectTab = new JPanel(new BorderLayout());

        JTable subjectTable = new JTable(subjectTableModel);
        subjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subjectTab.add(new JScrollPane(subjectTable), BorderLayout.CENTER);

        JPanel subjectButtons = new JPanel();
        subjectTab.add(subjectButtons, BorderLayout.PAGE_END);

        JButton subjectAddButton = new JButton("Add new subject");
        subjectAddButton.addActionListener((ActionEvent event) -> {
            subjects.add(new Subject("?", 0));
            subjectTableModel.fireTableDataChanged();
        });
        subjectButtons.add(subjectAddButton);

        JButton subjectRemoveButton = new JButton("Remove selected subject");
        subjectRemoveButton.addActionListener((ActionEvent event) -> {
            subjects.remove(subjectTable.getSelectedRow());
            subjectTableModel.fireTableDataChanged();
        });
        subjectButtons.add(subjectRemoveButton);

        tabs.addTab("Subjects", subjectTab);

        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(320, 480));
        root.add(buttons, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));

        frame.setVisible(true);
    }
}
