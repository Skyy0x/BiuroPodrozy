package pl.sky0x.travelAgency.controller.reuqest.filter;

public class TripPriceRangeRequest {

    private int minPrice;
    private int maxPrice;

    public TripPriceRangeRequest() {

    }

    public TripPriceRangeRequest(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

}
