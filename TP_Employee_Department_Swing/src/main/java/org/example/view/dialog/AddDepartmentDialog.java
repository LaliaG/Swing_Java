package org.example.view.dialog;

import org.example.controller.DepartmentController;
import org.example.view.layout.DepartmentLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDepartmentDialog extends JDialog {

    private JPanel formPanel;
    private JTextField nameField;
    private JButton addBtn, cancelBtn;
    private DepartmentController departmentController;

    public AddDepartmentDialog(DepartmentLayout departmentLayout) {
        this.departmentController = new DepartmentController();

        setTitle("Add Department");
        setSize(230, 150);
        setLayout(null);
        initializeComponents();
        configureLayout();
        addListeners();
    }

    private void initializeComponents() {
        formPanel = new JPanel();
        formPanel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 20);
        formPanel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(90, 20, 100, 20);
        formPanel.add(nameField);

        addBtn = new JButton("Add");
        addBtn.setBounds(10, 50, 80, 20);
        formPanel.add(addBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(110, 50, 80, 20);
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
                String name = nameField.getText();
                boolean success = departmentController.addDepartment(name);
                JOptionPane.showMessageDialog(AddDepartmentDialog.this,
                        success ? "Department added successfully." : "Department already exists.");

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


}
