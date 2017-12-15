package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SeeMoreActivity extends Activity implements View.OnClickListener {

    private ListView seeMoreListView;
    private ArrayList<Dish> dishes;
    private ArrayList<Restaurant> restaurants;
    private SeeMoreRestaurantAdapter seeMoreRestaurantAdapter;
    private SeeMoreDishAdapter seeMoreDishAdapter;

    private String category;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        seeMoreListView = (ListView) findViewById(R.id.seeMoreListView);

        category = getIntent().getStringExtra("category");
        type = getIntent().getStringExtra("type");

        dishes = new ArrayList<Dish>();
        restaurants = new ArrayList<Restaurant>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("categories").child(category);

        if(type.equals("restaurants")) {
            seeMoreRestaurantAdapter = new SeeMoreRestaurantAdapter();
            seeMoreListView.setAdapter(seeMoreRestaurantAdapter);
            seeMoreListView.setOnItemClickListener(seeMoreRestaurantAdapter);

            DatabaseReference restaurantRef = myRef.child("restaurants");
            restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot r : dataSnapshot.getChildren()) {
                        final String restaurantId = r.getKey();
                        DatabaseReference restaurantObjectRef = database.getReference("restaurants").child(restaurantId);
                        restaurantObjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                                //Toast.makeText(SeeMoreActivity.this, restaurantId, Toast.LENGTH_SHORT).show();
                                restaurant.setRestaurantId(restaurantId);
                                restaurants.add(restaurant);

                                Collections.sort(restaurants, new Comparator<Restaurant>() {
                                    @Override
                                    public int compare(Restaurant restaurant, Restaurant t1) {
                                        return t1.getAverageRating().compareTo(restaurant.getAverageRating());
                                    }
                                });

                                seeMoreRestaurantAdapter.notifyDataSetChanged();
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
        }
        else if(type.equals("dishes")) {
            seeMoreDishAdapter = new SeeMoreDishAdapter();
            seeMoreListView.setAdapter(seeMoreDishAdapter);
            seeMoreListView.setOnItemClickListener(seeMoreDishAdapter);

            DatabaseReference dishRef = myRef.child("dishes");
            dishRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        final String id = d.getKey();

                        DatabaseReference dishObjectRef = database.getReference("dishes");
                        dishObjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Dish d = dataSnapshot.child(id).getValue(Dish.class);
                                d.setDishId(id);
                                //Toast.makeText(SeeMoreActivity.this, d.getDishName(), Toast.LENGTH_SHORT).show();
                                dishes.add(d);

                                Collections.sort(dishes, new Comparator<Dish>() {
                                    @Override
                                    public int compare(Dish dish, Dish t1) {
                                        return t1.getAverageRating().compareTo(dish.getAverageRating());
                                    }
                                });

                                seeMoreDishAdapter.notifyDataSetChanged();

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
        }

    }

    public class SeeMoreDishAdapter extends BaseAdapter implements ListView.OnItemClickListener {

        @Override
        public int getCount() {
            return dishes.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.seemore, null);

            TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            TextView ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
            TextView subheaderTextView = (TextView) view.findViewById(R.id.subheaderTextView);

            nameTextView.setText(Integer.toString(i + 1) + ". " + dishes.get(i).getDishName());
            ratingTextView.setText(dishes.get(i).getAverageRating().toString());
            subheaderTextView.setText("   " + dishes.get(i).getRestaurantName());

            return view;
        }


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Toast.makeText(SeeMoreActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            Intent dishViewIntent = new Intent(SeeMoreActivity.this, DishViewActivity.class);
            dishViewIntent.putExtra("dishId", dishes.get(i).getDishId());
            startActivity(dishViewIntent);
        }
    }

    public class SeeMoreRestaurantAdapter extends BaseAdapter implements ListView.OnItemClickListener {

        @Override
        public int getCount() {
            return restaurants.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.seemore, null);

            TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            TextView ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
            TextView subheaderTextView = (TextView) view.findViewById(R.id.subheaderTextView);

            nameTextView.setText(Integer.toString(i + 1) + ". " + restaurants.get(i).getRestaurantName());
            ratingTextView.setText(restaurants.get(i).getAverageRating().toString());
            subheaderTextView.setText("");

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent restaurantViewIntent = new Intent(SeeMoreActivity.this, RestaurantViewActivity.class);
            //Toast.makeText(SeeMoreActivity.this, restaurants.get(i).getRestaurantId(), Toast.LENGTH_SHORT).show();
            restaurantViewIntent.putExtra("restaurantID", restaurants.get(i).getRestaurantId());
            startActivity(restaurantViewIntent);
        }
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
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                Intent logout = new Intent(this, LogoutActivity.class);
                startActivity(logout);
            }
            else {
                Intent logInOut = new Intent(this, LoginActivity.class);
                this.startActivity(logInOut);
            }

        }else if(item.getItemId() == R.id.menuItemAddReview) {
            Intent addReviewActivity = new Intent(this, AddReviewActivity.class);
            startActivity(addReviewActivity);
        }

        return super.onOptionsItemSelected(item);
    }

}
