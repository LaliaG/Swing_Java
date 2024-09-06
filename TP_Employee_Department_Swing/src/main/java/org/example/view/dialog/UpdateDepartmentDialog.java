package org.example.view.dialog;

import lombok.Data;
import org.example.controller.DepartmentController;
import org.example.model.Department;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class UpdateDepartmentDialog extends JDialog {

    private JPanel formPanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton updateBtn, cancelBtn;
    private DepartmentController departmentController;
    private Department department;

    public UpdateDepartmentDialog(Department department) {
        this.department = department;
        this.departmentController = new DepartmentController();

        setTitle("Update Department");
        setSize(250, 200);
        formPanel = new JPanel();
        formPanel.setLayout(null);

        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(department.getName());

        nameLabel.setBounds(15, 25, 80, 20);
        nameField.setBounds(80, 25, 100, 20);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(20, 50, 80, 30);
        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(120, 50, 80, 30);

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText();
                if (departmentController.updateDepartment(department.getId(), newName)) {
                    JOptionPane.showMessageDialog(UpdateDepartmentDialog.this, "Department updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(UpdateDepartmentDialog.this, "Failed to update department.");
                }
                dispose();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(updateBtn);
        formPanel.add(cancelBtn);

        getContentPane().add(formPanel);
    }
}
