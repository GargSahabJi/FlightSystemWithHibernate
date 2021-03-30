/*
* Class name: HibernateConnection
*
* Version info: jdk 1.8
*
* Copyright notice:
* 
* Author info: Arpit Garg
*
* Creation date: 26/Mar/2021
*
* Last updated By: Arpit Garg
*
* Last updated Date: 30/Mar/2021
*
* Description: Used for creating connection with database
*/
package com.nagarro.flightsystem.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.nagarro.flightsystem.model.FlightData;

public class HibernateConnection {
    /**
     * @return session
     */
    public Session getConnection() {
        Configuration configuration = new Configuration().configure().addAnnotatedClass(FlightData.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        return session;
    }
}
