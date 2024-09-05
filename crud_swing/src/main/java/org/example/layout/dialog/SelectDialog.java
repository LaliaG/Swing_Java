package org.example.layout.dialog;

import org.example.dao.PersonDAO;
import org.example.model.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelectDialog extends JDialog {

    public SelectDialog(Frame parent, PersonDAO personDAO, DefaultTableModel tableModel, JTable table) {
        super(parent, "Select Contact Details", true);

        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            setLayout(new GridLayout(3, 2, 10, 10));

            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            Person person = personDAO.getPersonById(id);

            JLabel nameLabel = new JLabel("Name:");
            JLabel nameValue = new JLabel(person.getName());

            JLabel numberLabel = new JLabel("Number:");
            JLabel numberValue = new JLabel(person.getNumber());

            add(nameLabel);
            add(nameValue);
            add(numberLabel);
            add(numberValue);

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> dispose());
            add(closeButton);

            pack();
            setLocationRelativeTo(parent);
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(parent, "Please select a row to view details.");
            dispose();
        }
    }


}
