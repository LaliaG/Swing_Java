package org.example.dao;

import org.example.model.Department;

public interface IDepartmentDAO extends IBaseDAO<Department>{

    Department getDepartmentByName(String name);
}
