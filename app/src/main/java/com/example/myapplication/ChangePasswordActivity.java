package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePasswordActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ImageView btn_back;
    private TextView saveButton;
    private EditText edtOldPass,edtNewPass,edtNewPassRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        saveButton = findViewById(R.id.saqtau);
        edtOldPass = findViewById(R.id.edtOldPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtNewPassRepeat = findViewById(R.id.edtNewPassRepeat);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ChangePasswordActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfileImage();
            }
        });
    }

    private void uploadProfileImage() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    String password = snapshot.child("password").getValue().toString();
                    if(!(password.equals(edtOldPass.getText().toString()))){
                        Toast.makeText(getApplicationContext(),R.string.qoldanystagy_quia,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(TextUtils.isEmpty(edtNewPass.getText().toString())){
            Toast.makeText(this,R.string.otinish_zhana_pass_tolt,Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(edtNewPassRepeat.getText().toString())){
            Toast.makeText(this,R.string.otinish_zhana_pass_qaita_tolt,Toast.LENGTH_SHORT).show();
        }

        else{
            if(!(edtNewPass.getText().toString().equals(edtNewPassRepeat.getText().toString()))){
                Toast.makeText(this,R.string.zhana_qupia_sozder_saikes,Toast.LENGTH_SHORT).show();
            }
            if(edtNewPass.length()<6){
                Toast.makeText(this,R.string.zhana_qup_tanbasy,Toast.LENGTH_SHORT).show();
            }
            if(edtNewPassRepeat.length()<6){
                Toast.makeText(this,R.string.zhana_qup_tanbasy,Toast.LENGTH_SHORT).show();
            }
            else {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle(R.string.aty_jondi_oz);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
                HashMap<String,Object> userMap = new HashMap<>();
                userMap.put("password",edtNewPass.getText().toString());
                databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
            }
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswordActivity.this,SettingsActivity.class);
        startActivity(intent);
    }
}