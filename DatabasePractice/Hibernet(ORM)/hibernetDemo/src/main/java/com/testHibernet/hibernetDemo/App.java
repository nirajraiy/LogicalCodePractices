package com.testHibernet.hibernetDemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.config.ConfigurationException;

public class App {
    public static void main(String[] args) {
        // Create a new student object
        Student niraj = new Student();
        niraj.setId(1);
        niraj.setName("Avani");
        niraj.setFebColor("Blue");

        // Configure Hibernate
        try {
            Configuration con = new Configuration().configure().addAnnotatedClass(Student.class); // Load hibernate.cfg.xml
            SessionFactory sf = con.buildSessionFactory(); // Build SessionFactory

            // Open a session
            Session session = sf.openSession();
            session.beginTransaction(); // Begin transaction

            // Save the student object
            session.save(niraj);

            session.getTransaction().commit(); // Commit the transaction
            session.close(); // Close the session
            sf.close(); // Close the SessionFactory

        } catch (ConfigurationException e) {
            System.err.println("Hibernate configuration error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
