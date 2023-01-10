package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TaqyryptarActivity extends AppCompatActivity {
    private ImageView btn_back;
    Button suraq1,suraq2;
    Dialog dialog7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taqyryptar);
        btn_back = findViewById(R.id.btn_back);
        suraq1 = findViewById(R.id.suraq1);
        suraq2 = findViewById(R.id.suarq2);
        dialog7 = new Dialog(this);
        suraq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = getLayoutInflater().inflate(R.layout.dialog7, null);
                dialog7 = new Dialog(TaqyryptarActivity.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                dialog7.setContentView(view);
                dialog7.show();
                dialog7.setContentView(R.layout.dialog7);
                dialog7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog7.show();
                ImageView btn_back_qosymsha_turaly = dialog7.findViewById(R.id.btn_back_qosymsha_turaly);
                btn_back_qosymsha_turaly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog7.dismiss();
                    }
                });
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaqyryptarActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TaqyryptarActivity.this,MainActivity.class);
        startActivity(intent);
    }
}