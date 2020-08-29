package com.gridnine.testing;

import com.gridnine.testing.filter.DepartingInPast;
import com.gridnine.testing.filter.DepartsBeforeArrives;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.MoreTwoHoursGroundTime;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import com.gridnine.testing.util.Log;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    private static final File propertyFile = new File("application.yaml");

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            List<Flight> flights = FlightBuilder.createFlights();
            List<FlightFilter> filters = getFilters(args);

            flights.stream().filter(flight -> {
                for (FlightFilter flightFilter : filters) {
                    if (!flightFilter.check(flight))
                        return false;
                }
                return true;
            })
            .forEach(flight -> Log.debug(flight.toString()));
        } catch (Exception e) {
            Log.error(e);
        }
    }

    private static List<FlightFilter> getFilters(String[] args) {
        List<FlightFilter> filters = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(propertyFile)) {

            if (args.length > 0) {
                Log.debug("Get filters from command line");
            } else {
                Properties property = new Properties();
                property.load(fis);
                Log.debug(property.getProperty("check-connection"));
                Log.debug(property.getProperty("filters") == null ?
                        "Properties file is empty, use default filter set:" :
                        "Get filters from application.yaml");
                args = property.getProperty("filters", "1 2 3").split(" ");
            }

            for (String arg : args) {
                if ("1".equals(arg) || "DepartingInPast".equals(arg)) {
                    filters.add(new DepartingInPast());
                    Log.debug("Filter: 1 Departing In Past");
                } else if ("2".equals(arg) || "DepartsBeforeArrives".equals(arg)) {
                    filters.add(new DepartsBeforeArrives());
                    Log.debug("Filter: 2 Departs Before Arrives");
                } else if ("3".equals(arg) || "MoreTwoHoursGroundTime".equals(arg)) {
                    filters.add(new MoreTwoHoursGroundTime());
                    Log.debug("Filter: 3 More TwoHours Ground Time");
                } else {
                    throw new IllegalArgumentException("Illegal arguments");
                }
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return filters;
    }
}
