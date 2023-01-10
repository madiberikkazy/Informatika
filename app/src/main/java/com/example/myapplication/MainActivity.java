package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
private ImageView menu;
private ProgressBar progress_bar;
private TextView propress_pencent,txtName;
private LinearLayout taqyryptar,quiz,dostar;
private FirebaseAuth mAuth;
private DatabaseReference databaseReference;
    private CircleImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        taqyryptar = findViewById(R.id.taqyryptar);
        quiz = findViewById(R.id.quiz);
        dostar = findViewById(R.id.dostar);
        progress_bar = findViewById(R.id.progress_bar);
        propress_pencent = findViewById(R.id.propress_pencent);
        txtName  = findViewById(R.id.txtName);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        profile_image = findViewById(R.id.user_img);

        txtName.setText(GlobalVar.currentUser.getName());
        menu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                ShowMenu();
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        dostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DostarActivity.class);
                startActivity(intent);
            }
        });
        taqyryptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaqyryptarActivity.class);
                startActivity(intent);
            }
        });
        getUserinfo();
    }
    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    String  name = snapshot.child("name").getValue().toString();
                    txtName.setText(name);
                    String  progress = snapshot.child("progress").getValue().toString();
                    int number = Integer.parseInt(progress);
                    int number_d = number/10;

                    progress_bar.setProgress(number_d);
                    propress_pencent.setText(number_d+"%");
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void ShowMenu(){
    PopupMenu popupMenu =new PopupMenu(this,menu);
    popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
    popupMenu.setForceShowIcon(true);
    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                    case R.id.baptaular:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    break;
                case R.id.jazyp_alu_menu:
                        Intent intent2 = new Intent(MainActivity.this, WriteActivity.class);
                        startActivity(intent2);
                    break;
            }
            return true;
        }
    });
    popupMenu.show();
}

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
        alertdialog.setTitle(R.string.shygu);
        alertdialog.setMessage(R.string.shyguy_qalaisyzba);
        alertdialog.setPositiveButton(R.string.ia, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        alertdialog.setNegativeButton(R.string.zhoq, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertdialog.show();
    }
}