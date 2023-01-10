package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DostarActivity extends AppCompatActivity {
    private ImageView btn_back,user_img;
    CardView dostarmen_jarysu;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    TextView user_point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dostar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        btn_back = findViewById(R.id.btn_back);
        user_point = findViewById(R.id.user_point);
        dostarmen_jarysu = findViewById(R.id.dostarmen_jarysu);
        TextView txt_btn_dostar = findViewById(R.id.txt_btn_dostar);
        TextView txt_btn_upailar = findViewById(R.id.txt_btn_upailar);
        TextView aty_turatyn_jer = findViewById(R.id.aty_turatyn_jer);
        ImageView user_img = findViewById(R.id.user_img);

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                String name = snapshot.child("name").getValue().toString();
                String  progress = snapshot.child("progress").getValue().toString();
                int number = Integer.parseInt(progress);
                String str2 = Integer.toString(number);
                user_point.setText(str2);
                aty_turatyn_jer.setText(name);
                if (snapshot.hasChild("image")) {
                    String image = snapshot.child("image").getValue().toString();
                    Picasso.get().load(image).into(user_img);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
        });
        dostarmen_jarysu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DostarActivity.this,CallingActivity.class);
                startActivity(intent);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout,DostarFragment1.class,null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
        txt_btn_dostar.setBackgroundResource(R.drawable.dostar_fragment_btn);
        txt_btn_upailar.setBackgroundResource(R.drawable.dostar_fragment_btn_no_active);
        txt_btn_dostar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        txt_btn_upailar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        txt_btn_dostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout,DostarFragment1.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
                txt_btn_dostar.setBackgroundResource(R.drawable.dostar_fragment_btn);
                txt_btn_upailar.setBackgroundResource(R.drawable.dostar_fragment_btn_no_active);
                txt_btn_dostar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                txt_btn_upailar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            }
        });

        txt_btn_upailar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout,DostarFragment2.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
                txt_btn_upailar.setBackgroundResource(R.drawable.dostar_fragment_btn);
                txt_btn_dostar.setBackgroundResource(R.drawable.dostar_fragment_btn_no_active);
                txt_btn_upailar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                txt_btn_dostar.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DostarActivity.this,MainActivity.class);
                startActivity(intent2);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DostarActivity.this,MainActivity.class);
        startActivity(intent);
    }
}