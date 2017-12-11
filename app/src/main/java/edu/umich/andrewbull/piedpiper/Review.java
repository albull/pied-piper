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
    //private edu.umich.andrewbull.piedpiper.User user;

    public Review() {


    }

    public Review(String dishId, String restaurantId, String dishRating, String restaurantRating, String dishReview, String restaurantReview) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.restaurantRating = restaurantRating;
        this.dishRating = dishRating;
        this.dishReview = dishReview;
        this.restaurantReview = restaurantReview;
    }






}
