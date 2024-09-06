package org.example.dao;

import lombok.Data;
import org.example.model.Person;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
public class PersonDAO {

    private Session session;

    public PersonDAO() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void savePerson(Object person) {
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
            System.out.println("Person added successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updatePerson(Person person) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
            System.out.println("Person updated successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletePerson(Long id) {
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();
            Person person = session.get(Person.class, id);
            if (person != null) {
                session.delete(person);
                System.out.println("Person deleted successfully");
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Person getPersonById(Long id) {
        try {
            return session.get(Person.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Person> getAllPersons() {
        try {
            Query<Person> query = session.createQuery("FROM Person", Person.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
