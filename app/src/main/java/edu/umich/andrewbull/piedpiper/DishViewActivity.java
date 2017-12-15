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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DishViewActivity extends Activity implements View.OnClickListener {

    private TextView textViewDishName;
    private TextView textViewRestaurantName;
    private TextView textViewDishRating;
    private Button buttonAddReview;
    private ListView reviewsListView;
    private ArrayList<Review> reviews;
    private ReviewAdapter reviewAdapter;

    private String dishId;

    private SearchView searchView;
    private ListView searchSuggestionsListView;
    private SearchSuggestions searchSuggestions;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_view);

        textViewDishName = (TextView) findViewById(R.id.textViewDishName);
        textViewRestaurantName = (TextView) findViewById(R.id.textViewRestaurantName);

        textViewDishRating = (TextView) findViewById(R.id.textViewDishRating);
        buttonAddReview = (Button) findViewById(R.id.buttonAddReview);


        buttonAddReview.setOnClickListener(this);

        reviews = new ArrayList<Review>();
        reviewAdapter = new ReviewAdapter();
        reviewsListView = (ListView) findViewById(R.id.reviewsListView);

        reviewsListView.setAdapter(reviewAdapter);
        reviewsListView.setOnItemClickListener(reviewAdapter);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.WHITE);
        searchSuggestionsListView = (ListView) findViewById(R.id.searchSuggestionsListView);
        searchSuggestions = new SearchSuggestions();

        dishId = getIntent().getStringExtra("dishId");

        //Toast.makeText(this, dishId, Toast.LENGTH_SHORT).show();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");
        bringCategoryItemsToFront();
        searchSuggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = searchSuggestions.suggestionMap.get(arrayAdapter.getItem(i));
                Intent searchResultsIntent = new Intent(DishViewActivity.this, SearchResultsViewActivity.class);
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
                Intent searchResultsIntent = new Intent(DishViewActivity.this, SearchResultsViewActivity.class);
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


                arrayAdapter = new ArrayAdapter<String>(DishViewActivity.this, R.layout.searchsuggestions, searchSuggestions.suggestions);
                arrayAdapter.getFilter().filter(s);
                searchSuggestionsListView.setAdapter(arrayAdapter);
                searchSuggestionsListView.bringToFront();


                return false;
            }
        });

        DatabaseReference dishReference = database.getReference("dishes");
        final DatabaseReference reviewReference = database.getReference("reviews");
        dishReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dish = dataSnapshot.child(dishId);
                //Toast.makeText(DishViewActivity.this, dishId, Toast.LENGTH_SHORT).show();
                Dish d = dish.getValue(Dish.class);
                d.setDishId(dishId);
                //Toast.makeText(DishViewActivity.this, d.getRestaurant(), Toast.LENGTH_SHORT).show();

                setDishText(d);
                if(d.getReviews() != null) {
                    for(final Map.Entry<String,Boolean> entry : d.getReviews().entrySet()) {
                        reviewReference.child(entry.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Review r = dataSnapshot.getValue(Review.class);
                                r.reviewId = entry.getKey();
                                reviews.add(r);

                                reviewAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDishText(Dish d) {
        textViewDishName.setText(d.getDishName());
        textViewRestaurantName.setText(d.getRestaurantName());
        textViewDishRating.setText(d.getAverageRating().toString() + "/5");
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

    public void bringCategoryItemsToFront() {
        textViewDishName.bringToFront();
        textViewRestaurantName.bringToFront();
        textViewDishRating.bringToFront();
        buttonAddReview.bringToFront();

    }

    public class ReviewAdapter extends BaseAdapter implements ListView.OnItemClickListener  {

        @Override
        public int getCount() {
            return reviews.size();
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
            view = getLayoutInflater().inflate(R.layout.reviewcell, null);

            TextView ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
            TextView reviewTextView = (TextView) view.findViewById(R.id.reviewTextView);


            ratingTextView.setText(reviews.get(i).dishRating.toString() + "/5");
            reviewTextView.setText(reviews.get(i).dishReview);

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }

}
