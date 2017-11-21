package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DishViewActivity extends Activity implements View.OnClickListener {

    private TextView textViewDishName;
    private TextView textViewRestaurantName;
    private TextView textViewAddress;
    private ImageView imageViewDishImage;
    private TextView textViewDishRating;
    private Button buttonAddReview;
    private TextView textViewReviewOne;
    private TextView textViewReviewTwo;
    private TextView textViewReviewThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_view);

        textViewDishName = (TextView) findViewById(R.id.textViewDishName);
        textViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        imageViewDishImage = (ImageView) findViewById(R.id.imageViewDishImage);
        textViewDishRating = (TextView) findViewById(R.id.textViewDishRating);
        buttonAddReview = (Button) findViewById(R.id.buttonAddReview);
        textViewReviewOne = (TextView) findViewById(R.id.textViewReviewOne);
        textViewReviewTwo = (TextView) findViewById(R.id.textViewReviewTwo);
        textViewReviewThree = (TextView) findViewById(R.id.textViewReviewThree);

        buttonAddReview.setOnClickListener(this);
        textViewReviewOne.setOnClickListener(this);
        textViewReviewTwo.setOnClickListener(this);
        textViewReviewThree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonAddReview) {
            Intent addReview = new Intent(this, AddReviewActivity.class);
            this.startActivity(addReview);
        } else if(view == textViewReviewOne || view == textViewReviewTwo || view == textViewReviewThree) {
            Intent specificReview = new Intent(this, SpecificReviewActivity.class);
            this.startActivity(specificReview);
        }
    }
}
