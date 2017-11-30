package edu.umich.andrewbull.piedpiper;

import java.util.Map;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Restaurant {

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


    public Restaurant() {
        super();
    }

    public String getRestaurantName() {
        return this.restaurantName;

    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
