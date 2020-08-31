package com.gridnine.testing.filter;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public class FlightFilters {

    private FlightFilters() {
    }

    public static boolean check(Flight flight) {

        List<FlightFilter> filters = FlightFilterFactory
                .getInstance().getFlightFilters();

        for (FlightFilter flightFilter : filters) {
            if (!flightFilter.check(flight))
                return false;
        }
        return true;
    }
}
