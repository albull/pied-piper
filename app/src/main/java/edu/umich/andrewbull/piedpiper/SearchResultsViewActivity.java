package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private ArrayList<Dish> dishes;
    private ArrayList<Restaurant> restaurants;
    private Integer dishCount;
    private Integer restaurantCount;

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
        categoryId = getIntent().getStringExtra("categoryId");

        dishes = new ArrayList<Dish>();
        restaurants = new ArrayList<Restaurant>();
        dishCount = 0;
        restaurantCount = 0;
        //categoryId = "pizza";

        Toast.makeText(SearchResultsViewActivity.this, categoryId, Toast.LENGTH_SHORT).show();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");

        categoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.child(categoryId).child("dishes").getChildren()) {
                    final String dishId = child.getKey();

                    DatabaseReference dishReference = database.getReference("dishes");
                    dishReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DataSnapshot dish = dataSnapshot.child(dishId);
                            Toast.makeText(SearchResultsViewActivity.this, dishId, Toast.LENGTH_SHORT).show();
                            Dish d = dish.getValue(Dish.class);
                            Toast.makeText(SearchResultsViewActivity.this, d.getDishName(), Toast.LENGTH_SHORT).show();
                            dishes.add(d);
                            setDishText(d);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                for(DataSnapshot child : dataSnapshot.child(categoryId).child("restaurants").getChildren()) {
                    final String restaurantId = child.getKey();

                    DatabaseReference restaurantReference = database.getReference("restaurants");
                    restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DataSnapshot restaurant = dataSnapshot.child(restaurantId);

                            Restaurant r = restaurant.getValue(Restaurant.class);
                            setRestaurantText(r);
                            restaurants.add(r);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
            Bundle b = new Bundle();

            this.startActivity(dishView);
        } else if(view == textViewRestaurant1 || view == textViewRestaurant2 || view == textViewRestaurant3) {
            Intent restaurantView = new Intent(this, RestaurantViewActivity.class);
            this.startActivity(restaurantView);
        }

    }

    public void setDishText(Dish dish) {
        //TODO: get restaurant name from the restaurant id
        //maybe export it to another function for the sake of abstraction
        if(dishCount == 0) {
            String text = "1. " + dish.getDishName() + " from " + dish.getRestaurant();
            textViewDish1.setText(text);
            dishCount++;
        }
        else if(dishCount == 1) {
            String text = "2. " + dish.getDishName() + " from " + dish.getRestaurant();
            textViewDish2.setText(text);
            dishCount++;
        }
        else if(dishCount == 2) {
            String text = "3. " + dish.getDishName() + " from " + dish.getRestaurant();
            textViewDish3.setText(text);
            dishCount++;
        }
    }

    public void setRestaurantText(Restaurant restaurant) {
        if(restaurantCount == 0) {
            String text = "1. " + restaurant.getRestaurantName();
            textViewRestaurant1.setText(text);
            restaurantCount++;
        }
        else if(restaurantCount == 1) {
            String text = "2. " + restaurant.getRestaurantName();
            textViewRestaurant2.setText(text);
            restaurantCount++;
        }
        else if(restaurantCount == 2) {
            String text = "3. " + restaurant.getRestaurantName();
            textViewRestaurant3.setText(text);
            restaurantCount++;
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
