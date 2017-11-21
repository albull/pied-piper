package edu.umich.andrewbull.piedpiper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    private TextView textViewUsername;
    private ImageView imageViewProfile;
    private TextView textViewReviewTitle;
    private TextView textViewReviewTitle1;
    private TextView textViewReviewTitle2;
    private TextView textViewReviewTitle3;
    private RatingBar ratingBar1;
    private RatingBar ratingBar2;
    private RatingBar ratingBar3;
    private TextView textViewReviewText1;
    private TextView textViewReviewText2;
    private TextView textViewReviewText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        textViewReviewTitle = (TextView) findViewById(R.id.textViewReviewTitle);
        textViewReviewTitle1 = (TextView) findViewById(R.id.textViewReviewTitle1);
        textViewReviewTitle2 = (TextView) findViewById(R.id.textViewReviewTitle2);
        textViewReviewTitle3 = (TextView) findViewById(R.id.textViewReviewTitle3);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar3 = (RatingBar) findViewById(R.id.ratingBar3);
        textViewReviewText1 = (TextView) findViewById(R.id.textViewReviewText1);
        textViewReviewText2 = (TextView) findViewById(R.id.textViewReviewText2);
        textViewReviewText3 = (TextView) findViewById(R.id.textViewReviewText3);

    }
}
