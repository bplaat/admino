// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class GradeTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    private final List<Grade> gradeList;
    private final String[] columnNames = new String[] { "Subject code", "Grade" };
    private final Class<?>[] columnClass = new Class<?>[] { String.class, Float.class };

    public GradeTableModel(List<Grade> gradeList) {
        this.gradeList = gradeList;
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
        return gradeList.size();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

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

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Grade grade = gradeList.get(rowIndex);

        if (columnIndex == 1) {
            grade.setGrade((float)value);
        }
    }
}
