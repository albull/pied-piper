package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchResultsViewActivity extends Activity implements View.OnClickListener{

    private TextView textViewDish1;
    private TextView textViewDish2;
    private TextView textViewDish3;
    private TextView textViewRestaurant1;
    private TextView textViewRestaurant2;
    private TextView textViewRestaurant3;
    private TextView textViewSeeMoreDish;
    private TextView textViewSeeMoreRestaurant;

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
}
