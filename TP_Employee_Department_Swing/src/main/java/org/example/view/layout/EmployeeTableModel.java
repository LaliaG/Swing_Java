package org.example.view.layout;

import org.example.model.Employee;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Firstname", "Lastname", "Role", "Department"};
    private List<Employee> employees;

    public EmployeeTableModel(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0: return employee.getId();
            case 1: return employee.getFirstname();
            case 2: return employee.getLastname();
            case 3: return employee.getRole();
            case 4: return employee.getDepartment().getName();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Implement if cells need to be editable
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        super.removeTableModelListener(l);
    }

    public void setData(List<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    public Employee getEmployeeAt(int selectedRow) {
        return null;
    }
}
