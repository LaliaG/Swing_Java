package org.example.view.dialog;

import lombok.Data;
import org.example.controller.DepartmentController;
import org.example.controller.EmployeeController;
import org.example.model.Department;
import org.example.model.Role;
import org.example.model.Employee;
import org.example.view.layout.EmployeeLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.util.Arrays.stream;

@Data
public class AddEmployeeDialog extends JDialog {

    private JPanel formPanel;
    private JTextField firstnameInput, lastnameInput;
    private JRadioButton managerRadioBtn, employeeRadioBtn, rhRadioBtn;
    private JComboBox<String> departmentComboBox;
    private JButton addBtn, cancelBtn;
    private EmployeeController employeeController;
    private DepartmentController departmentController;
    private EmployeeLayout table;

    public AddEmployeeDialog(EmployeeLayout table) {
        this.table = table;
        this.employeeController = new EmployeeController();
        this.departmentController = new DepartmentController();

        setTitle("Add Employee");
        setSize(600, 350);
        setLayout(null);
        initializeComponents();
        configureLayout();
        addListeners();
    }

    private void initializeComponents() {
        formPanel = new JPanel();
        formPanel.setLayout(null);

        JLabel firstnameLabel = new JLabel("First Name:");
        firstnameLabel.setBounds(10, 20, 80, 20);
        formPanel.add(firstnameLabel);

        firstnameInput = new JTextField();
        firstnameInput.setBounds(100, 20, 120, 20);
        formPanel.add(firstnameInput);

        JLabel lastnameLabel = new JLabel("Last Name:");
        lastnameLabel.setBounds(10, 50, 80, 20);
        formPanel.add(lastnameLabel);

        lastnameInput = new JTextField();
        lastnameInput.setBounds(100, 50, 120, 20);
        formPanel.add(lastnameInput);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 80, 80, 20);
        formPanel.add(roleLabel);

        managerRadioBtn = new JRadioButton("MANAGER");
        employeeRadioBtn = new JRadioButton("EMPLOYEE");
        rhRadioBtn = new JRadioButton("RH");
        ButtonGroup roleBtnGroup = new ButtonGroup();
        roleBtnGroup.add(managerRadioBtn);
        roleBtnGroup.add(employeeRadioBtn);
        roleBtnGroup.add(rhRadioBtn);

        managerRadioBtn.setBounds(100, 80, 100, 20);
        employeeRadioBtn.setBounds(200, 80, 100, 20);
        rhRadioBtn.setBounds(300, 80, 100, 20);
        formPanel.add(managerRadioBtn);
        formPanel.add(employeeRadioBtn);
        formPanel.add(rhRadioBtn);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(10, 110, 80, 20);
        formPanel.add(departmentLabel);

        List<Department> departments = (List<Department>) departmentController.getDepartments();
        departmentComboBox = new JComboBox<>(
                departments.stream()
                        .map(Department::getName) // Récupérer les noms des départements
                        .toArray(String[]::new)   // Convertir en tableau de chaînes
        );
        departmentComboBox.setBounds(100, 110, 150, 20);
        formPanel.add(departmentComboBox);

        addBtn = new JButton("Add");
        addBtn.setBounds(250, 180, 70, 20);
        formPanel.add(addBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(330, 180, 100, 20);
        formPanel.add(cancelBtn);
    }

    private void configureLayout() {
        getContentPane().add(formPanel);
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameInput.getText();
                String lastname = lastnameInput.getText();
                String selectedRole = getSelectedRole();
                Role role = Role.valueOf(selectedRole);
                Department department = departmentController.getDepartmentByName((String) departmentComboBox.getSelectedItem());

                boolean success = employeeController.saveEmployee(firstname, lastname, role, department);
                JOptionPane.showMessageDialog(AddEmployeeDialog.this,
                        success ? "Employee added successfully!" : "Failed to add employee");

                if (success) {
                    dispose();
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private String getSelectedRole() {
        if (managerRadioBtn.isSelected()) return managerRadioBtn.getText();
        if (employeeRadioBtn.isSelected()) return employeeRadioBtn.getText();
        if (rhRadioBtn.isSelected()) return rhRadioBtn.getText();
        return "";
    }
}
