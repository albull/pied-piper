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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchSuggestionsListView = (ListView) findViewById(R.id.searchSuggestionsListView);
        searchSuggestions = new SearchSuggestions();

        dishId = getIntent().getStringExtra("dishId");

        Toast.makeText(this, dishId, Toast.LENGTH_SHORT).show();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryReference = database.getReference("categories");
        bringCategoryItemsToFront();
        searchSuggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = searchSuggestions.suggestionMap.get(searchSuggestions.suggestions.get(i));
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

    public void bringCategoryItemsToFront() {
        textViewDishName.bringToFront();
        textViewRestaurantName.bringToFront();
        textViewAddress.bringToFront();
        imageViewDishImage.bringToFront();
        textViewDishRating.bringToFront();
        buttonAddReview.bringToFront();
        textViewReviewOne.bringToFront();
        textViewReviewTwo.bringToFront();
        textViewReviewThree.bringToFront();
    }

}
