package org.example.controller;

import org.example.dao.DepartmentDAO;
import org.example.model.Department;

import java.util.Arrays;
import java.util.List;

public class DepartmentController {

    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    public List<Department> getAllDepartments() {
        return departmentDAO.getAll();
    }

    public boolean addDepartment(Department department) {
        departmentDAO.save(department);
        return false;
    }

    public void updateDepartment(Department department) {
        departmentDAO.update(department);
    }

    public void deleteDepartment(int id) {
        Department department = departmentDAO.findById(id);
        if (department != null) {
            departmentDAO.delete(department.getId());
        }
    }

    public Department getDepartmentByName(String selectedItem) {
        return null;
    }

    public boolean updateDepartment(int id, String newName) {
        return false;
    }

    public boolean addDepartment(String name) {
        return false;
    }

    public Arrays getDepartments() {
        return null;
    }
}
