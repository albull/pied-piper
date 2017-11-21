package edu.umich.andrewbull.piedpiper;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Review {

    private String dish;
    private String restaurant;
//    private Double dishRating;
//    private Double restaurantRating;
//    private String dishReview;
//    private String restaurantReview;
    //private User user;

    public Review() {
        super();
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Review(String dishName, String restaurantName) {
        this.dish = dishName;
        this.restaurant = restaurantName;
//        this.dishRating = dishRating;
//        this.restaurantRating = restaurantRating;
//        this.dishReview = dishReview;
//        this.restaurantReview = restaurantReview;
    }
}
