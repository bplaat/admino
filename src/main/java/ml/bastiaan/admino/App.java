// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class App implements Runnable {
    private static App instance = null;

    private JFrame frame;
    private JTabbedPane tabs;
    private boolean gradesTabChange = false;
    private List<Subject> subjects;
    private List<Student> students;

    private App() {}

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private JSONObject loadData() {
        File settingsFile = new File(System.getProperty("user.home") + "/admino-data.json");
        if (settingsFile.exists() && !settingsFile.isDirectory()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(settingsFile));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
                bufferedReader.close();

                return new JSONObject(stringBuilder.toString());
            }

            catch (IOException | JSONException exception) {
                Log.warning(exception);
                return new JSONObject();
            }
        } else {
            return new JSONObject();
        }
    }

    public void run() {
        JSONObject data = loadData();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {}

        frame = new JFrame("The Administration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        // Root objects
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane rootScrollPane = new JScrollPane(root);
        rootScrollPane.setBorder(null);
        frame.add(rootScrollPane);

        // Tabs
        tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(640, 480));
        tabs.addChangeListener((ChangeEvent event) -> {
            if (!gradesTabChange && tabs.getTabCount() == 3) {
                tabs.remove(2);
            }
        });
        root.add(tabs, BorderLayout.CENTER);

        // Subject Tab
        subjects = new ArrayList<Subject>();
        if (data.has("subjects")) {
            try {
                JSONArray jsonSubjects = data.getJSONArray("subjects");
                for (int i = 0; i < jsonSubjects.length(); i++) {
                    subjects.add(Subject.fromJSON(jsonSubjects.getJSONObject(i)));
                }
            } catch (JSONException exception) {
                Log.error(exception);
            }
        }
        else {
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
        }

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
            int row = subjectTable.getSelectedRow();
            if (row != -1) {
                subjects.remove(row);
                subjectTableModel.fireTableDataChanged();
            }
        });
        subjectButtons.add(subjectRemoveButton);

        tabs.addTab("Subjects", subjectTab);

        // Student Tab
        students = new ArrayList<Student>();
        if (data.has("students")) {
            try {
                JSONArray jsonStudents = data.getJSONArray("students");
                for (int i = 0; i < jsonStudents.length(); i++) {
                    students.add(Student.fromJSON(jsonStudents.getJSONObject(i), subjects));
                }
            } catch (JSONException exception) {
                Log.error(exception);
            }
        }
        else {
            students.add(new Student("Bastiaan", "van der Plaat", Sex.MALE, "Technische Informatica", "TI1E"));
            students.add(new Student("Dirk", "de Jong", Sex.MALE, "Technische Informatica", "TI1E"));
            students.add(new Student("Jan", "Jansen", Sex.OTHER, "Informatica", "INF1A"));
            students.add(new Student("Lisa", "de Lange", Sex.FEMALE, "Informatica", "INF1A"));
            students.add(new Student("Michiel", "de Korte", Sex.MALE, "Technische Informatica", "TI1B"));

            for (Student student : students) {
                for (int i = 0; i < (int)(Math.random() * 10) + 5; i++) {
                    student.addGrade(
                        subjects.get((int)(Math.random() * subjects.size())),
                        (float)(Math.floor(Math.random() * 9 * 10) / 10) + 1
                    );
                }
            }
        }

        StudentTableModel studentTableModel = new StudentTableModel(students);

        JPanel studentTab = new JPanel(new BorderLayout());

        JTable studentTable = new JTable(studentTableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setDefaultEditor(Sex.class, new SexCellEditor());
        studentTable.setDefaultRenderer(JButton.class, new GradesCellRenderer());
        studentTable.setDefaultEditor(JButton.class, new GradesCellEditor());
        studentTab.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel studentButtons = new JPanel();
        studentTab.add(studentButtons, BorderLayout.PAGE_END);

        JButton studentAddButton = new JButton("Add new student");
        studentAddButton.addActionListener((ActionEvent event) -> {
            students.add(new Student("?", "?", Sex.UNKOWN, "?", "?"));
            studentTableModel.fireTableDataChanged();
        });
        studentButtons.add(studentAddButton);

        JButton studentRemoveButton = new JButton("Remove selected student");
        studentRemoveButton.addActionListener((ActionEvent event) -> {
            int row = studentTable.getSelectedRow();
            if (row != -1) {
                students.remove(row);
                studentTableModel.fireTableDataChanged();
            }
        });
        studentButtons.add(studentRemoveButton);

        tabs.addTab("Students", studentTab);

        // Buttons sidebar
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.setPreferredSize(new Dimension(196, 480));
        buttons.setBorder(new EmptyBorder(0, 8, 0, 0));
        root.add(buttons, BorderLayout.EAST);

        // Add the save data button to the sidebar
        JButton saveButton = new JButton("Save data");
        saveButton.addActionListener((ActionEvent event) -> {
            try {
                // Turn all objects to json data
                JSONObject jsonData = new JSONObject();

                // Make all subjects json objects
                JSONArray jsonSubject = new JSONArray();
                for (Subject subject : subjects) {
                    jsonSubject.put(subject.toJSON());
                }
                jsonData.put("subjects", jsonSubject);

                // Make all students json objects
                JSONArray jsonStudents = new JSONArray();
                for (Student student : students) {
                    jsonStudents.put(student.toJSON());
                }
                jsonData.put("students", jsonStudents);

                // Write the json the the storage file
                FileWriter settingsFileWriter = new FileWriter(System.getProperty("user.home") + "/admino-data.json");
                settingsFileWriter.write(jsonData.toString());
                settingsFileWriter.close();
            }

            // On error crash
            catch (IOException exception) {
                Log.error(exception);
            }
        });
        buttons.add(saveButton);

        // Make the GUI window visible
        frame.setVisible(true);
    }

    void openStudentGradesTab(int studentRow) {
        Student student = students.get(studentRow);

        GradeTableModel gradeTableModel = new GradeTableModel(student.getGrades());

        JPanel gradeTab = new JPanel(new BorderLayout());

        JTable gradeTable = new JTable(gradeTableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTab.add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        JPanel gradeButtons = new JPanel();
        gradeTab.add(gradeButtons, BorderLayout.PAGE_END);

        JComboBox<Subject> subjectInput = new JComboBox<Subject>();
        for (Subject subject : subjects) {
            subjectInput.addItem(subject);
        }
        gradeButtons.add(subjectInput);

        JTextField gradeInput = new JTextField(10);
        gradeButtons.add(gradeInput);

        JButton gradeAddButton = new JButton("Add new grade");
        gradeAddButton.addActionListener((ActionEvent event) -> {
            try {
                if (
                    student.addGrade(
                        (Subject)subjectInput.getSelectedItem(),
                        Float.parseFloat(gradeInput.getText())
                    )
                ) {
                    subjectInput.setSelectedIndex(0);
                    gradeInput.setText("");
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(frame, "There is already a grade for that subject entered", "Duplicated Grade Subject Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(frame, "The grade you've entered is not a good float value", "Grade Float Parse Error", JOptionPane.ERROR_MESSAGE);
            }

            gradeTableModel.fireTableDataChanged();
        });
        gradeButtons.add(gradeAddButton);

        JButton gradeRemoveButton = new JButton("Remove selected grade");
        gradeRemoveButton.addActionListener((ActionEvent event) -> {
            int row = gradeTable.getSelectedRow();
            if (row != -1) {
                student.removeGrade(row);
                gradeTableModel.fireTableDataChanged();
            }
        });
        gradeButtons.add(gradeRemoveButton);

        gradesTabChange = true;
        tabs.addTab("Grades for " + student.getFirstName() + " " + student.getLastName(), gradeTab);
        tabs.setSelectedComponent(gradeTab);
        gradesTabChange = false;
    }
}
