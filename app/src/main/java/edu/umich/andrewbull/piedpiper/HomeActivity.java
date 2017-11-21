package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends Activity implements Button.OnClickListener {


    private ImageButton imageButtonDish1, imageButtonDish2, imageButtonDish3, imageButtonDish4;
    private TextView textViewDish1, textViewDish2, textViewDish3, textViewDish4;
    private Button addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


            imageButtonDish1 = (ImageButton) findViewById(R.id.imageButtonDish1);
            imageButtonDish2 = (ImageButton) findViewById(R.id.imageButtonDish2);
            imageButtonDish3 = (ImageButton) findViewById(R.id.imageButtonDish3);
            imageButtonDish4 = (ImageButton) findViewById(R.id.imageButtonDish4);
            textViewDish1 = (TextView) findViewById(R.id.textViewDish1);
            textViewDish2 = (TextView) findViewById(R.id.textViewDish2);
            textViewDish3 = (TextView) findViewById(R.id.textViewDish3);
            textViewDish4 = (TextView) findViewById(R.id.textViewDish4);
            addReviewButton = (Button) findViewById(R.id.addReviewButton);

            addReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addReviewActivityIntent = new Intent(HomeActivity.this, AddReviewActivity.class);
                    startActivity(addReviewActivityIntent);
                }
            });
            textViewDish1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent searchResultsActivityIntent = new Intent(HomeActivity.this, SearchResultsViewActivity.class);
                    startActivity(searchResultsActivityIntent);
                }
            });
            imageButtonDish1.setOnClickListener(this);
            imageButtonDish2.setOnClickListener(this);
            imageButtonDish3.setOnClickListener(this);
            imageButtonDish4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == imageButtonDish1 || view == imageButtonDish2 || view == imageButtonDish3 || view == imageButtonDish4) {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);
            Bundle b = new Bundle();
            b.putString("categoryId", "pizza");
            searchResults.putExtra("categoryId", "pizza");
            this.startActivity(searchResults);

        }
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
