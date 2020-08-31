package com.gridnine.testing.filter;

import com.gridnine.testing.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlightFilterFactory {

    private static final FlightFilterFactory INSTANCE;
    private static final String PACKAGE = "com.gridnine.testing.filter_impls.";
    private static final File PROPERTY_FILE = new File("application.yaml");
    private static final String FLIGHT_FILTERS = "flightFilters";
    private static final String CHECK_CONNECTION = "check-connection";

    private final List<FlightFilter> flightFilters = new ArrayList<>();

    private FlightFilterFactory() {
        Properties property = new Properties();

        try (FileInputStream fis = new FileInputStream(PROPERTY_FILE)) {

            property.load(fis);

            Log.info(property.getProperty(CHECK_CONNECTION));
            Log.info("Get filters from Application.yaml");
            String flightFiltersProp = property.getProperty(FLIGHT_FILTERS);

            if (flightFiltersProp == null || flightFiltersProp.equals("")) {
                throw new IllegalArgumentException("Filters are not specified, " +
                        "check Application.yaml. Flights are shown without filters !!!");
            }

            for (String flightFilter : flightFiltersProp.split(" ")) {
                addFlightFilter(flightFilter);
            }
        } catch (IllegalArgumentException e) {
            Log.error(e.getMessage(), e);
        } catch (IOException e) {
            Log.error("Properties file not found! Put Application.yaml " +
                    "in the same folder as FlightFilter.jar.", e);
            Log.error("Flights are shown without filters: !!!\"", e);
        }
    }

    private void addFlightFilter(String flightFilter) {
        try {
            flightFilters.add((FlightFilter)
                    Class.forName(PACKAGE + flightFilter)
                            .getDeclaredConstructor().newInstance());
            Log.info("Filter: " + flightFilter);

        } catch (InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException |
                NoSuchMethodException | SecurityException |
                ClassNotFoundException e) {
            Log.error("Filter: " + flightFilter +
                    " - Invalid filter name. Filter not applied. " +
                    "Check Application.yaml !!!", e);
        }
    }

    static {
        INSTANCE = new FlightFilterFactory();
    }

    public static FlightFilterFactory getInstance() {
        return INSTANCE;
    }

    public List<FlightFilter> getFlightFilters() {
        return flightFilters;
    }
}
