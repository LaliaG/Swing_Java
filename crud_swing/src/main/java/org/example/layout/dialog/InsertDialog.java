package org.example.layout.dialog;

import org.example.model.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertDialog extends JDialog {

    public InsertDialog(Frame parent, PersonDAO personDAO, DefaultTableModel tableModel) {
        super(parent, "Insert Contact Details", true);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel numberLabel = new JLabel("Number:");
        JTextField numberField = new JTextField();

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
                    Person person = new Person();
                    person.setName(name);
                    person.setNumber(number);

                    personDAO.savePerson(person);
                    tableModel.addRow(new Object[]{person.getId(), person.getName(), person.getNumber()});
                    JOptionPane.showMessageDialog(parent, "Person added successfully!");
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
    }
}
