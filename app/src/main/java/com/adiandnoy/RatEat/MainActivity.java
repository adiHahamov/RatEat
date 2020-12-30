package com.adiandnoy.RatEat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView titel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    titel = findViewById(R.id.mainActivity_mainTitel);

    titel.setText("WELCOME");

    Button myButton = findViewById(R.id.MainActivity_button);

    myButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            titel.setText("Changedd");
        }
    });



    }

}