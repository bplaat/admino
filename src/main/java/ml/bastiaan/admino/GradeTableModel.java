// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class GradeTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;

    private final Map<Subject, Float> gradesMap;
    private final String[] columnNames = new String[] { "Subject code", "Grate" };
    private final Class<?>[] columnClass = new Class<?>[] { String.class, Float.class };

    public GradeTableModel(Map<Subject, Float> gradesMap) {
        this.gradesMap = gradesMap;
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
        return gradesMap.size();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {

        }
    }
}
