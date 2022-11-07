package com.example.partygames;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompat {



    public ListPlayers listPlayers = new ListPlayers();
    int num = 0;

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button buttonAddPlayer;
    Button games;
    Toast toast;
    EditText editText;
    LinearLayout linLay;
    ImageButton translateBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////////////////////////////////////////

        radioGroup = findViewById(R.id.radioGroup);
        buttonAddPlayer = findViewById(R.id.button40);
        games = findViewById(R.id.button37);
        translateBtn = findViewById(R.id.translate_btn);

        ///////////////////////////////////////////////////

        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlayer();
            }
        });

        ///////////////////////////////////////////////////

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Language.class);
                startActivity(intent);
            }
        });

        //////////////////////////////////////////////////
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listPlayers.getObjectListLenth() >= 2){
                    for(int i = 0; i < listPlayers.getObjectListLenth(); i++){
                        EditText editText = findViewById(i);
                        Player player = listPlayers.getPlayerById(i);
                        player.setName(editText.getText().toString());

                    }
                    addPlayerToJson("test.json");
                    Intent intent = new Intent(getApplicationContext(), Games.class);
                    startActivity(intent);
                } else {
                    toast.makeText(getApplicationContext(), R.string.y_need_to_add_player,  // уведомление о добавлении игрока
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    public void AddPlayer() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        /////////////////////////////////////////////////

        if(!radioButton.getText().equals(null)){   //если чекбокс не равен null выбран или male или female
            linLay = findViewById(R.id.linLay); //поиск линейного лейера по ид
            Player player = new Player();
            player.setSex((String) radioButton.getText());//создаётся новый игрок которому даётся пол и ид
            player.setId(num);
            listPlayers.addObjecToList(player); // игрок добавляется в список


            editText = new EditText(getApplicationContext()); //создаётся новый объект эдит текст
            editText.setText(radioButton.getText()); // num + " " +
            editText.setId(num);
            editText.setMaxLines(1);
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(10);//Filter to 10 characters
            editText.setFilters(filters);
            editText.setSingleLine();

            num += 1;

            if(player.getSex().equals("Male") || player.getSex().equals("Парень")){  // в зависимотси от пола выбрать автар
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons8_account, 0, 0, 0);
            } else {
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons8_female_user, 0, 0, 0);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            editText.setLayoutParams(params);
            linLay.addView(editText); // Добавить новую строку в список


        }


    }

    private void addPlayerToJson(String src) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("length", (Integer) listPlayers.getObjectListLenth()); // добавляется кол-во игроков (число)

        for (int i = 0; i < listPlayers.getObjectListLenth(); i++) { //цикл добавления каждого игрока
            Player first = listPlayers.getPlayerById(i);
            JSONArray list = new JSONArray();
            list.add(String.valueOf(first.getId()));
            list.add(first.getSex());
            list.add(first.getName());
            resultJson.put(i, list);
        }

        try (FileWriter file = new FileWriter(getFilesDir() + "/" + src)) {
            file.write(resultJson.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}