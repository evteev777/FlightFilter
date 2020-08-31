package com.gridnine.testing.filter_impls;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MoreTwoHoursGroundTime implements FlightFilter {

    @Override
    public boolean check(Flight flight) {
        // Multi segment flight
        if (flight.getSegments().size() > 1) {
            List<Segment> segmentList = flight.getSegments();
            long groundTime = 0;

            for (int i = 0; i < segmentList.size() - 1; i++) {
                LocalDateTime arr = segmentList.get(i).getArrivalDate();
                LocalDateTime depNext = segmentList.get(i + 1).getDepartureDate();
                groundTime += arr.until(depNext, ChronoUnit.MINUTES);
            }

            int minutesInHour = 60;
            return groundTime / minutesInHour <= 2;
        }
        return true;
    }
}
