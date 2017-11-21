package edu.umich.andrewbull.piedpiper;

import android.graphics.Picture;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Dish {

    private String name;
    private Category category;
    private Review[] reviews;
    private Picture[] pictures;
    private String[] tags;
    private Double averageRating;
    private Restaurant restaurant;

    public Dish() {
        super();
    }
}
