package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.type.Color;
public class LoginActivity extends AppCompatActivity {
    EditText edtEmail,edtPass;
    Button btnSignIn;
    TextView btn_tirkelu;
    private FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass  = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btn_tirkelu  = findViewById(R.id.txt_tirkelu);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        loadingBar  = new ProgressDialog(this);
        btn_tirkelu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticationUser();
            }
        });
    }
    private void authenticationUser() {
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.otinish_email_tolt,Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,R.string.otinish_pass_tolt,Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle(R.string.kiru);
            loadingBar.setMessage("Please,wait...");
            loadingBar.show();
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                           FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   GlobalVar.currentUser = snapshot.getValue(User.class);
                                   Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                   startActivity(intent);
                                   Toast.makeText(LoginActivity.this,R.string.satti_kiru,Toast.LENGTH_SHORT).show();
                                   finish();
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });

                        } else {
                            Toast.makeText(LoginActivity.this,R.string.okinishke_orai_kiru,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}