package com.testHibernet.MavanProject;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Project Started!!!" );
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
    }
}
