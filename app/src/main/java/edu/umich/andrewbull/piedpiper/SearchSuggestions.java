package edu.umich.andrewbull.piedpiper;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by andrewbull on 12/9/17.
 */

public class SearchSuggestions {

    public Map<String,String> suggestionMap;
    public ArrayList<String> suggestions;

    public SearchSuggestions() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("suggestions");
        suggestions = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suggestionMap = (Map<String, String>)dataSnapshot.getValue();

                for(Map.Entry<String,String> entry : suggestionMap.entrySet()) {
                    suggestions.add(entry.getKey());
                    Log.d("msg", entry.getKey() + entry.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
