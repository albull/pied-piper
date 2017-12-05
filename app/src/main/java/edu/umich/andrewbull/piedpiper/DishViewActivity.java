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
    private String dishId;

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

        dishId = getIntent().getStringExtra("dishId");

        Toast.makeText(this, dishId, Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference dishReference = database.getReference("dishes");
        dishReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dish = dataSnapshot.child(dishId);
                Toast.makeText(DishViewActivity.this, dishId, Toast.LENGTH_SHORT).show();
                Dish d = dish.getValue(Dish.class);
                d.setDishId(dishId);
                Toast.makeText(DishViewActivity.this, d.getRestaurant(), Toast.LENGTH_SHORT).show();

                setDishText(d);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDishText(Dish d) {
        textViewDishName.setText(d.getDishName());
        textViewRestaurantName.setText(d.getRestaurant());
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
