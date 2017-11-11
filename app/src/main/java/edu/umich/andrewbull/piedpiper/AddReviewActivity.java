package edu.umich.andrewbull.piedpiper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class AddReviewActivity extends AppCompatActivity {

    private Button submitReviewButton;
    private EditText restaurantNameEditText;
    private EditText restaurantRatingEditText;
    private EditText restaurantReviewEditText;
    private EditText dishNameEditText;
    private EditText dishRatingEditText;
    private EditText dishReviewEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        submitReviewButton = (Button) findViewById(R.id.submitReviewButton);
        restaurantNameEditText = (EditText) findViewById(R.id.restaurantNameEditText);
        restaurantRatingEditText = (EditText) findViewById(R.id.restaurantRatingEditText);
        restaurantReviewEditText = (EditText) findViewById(R.id.restaurantReviewEditText);
        dishNameEditText = (EditText) findViewById(R.id.dishNameEditText);
        dishRatingEditText = (EditText) findViewById(R.id.dishRatingEditText);
        dishReviewEditText = (EditText) findViewById(R.id.dishReviewEditText);
    }
}
