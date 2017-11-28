package edu.umich.andrewbull.piedpiper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andrewbull on 11/9/17.
 */

public final class Dish implements Parcelable {

    private String dishName;
    private String category;
    private String restaurant;
    private Double averageRating;
//    private Review[] reviews;
//    private Picture[] pictures;
//    private String[] tags;
//    private Double averageRating;
//    private Restaurant restaurant;

    public Dish() {
        super();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(dishName);
        out.writeString(category);
        out.writeString(restaurant);
        out.writeDouble(averageRating);
    }

    public String getDishName() {
        return this.dishName;
    }

    public String getRestaurant() {
        return this.restaurant;
    }

    public String getCategory() {
        return this.category;
    }

    public static final Parcelable.Creator<Dish> CREATOR
            = new Parcelable.Creator<Dish>() {
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    private Dish(Parcel in) {
        dishName = in.readString();
        category = in.readString();
        restaurant = in.readString();
        averageRating = in.readDouble();

    }
}
