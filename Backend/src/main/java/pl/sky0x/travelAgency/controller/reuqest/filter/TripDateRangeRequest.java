package pl.sky0x.travelAgency.controller.reuqest.filter;

import java.time.LocalDate;

public class TripDateRangeRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    public TripDateRangeRequest() {
    }

    public TripDateRangeRequest(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
