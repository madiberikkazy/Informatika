package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtEmail,edtName,edtPhone,edtPass;
    String a = "1";
    String b = "0";
    Button btnRegister;
    ProgressDialog loadingBar;
    FirebaseDatabase db;
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        db = FirebaseDatabase.getInstance();
        userRef = db.getReference("User");
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtPass = findViewById(R.id.edtPass);
        btnRegister = findViewById(R.id.btnRegister);
        loadingBar  = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

        });
        TextView txt_kiru = findViewById(R.id.txt_kiru);
        txt_kiru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
        private void registerUser() {
            String email = edtEmail.getText().toString();
            String pass = edtPass.getText().toString();
            RegisterUser(email,pass);
        }

    private void RegisterUser(String email, String pass) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.otinish_email_tolt,Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,R.string.otinish_pass_tolt,Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle(R.string.tirkelu);
            loadingBar.setMessage("Please,wait...");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User();
                        user.setEmail(edtEmail.getText().toString());
                        user.setName(edtName.getText().toString());
                        user.setPhone(edtPhone.getText().toString());
                        user.setPassword(edtPass.getText().toString());
                        user.setSuraq1(a);
                        user.setProgress(b);
                        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this,R.string.satti_tirkelu,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,R.string.okinishke_orai_tirkelu,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,R.string.okinishke_orai_tirkelu,Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }

    }
}