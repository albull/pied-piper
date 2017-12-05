package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String restaurantID;

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

        restaurantID = getIntent().getStringExtra("restaurantID");

        Toast.makeText(this, restaurantID, Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference dishReference = database.getReference("dishes");
        dishReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot restaurant = dataSnapshot.child(restaurantID);
                Toast.makeText(RestaurantViewActivity.this, restaurantID, Toast.LENGTH_SHORT).show();
                Restaurant r = restaurant.getValue(Restaurant.class);
                r.setRestaurantId(restaurantID);
                Toast.makeText(RestaurantViewActivity.this, r.getRestaurantName(), Toast.LENGTH_SHORT).show();

                setRestaurantText(r);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setRestaurantText(Restaurant r) {
        textViewRestaurantName.setText(r.getRestaurantName());
        textViewAddress.setText(r.getAddressLine1());
        textViewPhoneNumber.setText(r.getPhoneNumber());
        textViewRestaurantRating.setText(Double.toString(r.getAverageRating()));
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
