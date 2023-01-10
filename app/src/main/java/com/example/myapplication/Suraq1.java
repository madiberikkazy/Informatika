package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Suraq1 extends AppCompatActivity {
    TextView suraq1, suraq2,suraq3,suraq4,suraq5,suraq6;
    RadioButton sq1_jauap1, sq1_jauap2, sq1_jauap3, sq1_jauap4;
    RadioButton sq2_jauap2, sq2_jauap1, sq2_jauap3, sq2_jauap4;
    RadioButton sq3_jauap2, sq3_jauap1, sq3_jauap3, sq3_jauap4;
    RadioButton sq4_jauap2, sq4_jauap1, sq4_jauap3, sq4_jauap4;
    RadioButton sq5_jauap2, sq5_jauap1, sq5_jauap3, sq5_jauap4;
    RadioButton sq6_jauap2, sq6_jauap1, sq6_jauap3, sq6_jauap4;
    Button btn_tekseru;
    Dialog dialog;
    ProgressBar progress_bar;
    CountDownTimer mCountDownTimer;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    int i = 60;
    String[] suraktar = {"блокты оңға жылжыту", "қатені іздеу","бүлінген жолды қалпына келтіру",
            "маркерді іздеу","блок баспадан шығару","Блок басын белгілеу",};
    String[][] varianttar = {
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},
            {"Ctrl-K Y", "Ctrl-K Y", "Ctrl-K n", "Ctrl-Q ]"},


    };
    String[] jauaptar = {"Ctrl-K n", "Ctrl-K Y","Ctrl-K n","Ctrl-Q ]","Ctrl-Q ]","Ctrl-K Y",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suraq1);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setProgress(i);
        mCountDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                progress_bar.setProgress(i);
                i--;
            }

            @Override
            public void onFinish() {
                i--;
                tekseris(suraq1, sq1_jauap1, sq1_jauap2, sq1_jauap3, sq1_jauap4, 0);
                tekseris(suraq2, sq2_jauap1, sq2_jauap2, sq2_jauap3, sq2_jauap4, 1);
                tekseris(suraq3, sq3_jauap1, sq3_jauap2, sq3_jauap3, sq3_jauap4, 2);
                tekseris(suraq4, sq4_jauap1, sq4_jauap2, sq4_jauap3, sq4_jauap4, 3);
                tekseris(suraq5, sq5_jauap1, sq5_jauap2, sq5_jauap3, sq5_jauap4, 4);
                tekseris(suraq6, sq6_jauap1, sq6_jauap2, sq6_jauap3, sq6_jauap4, 5);
                openDialogPoint();

                btn_tekseru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tekseris(suraq1, sq1_jauap1, sq1_jauap2, sq1_jauap3, sq1_jauap4, 0);
                        tekseris(suraq2, sq2_jauap1, sq2_jauap2, sq2_jauap3, sq2_jauap4, 1);
                        tekseris(suraq3, sq3_jauap1, sq3_jauap2, sq3_jauap3, sq3_jauap4, 2);
                        tekseris(suraq4, sq4_jauap1, sq4_jauap2, sq4_jauap3, sq4_jauap4, 3);
                        tekseris(suraq5, sq5_jauap1, sq5_jauap2, sq5_jauap3, sq5_jauap4, 4);
                        tekseris(suraq6, sq6_jauap1, sq6_jauap2, sq6_jauap3, sq6_jauap4, 5);

                        openDialogPoint();
                    }
                });
            }
        };
        mCountDownTimer.start();
        dialog = new Dialog(this);
        suraq1 = findViewById(R.id.suraq1);
        suraq2 = findViewById(R.id.suraq2);
        suraq3 = findViewById(R.id.suraq3);
        suraq4 = findViewById(R.id.suraq4);
        suraq5 = findViewById(R.id.suraq5);
        suraq6 = findViewById(R.id.suraq6);

        sq1_jauap1 = findViewById(R.id.sq1_jauap1);
        sq1_jauap2 = findViewById(R.id.sq1_jauap2);
        sq1_jauap3 = findViewById(R.id.sq1_jauap3);
        sq1_jauap4 = findViewById(R.id.sq1_jauap4);

        sq2_jauap2 = findViewById(R.id.sq2_jauap2);
        sq2_jauap1 = findViewById(R.id.sq2_jauap1);
        sq2_jauap4 = findViewById(R.id.sq2_jauap4);
        sq2_jauap3 = findViewById(R.id.sq2_jauap3);

        sq3_jauap2 = findViewById(R.id.sq3_jauap2);
        sq3_jauap1 = findViewById(R.id.sq3_jauap1);
        sq3_jauap4 = findViewById(R.id.sq3_jauap4);
        sq3_jauap3 = findViewById(R.id.sq3_jauap3);

        sq4_jauap2 = findViewById(R.id.sq4_jauap2);
        sq4_jauap1 = findViewById(R.id.sq4_jauap1);
        sq4_jauap4 = findViewById(R.id.sq4_jauap4);
        sq4_jauap3 = findViewById(R.id.sq4_jauap3);

        sq5_jauap2 = findViewById(R.id.sq5_jauap2);
        sq5_jauap1 = findViewById(R.id.sq5_jauap1);
        sq5_jauap4 = findViewById(R.id.sq5_jauap4);
        sq5_jauap3 = findViewById(R.id.sq5_jauap3);

        sq6_jauap2 = findViewById(R.id.sq6_jauap2);
        sq6_jauap1 = findViewById(R.id.sq6_jauap1);
        sq6_jauap4 = findViewById(R.id.sq6_jauap4);
        sq6_jauap3 = findViewById(R.id.sq6_jauap3);

        btn_tekseru = findViewById(R.id.btn_tekseru);
        suraqtarJinau();

        btn_tekseru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tekseris(suraq1, sq1_jauap1, sq1_jauap2, sq1_jauap3, sq1_jauap4, 0);
                tekseris(suraq2, sq2_jauap1, sq2_jauap2, sq2_jauap3, sq2_jauap4, 1);
                tekseris(suraq3, sq3_jauap1, sq3_jauap2, sq3_jauap3, sq3_jauap4, 2);
                tekseris(suraq4, sq4_jauap1, sq4_jauap2, sq4_jauap3, sq4_jauap4, 3);
                tekseris(suraq5, sq5_jauap1, sq5_jauap2, sq5_jauap3, sq5_jauap4, 4);
                tekseris(suraq6, sq6_jauap1, sq6_jauap2, sq6_jauap3, sq6_jauap4, 5);
                openDialogPoint();
                mCountDownTimer.cancel();
            }
        });
    }

    public void openDialogPoint() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog6, null);
        dialog.setContentView(R.layout.dialog6);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ImageView img = dialog.findViewById(R.id.img);
        CircleImageView user_img_dialog = dialog.findViewById(R.id.user_img_dialog);
        TextView upai = dialog.findViewById(R.id.upai);
        TextView san = dialog.findViewById(R.id.san);
        TextView ret_san = dialog.findViewById(R.id.ret_san);
        TextView aty_turatyn_jer = dialog.findViewById(R.id.aty_turatyn_jer);
        DigitalClock clock = dialog.findViewById(R.id.clock);
        ImageView btn_share = dialog.findViewById(R.id.btn_share);

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    String  name = snapshot.child("name").getValue().toString();
                    String  suraq1 = snapshot.child("suraq1").getValue().toString();
                    int number = Integer.parseInt(suraq1);
//                    int number_plus = number+1;
//                    String str_number_plus = String.valueOf(number_plus);
                    int b = a * 100 / 10;
                    String progress = String.valueOf(b);
                    HashMap<String,Object> userMap = new HashMap<>();
                    userMap.put("progress",progress);
//                    userMap.put("suraq1",str_number_plus);
                    databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                    ret_san.setText(suraq1);




//                    HashMap<String,Object> userMap = new HashMap<>();

//                    databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                    aty_turatyn_jer.setText(name);
                    if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(user_img_dialog);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        builder.setView(view);
        int b = a * 100 / 10;
        if (b < 30) {
            img.setImageResource(R.drawable.emotion1);
        } else if (b < 60) {
            img.setImageResource(R.drawable.emotion2);
        } else {
            img.setImageResource(R.drawable.emotion3);
        }
        upai.setText(String.valueOf(b + " %"));
        san.setText(String.valueOf(a+"/10"));
    }


    int a;
    public void tekseris(TextView suraq, RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4, int index) {
        if (rb1.isChecked() &&
                rb1.getText().toString().equals(jauaptar[index])) {
            rb1.setTextColor(getResources().getColor(R.color.green));
            a = a + 1;
        } else if (rb2.isChecked() &&
                rb2.getText().toString().equals(jauaptar[index])) {
            rb2.setTextColor(getResources().getColor(R.color.green));
            a = a + 1;
        } else if (rb3.isChecked() &&
                rb3.getText().toString().equals(jauaptar[index])) {
            rb3.setTextColor(getResources().getColor(R.color.green));
            a = a + 1;
        } else if (rb4.isChecked() &&
                rb4.getText().toString().equals(jauaptar[index])) {
            rb4.setTextColor(getResources().getColor(R.color.green));
            a = a + 1;
        } else {
            if (rb1.isChecked()) {
                rb1.setTextColor(getResources().getColor(R.color.red));
                if (rb1.getText().toString().equals(jauaptar[index])) {
                    rb1.setTextColor(getResources().getColor(R.color.green));
                } else if (rb2.getText().toString().equals(jauaptar[index])) {
                    rb2.setTextColor(getResources().getColor(R.color.green));
                } else if (rb3.getText().toString().equals(jauaptar[index])) {
                    rb3.setTextColor(getResources().getColor(R.color.green));
                } else if (rb4.getText().toString().equals(jauaptar[index])) {
                    rb4.setTextColor(getResources().getColor(R.color.green));
                }
            } else if (rb2.isChecked()) {
                rb2.setTextColor(getResources().getColor(R.color.red));
                if (rb1.getText().toString().equals(jauaptar[index])) {
                    rb1.setTextColor(getResources().getColor(R.color.green));
                } else if (rb2.getText().toString().equals(jauaptar[index])) {
                    rb2.setTextColor(getResources().getColor(R.color.green));
                } else if (rb3.getText().toString().equals(jauaptar[index])) {
                    rb3.setTextColor(getResources().getColor(R.color.green));
                } else if (rb4.getText().toString().equals(jauaptar[index])) {
                    rb4.setTextColor(getResources().getColor(R.color.green));
                }
            } else if (rb3.isChecked()) {
                rb3.setTextColor(getResources().getColor(R.color.red));
                if (rb1.getText().toString().equals(jauaptar[index])) {
                    rb1.setTextColor(getResources().getColor(R.color.green));
                } else if (rb2.getText().toString().equals(jauaptar[index])) {
                    rb2.setTextColor(getResources().getColor(R.color.green));
                } else if (rb3.getText().toString().equals(jauaptar[index])) {
                    rb3.setTextColor(getResources().getColor(R.color.green));
                } else if (rb4.getText().toString().equals(jauaptar[index])) {
                    rb4.setTextColor(getResources().getColor(R.color.green));
                }
            } else if (rb4.isChecked()) {
                rb4.setTextColor(getResources().getColor(R.color.red));
                if (rb1.getText().toString().equals(jauaptar[index])) {
                    rb1.setTextColor(getResources().getColor(R.color.green));
                } else if (rb2.getText().toString().equals(jauaptar[index])) {
                    rb2.setTextColor(getResources().getColor(R.color.green));
                } else if (rb3.getText().toString().equals(jauaptar[index])) {
                    rb3.setTextColor(getResources().getColor(R.color.green));
                } else if (rb4.getText().toString().equals(jauaptar[index])) {
                    rb4.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                if (rb1.getText().toString().equals(jauaptar[index])) {
                    rb1.setTextColor(getResources().getColor(R.color.green));
                } else if (rb2.getText().toString().equals(jauaptar[index])) {
                    rb2.setTextColor(getResources().getColor(R.color.green));
                } else if (rb3.getText().toString().equals(jauaptar[index])) {
                    rb3.setTextColor(getResources().getColor(R.color.green));
                } else if (rb4.getText().toString().equals(jauaptar[index])) {
                    rb4.setTextColor(getResources().getColor(R.color.green));
                }
            }
        }
        rb1.setClickable(false);
        rb2.setClickable(false);
        rb3.setClickable(false);
        rb4.setClickable(false);
        return;
    }

    public void suraqtarJinau() {
        suraq1.setText(suraktar[0]);
        sq1_jauap1.setText(varianttar[0][0]);
        sq1_jauap2.setText(varianttar[0][1]);
        sq1_jauap3.setText(varianttar[0][2]);
        sq1_jauap4.setText(varianttar[0][3]);

        suraq2.setText(suraktar[1]);
        sq2_jauap1.setText(varianttar[1][0]);
        sq2_jauap2.setText(varianttar[1][1]);
        sq2_jauap3.setText(varianttar[1][2]);
        sq2_jauap4.setText(varianttar[1][3]);

        suraq3.setText(suraktar[2]);
        sq3_jauap1.setText(varianttar[2][0]);
        sq3_jauap2.setText(varianttar[2][1]);
        sq3_jauap3.setText(varianttar[2][2]);
        sq3_jauap4.setText(varianttar[2][3]);

        suraq4.setText(suraktar[3]);
        sq4_jauap1.setText(varianttar[3][0]);
        sq4_jauap2.setText(varianttar[3][1]);
        sq4_jauap3.setText(varianttar[3][2]);
        sq4_jauap4.setText(varianttar[3][3]);

        suraq5.setText(suraktar[4]);
        sq5_jauap1.setText(varianttar[4][0]);
        sq5_jauap2.setText(varianttar[4][1]);
        sq5_jauap3.setText(varianttar[4][2]);
        sq5_jauap4.setText(varianttar[4][3]);

        suraq6.setText(suraktar[5]);
        sq6_jauap1.setText(varianttar[5][0]);
        sq6_jauap2.setText(varianttar[5][1]);
        sq6_jauap3.setText(varianttar[5][2]);
        sq6_jauap4.setText(varianttar[5][3]);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Suraq1.this,QuizActivity.class);
        startActivity(intent);
    }
}