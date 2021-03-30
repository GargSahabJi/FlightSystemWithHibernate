/*
* Class name: Main
*
* Version info: jdk 1.8
*
* Copyright notice:
* 
* Author info: Arpit Garg
*
* Creation date: 23/Mar/2021
*
* Last updated By: Arpit Garg
*
* Last updated Date: 24/Mar/2021
*
* Description: Main Class
*/
package com.nagarro.flightsystem.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nagarro.flightsystem.dao.impl.SaveFlightDataImpl;
import com.nagarro.flightsystem.exception.InvalidStateException;
import com.nagarro.flightsystem.model.FlightData;
import com.nagarro.flightsystem.utils.FindFolderFiles;
import com.nagarro.flightsystem.utils.FlightTracker;
import com.nagarro.flightsystem.utils.HibernateConnection;
import com.nagarro.flightsystem.utils.ValidationUtils;
import com.opencsv.exceptions.CsvValidationException;

public class Main {
    private static final String RE_ENTER_FLIGHT_DATE = "Re-enter flight date";
    private static final String RE_ENTER_CHOICE = "Re-enter choice";
    private static final String RE_ENTER_FLIGHT_CLASS = "Re-enter flight class";
    private static final String RE_ENTER_ARRIVAL_LOCATION = "Re-enter arrival Location";
    private static final String RE_ENTER_DEPARTURE_LOCATION = "Re-enter departure location";
    private static final String ENTER_DATE_IN_FORMATE_OF_DD_MM_YYYY = "Enter Date in formate of DD-MM-YYYY: ";
    private static final String ENTER_OUTPUT_PREFERENCE_1_FARE_2_FLIGHT_DURATION_ENTER_PREFERENCE_CHOICE_CODE = "Enter Output Preference :   1. Fare \t 2. Flight Duration \nEnter Preference Choice Code : ";
    private static final String ENTER_CLASS = "Enter Class : ";
    private static final String ENTER_ARRIVAL_LOCATION = "Enter Arrival Location  : ";
    private static final String ENTER_DEPARTURE_LOCATION = "Enter Departure Location  : ";
    private static Scanner scan = new Scanner(System.in);
    private static String departureLocation;
    private static String arrivalLocation;
    private static String flightClass;
    private static int sortingChoice;
    private static String flightDate;

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws NumberFormatException
     * @throws CsvValidationException
     */
    public static void main(String[] args)
            throws InterruptedException, CsvValidationException, NumberFormatException, IOException {
        ArrayList<String> filesName = new FindFolderFiles().getAllFiles();
        for (int i = 0; i < filesName.size(); i++) {
            new SaveFlightDataImpl().saveFlight(filesName.get(i));
        }
        char ch;
        do {
            enterFlightDetails();
            System.out.println("Press y for continue.");
            ch = scan.next().charAt(0);
        } while (ch == 'y' || ch == 'Y');
        clearFlightTable();
    }

    public static void clearFlightTable() {
        Session session = new HibernateConnection().getConnection();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from FlightData").executeUpdate();
        transaction.commit();
    }

    public static void enterFlightDetails() {
        enterDepartueLoacation();
        enterArrivalLocation();
        enterFlightDate();
        enterFlightClass();
        enterSortingChoice();
        FlightData flight = new FlightData();
        flight.setArrivalLocation(arrivalLocation);
        flight.setDepartureLocation(departureLocation);
        flight.setValidTill(flightDate);
        flight.setFlightClass(flightClass);
        new FlightTracker().getFlightData(flight, sortingChoice);
    }

    /**
     * Enter Flight Date
     */
    private static void enterFlightDate() {
        System.out.print(ENTER_DATE_IN_FORMATE_OF_DD_MM_YYYY);
        boolean flag = true;
        while (flag) {
            flightDate = scan.next().toUpperCase();
            try {
                new ValidationUtils().flightDateValidation(flightDate);
                flag = false;
            } catch (InvalidStateException e) {
                System.out.println(RE_ENTER_FLIGHT_DATE);
                flag = true;
            }
        }

    }

    /**
     * Enter your Sorting choice
     */
    private static void enterSortingChoice() {
        System.out.print(ENTER_OUTPUT_PREFERENCE_1_FARE_2_FLIGHT_DURATION_ENTER_PREFERENCE_CHOICE_CODE);
        boolean flag = true;
        while (flag) {
            String choice = scan.next();
            try {
                new ValidationUtils().choiceValidation(choice);
                flag = false;
                sortingChoice = Integer.parseInt(choice);
            } catch (InvalidStateException e) {
                System.out.println(RE_ENTER_CHOICE);
                flag = true;
            }
        }
    }

    /**
     * Enter class of Flight
     */
    private static void enterFlightClass() {
        System.out.print("Enter Flight Class:\n 1. E-Econimcal \t 2. B- Business :");
        boolean flag = true;
        while (flag) {
            flightClass = scan.next().toUpperCase();
            try {
                new ValidationUtils().classValidation(flightClass);
                new ValidationUtils().flightClassValidation(flightClass);
                flag = false;
            } catch (InvalidStateException e) {
                System.out.println(RE_ENTER_FLIGHT_CLASS);
                flag = true;
            }
        }
    }

    /**
     * Enter Arrival Location
     */
    private static void enterArrivalLocation() {
        System.out.print(ENTER_ARRIVAL_LOCATION);
        boolean flag = true;
        while (flag) {
            arrivalLocation = scan.next().toUpperCase();
            try {
                new ValidationUtils().nameValidation(arrivalLocation);
                flag = false;
            } catch (InvalidStateException e) {
                System.out.println(RE_ENTER_ARRIVAL_LOCATION);
                flag = true;
            }
        }
    }

    /**
     * Enter Departure Location
     */
    private static void enterDepartueLoacation() {
        System.out.print(ENTER_DEPARTURE_LOCATION);
        boolean flag = true;
        while (flag) {
            departureLocation = scan.next().toUpperCase();
            try {
                new ValidationUtils().nameValidation(departureLocation);
                flag = false;
            } catch (InvalidStateException e) {
                System.out.println(RE_ENTER_DEPARTURE_LOCATION);
                flag = true;
            }
        }
    }
}