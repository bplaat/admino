// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JTable;

// The feault grades cell rendere for the student model
public class GradesCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1;

    // Just return a button whith dots
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        return new JButton("...");
    }
}
