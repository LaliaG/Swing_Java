package org.example.utils;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ConnexionDb {

    // Optionnel : Méthode pour obtenir le SessionFactory
    @Getter
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
/* quand on utilise Lombok ligne 47-49 disparait
    // Optionnel : Méthode pour obtenir le SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
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
