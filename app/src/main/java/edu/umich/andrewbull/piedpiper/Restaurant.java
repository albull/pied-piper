package edu.umich.andrewbull.piedpiper;

import java.util.Map;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Restaurant {

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUri() {
        return uri;
    }

    public Boolean getDeliveryOptions() {
        return deliveryOptions;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Map<String, Boolean> getDishes() {
        return dishes;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    private String restaurantName;
    //private edu.umich.andrewbull.piedpiper.Location location;
    private String phoneNumber;
    private String uri;
    //private Hours hours;
    private Boolean deliveryOptions;
    //private Dish[] dishes;
    //private Review[] reviews;
    //private Picture[] pictures;
    private Double averageRating;
    private Map<String, Boolean> dishes;
    private String restaurantId;
    private String addressLine1;


    public Restaurant() {

    }

    public String getRestaurantName() {
        return this.restaurantName;

    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
