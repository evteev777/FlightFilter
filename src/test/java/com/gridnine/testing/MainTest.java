package com.gridnine.testing;

import com.gridnine.testing.filter.DepartingInPast;
import com.gridnine.testing.filter.DepartsBeforeArrives;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.MoreTwoHoursGroundTime;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class MainTest {

    List<Flight> flights = FlightBuilder.createFlights();

    @Test
    void flightsCountAfterDepartingInPastFilterShouldBeOneLess() {
        FlightFilter flightFilter = new DepartingInPast();
        long actual = flights.stream().filter(flightFilter::check).count();
        int expected = flights.size() - 1;
        Assert.isTrue(expected == actual,
                "Flights count after departing in past filter should be one less");
    }

    @Test
    void flightsCountAfterDepartsBeforeArrivesFilterShouldBeOneLess() {
        FlightFilter flightFilter = new DepartsBeforeArrives();
        long actual = flights.stream().filter(flightFilter::check).count();
        int expected = flights.size() - 1;
        Assert.isTrue(expected == actual,
                "Flights count after departs before arrives filter should be one less");
    }

    @Test
    void flightsCountAfterMoreTwoHoursGroundTimeFilterShouldBeTwoLess() {
        FlightFilter flightFilter = new MoreTwoHoursGroundTime();
        long actual = flights.stream().filter(flightFilter::check).count();
        int expected = flights.size() - 2;
        Assert.isTrue(expected == actual,
                "flightsCountAfterMoreTwoHoursGroundTimeFilterShouldBeTwoLess");
    }
}
