/*
* Class name: SaveFlightDataImpl
*
* Version info: jdk 1.8
*
* Copyright notice:
* 
* Author info: Arpit Garg
*
* Creation date: 27/Mar/2021
*
* Last updated By: Arpit Garg
*
* Last updated Date: 30/Mar/2021
*
* Description: save all the flights in database
*/
package com.nagarro.flightsystem.dao.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nagarro.flightsystem.dao.SaveFlightData;
import com.nagarro.flightsystem.model.FlightData;
import com.nagarro.flightsystem.utils.HibernateConnection;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@SuppressWarnings("deprecation")
public class SaveFlightDataImpl implements SaveFlightData {
    private static final String C_USERS_ARPITGARG02_ECLIPSE_WORKSPACE_FLIGHT_SYSTEM_BIN_RESOURCES = "C:/Users/arpitgarg02/eclipse-workspace/FlightSystem/bin/resources/";
    static ArrayList<String> flightInformation;
    private String departureLocation;
    private String arrivalLocaton;
    private String flightDate;
    private String flightClass;

    /**
     * save all the flights
     * 
     * @throws IOException
     * @throws CsvValidationException
     * @throws NumberFormatException
     */
    public void saveFlight(String fileName) throws IOException, CsvValidationException, NumberFormatException {
        String fileForRead = C_USERS_ARPITGARG02_ECLIPSE_WORKSPACE_FLIGHT_SYSTEM_BIN_RESOURCES + fileName;
        try (CSVReader reader = new CSVReader(new FileReader(fileForRead))) {
            String[] lineInCSV = reader.readNext();
            while ((lineInCSV = reader.readNext()) != null) {
                String[] flight = lineInCSV[0].split("\\|");
                FlightData flightInformation = new FlightData();
                flightInformation.setFlightNumber(flight[0]);
                flightInformation.setDepartureLocation(flight[1]);
                flightInformation.setArrivalLocation(flight[2]);
                flightInformation.setValidTill(flight[3]);
                flightInformation.setFlightTime(flight[4]);
                flightInformation.setFlightDuration(Float.parseFloat(flight[5]));
                flightInformation.setFare(Float.parseFloat(flight[6]));
                flightInformation.setSeatAvailability(flight[7]);
                flightInformation.setFlightClass(flight[8]);
                Session session = new HibernateConnection().getConnection();
                Transaction transaction = session.beginTransaction();
                session.save(flightInformation);
                transaction.commit();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get flights from database
     */
    public void getFlight(FlightData flightForSearch) {
        this.departureLocation = flightForSearch.getDepartureLoaction();
        this.arrivalLocaton = flightForSearch.getArrivalLocation();
        this.flightDate = flightForSearch.getValidTill();
        this.flightClass = flightForSearch.getFlightClass();
        Session session = new HibernateConnection().getConnection();
        @SuppressWarnings("unchecked")
        Query<FlightData> query = session.createQuery("from FlightData");
        List<FlightData> flightDataList = query.list();
        Iterator<FlightData> iterator = flightDataList.iterator();
        while (iterator.hasNext()) {
            FlightData flightInformation = iterator.next();
            if (flightInformation.getDepartureLoaction().equals(departureLocation)
                    && flightInformation.getArrivalLocation().equals(arrivalLocaton)
                    && flightInformation.getValidTill().equals(flightDate)
                    && (flightInformation.getFlightClass().equals(flightClass)
                            || flightInformation.getFlightClass().contains(flightClass))) {
                new FlightData().addFlight(flightInformation);
            }
        }
    }
}