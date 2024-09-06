package org.example.layout.dialog;

import org.example.dao.PersonDAO;
import org.example.layout.MainFrame;
import org.example.model.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateDialog extends JDialog {

    public UpdateDialog(MainFrame parent, PersonDAO personDAO, DefaultTableModel tableModel, JTable table) {
        super(parent, "Update Contact Details", true);

        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            setLayout(new GridLayout(3, 2, 10, 10));

            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            Person person = personDAO.getPersonById(id);

            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(person.getName());

            JLabel numberLabel = new JLabel("Number:");
            JTextField numberField = new JTextField(person.getNumber());

            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            add(nameLabel);
            add(nameField);
            add(numberLabel);
            add(numberField);
            add(okButton);
            add(cancelButton);

            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String number = numberField.getText();

                    if (!name.isEmpty() && !number.isEmpty()) {
                        person.setName(name);
                        person.setNumber(number);

                        personDAO.updatePerson(person);
                        tableModel.setValueAt(person.getName(), selectedRow, 1);
                        tableModel.setValueAt(person.getNumber(), selectedRow, 2);
                        JOptionPane.showMessageDialog(parent, "Person updated successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(parent, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
            JOptionPane.showMessageDialog(parent, "Please select a row to update.");
            dispose();
        }
    }

    public UpdateDialog(MainFrame parent, PersonDAO personDAO, int personId) {
    }
}
