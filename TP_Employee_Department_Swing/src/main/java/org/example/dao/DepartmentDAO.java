package org.example.dao;

import org.example.model.Department;
import org.example.utils.ConnexionDb;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentDAO implements IBaseDAO<Department>{

    private Session session;
    private Transaction transaction;

    public DepartmentDAO() {
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
    public List<Department> getAll() {
        try {
            String hql = "FROM Department";
            return session.createQuery(hql, Department.class).getResultList();
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department findById(int id) {
        try {
            return session.get(Department.class, id);
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(Department department) {
        try {
            beginTransaction();
            session.save(department);
            commitTransaction();
            return 1; // Indicate success
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Department department) {
        try {
            beginTransaction();
            session.update(department);
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
            Department department = session.get(Department.class, id);
            if (department != null) {
                session.delete(department);
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

    public Department getDepartmentByName(String name) {
        try {
            String hql = "FROM Department WHERE name = :name";
            return session.createQuery(hql, Department.class)
                    .setParameter("name", name)
                    .uniqueResult();
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
