package org.example.dao;

import org.example.model.Employee;
import org.example.utils.ConnexionDb;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public  class EmployeeDAO implements IBaseDAO<Employee>{

    private Session session;
    private Transaction transaction;

    public EmployeeDAO() {
        this.session = ConnexionDb.getSessionFactory().openSession();
    }

    private void beginTransaction() {
        if (transaction == null) {
            transaction = session.beginTransaction();
        }
    }

    private void commitTransaction() {
        if (transaction != null) {
            transaction.commit();
            transaction = null;
        }
    }

    private void rollbackTransaction() {
        if (transaction != null) {
            transaction.rollback();
            transaction = null;
        }
    }

    @Override
    public List<Employee> getAll() {
        try {
            String hql = "FROM Employee";
            return session.createQuery(hql, Employee.class).getResultList();
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee findById(int id) {
        try {
            return session.get(Employee.class, id);
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(Employee employee) {
        try {
            beginTransaction();
            session.save(employee);
            commitTransaction();
            return 1; // Indicate success
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            beginTransaction();
            session.update(employee);
            commitTransaction();
            return 1; // Indicate success
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);
                commitTransaction();
                return true; // Indicate success
            } else {
                rollbackTransaction();
                return false; // Indicate failure
            }
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (session != null) {
            session.close();
        }
    }

}
