package org.example.view.layout;

import org.example.dao.DepartmentDAO;
import org.example.dao.EmployeeDAO;
import org.example.model.Department;
import org.example.view.dialog.AddDepartmentDialog;
import org.example.view.dialog.UpdateDepartmentDialog;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DepartmentLayout extends JFrame {

    private JPanel contentPanel, btnPanel;
    private JTable table;
    private JButton addBtn, updateBtn, deleteBtn, employeeBtn;
    private TableModel departmentTableModel;
    private final DepartmentDAO departmentDAO;

    public DepartmentLayout(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;

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
        employeeBtn = new JButton("Employee");

        // Panneau des boutons
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(employeeBtn);

        contentPanel.add(btnPanel, BorderLayout.SOUTH);

        // Actions des boutons
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDepartmentDialog dialog = new AddDepartmentDialog(DepartmentLayout.this);
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
                    Department department = ((DepartmentTableModel) departmentTableModel).getDepartmentAt(selectedRow);
                    UpdateDepartmentDialog dialog = new UpdateDepartmentDialog(department);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    refreshTableData(); // Mise à jour après modification
                } else {
                    JOptionPane.showMessageDialog(DepartmentLayout.this, "Please select a department to update.");
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Department department = ((DepartmentTableModel) departmentTableModel).getDepartmentAt(selectedRow);
                    departmentDAO.delete(department.getId());
                    refreshTableData(); // Mise à jour après suppression
                } else {
                    JOptionPane.showMessageDialog(DepartmentLayout.this, "Please select a department to delete.");
                }
            }
        });

        employeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EmployeeLayout employeeLayout = new EmployeeLayout(new EmployeeDAO());
                employeeLayout.setLocationRelativeTo(null);
                employeeLayout.setVisible(true);
            }
        });

        // Configuration de la fenêtre
        setContentPane(contentPanel);
        setTitle("Department Manager");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void refreshTableData() {
        List<Department> departments = departmentDAO.getAll();
        departmentTableModel = new DepartmentTableModel(departments);
        table = new JTable(departmentTableModel);
    }

    public JPanel getContentPanel() {
        return contentPanel; // Correction ici
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            DepartmentLayout departmentLayout = new DepartmentLayout(departmentDAO);
            departmentLayout.setVisible(true);
        });
    }
}
