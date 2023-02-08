package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class ChangeActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private CircleImageView profile_image;
    private FirebaseAuth mAuth;
    private ImageView btn_back;
    private TextView saveButton;
    private EditText edtName,edtPhone,edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        profile_image = findViewById(R.id.profile_image);
        saveButton = findViewById(R.id.saqtau);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        btn_back = findViewById(R.id.btn_back);
        getUserinfo();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ChangeActivity.this,SettingsActivity.class);
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
        if(TextUtils.isEmpty(edtName.getText().toString())){
            Toast.makeText(this,R.string.otinish_at_tolt,Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(edtPhone.getText().toString())){
            Toast.makeText(this,R.string.otinish_phone_tolt,Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(edtEmail.getText().toString())){
            Toast.makeText(this,R.string.otinish_email_tolt,Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String,Object> userMap = new HashMap<>();
            userMap.put("name",edtName.getText().toString());
            userMap.put("phone",edtPhone.getText().toString());
            userMap.put("email",edtEmail.getText().toString());
            databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
        }
    }
    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    String name = snapshot.child("name").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    edtName.setText(name);
                    edtPhone.setText(phone);
                    edtEmail.setText(email);
                    if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangeActivity.this,SettingsActivity.class);
        startActivity(intent);
    }
}