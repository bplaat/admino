// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.List;
import javax.swing.table.AbstractTableModel;

// The subject table model
public class SubjectTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    // The subjects interal list reference
    private final List<Subject> subjectList;

    // The subject table editable boolean holder
    private final boolean editable;

    // The column names and types
    private final String[] columnNames = new String[] { "Id", "Code", "Year" };
    private final Class<?>[] columnClass = new Class<?>[] { Object.class, String.class, Integer.class };

    // The subject table model constructor which accepts a subjects list and if it is editable
    public SubjectTableModel(List<Subject> subjectList, boolean editable) {
        this.subjectList = subjectList;
        this.editable = editable;
    }

    // The function that returns the column names
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // The function that returns the column types
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    // The function that returns the table column count
    public int getColumnCount() {
        return columnNames.length;
    }

    // The function that returns the table rows count
    public int getRowCount() {
        return subjectList.size();
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
        Subject subject = subjectList.get(rowIndex);

        if (columnIndex == 0) {
            return subject.getId();
        }

        if (columnIndex == 1) {
            return subject.getCode();
        }

        if (columnIndex == 2) {
            return subject.getYear();
        }

        return null;
    }

    // The function which handles column edits
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Subject subject = subjectList.get(rowIndex);

        if (columnIndex == 1) {
            subject.setCode((String)value);
        }

        if (columnIndex == 2) {
            subject.setYear((int)value);
        }

        // Update the comboboxes
        App.getInstance().updateComboBoxes();
    }
}
