package com.nagarro.flightsystem.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.nagarro.flightsystem.model.FlightData;

public class HibernateConnection {
    public Session getConnection() {
        Configuration configuration = new Configuration().configure().addAnnotatedClass(FlightData.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        return session;
    }
}
