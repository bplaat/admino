// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

// The app main singleton class
public class App implements Runnable {
    // The app instance holder
    private static App instance = null;

    // Other fields
    private JFrame frame;
    private JTabbedPane tabs;
    private List<Subject> subjects;
    private List<Student> students;
    private JComboBox<Subject> subjectInput = null;
    private JComboBox<Student> studentInput = null;
    private JComboBox<Subject> gradeSubjectInput = null;

    // The constructor is private must use getInstance
    private App() {}

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    // A function which loads the data file to a json object
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

    // The main Java Swing run function
    public void run() {
        // Load the data
        JSONObject data = loadData();

        // Set the UI look and feel for Java Swing to the native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {}

        // Create the window
        frame = new JFrame("The Administration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        // Create the root pane
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Create the root scrollpane
        JScrollPane rootScrollPane = new JScrollPane(root);
        rootScrollPane.setBorder(null);
        frame.add(rootScrollPane);

        // Create tabs pane
        tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(640, 480));
        root.add(tabs, BorderLayout.CENTER);

        // Load subjects
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

        // Or create some default ones
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

        // Create subjects tab
        JPanel subjectTab = new JPanel(new BorderLayout());
        tabs.addTab("Subjects", subjectTab);

        // Create subjects table
        SubjectTableModel subjectTableModel = new SubjectTableModel(subjects, true);
        JTable subjectTable = new JTable(subjectTableModel);
        subjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subjectTab.add(new JScrollPane(subjectTable), BorderLayout.CENTER);

        JPanel subjectButtons = new JPanel();
        subjectTab.add(subjectButtons, BorderLayout.PAGE_END);

        // Create subjects add button
        JButton subjectAddButton = new JButton("Add new subject");
        subjectAddButton.addActionListener((ActionEvent event) -> {
            subjects.add(new Subject("?", 0));
            subjectTableModel.fireTableDataChanged();
            updateComboBoxes();
        });
        subjectButtons.add(subjectAddButton);

        // Create subject remove button
        JButton subjectRemoveButton = new JButton("Remove selected subject");
        subjectRemoveButton.addActionListener((ActionEvent event) -> {
            int row = subjectTable.getSelectedRow();
            if (row != -1) {
                subjects.remove(row);
                subjectTableModel.fireTableDataChanged();
                updateComboBoxes();
            }
        });
        subjectButtons.add(subjectRemoveButton);

        // Load students data
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

        // Or create some default data
        else {
            students.add(new Student("Bastiaan", "van der Plaat", Sex.MALE, "Technische Informatica", "TI1E"));
            students.add(new Student("Dirk", "de Jong", Sex.MALE, "Technische Informatica", "TI1E"));
            students.add(new Student("Jan", "Jansen", Sex.OTHER, "Informatica", "INF1A"));
            students.add(new Student("Lisa", "de Lange", Sex.FEMALE, "Informatica", "INF1A"));
            students.add(new Student("Michiel", "de Korte", Sex.MALE, "Technische Informatica", "TI1B"));

            // Give all the new students some random grates
            for (Student student : students) {
                for (int i = 0; i < (int)(Math.random() * 25) + 5; i++) {
                    student.addGrade(
                        subjects.get((int)(Math.random() * subjects.size())),
                        (float)(Math.floor(Math.random() * 9 * 10) / 10) + 1
                    );
                }
            }
        }

        // Create student tab
        JPanel studentTab = new JPanel(new BorderLayout());
        tabs.addTab("Students", studentTab);

        // Create students table
        StudentTableModel studentTableModel = new StudentTableModel(students, true);
        JTable studentTable = new JTable(studentTableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setDefaultEditor(Sex.class, new SexCellEditor());
        studentTable.setDefaultRenderer(JButton.class, new GradesCellRenderer());
        studentTable.setDefaultEditor(JButton.class, new GradesCellEditor());
        studentTab.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel studentButtons = new JPanel();
        studentTab.add(studentButtons, BorderLayout.PAGE_END);

        // Create add students button
        JButton studentAddButton = new JButton("Add new student");
        studentAddButton.addActionListener((ActionEvent event) -> {
            students.add(new Student("?", "?", Sex.UNKOWN, "?", "?"));
            studentTableModel.fireTableDataChanged();
            updateComboBoxes();
        });
        studentButtons.add(studentAddButton);

        // Create remove students button
        JButton studentRemoveButton = new JButton("Remove selected student");
        studentRemoveButton.addActionListener((ActionEvent event) -> {
            int row = studentTable.getSelectedRow();
            if (row != -1) {
                students.remove(row);
                studentTableModel.fireTableDataChanged();
                updateComboBoxes();
            }
        });
        studentButtons.add(studentRemoveButton);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.setPreferredSize(new Dimension(240, 480));
        sidebar.setBorder(new EmptyBorder(0, 8, 0, 0));
        root.add(sidebar, BorderLayout.EAST);

        sidebar.add(Box.createVerticalGlue());

        // Create logo
        JLabel logolabel = new JLabel("Admino");
        logolabel.setFont(logolabel.getFont().deriveFont(Font.BOLD, 24));
        logolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logolabel);

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        // Add the save data button to the sidebar
        JButton saveButton = new JButton("Save data");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        sidebar.add(saveButton);

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        // Create subject input field
        JLabel subjectLabel = new JLabel("Subject:");
        Font normalFont = subjectLabel.getFont().deriveFont(14f);
        subjectLabel.setFont(normalFont);
        subjectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(subjectLabel);

        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        subjectInput = new JComboBox<Subject>();
        subjectInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateComboBoxes();
        sidebar.add(subjectInput);
        subjectInput.setMaximumSize(subjectInput.getPreferredSize());

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        // Create student input field
        JLabel studentLabel = new JLabel("Student:");
        studentLabel.setFont(normalFont);
        studentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(studentLabel);

        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        studentInput = new JComboBox<Student>();
        studentInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateComboBoxes();
        sidebar.add(studentInput);
        studentInput.setMaximumSize(studentInput.getPreferredSize());

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        // Create passed students of subject button
        JButton passedStudentsOfSubjectButton = new JButton("Get all passed students of subject");
        passedStudentsOfSubjectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        passedStudentsOfSubjectButton.addActionListener((ActionEvent event) -> {
            Subject subject = (Subject)subjectInput.getSelectedItem();

            // Filter passed students
            List<Student> passedStudents = new ArrayList<Student>();
            for (Student student : students) {
                for (Grade grade : student.getGrades()) {
                    if (
                        grade.getSubject().getCode() == subject.getCode() &&
                        grade.getGrade() >= 5.5
                    ) {
                        passedStudents.add(student);
                        break;
                    }
                }
            }

            // Create dialog
            JDialog dialog = new JDialog(frame, "Passed students for subject result");
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);

            // Create passed students table
            StudentTableModel passedStudentTableModel = new StudentTableModel(passedStudents, false);
            JTable passedStudentTable = new JTable(passedStudentTableModel);
            passedStudentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dialog.add(new JScrollPane(passedStudentTable));

            dialog.setVisible(true);
        });
        sidebar.add(passedStudentsOfSubjectButton);

        // Create failed students of subject button
        JButton failedStudentsOfSubjectButton = new JButton("Get all failed students of subject");
        failedStudentsOfSubjectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        failedStudentsOfSubjectButton.addActionListener((ActionEvent event) -> {
            Subject subject = (Subject)subjectInput.getSelectedItem();

            // Filter failed students
            List<Student> failedStudents = new ArrayList<Student>();
            for (Student student : students) {
                for (Grade grade : student.getGrades()) {
                    if (
                        grade.getSubject().getCode() == subject.getCode() &&
                        grade.getGrade() < 5.5
                    ) {
                        failedStudents.add(student);
                        break;
                    }
                }
            }

            // Create dialog
            JDialog dialog = new JDialog(frame, "Failed students for subject result");
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);

            // Create failed students table
            StudentTableModel failedStudentTableModel = new StudentTableModel(failedStudents, false);
            JTable failedStudentTable = new JTable(failedStudentTableModel);
            failedStudentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dialog.add(new JScrollPane(failedStudentTable));

            dialog.setVisible(true);
        });
        sidebar.add(failedStudentsOfSubjectButton);

        // Create passed subjects of student button
        JButton passedSubjectsOfStudentButton = new JButton("Get all passed subjects of student");
        passedSubjectsOfStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        passedSubjectsOfStudentButton.addActionListener((ActionEvent event) -> {
            Student student = (Student)studentInput.getSelectedItem();

            // Filter passed subjects
            List<Subject> passedSubjects = student.getGrades().stream()
                .filter(grade -> grade.getGrade() >= 5.5)
                .map(grade -> grade.getSubject())
                .collect(Collectors.toList());

            // Create dialog
            JDialog dialog = new JDialog(frame, "Passed subject of student result");
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);

            // Create passed subjects table
            SubjectTableModel passedSubjectTableModel = new SubjectTableModel(passedSubjects, false);
            JTable passedSubjectTable = new JTable(passedSubjectTableModel);
            passedSubjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dialog.add(new JScrollPane(passedSubjectTable));

            dialog.setVisible(true);
        });
        sidebar.add(passedSubjectsOfStudentButton);

        // Create failed subjects of student button
        JButton failedSubjectsOfStudentButton = new JButton("Get all failed subjects of student");
        failedSubjectsOfStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        failedSubjectsOfStudentButton.addActionListener((ActionEvent event) -> {
            Student student = (Student)studentInput.getSelectedItem();

            // Filter failed subjects
            List<Subject> failedSubjects = student.getGrades().stream()
                .filter(grade -> grade.getGrade() < 5.5)
                .map(grade -> grade.getSubject())
                .collect(Collectors.toList());

            // Create dialog
            JDialog dialog = new JDialog(frame, "Failed subject of student result");
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);

            // Create failed subjects table
            SubjectTableModel failedSubjectTableModel = new SubjectTableModel(failedSubjects, false);
            JTable failedSubjectTable = new JTable(failedSubjectTableModel);
            failedSubjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dialog.add(new JScrollPane(failedSubjectTable));

            dialog.setVisible(true);
        });
        sidebar.add(failedSubjectsOfStudentButton);

        // Create average grade of subject button
        JButton averageGradeOfSubjectButton = new JButton("Get average grade of subject");
        averageGradeOfSubjectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageGradeOfSubjectButton.addActionListener((ActionEvent event) -> {
            Subject subject = (Subject)subjectInput.getSelectedItem();

            // Calculate average grade
            float gradeSum = 0;
            int gradeCount = 0;
            for (Student student : students) {
                for (Grade grade : student.getGrades()) {
                    if (
                        grade.getSubject().getCode() == subject.getCode()
                    ) {
                        gradeSum += grade.getGrade();
                        gradeCount++;
                        break;
                    }
                }
            }
            float gradeAverage = Math.round(gradeSum / gradeCount * 10) / 10;

            // Show dialog
            JOptionPane.showMessageDialog(frame, "The grade average of all grades is " + gradeAverage, "Grade Average Result", JOptionPane.INFORMATION_MESSAGE);
        });
        sidebar.add(averageGradeOfSubjectButton);

        // Create grade sum of student button
        JButton gradeSumOfStudentButton = new JButton("Get grade sum of student");
        gradeSumOfStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradeSumOfStudentButton.addActionListener((ActionEvent event) -> {
            Student student = (Student)studentInput.getSelectedItem();

            // Calculate grade sum
            double sum = student.getGrades().stream()
                .mapToDouble(grade -> grade.getGrade())
                .sum();
            float gradeSum = Math.round((float)sum * 10) / 10;

            // Show dialog
            JOptionPane.showMessageDialog(frame, "The grade sum of all grades is " + gradeSum, "Grade Sum Result", JOptionPane.INFORMATION_MESSAGE);
        });
        sidebar.add(gradeSumOfStudentButton);

        // Create grade standard deviation of student button
        JButton gradeStandardDeviationOfStudentButton = new JButton("Get grade standard deviation of student");
        gradeStandardDeviationOfStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradeStandardDeviationOfStudentButton.addActionListener((ActionEvent event) -> {
            // TODO
        });
        sidebar.add(gradeStandardDeviationOfStudentButton);

        // Create grade variation of student button
        JButton gradeVariationOfStudentButton = new JButton("Get grade variation of student");
        gradeVariationOfStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gradeVariationOfStudentButton.addActionListener((ActionEvent event) -> {
            // TODO
        });
        sidebar.add(gradeVariationOfStudentButton);

        // Create student sex of subject button
        JButton studentSexOfSubjectButton = new JButton("Get student sex of subject");
        studentSexOfSubjectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentSexOfSubjectButton.addActionListener((ActionEvent event) -> {
            // TODO
        });
        sidebar.add(studentSexOfSubjectButton);

        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));

        // Create footer
        JLabel footerLabel = new JLabel("Made by Bastiaan van der Plaat");
        footerLabel.setFont(normalFont);
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(footerLabel);

        sidebar.add(Box.createVerticalGlue());

        // Make the GUI window visible
        frame.setVisible(true);
    }

    // Update the comboboxes with new data
    void updateComboBoxes() {
        // Update subject input
        if (subjectInput != null) {
            int selectedSubjectIndex = subjectInput.getSelectedIndex();

            subjectInput.removeAllItems();
            for (Subject subject : subjects) {
                subjectInput.addItem(subject);
            }

            if (selectedSubjectIndex != -1) {
                subjectInput.setSelectedIndex(selectedSubjectIndex);
            }
        }

        // Update student input
        if (studentInput != null) {
            int selectedStudentIndex = studentInput.getSelectedIndex();

            studentInput.removeAllItems();
            for (Student student : students) {
                studentInput.addItem(student);
            }

            if (selectedStudentIndex != -1) {
                studentInput.setSelectedIndex(selectedStudentIndex);
            }
        }

        // Update grade subject input
        if (gradeSubjectInput != null) {
            int selectedGradeSubjectIndex = gradeSubjectInput.getSelectedIndex();

            gradeSubjectInput.removeAllItems();
            for (Subject subject : subjects) {
                gradeSubjectInput.addItem(subject);
            }

            if (selectedGradeSubjectIndex != -1) {
                gradeSubjectInput.setSelectedIndex(selectedGradeSubjectIndex);
            }
        }
    }

    // Create and show students grade tab
    void openStudentGradesTab(int index) {
        // Fetch the student
        Student student = students.get(index);

        // Create grades tab
        JPanel gradeTab = new JPanel(new BorderLayout());

        // Create grades table
        GradeTableModel gradeTableModel = new GradeTableModel(student.getGrades());
        JTable gradeTable = new JTable(gradeTableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTab.add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        JPanel gradeButtons = new JPanel();
        gradeTab.add(gradeButtons, BorderLayout.PAGE_END);

        // Create subject input field
        gradeSubjectInput = new JComboBox<Subject>();
        updateComboBoxes();
        gradeButtons.add(gradeSubjectInput);

        // Create grade input field
        JTextField gradeInput = new JTextField(10);
        gradeButtons.add(gradeInput);

        // Create add grade button
        JButton gradeAddButton = new JButton("Add new grade");
        gradeAddButton.addActionListener((ActionEvent event) -> {
            try {
                // Add new grade and check if not a duplicate
                if (
                    student.addGrade(
                        (Subject)gradeSubjectInput.getSelectedItem(),
                        Float.parseFloat(gradeInput.getText())
                    )
                ) {
                    gradeSubjectInput.setSelectedIndex(0);
                    gradeInput.setText("");
                }

                // When duplicated grade show error message
                else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(frame, "There is already a grade for that subject entered", "Duplicated Grade Subject Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // When number was not a float show error message
            catch (NumberFormatException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(frame, "The grade you've entered is not a good float value", "Grade Float Parse Error", JOptionPane.ERROR_MESSAGE);
            }

            gradeTableModel.fireTableDataChanged();
        });
        gradeButtons.add(gradeAddButton);

        // Create remove grade button
        JButton gradeRemoveButton = new JButton("Remove selected grade");
        gradeRemoveButton.addActionListener((ActionEvent event) -> {
            int row = gradeTable.getSelectedRow();
            if (row != -1) {
                student.removeGrade(row);
                gradeTableModel.fireTableDataChanged();
            }
        });
        gradeButtons.add(gradeRemoveButton);

        // Remove the third tab when a grades tab was already opend
        if (tabs.getTabCount() == 3) {
            tabs.remove(2);
        }

        // Add grade page to the sabs and show it
        tabs.addTab("Grades for " + student.getFirstName() + " " + student.getLastName(), gradeTab);
        tabs.setSelectedComponent(gradeTab);
    }
}
