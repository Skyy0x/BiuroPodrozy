package pl.sky0x.travelAgency.controller.reuqest.filter;

public class TripFilterRequest {

    private TripDateRangeRequest dateRangeRequest;
    private TripPriceRangeRequest priceRangeRequest;
    private TripDurationRangeRequest durationRangeRequest;
    private int peopleNumber;

    public TripFilterRequest() {

    }

    public TripFilterRequest(
            TripDateRangeRequest dateRangeRequest,
            TripPriceRangeRequest priceRangeRequest,
            TripDurationRangeRequest durationRangeRequest,
            int peopleNumber) {
        this.dateRangeRequest = dateRangeRequest;
        this.priceRangeRequest = priceRangeRequest;
        this.durationRangeRequest = durationRangeRequest;
        this.peopleNumber = peopleNumber;
    }

    public TripDateRangeRequest getDateRangeRequest() {
        return dateRangeRequest;
    }

    public TripPriceRangeRequest getPriceRangeRequest() {
        return priceRangeRequest;
    }

    public TripDurationRangeRequest getDurationRangeRequest() {
        return durationRangeRequest;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }
}
