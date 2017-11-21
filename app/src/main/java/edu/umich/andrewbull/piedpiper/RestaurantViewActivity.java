package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantViewActivity extends Activity implements View.OnClickListener{

    private TextView textViewRestaurantName;
    private TextView textViewAddress;
    private TextView textViewPhoneNumber;
    private TextView textViewRestaurantRating;
    private ImageView imageViewRestaurantImage;
    private ImageView imageViewRestaurantImage2;
    private ImageView imageViewRestaurantImage3;
    private Button buttonAddReview;
    private TextView textViewBestDishOne;
    private TextView textViewBestDishTwo;
    private TextView textViewBestDishThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        textViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewPhoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        textViewRestaurantRating = (TextView) findViewById(R.id.textViewRestaurantRating);
        imageViewRestaurantImage = (ImageView) findViewById(R.id.imageViewRestaurantImage);
        imageViewRestaurantImage2 = (ImageView) findViewById(R.id.imageViewRestaurantImage2);
        imageViewRestaurantImage3 = (ImageView) findViewById(R.id.imageViewRestaurantImage3);
        buttonAddReview = (Button) findViewById(R.id.buttonAddReview);
        textViewBestDishOne = (TextView) findViewById(R.id.textViewBestDishOne);
        textViewBestDishTwo = (TextView) findViewById(R.id.textViewBestDishTwo);
        textViewBestDishThree = (TextView) findViewById(R.id.textViewBestDishThree);

        buttonAddReview.setOnClickListener(this);
        textViewBestDishOne.setOnClickListener(this);
        textViewBestDishTwo.setOnClickListener(this);
        textViewBestDishThree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonAddReview) {
            Intent addReview = new Intent(this, AddReviewActivity.class);
            this.startActivity(addReview);
        } else if(view == textViewBestDishOne || view == textViewBestDishTwo || view == textViewBestDishThree) {
            Intent dishView = new Intent(this, DishViewActivity.class);
            this.startActivity(dishView);
        }
    }
}
