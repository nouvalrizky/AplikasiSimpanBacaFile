package com.example.fileaplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button tombolNewFile, tombolSaveFile, tombolOpenFile;
    EditText inputTitle, inputFileData;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tombolNewFile = findViewById(R.id.tombolNewFile);
        tombolSaveFile = findViewById(R.id.tombolSaveFile);
        tombolOpenFile = findViewById(R.id.tombolOpenFile);
        inputTitle = findViewById(R.id.inputTitle);
        inputFileData = findViewById(R.id.inputFileData);

        tombolNewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileBaru();
            }
        });

        tombolOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lihatDaftarFile();
            }
        });

        tombolSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanFile();
            }
        });
    }

//  Method untuk membuat file baru
    public void fileBaru(){
        inputTitle.getText().clear();
        inputFileData.getText().clear();
        Toast.makeText(this, "Membuka File Baru!", Toast.LENGTH_SHORT).show();
    }

//  Method untuk menampilkan daftar file
    private void lihatDaftarFile(){
        path = getFilesDir();
        ArrayList<String> listFile = new ArrayList<String>();
        for (String file : path.list()){
            listFile.add(file);
        }
        final String[] items = listFile.toArray(new String[listFile.size()]);

//      Membuat Alert Dialog untuk menampilkan daftar file
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang ingin dibuka");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bacaFile(items[i].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//  Method untuk memuat dan menampilkan file
    private void bacaFile(String title){

        String finalFile = "";

        try{
            InputStream inputStream = this.openFileInput(title);

            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String text;

                while((text = bufferedReader.readLine()) != null){
                    stringBuilder.append(text);
                    stringBuilder.append(System.getProperty("line.separator"));
                }
                inputStream.close();
                finalFile = stringBuilder.toString();
            }

        } catch (FileNotFoundException e) {
            Log.e("File Tidak Ditemukan", e.toString());
        } catch (IOException e) {
            Log.e("Tidak Dapat Baca File", e.toString());
        }

        inputTitle.setText(title);
        inputFileData.setText(finalFile);
    }

//  Method untuk menyimpan file
    public void simpanFile(){
        String title = inputTitle.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(this, "Isi Title terlebih dahulu!", Toast.LENGTH_SHORT).show();
        }else{
            String fileData = inputFileData.getText().toString();

            try{
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(title, Context.MODE_PRIVATE));
                outputStreamWriter.write(fileData);
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Error", "File gagal ditulis : " + e.toString());
            }

            Toast.makeText(this, "Menyimpan file " + title, Toast.LENGTH_SHORT).show();
        }
    }

}