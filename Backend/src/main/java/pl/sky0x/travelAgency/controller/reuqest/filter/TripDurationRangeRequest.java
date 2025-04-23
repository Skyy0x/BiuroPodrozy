package pl.sky0x.travelAgency.controller.reuqest.filter;

public class TripDurationRangeRequest {

    private int minDays;
    private int maxDays;

    public TripDurationRangeRequest() {

    }

    public TripDurationRangeRequest(int minDays, int maxDays) {
        this.minDays = minDays;
        this.maxDays = maxDays;
    }

    public int getMinDays() {
        return minDays;
    }

    public int getMaxDays() {
        return maxDays;
    }

}
