package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewActivity extends Activity {

    private Button submitReviewButton;
    private AutoCompleteTextView restaurantNameEditText;
    private EditText restaurantRatingEditText;
    private EditText restaurantReviewEditText;
    private AutoCompleteTextView dishNameEditText;
    private EditText dishRatingEditText;
    private EditText dishReviewEditText;
    private DishSuggestions dishSuggestions;
    private RestaurantSuggestions restaurantSuggestions;
    private ArrayAdapter<String> dishSuggestionArrayAdapter;
    private ArrayAdapter<String> restaurantSuggestionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        submitReviewButton = (Button) findViewById(R.id.submitReviewButton);
        restaurantNameEditText = (AutoCompleteTextView) findViewById(R.id.restaurantNameEditText);
        restaurantRatingEditText = (EditText) findViewById(R.id.restaurantRatingEditText);
        restaurantReviewEditText = (EditText) findViewById(R.id.restaurantReviewEditText);
        dishNameEditText = (AutoCompleteTextView) findViewById(R.id.dishNameEditText);
        dishRatingEditText = (EditText) findViewById(R.id.dishRatingEditText);
        dishReviewEditText = (EditText) findViewById(R.id.dishReviewEditText);

        dishSuggestions = new DishSuggestions();
        restaurantSuggestions = new RestaurantSuggestions();
        dishSuggestionArrayAdapter = new ArrayAdapter<String>(this,R.layout.searchsuggestions,dishSuggestions.dishSuggestions);
        restaurantSuggestionArrayAdapter = new ArrayAdapter<String>(this,R.layout.searchsuggestions,restaurantSuggestions.restaurantSuggestions);
        dishNameEditText.setAdapter(dishSuggestionArrayAdapter);
        restaurantNameEditText.setAdapter(restaurantSuggestionArrayAdapter);

        restaurantNameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddReviewActivity.this, "hello", Toast.LENGTH_SHORT).show();

                dishSuggestions = new DishSuggestions(restaurantSuggestions.restaurantSuggestionsMap.get(restaurantNameEditText.getText().toString()).getDishes());
                ArrayAdapter<String> suggestionArrayAdapter = new ArrayAdapter<String>(AddReviewActivity.this,R.layout.searchsuggestions,dishSuggestions.dishSuggestions);
                dishNameEditText.setAdapter(suggestionArrayAdapter);
            }
        });
        restaurantNameEditText
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddReviewActivity.this, "hello", Toast.LENGTH_SHORT).show();
                dishSuggestions = new DishSuggestions(restaurantSuggestions.restaurantSuggestionsMap.get(restaurantNameEditText.getText().toString()).getDishes());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = database.getReference();

                String dishName = dishNameEditText.getText().toString();
                String restaurantName = restaurantNameEditText.getText().toString();

                String dishId = dishSuggestions.dishSuggestionMap.get(dishName);
                String restaurantId = restaurantSuggestions.restaurantSuggestionsMap.get(restaurantName).getRestaurantId();

                String dishRating = dishRatingEditText.getText().toString();
                String restaurantRating = restaurantRatingEditText.getText().toString();

                String dishReview = dishReviewEditText.getText().toString();
                String restaurantReview = restaurantReviewEditText.getText().toString();

                Review myReview = new Review(dishId,restaurantId,dishRating,restaurantRating,dishReview,restaurantReview);

                String key = myRef.push().getKey();
                myRef.child("reviews").child(key).setValue(myReview);
                myRef.child("dishes").child(dishId).child("reviews").child(key).setValue(true);
                myRef.child("restaurants").child(restaurantId).child("reviews").child(key).setValue(true);


                Intent returnToHomeIntent = new Intent(AddReviewActivity.this, HomeActivity.class);
                startActivity(returnToHomeIntent);

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
