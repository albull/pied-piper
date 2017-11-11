package edu.umich.andrewbull.piedpiper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class firstPage extends Activity implements Button.OnClickListener{

    private ImageButton imageButtonDish1, imageButtonDish2, imageButtonDish3, imageButtonDish4;
    private TextView textViewDish1, textViewDish2, textViewDish3, textViewDish4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        imageButtonDish1 = (ImageButton) findViewById(R.id.imageButtonDish1);
        imageButtonDish2 = (ImageButton) findViewById(R.id.imageButtonDish2);
        imageButtonDish3 = (ImageButton) findViewById(R.id.imageButtonDish3);
        imageButtonDish4 = (ImageButton) findViewById(R.id.imageButtonDish4);
        textViewDish1 = (TextView) findViewById(R.id.textViewDish1);
        textViewDish2 = (TextView) findViewById(R.id.textViewDish2);
        textViewDish3 = (TextView) findViewById(R.id.textViewDish3);
        textViewDish4 = (TextView) findViewById(R.id.textViewDish4);

        imageButtonDish1.setOnClickListener(this);
        imageButtonDish2.setOnClickListener(this);
        imageButtonDish3.setOnClickListener(this);
        imageButtonDish4.setOnClickListener(this);
    }

    @Override
    public void onClick( View view) {
    }
}
