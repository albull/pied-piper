package edu.umich.andrewbull.piedpiper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrewbull on 12/9/17.
 */

public class RestaurantSuggestions {
    public Map<String,Restaurant> restaurantSuggestionsMap;
    public ArrayList<String> restaurantSuggestions;


    RestaurantSuggestions() {
        restaurantSuggestionsMap = new HashMap<String,Restaurant>();
        restaurantSuggestions = new ArrayList<String>();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("restaurants");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot restaurant : dataSnapshot.getChildren()) {
                    Restaurant r = restaurant.getValue(Restaurant.class);
                    r.setRestaurantId(restaurant.getKey().toString());
                    restaurantSuggestionsMap.put(r.getRestaurantName().toString(), r);
                    restaurantSuggestions.add(r.getRestaurantName().toString());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
