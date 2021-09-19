package com.example.fileaplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
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
        path = getFilesDir();


        tombolNewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFile();
            }
        });

        tombolOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });

        tombolSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();
            }
        });
    }

//  Method untuk membuat file baru
    public void newFile(){
        inputTitle.setText("");
        inputFileData.setText("");
        Toast.makeText(this, "Buka File Baru!", Toast.LENGTH_SHORT).show();
    }

//  Method untuk membuka file yang sudah ada
    private void openFile(){
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()){
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

//      Membuat Alert Dialog untuk menampilkan daftar file
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang ingin dibuka");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadData(items[i].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//  Method untuk menyimpan file
    public void saveFile(){
        if(inputTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Isi Title terlebih dahulu!", Toast.LENGTH_SHORT).show();
        }else{
            String title = inputTitle.getText().toString();
            String fileData = inputFileData.getText().toString();
            FileHandling.simpanFile(title, fileData, this);
            Toast.makeText(this, "Menyimpan file " + title, Toast.LENGTH_SHORT).show();
        }
    }

//  Method untuk memuat dan menampilkan file
    private void loadData(String title){
        String text = FileHandling.bacaFile(this, title);
        inputTitle.setText(title);
        inputFileData.setText(text);
    }

}