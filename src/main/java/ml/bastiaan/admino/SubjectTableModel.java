// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SubjectTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    private final List<Subject> subjectList;
    private final String[] columnNames = new String[] { "Id", "Code", "Year" };
    private final Class<?>[] columnClass = new Class<?>[] { Object.class, String.class, Integer.class };

    public SubjectTableModel(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return subjectList.size();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

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

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Subject subject = subjectList.get(rowIndex);

        if (columnIndex == 1) {
            subject.setCode((String)value);
        }

        if (columnIndex == 2) {
            subject.setYear((int)value);
        }
    }
}
