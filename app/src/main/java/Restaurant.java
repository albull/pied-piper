import android.graphics.Picture;

import java.net.URI;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Restaurant {

    private String restaurantName;
    private Location location;
    private String phoneNumber;
    private URI uri;
    private Hours hours;
    private Boolean deliveryOptions;
    private Dish[] dishes;
    private Review[] reviews;
    private Picture[] pictures;
    private Double averageRating;


    public Restaurant() {
        super();
    }
}
