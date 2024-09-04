package org.example.layout;

import org.example.model.Personne;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class FormLayout extends JFrame{

    private JPanel mainPanel;
    private JPanel formPanel;
    private JButton validateButton;
    private JButton detailButton;
    private JTable dataTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private Personne personne;

    public FormLayout() {
        // Initialisation des composants
        mainPanel = new JPanel(new BorderLayout());
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajouter des marges

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; // Aligné à gauche

        // Titre du formulaire
        JLabel formTitle = new JLabel("Formulaire d'ajout");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Étendre sur deux colonnes pour un alignement uniforme
        formPanel.add(formTitle, gbc);

        // Panneau du nom
        JLabel labelName = new JLabel("Nom :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(labelName, gbc);

        JTextField inputName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(inputName, gbc);

        // Panneau de l'email
        JLabel labelEmail = new JLabel("Email :");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(labelEmail, gbc);

        JTextField inputEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(inputEmail, gbc);

        // Panneau du genre
        JLabel labelGender = new JLabel("Genre :");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(labelGender, gbc);

        JRadioButton radioButtonH = new JRadioButton("Homme");
        JRadioButton radioButtonF = new JRadioButton("Femme");

        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(radioButtonH);
        genderButtonGroup.add(radioButtonF);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(radioButtonH);
        genderPanel.add(radioButtonF);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(genderPanel, gbc);

        // Bouton de validation
        validateButton = new JButton("Ajouter");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(validateButton, gbc);

        validateButton.addActionListener(e -> {
            personne = new Personne();
            personne.setName(inputName.getText());
            personne.setEmail(inputEmail.getText());

            if (radioButtonH.isSelected()) {
                personne.setGender("Homme");
            } else if (radioButtonF.isSelected()) {
                personne.setGender("Femme");
            }

            // Ajouter les informations de la personne au tableau
            Vector<String> rowData = new Vector<>();
            rowData.add(personne.getName());
            rowData.add(personne.getEmail());
            rowData.add(personne.getGender());
            tableModel.addRow(rowData);

            // Réinitialiser les champs de saisie
            inputName.setText("");
            inputEmail.setText("");
            genderButtonGroup.clearSelection();
        });

        // Panneau pour le tableau des données
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Titre du tableau
        JLabel tableTitle = new JLabel("Tableau des données");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        // Configuration du tableau
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nom");
        tableModel.addColumn("Email");
        tableModel.addColumn("Genre");

        dataTable = new JTable(tableModel);
        scrollPane = new JScrollPane(dataTable);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Bouton des détails, placé sous le tableau
        detailButton = new JButton("Détails");
        detailButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                String nom = tableModel.getValueAt(selectedRow, 0).toString();
                String email = tableModel.getValueAt(selectedRow, 1).toString();
                String genre = tableModel.getValueAt(selectedRow, 2).toString();

                // Créer une personne à partir de la ligne sélectionnée
                Personne selectedPerson = new Personne();
                selectedPerson.setName(nom);
                selectedPerson.setEmail(email);
                selectedPerson.setGender(genre);

                // Afficher les détails dans une boîte de dialogue
                JOptionPane.showMessageDialog(null, selectedPerson.toString(), "Détails de la personne", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        tablePanel.add(detailButton, BorderLayout.SOUTH);  // Place le bouton "Détails" en bas

        // Ajouter les panneaux au panneau principal
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Ajouter le panneau principal à la fenêtre
        this.getContentPane().add(mainPanel);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
