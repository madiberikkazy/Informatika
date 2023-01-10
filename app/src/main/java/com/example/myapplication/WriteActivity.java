package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class WriteActivity extends AppCompatActivity {

ImageView btn_back;
static ListView listview;
static ArrayList<String> items;
static ListViewAdapter1 adapter;
ImageView img_save;
EditText write_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        btn_back = findViewById(R.id.btn_back);
        listview = findViewById(R.id.listview);
        img_save = findViewById(R.id.img_save);
        write_txt = findViewById(R.id.write_txt);
        items = new ArrayList<>();
        adapter = new ListViewAdapter1(getApplicationContext(),items);
        listview.setAdapter(adapter);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = write_txt.getText().toString();
                if (text==null||text.length()==0){
                    Toast.makeText(WriteActivity.this,R.string.matin_jazynyz,Toast.LENGTH_SHORT).show();
                }
                else {
                    addCard(text);
                    write_txt.setText("");
                }
            }
        });
        loadContent();
    }

    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path,"list.txt");
        byte[] content = new byte[(int)readFrom.length()];
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            s = s.substring(1,s.length()-1);
            String split[] =s.split(", ");
            items = new ArrayList<>(Arrays.asList(split));
            adapter = new ListViewAdapter1(this,items);
            listview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path,"list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    public static void  removeItem(int remove){
        items.remove(remove);
        listview.setAdapter(adapter);
    }
    private void addCard(String item) {
        items.add(item);
        listview.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WriteActivity.this,MainActivity.class);
        startActivity(intent);
    }
}