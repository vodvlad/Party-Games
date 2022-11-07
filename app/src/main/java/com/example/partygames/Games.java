package com.example.partygames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Games extends AppCompat {

    Button home;
    Button trorfl;
    Button alias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        home = findViewById(R.id.button38);
        trorfl = findViewById(R.id.buttontof);
        alias = findViewById(R.id.buttontof2);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        alias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Alias.class);
                startActivity(intent);
            }
        });

        trorfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TruthOrDare.class);
                    startActivity(intent);
            }
        });

    }



}