package com.example.notepad;

import android.app.Activity;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    private static final String FILE_NAME = "testNote";

    public static void write(Activity activity, String text){
        try (FileOutputStream fos = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(Activity activity){
        try (FileInputStream fis = activity.openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder(1000);
            while (fis.read(bytes) != -1) {
                sb.append(new String(bytes, StandardCharsets.UTF_8));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "";
    }


}
