// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;

// The grades table cell editor for the studens table
public class GradesCellEditor extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1;

    // Return no data
    public Object getCellEditorValue() {
        return null;
    }

    // As editor compontent return button with action handler for the app instance
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton button = new JButton("...");
        button.addActionListener((ActionEvent event) -> {
            App.getInstance().openStudentGradesTab(row);
        });
        return button;
    }
}
