package edu.umich.andrewbull.piedpiper;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Review {

    public String dishId;
    public String restaurantId;
    public String dishRating;
    public String restaurantRating;
    public String dishReview;
    public String restaurantReview;
    public String reviewId;
    public String restaurantName;
    public String dishName;
    //private edu.umich.andrewbull.piedpiper.User user;

    public Review() {


    }

    public Review(String dishId, String restaurantId, String dishRating, String restaurantRating, String dishReview, String restaurantReview, String dishName, String restaurantName) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.restaurantRating = restaurantRating;
        this.dishRating = dishRating;
        this.dishReview = dishReview;
        this.restaurantReview = restaurantReview;
        this.dishName = dishName;
        this.restaurantName = restaurantName;
    }






}
