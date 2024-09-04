package org.example.layout;

import org.example.layout.dialog.DeleteDialog;
import org.example.layout.dialog.SelectDialog;
import org.example.layout.dialog.UpdateDialog;
import org.example.model.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private PersonDAO personDAO = new PersonDAO();

    public MainFrame() {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Créer les boutons
        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton selectButton = new JButton("Select");

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);

        // Table pour afficher les données
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Number"}, 0);
        table = new JTable(tableModel);
        loadPersons();  // Charger les données

        // ScrollPane pour la table
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Layout principal
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Actions des boutons
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertDialog insertDialog = new InsertDialog(MainFrame.this, personDAO, tableModel);
                insertDialog.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateDialog updateDialog = new UpdateDialog(MainFrame.this, personDAO, tableModel, table);
                updateDialog.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteDialog deleteDialog = new DeleteDialog(MainFrame.this, personDAO, tableModel, table);
                deleteDialog.setVisible(true);
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectDialog selectDialog = new SelectDialog(MainFrame.this, personDAO, tableModel, table);
                selectDialog.setVisible(true);
            }
        });

        setVisible(true);
    }

    private void loadPersons() {
        List<Person> persons = personDAO.getAllPersons();
        for (Person person : persons) {
            tableModel.addRow(new Object[]{person.getId(), person.getName(), person.getNumber()});
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
