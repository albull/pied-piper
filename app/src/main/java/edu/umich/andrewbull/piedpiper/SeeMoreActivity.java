package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SeeMoreActivity extends Activity implements View.OnClickListener {

    private TextView textViewCategory;
    private TextView textViewRankingOne;
    private TextView textViewRankingTwo;
    private TextView textViewRankingThree;
    private TextView textViewRankingFour;
    private TextView textViewRankingFive;
    private TextView textViewRankingSix;
    private TextView textViewRankingSeven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewRankingOne = (TextView) findViewById(R.id.textViewRankingOne);
        textViewRankingTwo = (TextView) findViewById(R.id.textViewRankingTwo);
        textViewRankingThree = (TextView) findViewById(R.id.textViewRankingThree);
        textViewRankingFour = (TextView) findViewById(R.id.textViewRankingFour);
        textViewRankingFive = (TextView) findViewById(R.id.textViewRankingFive);
        textViewRankingSix = (TextView) findViewById(R.id.textViewRankingSix);
        textViewRankingSeven = (TextView) findViewById(R.id.textViewRankingSeven);

        textViewRankingOne.setOnClickListener(this);
        textViewRankingTwo.setOnClickListener(this);
        textViewRankingThree.setOnClickListener(this);
        textViewRankingFour.setOnClickListener(this);
        textViewRankingFive.setOnClickListener(this);
        textViewRankingSix.setOnClickListener(this);
        textViewRankingSeven.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == textViewRankingOne || view == textViewRankingTwo || view == textViewRankingThree
                || view == textViewRankingFour || view == textViewRankingFive || view ==textViewRankingSix
                || view == textViewRankingSeven) {
            Intent goToDetail = new Intent(this, DishViewActivity.class);
            this.startActivity(goToDetail);
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

}
