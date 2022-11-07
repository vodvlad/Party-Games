package com.example.partygames;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class TaskManager {
    public TaskManager(String nameJsonFile, InputStream stream) {
        this.nameJsonFile = nameJsonFile;
        String tContents = "";

        try {;
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);

            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(tContents);
                int len = Integer.parseInt(jsonObject.get("length").toString());
                this.jsonObject = jsonObject;
                this.length = len;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Handle exceptions here
        }
    }

    String nameJsonFile;
    JSONObject jsonObject;
    int length;

    public String getRandomTask() {
        int a = 0;
        int b = length-1;
        int x = a + (int)(Math.random() * ((b - a) + 1));
        String len = jsonObject.get(String.valueOf(x)).toString();
        return len;
    }
}
