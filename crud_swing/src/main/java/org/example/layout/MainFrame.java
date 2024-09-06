package org.example.layout;

import org.example.dao.PersonDAO;
import org.example.layout.dialog.InsertDialog;
import org.example.layout.dialog.UpdateDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JButton btnInsert, btnUpdate, btnDelete, btnSelect;
    private PersonDAO personDAO;

    public MainFrame(Object person) {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Initialisation du DAO
        personDAO = new PersonDAO();

        // Création du panel pour les boutons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        // Boutons
        btnInsert = new JButton("Insert");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSelect = new JButton("Select");

        // Ajout des boutons au panel
        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSelect);

        // Ajout du panel au frame
        add(panel);

        // Action listeners pour les boutons
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertDialog insertDialog = new InsertDialog(MainFrame.this, personDAO, person);
                insertDialog.setVisible(true);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int personId = getSelectedPersonId();
                if (personId != -1) {
                    UpdateDialog updateDialog = new UpdateDialog(MainFrame.this, personDAO, personId);
                    updateDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a person first.");
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int personId = getSelectedPersonId();
                if (personId != -1) {
                    int confirm = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        personDAO.deletePerson((long) personId);
                        // Rafraîchir la table ou une autre action après suppression
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a person first.");
                }
            }
        });

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implémenter la logique de sélection (affichage des données, etc.)
                JOptionPane.showMessageDialog(MainFrame.this, "Select action clicked.");
            }
        });
    }

    public MainFrame() {

    }

    private int getSelectedPersonId() {
        // Simuler une sélection dans une liste ou table
        // Retourner -1 si aucune personne n'est sélectionnée, ou retourner l'ID de la personne sélectionnée
        return -1; // À mettre à jour selon votre logique
    }

    public static void main(String[] args, Object person) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame(person).setVisible(true);
            }
        });
    }

  /*  private JButton btnInsert, btnUpdate, btnDelete, btnSelect;
    private PersonDAO personDAO;

   /* private DefaultTableModel tableModel;
    private JTable table;
    private PersonDAO personDAO = new PersonDAO();

    public MainFrame() {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Initialisation du DAO
        personDAO = new PersonDAO();

        // Création du panel pour les boutons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

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
    }*/
}
