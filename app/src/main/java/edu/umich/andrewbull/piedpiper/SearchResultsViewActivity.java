package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

    private SearchView searchView;
    private ListView searchSuggestionsListView;
    private SearchSuggestions searchSuggestions;
    private ArrayAdapter<String> arrayAdapter;


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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchSuggestionsListView = (ListView) findViewById(R.id.searchSuggestionsListView);
        searchSuggestions = new SearchSuggestions();

        dishes = new ArrayList<Dish>();
        restaurants = new ArrayList<Restaurant>();
        dishCount = 0;
        restaurantCount = 0;
        //categoryId = "pizza";

        Toast.makeText(SearchResultsViewActivity.this, categoryId, Toast.LENGTH_SHORT).show();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");
        bringCategoryItemsToFront();
        searchSuggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = searchSuggestions.suggestionMap.get(arrayAdapter.getItem(i));
                Intent searchResultsIntent = new Intent(SearchResultsViewActivity.this, SearchResultsViewActivity.class);
                searchResultsIntent.putExtra("categoryId", id);
                startActivity(searchResultsIntent);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchSuggestionsListView.setBackgroundColor(Color.TRANSPARENT);
                searchSuggestionsListView.setAdapter(null);
                bringCategoryItemsToFront();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String id = searchSuggestions.suggestionMap.get(s);
                Intent searchResultsIntent = new Intent(SearchResultsViewActivity.this, SearchResultsViewActivity.class);
                searchResultsIntent.putExtra("categoryId", id);
                searchView.bringToFront();
                searchView.setBackgroundColor(Color.WHITE);
                searchSuggestionsListView.bringToFront();
                startActivity(searchResultsIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {



//                searchSuggestionsListView.getBackground().setAlpha(51);
                searchSuggestionsListView.setBackgroundColor(Color.WHITE);


                arrayAdapter = new ArrayAdapter<String>(SearchResultsViewActivity.this, R.layout.searchsuggestions, searchSuggestions.suggestions);
                arrayAdapter.getFilter().filter(s);
                searchSuggestionsListView.setAdapter(arrayAdapter);
                searchSuggestionsListView.bringToFront();


                return false;
            }
        });



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
                            d.setDishId(dishId);
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
                            r.setRestaurantId(restaurantId);



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
        if(view == textViewSeeMoreDish) {
            Intent seeMore = new Intent(this, SeeMoreActivity.class);
            seeMore.putExtra("category", categoryId);
            seeMore.putExtra("type","dishes");
            this.startActivity(seeMore);
        } else if(view == textViewSeeMoreRestaurant) {
            Intent seeMore = new Intent(this, SeeMoreActivity.class);
            seeMore.putExtra("category", categoryId);
            seeMore.putExtra("type","restaurants");
            this.startActivity(seeMore);
        } else if(view == textViewDish1) {
            Intent dishView = new Intent(this, DishViewActivity.class);

            if(dishes.size() >= 1) {
                Dish d = dishes.get(0);
                dishView.putExtra("dishId", d.getDishId());
                this.startActivity(dishView);
            }
        }
        else if(view == textViewDish2) {
            Intent dishView = new Intent(this, DishViewActivity.class);

            if(dishes.size() >= 2) {
                Dish d = dishes.get(1);
                dishView.putExtra("dishId", d.getDishId());
                this.startActivity(dishView);
            }
        }
        else if(view == textViewDish3) {
            Intent dishView = new Intent(this, DishViewActivity.class);

            if(dishes.size() >= 3) {
                Dish d = dishes.get(2);
                dishView.putExtra("dishId", d.getDishId());
                this.startActivity(dishView);
            }

        }
        else if(view == textViewRestaurant1) {
            Intent restaurantView = new Intent(this, RestaurantViewActivity.class);
            if(restaurants.size() >= 1) {
                Restaurant r = restaurants.get(0);
                restaurantView.putExtra("restaurantID", r.getRestaurantId());
                this.startActivity(restaurantView);
            }

        }
        else if(view == textViewRestaurant2) {
            Intent restaurantView = new Intent(this, RestaurantViewActivity.class);
            if(restaurants.size() >= 2) {
                Restaurant r = restaurants.get(1);
                restaurantView.putExtra("restaurantID", r.getRestaurantId());
                this.startActivity(restaurantView);
            }

        }
        else if(view == textViewRestaurant3 ) {
            Intent restaurantView = new Intent(this, RestaurantViewActivity.class);
            if(restaurants.size() >= 3) {
                Restaurant r = restaurants.get(2);
                restaurantView.putExtra("restaurantID", r.getRestaurantId());
                this.startActivity(restaurantView);
            }

        }

    }

    public void setDishText(Dish dish) {
        //TODO: get restaurant name from the restaurant id
        //maybe export it to another function for the sake of abstraction
        if(dishCount == 0) {
            String text = "1. " + dish.getDishName() + " from " + dish.getRestaurantName();
            textViewDish1.setText(text);
            dishCount++;
        }
        else if(dishCount == 1) {
            String text = "2. " + dish.getDishName() + " from " + dish.getRestaurantName();
            textViewDish2.setText(text);
            dishCount++;
        }
        else if(dishCount == 2) {
            String text = "3. " + dish.getDishName() + " from " + dish.getRestaurantName();
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

    public void bringCategoryItemsToFront() {
        textViewDish1.bringToFront();
        textViewDish2.bringToFront();
        textViewDish3.bringToFront();
        textViewRestaurant1.bringToFront();
        textViewRestaurant2.bringToFront();
        textViewRestaurant3.bringToFront();
        textViewSeeMoreDish.bringToFront();
        textViewSeeMoreRestaurant.bringToFront();
    }

}
