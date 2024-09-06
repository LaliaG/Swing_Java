package org.example.layout.dialog;

import org.example.dao.PersonDAO;
import org.example.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertDialog extends JDialog {

    private JTextField nameField, numberField;
    private JButton btnAdd, btnCancel;
    private PersonDAO personDAO;

    public InsertDialog(JFrame parent, PersonDAO personDAO, Object person) {
        super(parent, "Add Person", true);
        this.personDAO = personDAO;

        setLayout(new GridLayout(3, 2, 10, 10));

        // Champ de texte pour le nom
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // Champ de texte pour le numéro
        add(new JLabel("Number:"));
        numberField = new JTextField();
        add(numberField);

        // Boutons
        btnAdd = new JButton("Add");
        btnCancel = new JButton("Cancel");

        add(btnAdd);
        add(btnCancel);

        // Action sur le bouton Add
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String number = numberField.getText();

                // Ajout de la personne via le DAO
                personDAO.savePerson(person);
                new Person(new Person(name, number));

                // Fermer le dialogue après l'ajout
                dispose();
            }
        });

        // Action sur le bouton Cancel
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

   /* public InsertDialog(Frame parent, PersonDAO personDAO) {
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
    }*/
}
