package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchResultsViewActivity extends Activity implements View.OnClickListener{

    private TextView textViewDish1;
    private TextView textViewDish2;
    private TextView textViewDish3;
    private TextView textViewRestaurant1;
    private TextView textViewRestaurant2;
    private TextView textViewRestaurant3;
    private TextView textViewSeeMoreDish;
    private TextView textViewSeeMoreRestaurant;
    private String categoryId;
    private Dish[] dishes;
    private Restaurant[] restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_view);

        textViewDish1 = (TextView) findViewById(R.id.textViewDish1);
        textViewDish2 = (TextView) findViewById(R.id.textViewDish2);
        textViewDish3 = (TextView) findViewById(R.id.textViewDish3);
        textViewRestaurant1 = (TextView) findViewById(R.id.textViewRestaurant1);
        textViewRestaurant2 = (TextView) findViewById(R.id.textViewRestaurant2);
        textViewRestaurant3 = (TextView) findViewById(R.id.textViewRestaurant3);
        textViewSeeMoreDish = (TextView) findViewById(R.id.textViewSeeMoreDish);
        textViewSeeMoreRestaurant = (TextView) findViewById(R.id.textViewSeeMoreRestaurant);
        //categoryId = getIntent().getStringExtra("categoryId");
        categoryId = "pizza";




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");

        categoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.child(categoryId).child("dishes").getChildren()) {
                    String dishId = child.getKey();

                    

                }

                for(DataSnapshot child : dataSnapshot.child(categoryId).child("restaurants").getChildren()) {
                    String restaurantId = child.getKey();

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        textViewSeeMoreDish.setOnClickListener(this);
        textViewSeeMoreRestaurant.setOnClickListener(this);
        textViewDish1.setOnClickListener(this);
        textViewDish2.setOnClickListener(this);
        textViewDish3.setOnClickListener(this);
        textViewRestaurant1.setOnClickListener(this);
        textViewRestaurant2.setOnClickListener(this);
        textViewRestaurant3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == textViewSeeMoreDish || view == textViewSeeMoreRestaurant) {
            Intent seeMore = new Intent(this, SeeMoreActivity.class);
            this.startActivity(seeMore);
        } else if(view == textViewDish1 || view == textViewDish2 || view == textViewDish3) {
            Intent dishView = new Intent(this, DishViewActivity.class);
            this.startActivity(dishView);
        } else if(view == textViewRestaurant1 || view == textViewRestaurant2 || view == textViewRestaurant3) {
            Intent restaurantView = new Intent(this, RestaurantViewActivity.class);
            this.startActivity(restaurantView);
        }

    }

    public void getDish(String dishId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dishReference = database.getReference("dishes");

        dishReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getRestaurant(String restaurantId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantReference = database.getReference("dishes");

        restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
