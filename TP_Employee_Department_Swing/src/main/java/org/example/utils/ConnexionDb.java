package org.example.utils;

import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class ConnexionDb {

    private static SessionFactory sessionFactory;

    // Configuration de la session Hibernate
    static {
        try {
            // Construction du registre à partir de la configuration
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

            // Construction du SessionFactory
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            if (sessionFactory != null) {
                StandardServiceRegistryBuilder.destroy(sessionFactory.getSessionFactoryOptions().getServiceRegistry());
            }
        }
    }

    // Méthode pour obtenir la session Hibernate
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // Méthode pour fermer le SessionFactory
    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static SessionBuilder getSessionFactory() {
        return null;
    }


   /* private static final SessionFactory sessionFactory;

    static {
        try {
            // Charge la configuration de Hibernate à partir du fichier hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log l'erreur pour mieux comprendre pourquoi la session factory n'est pas initialisée
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }*/




    /*private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("employee_manager");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        entityManagerFactory.close();
    }*/

    /*private static final String URI = "jdbc:mysql://localhost:3306/employee_manager";
    private static final String USER = "root";
    private static final String PASSWORD = "Laliayou19";

    public static Connection getConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(URI,USER,PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }*/
}
