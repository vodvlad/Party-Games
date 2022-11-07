package com.example.partygames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class Alias extends AppCompat {
    Button games;
    Button startGame;
    Button red;
    Button blue;
    LinearLayout linearLayoutRed;
    LinearLayout linearLayoutBlue;
    LinearLayout linearLayoutStart;
    LinearLayout linearLayoutStart1;
    LinearLayout linLayBlue;
    LinearLayout linLayRed;
    TextView playerName;
    TextView aliasText;
    int numberPlayer;
    AliasTeams aliasTeams;
    String blueTeamColor; /////////пофиксить мультиязычность
    String redTeamColor; /////////пофиксить мультиязычность

    ListPlayers listPlayers1 = new ListPlayers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numberPlayer = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alias);
        blueTeamColor = getResources().getString(R.string.blueTeam);
        redTeamColor = getResources().getString(R.string.redTeam);
        games = findViewById(R.id.buttongames);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        startGame = findViewById(R.id.startgame);
        linearLayoutRed = findViewById(R.id.linearLayoutRed);
        linearLayoutBlue = findViewById(R.id.linearLayoutBlue);
        linearLayoutStart = findViewById(R.id.linearLayoutStart);
        linearLayoutStart1 = findViewById(R.id.linearLayoutStart1);
        linLayBlue = findViewById(R.id.linLayBlueT);
        linLayRed = findViewById(R.id.linLayRedT);
        playerName = findViewById(R.id.name_player);
        aliasText = findViewById(R.id.alias_text);
        aliasTeams = new AliasTeams(blueTeamColor, redTeamColor);

        aliasText.setText("Тут должны быть правила алиаса");

        parsePlayerJson("test.json");


        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Games.class);
                startActivity(intent);
            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName.setText(listPlayers1.getPlayerById(numberPlayer).getName());
                aliasText.setText("распредели игроков по команды");
                linearLayoutStart.setVisibility(View.INVISIBLE);
                linearLayoutRed.setVisibility(View.VISIBLE);
                linearLayoutBlue.setVisibility(View.VISIBLE);
                playerName.setVisibility(View.VISIBLE);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberPlayer != listPlayers1.getObjectListLenth()-1){
                    aliasTeams.addPlayerToBlueTeam(listPlayers1.getPlayerById(numberPlayer));
                    addPlayerToList(listPlayers1.getPlayerById(numberPlayer),linLayBlue);
                    numberPlayer++;
                    playerName.setText(listPlayers1.getPlayerById(numberPlayer).getName());


                } else {
                    playerName.setText(aliasTeams.currentTeamName());
                    linearLayoutRed.setVisibility(View.INVISIBLE);
                    linearLayoutBlue.setVisibility(View.INVISIBLE);
                    linearLayoutStart1.setVisibility(View.VISIBLE);
                    playerName.setVisibility(View.VISIBLE);
                }
            }
        });



        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberPlayer != listPlayers1.getObjectListLenth()-1){
                    aliasTeams.addPlayerToRedTeam(listPlayers1.getPlayerById(numberPlayer));
                    addPlayerToList(listPlayers1.getPlayerById(numberPlayer),linLayRed);
                    numberPlayer++;
                    playerName.setText(listPlayers1.getPlayerById(numberPlayer).getName());


                } else {
                    playerName.setText(aliasTeams.currentTeamName());
                    linearLayoutRed.setVisibility(View.INVISIBLE);
                    linearLayoutBlue.setVisibility(View.INVISIBLE);
                    linearLayoutStart1.setVisibility(View.VISIBLE);

                    playerName.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void addPlayerToList(Player player, LinearLayout linearLayout) {

        TextView textView = new TextView(getApplicationContext());
        textView.setText(player.getName());
        if(player.getSex().equals("Male") || player.getSex().equals("Парень")){  // в зависимотси от пола выбрать автар
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons8_account, 0, 0, 0);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons8_female_user, 0, 0, 0);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textView.setLayoutParams(params);
        linearLayout.addView(textView);

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