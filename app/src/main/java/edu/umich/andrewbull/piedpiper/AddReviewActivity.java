package edu.umich.andrewbull.piedpiper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener {

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

        submitReviewButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

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
