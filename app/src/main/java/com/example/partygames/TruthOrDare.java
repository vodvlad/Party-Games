package com.example.partygames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

public class TruthOrDare extends AppCompat {

    private AdView adView;
    Button trorfl1;
    Button startgame;
    TextView name;
    TextView zadanie;
    ListPlayers listPlayers1 = new ListPlayers();
    LinearLayout linearLayout;
    LinearLayout linearLayout3;
    LinearLayout linearLayoutDone;
    Button done;
    Button dare;
    Button truth;
    InputStream stream;
    int numberPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numberPlayer = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_or_false);

        trorfl1 = findViewById(R.id.button1);
        name = findViewById(R.id.name_player);
        zadanie = findViewById(R.id.zadanie);
        zadanie.setText(R.string.rules);


        linearLayout = findViewById(R.id.linearLayout);
        linearLayout3 = findViewById(R.id.linearLayoutRed);
        linearLayoutDone = findViewById(R.id.linearLayoutDone);
        linearLayout.setVisibility(View.INVISIBLE);
        linearLayoutDone.setVisibility(View.INVISIBLE);

        startgame = findViewById(R.id.startgame);
        done = findViewById(R.id.Done);
        dare = findViewById(R.id.dare);
        truth = findViewById(R.id.truth);

        parsePlayerJson("test.json");

        /////////////////////РЕКЛАМА///////////////////////////////
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




        /////////////////////РЕКЛАМА///////////////////////////////

        // Список вопросов ПРАВДА
        {
            try {
                stream = getAssets().open("TaskListTruth.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TaskManager taskManagerTruth = new TaskManager("TaskListTruth.json", stream);
        //---------------------------------------------

        // Список вопросов Действие
        {
            try {
                stream = getAssets().open("TaskListDare.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TaskManager taskManagerDare = new TaskManager("TaskListDare.json", stream);
        //---------------------------------------------





        trorfl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Games.class);
                startActivity(intent);
            }

        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayoutDone.setVisibility(View.INVISIBLE);
                zadanie.setText(R.string.choose_true);
                nextName();
            }

            private void nextName() {
                if(numberPlayer >= listPlayers1.getObjectListLenth()-1){
                    name.setText(listPlayers1.getPlayerById(numberPlayer).getName());
                    numberPlayer = 0;
                } else {
                    name.setText(listPlayers1.getPlayerById(numberPlayer).getName());
                    numberPlayer++;
                }
            }


        });
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                zadanie.setText(R.string.choose_true);
                name.setText(listPlayers1.getPlayerById(numberPlayer).getName());
                numberPlayer++;
            }

        });
        truth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Task = taskManagerTruth.getRandomTask();
                zadanie.setText(Task);
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayoutDone.setVisibility(View.VISIBLE);
            }

        });

        dare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Task = taskManagerDare.getRandomTask();
                zadanie.setText(Task);
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayoutDone.setVisibility(View.VISIBLE);
            }

        });

    }


    private void parsePlayerJson(String src) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(getFilesDir() + "/" + src)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            int len = Integer.parseInt(jsonObject.get("length").toString());
            for(int i = 0; i < len; i++){

                JSONArray msg = (JSONArray) jsonObject.get(String.valueOf(i));
                Iterator<String> iterator = msg.iterator();
                Player player = new Player();
                player.setId(Integer.parseInt(iterator.next()));
                player.setSex(iterator.next().toString());
                player.setName(iterator.next().toString());
                listPlayers1.addObjecToList(player);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}