package com.gridnine.testing;

import com.gridnine.testing.filter.FlightFilters;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.util.Log;
import org.apache.log4j.BasicConfigurator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            List<Flight> flights = FlightBuilder.createFlights();

            flights.stream().filter(FlightFilters::check)
                    .forEach(flight -> Log.info(flight.toString()));
        } catch (Exception e) {
            Log.error(e);
        }
    }
}
