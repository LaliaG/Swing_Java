package org.example.dao;

import lombok.Data;
import org.example.model.Person;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
public class PersonDAO {

    public void savePerson(Person person) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePerson(Person person) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deletePerson(Person person) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(person);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Person getPersonById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Person.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Person> getAllPersons() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Person", Person.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
