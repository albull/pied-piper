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

public class DishSuggestions {
    public Map<String,String> dishSuggestionMap;
    public ArrayList<String> dishSuggestions;

    public DishSuggestions() {
        dishSuggestions = new ArrayList<String>();
        dishSuggestionMap = new HashMap<String,String>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("dishes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dish : dataSnapshot.getChildren()) {
                    Dish d = dish.getValue(Dish.class);
                    d.setDishId(dish.getKey().toString());
                    dishSuggestionMap.put(d.getDishName().toString(),d.getDishId().toString());
                    dishSuggestions.add(d.getDishName().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public DishSuggestions(final Map<String,Boolean> dishes) {
        dishSuggestions = new ArrayList<String>();
        dishSuggestionMap = new HashMap<String,String>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("dishes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dish : dataSnapshot.getChildren()) {
                    Dish d = dish.getValue(Dish.class);
                    d.setDishId(dish.getKey().toString());
                    if(dishes.containsKey(d.getDishId())) {
                        dishSuggestionMap.put(d.getDishName().toString(),d.getDishId().toString());
                        dishSuggestions.add(d.getDishName().toString());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
