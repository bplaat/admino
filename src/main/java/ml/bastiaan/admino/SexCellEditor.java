// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class SexCellEditor extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1;

    private Sex sex;

    public Object getCellEditorValue() {
        return this.sex;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Sex) {
            this.sex = (Sex)value;
        }

        JComboBox<Sex> sexComboBox = new JComboBox<Sex>();
        for (Sex sex : Sex.values()) {
            if (sex != Sex.UNKOWN) {
                sexComboBox.addItem(sex);
            }
        }

        sexComboBox.setSelectedItem(sex);
        sexComboBox.addActionListener((ActionEvent event) -> {
            this.sex = (Sex)sexComboBox.getSelectedItem();
        });

        if (isSelected) {
            sexComboBox.setBackground(table.getSelectionBackground());
        } else {
            sexComboBox.setBackground(table.getSelectionForeground());
        }

        return sexComboBox;
    }
}
