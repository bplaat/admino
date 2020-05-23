// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;

// The student table model class
public class StudentTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    // The student list model data holder
    private final List<Student> studentList;

    // The editable boolean holder
    private final boolean editable;

    // The column names and types
    private final String[] columnNames = new String[] { "Id", "First name", "Last name", "Sex", "Study name", "Class name" };
    private final Class<?>[] columnClass = new Class<?>[] { Object.class, String.class, String.class, Sex.class, String.class, String.class };

    // The editable table column names and types
    private final String[] editableColumnNames = new String[] { "Id", "First name", "Last name", "Sex", "Study name", "Class name", "Grades" };
    private final Class<?>[] editableColumnClass = new Class<?>[] { Object.class, String.class, String.class, Sex.class, String.class, String.class, JButton.class };

    // The table model constructor which accepts a student list for the data and if it is editable
    public StudentTableModel(List<Student> studentList, boolean editable) {
        this.studentList = studentList;
        this.editable = editable;
    }

    // The function that returns the column names
    public String getColumnName(int column) {
        if (editable) {
            return editableColumnNames[column];
        } else {
            return columnNames[column];
        }
    }

    // The function that returns the column types
    public Class<?> getColumnClass(int columnIndex) {
        if (editable) {
            return editableColumnClass[columnIndex];
        } else {
            return columnClass[columnIndex];
        }
    }

    // The function that returns the table column count
    public int getColumnCount() {
        if (editable) {
            return editableColumnNames.length;
        } else {
            return columnNames.length;
        }
    }

    // The function that returns the table rows count
    public int getRowCount() {
        return studentList.size();
    }

    // The function which tells if a cel is editable (everything is editable when editable except the first id field)
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (editable) {
            return columnIndex != 0;
        } else {
            return false;
        }
    }

    // The function which tells what value must be displayed per column
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = studentList.get(rowIndex);

        if (columnIndex == 0) {
            return student.getId();
        }

        if (columnIndex == 1) {
            return student.getFirstName();
        }

        if (columnIndex == 2) {
            return student.getLastName();
        }

        if (columnIndex == 3) {
            return student.getSex();
        }

        if (columnIndex == 4) {
            return student.getStudyName();
        }

        if (columnIndex == 5) {
            return student.getClassName();
        }

        return null;
    }

    // The function which handles column edits
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Student student = studentList.get(rowIndex);

        if (columnIndex == 1) {
            student.setFirstName((String)value);
        }

        if (columnIndex == 2) {
            student.setLastName((String)value);
        }

        if (columnIndex == 3) {
            student.setSex((Sex)value);
        }

        if (columnIndex == 4) {
            student.setStudyName((String)value);
        }

        if (columnIndex == 5) {
            student.setClassName((String)value);
        }

        // Update the comboboxes
        App.getInstance().updateComboBoxes();
    }
}
