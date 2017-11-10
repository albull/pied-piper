package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class firstPage extends Activity {

    private ImageButton imageButtonDishPic;
    private TextView textViewDishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        imageButtonDishPic = (ImageButton) findViewById(R.id.imageButtonDishPic);
        textViewDishName = (TextView) findViewById(R.id.textViewDishName);

        imageButtonDishPic.setOnClickListener(this);
    }

    @Override
    public void onClick( View view) {
    }
}
