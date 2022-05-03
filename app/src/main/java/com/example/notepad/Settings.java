package com.example.notepad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Settings {
    private static final String FILE_NAME = "settings";
    private static Settings settings;

    private String font;
    private String color;

    public static Settings getSettings() {
        if(settings==null) settings = new Settings();
        return settings;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static void save(Activity activity){
        Gson gson = new Gson();
        try {
            FileOutputStream stream = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            Settings settings = Settings.getSettings();
            EditText fontEditText = activity.findViewById(R.id.fontEditText);
            EditText colorEditText = activity.findViewById(R.id.colorEditText);
            settings.setColor(colorEditText.getText().toString());
            settings.setFont(fontEditText.getText().toString());
            String json = gson.toJson(settings);
            //Convert your JSON String to Bytes and write() it
            stream.write(json.getBytes());
            //Finally flush and close your FileOutputStream
            stream.flush();
            stream.close();
            Log.e("FF", "save");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void open(Context context){
        Gson gson = new Gson();
        String text = "";
        try {
            FileInputStream inputStream = context.openFileInput(FILE_NAME);
            StringBuilder stringBuilder = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                text = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            //Log your error with Log.e
        } catch (IOException e) {
            //Log your error with Log.e
        }

        Settings settingsFromJson = gson.fromJson(text, Settings.class);
        Settings.getSettings().setFont(settingsFromJson.getFont());
        Settings.getSettings().setColor(settingsFromJson.getColor());
        Log.e("FF", "from json color = " + settingsFromJson.getColor());
        Log.e("FF", "from json font = " + settingsFromJson.getFont());
    }




}
