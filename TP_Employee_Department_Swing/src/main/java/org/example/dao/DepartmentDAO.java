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
        this.session = ConnexionDb.getSession();
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
            beginTransaction(); // Ajout de la transaction
            String hql = "FROM Department";
            List<Department> result = session.createQuery(hql, Department.class).getResultList();
            commitTransaction(); // Validation de la transaction
            return result;
        } catch (Exception e) {
            rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department findById(int id) {
        try {
            beginTransaction();
            Department department = session.get(Department.class, id);
            commitTransaction();
            return department;
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
            beginTransaction();
            String hql = "FROM Department WHERE name = :name";
            Department department = session.createQuery(hql, Department.class)
                    .setParameter("name", name)
                    .uniqueResult();
            commitTransaction();
            return department;
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
