package org.example.view.layout;

import org.example.dao.DepartmentDAO;
import org.example.dao.EmployeeDAO;
import org.example.model.Employee;
import org.example.view.dialog.AddEmployeeDialog;
import org.example.view.dialog.UpdateEmployeeDialog;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class EmployeeLayout extends JFrame {

    private JPanel contentPanel, btnPanel;
    private JTable table;
    private JButton addBtn, updateBtn, deleteBtn, departmentBtn;
    private TableModel employeeTableModel;
    private final EmployeeDAO employeeDAO;

    public EmployeeLayout(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;

        // Initialisation du panneau de contenu
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Chargement initial des données et configuration du tableau
        refreshTableData();
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Création des boutons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        departmentBtn = new JButton("Department");

        // Panneau des boutons
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(departmentBtn);

        contentPanel.add(btnPanel, BorderLayout.SOUTH);

        // Actions des boutons
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeDialog dialog = new AddEmployeeDialog(EmployeeLayout.this);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                refreshTableData(); // Mise à jour des données après ajout
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Employee employee = ((EmployeeTableModel) employeeTableModel).getEmployeeAt(selectedRow);
                    UpdateEmployeeDialog dialog = new UpdateEmployeeDialog(employee);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    refreshTableData(); // Mise à jour après modification
                } else {
                    JOptionPane.showMessageDialog(EmployeeLayout.this, "Please select an employee to update.");
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Employee employee = ((EmployeeTableModel) employeeTableModel).getEmployeeAt(selectedRow);
                    employeeDAO.delete(employee.getId());
                    refreshTableData(); // Mise à jour après suppression
                } else {
                    JOptionPane.showMessageDialog(EmployeeLayout.this, "Please select an employee to delete.");
                }
            }
        });

        departmentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                DepartmentLayout departmentLayout = new DepartmentLayout(new DepartmentDAO());
                departmentLayout.setLocationRelativeTo(null);
                departmentLayout.setVisible(true);
            }
        });

        // Configuration de la fenêtre
        setContentPane(contentPanel);
        setTitle("Employee Manager");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void refreshTableData() {
        List<Employee> employees = employeeDAO.getAll();
        employeeTableModel = new EmployeeTableModel(employees);
        table = new JTable(employeeTableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            EmployeeLayout employeeLayout = new EmployeeLayout(employeeDAO);
            employeeLayout.setVisible(true);
        });
    }


}
