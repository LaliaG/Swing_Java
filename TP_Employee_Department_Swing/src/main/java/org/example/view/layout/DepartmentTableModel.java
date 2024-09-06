package org.example.view.layout;

import org.example.model.Department;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DepartmentTableModel extends AbstractTableModel {

    private final String[] columns = {"ID", "Name"};
    private List<Department> departments;

    public DepartmentTableModel(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public int getRowCount() {
        return departments.size();
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
        Department department = departments.get(rowIndex);
        switch (columnIndex) {
            case 0: return department.getId();
            case 1: return department.getName();
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

    public void setData(List<Department> departments) {
        this.departments = departments;
        fireTableDataChanged();
    }

    public Department getDepartmentAt(int selectedRow) {
        return null;
    }
}
