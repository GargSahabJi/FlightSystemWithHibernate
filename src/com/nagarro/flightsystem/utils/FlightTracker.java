/*
* Class name: FlightTracker
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
* Description: Used for the print list of flights
*/
package com.nagarro.flightsystem.utils;

import java.util.ArrayList;
import java.util.Collections;
import com.nagarro.flightsystem.dao.impl.SaveFlightDataImpl;
import com.nagarro.flightsystem.model.FlightData;

public class FlightTracker {
    private static final String NO_RECORD_FOUND = "No Record Found";
    private static FlightData flight = new FlightData();
    private ArrayList<String> filesName = new ArrayList<>();
    private String departureLocation;
    private String arrivalLocation;
    private String flightDate;
    private String flightClass;
    private int sortingChoice;

    /**
     * @param filesName
     * @param departureLocation
     * @param arrivalLocation
     * @param flightDate
     * @param flightClass
     * @param sortingChoice
     */

    /**
     * Find the flights
     */
    public void getFlightData(FlightData flight, int sortingChoice) {
        this.departureLocation = flight.getDepartureLoaction();
        this.arrivalLocation = flight.getArrivalLocation();
        this.flightDate = flight.getValidTill();
        this.flightClass = flight.getFlightClass();
        this.sortingChoice = sortingChoice;
        new SaveFlightDataImpl().getFlight(flight);
        choiceForSorting(sortingChoice);
        showFlightList();
    }

    /**
     * select the choice option
     * 
     * @param choice
     */
    private static void choiceForSorting(int choice) {
        switch (choice) {
        case 1:
            Collections.sort(flight.getFlightList(), new FareSort());
            break;

        case 2:
            Collections.sort(flight.getFlightList(), new FlightDurationSort());
            break;

        default:
            break;
        }
    }

    /**
     * print the list of flights
     */
    private static void showFlightList() {
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %12s %10s %10s %10s %10s %10s", "Flight No.", "Departure", "Arrival",
                "Valid Till", "Time", "Duration", "fare", "Seat", "Class");
        System.out.println();
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        if (flight.getFlightList().isEmpty()) {
            System.out.printf("%50s", NO_RECORD_FOUND);
            System.out.println();
        } else {
            flight.getFlightList().forEach(flightData -> printFlightData(flightData));
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        new FlightData().clearFlightList();
    }

    public static void printFlightData(FlightData flightData) {
        System.out.printf("%8s %8s %10s %15s %10s %8s %15s %7s %8s", flightData.getFlightNumber(),
                flightData.getDepartureLoaction(), flightData.getArrivalLocation(), flightData.getValidTill(),
                flightData.getFlightTime(), flightData.getFlightDuration(), flightData.getFare(),
                flightData.getSeatAvailability(), flightData.getFlightClass());
        System.out.println();
    }
}
