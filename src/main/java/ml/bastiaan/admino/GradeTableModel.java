// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.List;
import javax.swing.table.AbstractTableModel;

// The grade table model class
public class GradeTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    // The grades list data holder
    private final List<Grade> gradeList;

    // The column names and types
    private final String[] columnNames = new String[] { "Subject code", "Grade" };
    private final Class<?>[] columnClass = new Class<?>[] { String.class, Float.class };

    // In the constructor we give the grades list with
    public GradeTableModel(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    // A function which returns the column name
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // A function which returns the column type
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    // A function which returns the table column count
    public int getColumnCount() {
        return columnNames.length;
    }

    // A function which returns the table row count
    public int getRowCount() {
        return gradeList.size();
    }

    // A function which returns which cells are editable (all except first subject one)
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    // The function which returns the values per column
    public Object getValueAt(int rowIndex, int columnIndex) {
        Grade grade = gradeList.get(rowIndex);

        if (columnIndex == 0) {
            return grade.getSubject().getCode();
        }

        if (columnIndex == 1) {
            return grade.getGrade();
        }

        return null;
    }

    // A function which handels all the edits
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Grade grade = gradeList.get(rowIndex);

        if (columnIndex == 1) {
            grade.setGrade((float)value);
        }
    }
}
