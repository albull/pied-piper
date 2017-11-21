package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SpecificReviewActivity extends Activity {

    private TextView textViewRatingName;
    private TextView textViewDishName;
    private TextView textViewRestaurantName;
    private TextView textViewRating;
    private ImageView imageViewPictureOne;
    private ImageView imageViewPictureTwo;
    private TextView textViewRatingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_review);

        textViewRatingName = (TextView) findViewById(R.id.textViewRatingName);
        textViewDishName = (TextView) findViewById(R.id.textViewDishName);
        textViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        imageViewPictureOne = (ImageView) findViewById(R.id.imageViewPictureOne);
        imageViewPictureTwo = (ImageView) findViewById(R.id.imageViewPictureTwo);
        textViewRatingDetail = (TextView) findViewById(R.id.textViewRatingDetail);

    }
}
