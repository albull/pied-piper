package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Account extends Activity {

    private TextView textViewUsername;
    private ListView userReviewsListView;
    private ArrayList<Review> reviews;
    private UserReviewsAdapter userReviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        reviews = new ArrayList<Review>();
        userReviewsAdapter = new UserReviewsAdapter();





        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        userReviewsListView = (ListView) findViewById(R.id.userReviews);

        userReviewsListView.setAdapter(userReviewsAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getEmail();

            textViewUsername.setText(email);
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = database.getReference("users").child(uid).child("reviews");
            final DatabaseReference reviewsRef = database.getReference("reviews");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot r : dataSnapshot.getChildren()) {
                        String reviewId = r.getKey();
                        reviewsRef.child(reviewId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Review review = dataSnapshot.getValue(Review.class);
                                if(review != null) {
                                    reviews.add(review);
                                    userReviewsAdapter.notifyDataSetChanged();
                                }
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

        }
        else {
            Intent loginIntent = new Intent(Account.this, LoginActivity.class);
            startActivity(loginIntent);
        }


    }

    public class UserReviewsAdapter extends BaseAdapter {

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
            view = getLayoutInflater().inflate(R.layout.userreviewcell, null);

            TextView dishName = (TextView) view.findViewById(R.id.userReviewDishNameTextView);
            TextView restaurantName = (TextView) view.findViewById(R.id.userReviewRestaurantNameTextView);
            TextView dishRating = (TextView) view.findViewById(R.id.userReviewDishRatingTextView);
            TextView restaurantRating = (TextView) view.findViewById(R.id.userReviewRestaurantRatingTextView);
            TextView dishReview = (TextView) view.findViewById(R.id.userReviewDishReviewTextView);
            TextView restaurantReview = (TextView) view.findViewById(R.id.userReviewRestaurantReviewTextView);


            if(reviews.get(i).restaurantName != null) {
                restaurantName.setText(reviews.get(i).restaurantName.toString());

            }

            if(reviews.get(i).dishName != null) {
                dishName.setText(reviews.get(i).dishName.toString());
            }

            if(reviews.get(i).restaurantRating != null) {
                restaurantRating.setText(reviews.get(i).restaurantRating.toString());
            }

            if(reviews.get(i).dishRating != null) {
                dishRating.setText(reviews.get(i).dishRating.toString());
            }

            if(reviews.get(i).dishReview != null) {
                dishReview.setText(reviews.get(i).dishReview.toString());
            }

            if(reviews.get(i).restaurantReview != null) {
                restaurantReview.setText(reviews.get(i).restaurantReview.toString());
            }


            return view;

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

}