package com.example.fileaplication;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHandling {

    static void writeFile(String title, String fileData, Context context){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(title, Context.MODE_PRIVATE));
            outputStreamWriter.write(fileData);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Error", "File gagal ditulis : " + e.toString());
        }
    }

    static String readFile(Context context, String title){
        String rtnFile = "";

        try{
            InputStream inputStream = context.openFileInput(title);

            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String recieveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while((recieveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(recieveString);
                    stringBuilder.append(System.getProperty("line.separator"));
                }
                inputStream.close();
                rtnFile = stringBuilder.toString();
            }

        } catch (FileNotFoundException e) {
            Log.e("File Tidak Ditemukan", e.toString());
        } catch (IOException e) {
            Log.e("Tidak Dapat Baca File", e.toString());
        }
        return rtnFile;
    }

}
