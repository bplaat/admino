// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class SexCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private static final long serialVersionUID = 1;

    private Sex sex;

    public Object getCellEditorValue() {
        return this.sex;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Sex) {
            this.sex = (Sex)value;
        }

        JComboBox<Sex> sexCombo = new JComboBox<Sex>();
        for (Sex sex : Sex.values()) {
            sexCombo.addItem(sex);
        }

        sexCombo.setSelectedItem(sex);
        sexCombo.addActionListener(this);

        if (isSelected) {
            sexCombo.setBackground(table.getSelectionBackground());
        } else {
            sexCombo.setBackground(table.getSelectionForeground());
        }

        return sexCombo;
    }

    public void actionPerformed(ActionEvent event) {
        JComboBox<Sex> sexCombo = (JComboBox<Sex>)event.getSource();
        this.sex = (Sex)sexCombo.getSelectedItem();
    }
}
