package org.example.layout.dialog;

import org.example.layout.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDialog extends JDialog {

    public DeleteDialog(MainFrame parent, PersonDAO personDAO, DefaultTableModel tableModel, JTable table) {
        super(parent, "Delete Contact Details", true);

        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            setLayout(new GridLayout(3, 2, 10, 10));

            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            Person person = personDAO.getPersonById(id);

            JLabel nameLabel = new JLabel("Name:");
            JLabel nameValue = new JLabel(person.getName());

            JLabel numberLabel = new JLabel("Number:");
            JLabel numberValue = new JLabel(person.getNumber());

            JButton deleteButton = new JButton("Delete");
            JButton cancelButton = new JButton("Cancel");

            add(nameLabel);
            add(nameValue);
            add(numberLabel);
            add(numberValue);
            add(deleteButton);
            add(cancelButton);

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    personDAO.deletePerson(person);
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(parent, "Person deleted successfully!");
                    dispose();
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            pack();
            setLocationRelativeTo(parent);
        } else {
            JOptionPane.showMessageDialog(parent, "Please select a row to delete.");
            dispose();
        }
    }
}
