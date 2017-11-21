import android.graphics.Picture;

import edu.umich.andrewbull.piedpiper.Review;

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
