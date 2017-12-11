package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.Map;

public class RestaurantViewActivity extends Activity implements View.OnClickListener{

    private TextView textViewRestaurantName;
    private TextView textViewAddress;
    private TextView textViewPhoneNumber;
    private TextView textViewRestaurantRating;
    private ListView dishesListView;
    private DishAdapter dishAdapter;

    private Button buttonAddReview;

    private ArrayList<Dish> dishes;
    private String restaurantID;

    private SearchView searchView;
    private ListView searchSuggestionsListView;
    private SearchSuggestions searchSuggestions;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        textViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewPhoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        textViewRestaurantRating = (TextView) findViewById(R.id.textViewRestaurantRating);
        dishesListView = (ListView) findViewById(R.id.dishesListView);
        buttonAddReview = (Button) findViewById(R.id.buttonAddReview);

        dishAdapter = new DishAdapter();
        buttonAddReview.setOnClickListener(this);
        dishes = new ArrayList<Dish>();
        dishesListView.setAdapter(dishAdapter);
        dishesListView.setOnItemClickListener(dishAdapter);
        restaurantID = getIntent().getStringExtra("restaurantID");

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.WHITE);
        searchSuggestionsListView = (ListView) findViewById(R.id.searchSuggestionsListView);
        searchSuggestions = new SearchSuggestions();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Toast.makeText(this, restaurantID, Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference restaurantReference = database.getReference("restaurants");
        restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot restaurant = dataSnapshot.child(restaurantID);
                Toast.makeText(RestaurantViewActivity.this, restaurantID, Toast.LENGTH_SHORT).show();
                Restaurant r = restaurant.getValue(Restaurant.class);
                r.setRestaurantId(restaurantID);
                Toast.makeText(RestaurantViewActivity.this, r.getRestaurantName(), Toast.LENGTH_SHORT).show();

                setRestaurantText(r);
                DatabaseReference dishReference = database.getReference("dishes");
                for(Map.Entry<String,Boolean> entry : r.getDishes().entrySet()) {
                    Toast.makeText(RestaurantViewActivity.this, entry.getKey(), Toast.LENGTH_SHORT).show();
                    dishReference.child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Dish d = dataSnapshot.getValue(Dish.class);
                            d.setDishId(dataSnapshot.getKey());
                            dishes.add(d);
                            dishAdapter.notifyDataSetChanged();
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

        bringCategoryItemsToFront();
        searchSuggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = searchSuggestions.suggestionMap.get(searchSuggestions.suggestions.get(i));
                Intent searchResultsIntent = new Intent(RestaurantViewActivity.this, SearchResultsViewActivity.class);
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
                Intent searchResultsIntent = new Intent(RestaurantViewActivity.this, SearchResultsViewActivity.class);
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


                arrayAdapter = new ArrayAdapter<String>(RestaurantViewActivity.this, R.layout.searchsuggestions, searchSuggestions.suggestions);
                arrayAdapter.getFilter().filter(s);
                searchSuggestionsListView.setAdapter(arrayAdapter);
                searchSuggestionsListView.bringToFront();


                return false;
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

    public class DishAdapter extends BaseAdapter implements ListView.OnItemClickListener {

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

            Intent dishViewIntent = new Intent(RestaurantViewActivity.this, DishViewActivity.class);
            dishViewIntent.putExtra("dishId", dishes.get(i).getDishId());
            startActivity(dishViewIntent);
        }
    }

    public void bringCategoryItemsToFront() {
        textViewRestaurantName.bringToFront();
        textViewAddress.bringToFront();
        textViewPhoneNumber.bringToFront();
        textViewRestaurantRating.bringToFront();
        dishesListView.bringToFront();


        buttonAddReview.bringToFront();
    }

}
