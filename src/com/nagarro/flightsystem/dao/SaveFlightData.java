/*
* Class name: SearchAllFlight
*
* Version info: jdk 1.8
*
* Copyright notice:
* 
* Author info: Arpit Garg
*
* Creation date: 24/Mar/2021
*
* Last updated By: Arpit Garg
*
* Last updated Date: 24/Mar/2021
*
* Description: Used for search list of flights
*/
package com.nagarro.flightsystem.dao;

import java.io.IOException;

import com.nagarro.flightsystem.model.FlightData;
import com.opencsv.exceptions.CsvValidationException;

public interface SaveFlightData {
    public void saveFlight(String fileName) throws IOException, CsvValidationException, NumberFormatException;

    public void getFlight(FlightData flightForSearch);
}
