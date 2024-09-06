package org.example.view.dialog;

import lombok.Data;
import org.example.controller.DepartmentController;
import org.example.controller.EmployeeController;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Data
public class UpdateEmployeeDialog extends JDialog {

    private JPanel formPanel;
    private JTextField firstnameInput, lastnameInput;
    private JRadioButton managerRadioBtn, employeeRadioBtn, rhRadioBtn;
    private JComboBox<String> departmentComboBox;
    private JButton updateBtn, cancelBtn;
    private EmployeeController employeeController;
    private DepartmentController departmentController;
    private Employee employee;

    public UpdateEmployeeDialog(Employee employee) {
        this.employee = employee;
        this.employeeController = new EmployeeController();
        this.departmentController = new DepartmentController();

        setTitle("Update Employee");
        setSize(580, 350);
        setModal(true); // Rend la boîte de dialogue modale
        setLocationRelativeTo(null); // Centre la boîte de dialogue sur l'écran

        formPanel = new JPanel();
        formPanel.setLayout(null); // Utilisation d'un layout nul pour positionnement manuel

        // Création des labels
        JLabel firstnameLabel = new JLabel("First Name :");
        firstnameLabel.setBounds(10, 20, 80, 20);
        JLabel lastnameLabel = new JLabel("Last Name :");
        lastnameLabel.setBounds(10, 50, 80, 20);
        JLabel roleLabel = new JLabel("Role :");
        roleLabel.setBounds(10, 80, 80, 20);
        JLabel departmentLabel = new JLabel("Department :");
        departmentLabel.setBounds(10, 110, 100, 20);

        // Création des champs de texte
        firstnameInput = new JTextField(employee.getFirstname());
        firstnameInput.setBounds(120, 20, 150, 20);

        lastnameInput = new JTextField(employee.getLastname());
        lastnameInput.setBounds(120, 50, 150, 20);

        // Création des boutons radio pour le rôle
        managerRadioBtn = new JRadioButton("MANAGER");
        managerRadioBtn.setBounds(120, 80, 100, 20);
        employeeRadioBtn = new JRadioButton("EMPLOYEE");
        employeeRadioBtn.setBounds(220, 80, 100, 20);
        rhRadioBtn = new JRadioButton("RH");
        rhRadioBtn.setBounds(320, 80, 100, 20);

        // Groupement des boutons radio
        ButtonGroup roleBtnGroup = new ButtonGroup();
        roleBtnGroup.add(managerRadioBtn);
        roleBtnGroup.add(employeeRadioBtn);
        roleBtnGroup.add(rhRadioBtn);

        // Sélection du bouton radio en fonction du rôle de l'employé
        Role role = employee.getRole();
        if (role == Role.MANAGER) {
            managerRadioBtn.setSelected(true);
        } else if (role == Role.EMPLOYEE) {
            employeeRadioBtn.setSelected(true);
        } else if (role == Role.RH) {
            rhRadioBtn.setSelected(true);
        }

        // Récupération des départements et préparation du tableau de noms
        List<Department> departments = (List<Department>) departmentController.getDepartments();
        String[] departmentNames;

        if (departments != null && !departments.isEmpty()) {
            departmentNames = departments.stream()
                    .map(Department::getName)
                    .toArray(String[]::new);
        } else {
            // Gérer le cas où la liste est vide ou nulle
            departmentNames = new String[]{"No Departments Available"};
        }

        // Création du JComboBox pour les départements
        departmentComboBox = new JComboBox<>(departmentNames);
        departmentComboBox.setBounds(120, 110, 150, 20);
        departmentComboBox.setSelectedItem(employee.getDepartment().getName());

        // Création des boutons Update et Cancel
        updateBtn = new JButton("Update");
        updateBtn.setBounds(120, 180, 100, 30);
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateAction();
            }
        });

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(240, 180, 100, 30);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la boîte de dialogue
            }
        });

        // Ajout des composants au panneau de formulaire
        formPanel.add(firstnameLabel);
        formPanel.add(firstnameInput);
        formPanel.add(lastnameLabel);
        formPanel.add(lastnameInput);
        formPanel.add(roleLabel);
        formPanel.add(managerRadioBtn);
        formPanel.add(employeeRadioBtn);
        formPanel.add(rhRadioBtn);
        formPanel.add(departmentLabel);
        formPanel.add(departmentComboBox);
        formPanel.add(updateBtn);
        formPanel.add(cancelBtn);

        // Ajout du panneau de formulaire au contenu de la boîte de dialogue
        getContentPane().add(formPanel);
    }

    /**
     * Méthode pour gérer l'action de mise à jour de l'employé.
     */
    private void handleUpdateAction() {
        String firstname = firstnameInput.getText().trim();
        String lastname = lastnameInput.getText().trim();
        String selectedRole = "";

        if (managerRadioBtn.isSelected()) {
            selectedRole = managerRadioBtn.getText();
        } else if (employeeRadioBtn.isSelected()) {
            selectedRole = employeeRadioBtn.getText();
        } else if (rhRadioBtn.isSelected()) {
            selectedRole = rhRadioBtn.getText();
        }

        // Validation du rôle sélectionné
        Role updatedRole;
        try {
            updatedRole = Role.valueOf(selectedRole);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid role selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupération du département sélectionné
        String selectedDepartmentName = (String) departmentComboBox.getSelectedItem();
        Department department = null;
        if (selectedDepartmentName != null && !selectedDepartmentName.isEmpty() && !"No Departments Available".equals(selectedDepartmentName)) {
            department = departmentController.getDepartmentByName(selectedDepartmentName);
            if (department == null) {
                JOptionPane.showMessageDialog(this, "Selected department does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a valid department.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mise à jour des détails de l'employé
        employee.setFirstname(firstname);
        employee.setLastname(lastname);
        employee.setRole(updatedRole);
        employee.setDepartment(department);

        // Appel au contrôleur pour mettre à jour l'employé
        boolean success = employeeController.updateEmployee(employee);
        if (success) {
            JOptionPane.showMessageDialog(this, "Employee updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Ferme la boîte de dialogue après succès
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
