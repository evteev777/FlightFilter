package com.gridnine.testing.filter;

import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class DepartsBeforeArrives implements FlightFilter {

    @Override
    public boolean check(Flight flight) {
        if (flight.getSegments().size() == 1) {
            // One segment flight
            LocalDateTime dep = flight.getSegments().get(0).getDepartureDate();
            LocalDateTime arr = flight.getSegments().get(0).getArrivalDate();

            if (dep.isAfter(arr)) {
                return false;
            }
        } else {
            // Multi segment flight
            List<Segment> segmentList = flight.getSegments();

            for (int i = 0; i < segmentList.size() - 1; i++) {
                LocalDateTime dep = segmentList.get(i).getDepartureDate();
                LocalDateTime arr = segmentList.get(i).getArrivalDate();
                LocalDateTime depNext = segmentList.get(i + 1).getDepartureDate();

                if (dep.isAfter(arr) || arr.isAfter(depNext)) {
                    return false;
                }
            }
        }
        return true;
    }
}
