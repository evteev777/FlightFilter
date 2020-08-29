package com.gridnine.testing;

import com.gridnine.testing.filter.DepartingInPast;
import com.gridnine.testing.filter.DepartsBeforeArrives;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.MoreTwoHoursGroundTime;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<FlightFilter> filters = new ArrayList<>();

    public static void main(String[] args) {
        try {
            List<Flight> flights = FlightBuilder.createFlights();
            if (args.length > 0) {
                getFilters(args);
            } else {
                getFilters(new String[] {"DepartingInPast",
                        "DepartsBeforeArrives", "MoreTwoHoursGroundTime"});
            }

            flights.stream()
                    .filter(flight -> {
                        for (FlightFilter flightFilter : filters) {
                            if (!flightFilter.check(flight))
                                return false;
                        }
                        return true;
                    })
                    .forEach(flight -> Log.info(flight.toString()));

        } catch (Exception e) {
            Log.error(e);
        }
    }

    private static void getFilters(String[] args) {
        for (String arg : args) {
            if ("1".equals(arg) || "DepartingInPast".equals(arg)) {
                filters.add(new DepartingInPast());
            } else if ("2".equals(arg) || "DepartsBeforeArrives".equals(arg)) {
                filters.add(new DepartsBeforeArrives());
            } else if ("3".equals(arg) || "MoreTwoHoursGroundTime".equals(arg)) {
                filters.add(new MoreTwoHoursGroundTime());
            } else {
                throw new IllegalArgumentException("Illegal arguments");
            }
        }
    }

}
