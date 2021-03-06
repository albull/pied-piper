package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends Activity implements View.OnClickListener {


    private ImageButton imageButtonDish1, imageButtonDish2, imageButtonDish3, imageButtonDish4, imageButtonSushi, imageButtonTacos, imageButtonSalad, imageButtonNoodles;
    private TextView textViewDish1, textViewDish2, textViewDish3, textViewDish4, textViewSushi, textViewTacos, textViewSalad, textViewNoodles;


    private String category1, category2, category3, category4;

    private SearchView searchView;
    private ListView searchSuggestionsListView;
    private SearchSuggestions searchSuggestions;
    private ArrayAdapter<String> arrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


            imageButtonDish1 = (ImageButton) findViewById(R.id.imageButtonDish1);
            imageButtonDish2 = (ImageButton) findViewById(R.id.imageButtonDish2);
            imageButtonDish3 = (ImageButton) findViewById(R.id.imageButtonDish3);
            imageButtonDish4 = (ImageButton) findViewById(R.id.imageButtonDish4);
            imageButtonSushi = (ImageButton) findViewById(R.id.imageButtonSushi);
            imageButtonNoodles = (ImageButton) findViewById(R.id.imageButtonNoodles);
            imageButtonTacos = (ImageButton) findViewById(R.id.imageButtonTaco);
            imageButtonSalad = (ImageButton) findViewById(R.id.imageButtonSalad);
            textViewDish1 = (TextView) findViewById(R.id.textViewDish1);
            textViewDish2 = (TextView) findViewById(R.id.textViewDish2);
            textViewDish3 = (TextView) findViewById(R.id.textViewDish3);
            textViewDish4 = (TextView) findViewById(R.id.textViewDish4);
            textViewSushi = (TextView) findViewById(R.id.textViewSushi);
            textViewNoodles = (TextView) findViewById(R.id.textViewNoodles);
            textViewSalad = (TextView) findViewById(R.id.textViewSalad);
            textViewTacos = (TextView) findViewById(R.id.textViewTacos);

            searchView = (SearchView) findViewById(R.id.searchView);
            searchSuggestionsListView = (ListView) findViewById(R.id.searchSuggestionsListView);
            searchSuggestions = new SearchSuggestions();


            category1 = "pizza";
            category2 = "pasta";
            category3 = "sandwiches";
            category4 = "burgers";

            textViewDish1.setText(category1);
            textViewDish2.setText(category2);
            textViewDish3.setText(category3);
            textViewDish4.setText(category4);

            imageButtonDish1.setOnClickListener(this);
            imageButtonDish2.setOnClickListener(this);
            imageButtonDish3.setOnClickListener(this);
            imageButtonDish4.setOnClickListener(this);
            imageButtonTacos.setOnClickListener(this);
            imageButtonSushi.setOnClickListener(this);
            imageButtonSalad.setOnClickListener(this);
            imageButtonNoodles.setOnClickListener(this);

            textViewDish1.setOnClickListener(this);
            textViewDish2.setOnClickListener(this);
            textViewDish3.setOnClickListener(this);
            textViewDish4.setOnClickListener(this);
            textViewTacos.setOnClickListener(this);
            textViewSushi.setOnClickListener(this);
            textViewNoodles.setOnClickListener(this);
            textViewSalad.setOnClickListener(this);

        bringCategoryItemsToFront();


        searchSuggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String id = searchSuggestions.suggestionMap.get(arrayAdapter.getItem(i));
                Intent searchResultsIntent = new Intent(HomeActivity.this, SearchResultsViewActivity.class);
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
                Intent searchResultsIntent = new Intent(HomeActivity.this, SearchResultsViewActivity.class);
                searchResultsIntent.putExtra("categoryId", id);
                startActivity(searchResultsIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {



//                searchSuggestionsListView.getBackground().setAlpha(51);
                searchSuggestionsListView.setBackgroundColor(Color.WHITE);


                arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, R.layout.searchsuggestions, searchSuggestions.suggestions);
                arrayAdapter.getFilter().filter(s);
                searchSuggestionsListView.setAdapter(arrayAdapter);
                searchSuggestionsListView.bringToFront();


                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.equals(imageButtonDish1) || view.equals(textViewDish1) )
        {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);
            Bundle b = new Bundle();
            b.putString("categoryId", category1);
            searchResults.putExtra("categoryId", category1);
            this.startActivity(searchResults);

        }
        else if(view.equals(imageButtonDish2) || view.equals(textViewDish2))
        {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);
            Bundle b = new Bundle();
            b.putString("categoryId", category2);
            searchResults.putExtra("categoryId", category2);
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonDish3) || view.equals(textViewDish3))
        {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);
            Bundle b = new Bundle();
            b.putString("categoryId", category3);
            searchResults.putExtra("categoryId", category3);
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonDish4) || view.equals(textViewDish4))
        {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);
            Bundle b = new Bundle();
            b.putString("categoryId", category4);
            searchResults.putExtra("categoryId", category4);
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonSushi) || view.equals(textViewSushi)) {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);

            searchResults.putExtra("categoryId", "sushi");
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonSalad) || view.equals(textViewSalad)) {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);

            searchResults.putExtra("categoryId", "salad");
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonNoodles) || view.equals(textViewNoodles)) {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);

            searchResults.putExtra("categoryId", "thai");
            this.startActivity(searchResults);
        }
        else if(view.equals(imageButtonTacos) || view.equals(textViewTacos)) {
            Intent searchResults = new Intent(this, SearchResultsViewActivity.class);

            searchResults.putExtra("categoryId", "tacos");
            this.startActivity(searchResults);
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
            Intent goHome = new Intent(HomeActivity.this, HomeActivity.class);
            this.startActivity(goHome);
        } else if(item.getItemId() == R.id.menuItemMyAccount) {
            Intent myAccount = new Intent(this, Account.class);
            this.startActivity(myAccount);
        }else if(item.getItemId() == R.id.menuItemLogInOut) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                Intent logout = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(logout);
            }
            else {
                Intent logInOut = new Intent(this, LoginActivity.class);
                this.startActivity(logInOut);
            }

        }else if(item.getItemId() == R.id.menuItemAddReview) {
            Intent addReviewActivity = new Intent(HomeActivity.this, AddReviewActivity.class);
            startActivity(addReviewActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    public void bringCategoryItemsToFront() {
        imageButtonDish1.bringToFront();
        imageButtonDish2.bringToFront();
        imageButtonDish3.bringToFront();
        imageButtonDish4.bringToFront();
        imageButtonSushi.bringToFront();
        imageButtonNoodles.bringToFront();
        imageButtonTacos.bringToFront();
        imageButtonSalad.bringToFront();

        textViewDish1.bringToFront();
        textViewDish2.bringToFront();
        textViewDish3.bringToFront();
        textViewDish4.bringToFront();
        textViewSalad.bringToFront();
        textViewNoodles.bringToFront();
        textViewSushi.bringToFront();
        textViewTacos.bringToFront();
    }


}
