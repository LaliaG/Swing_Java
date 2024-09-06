package org.example.controller;

import org.example.dao.EmployeeDAO;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;

import java.util.List;

public class EmployeeController {

    private final EmployeeDAO employeeDAO;

    public EmployeeController() {
        this.employeeDAO = new EmployeeDAO();
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    public Employee saveEmployee(Employee employee) {
        employeeDAO.save(employee);
        // Assuming that the save operation returns the saved employee object
        return employeeDAO.findById(employee.getId());
    }

    public boolean updateEmployee(Employee employee) {
        employeeDAO.update(employee);
        return false;
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            employeeDAO.delete(id);
        }
    }

    public void close() {
        employeeDAO.close();
    }

    public boolean saveEmployee(String firstname, String lastname, Role role, Department department) {
        return false;
    }
}
