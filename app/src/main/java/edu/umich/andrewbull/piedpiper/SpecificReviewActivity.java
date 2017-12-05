package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mainMenuInflater = getMenuInflater();
        mainMenuInflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuItemHome) {
            Intent goHome = new Intent(this, HomeActivity.class);
            this.startActivity(goHome);
        } else if(item.getItemId() == R.id.menuItemMyAccount) {
            Intent myAccount = new Intent(this, Account.class);
            this.startActivity(myAccount);
        }else if(item.getItemId() == R.id.menuItemLogInOut) {
            Intent logInOut = new Intent(this, LoginActivity.class);
            this.startActivity(logInOut);
        }

        return super.onOptionsItemSelected(item);
    }

}
