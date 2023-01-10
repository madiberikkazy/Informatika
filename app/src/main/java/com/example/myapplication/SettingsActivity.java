package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.BuildConfig;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private ImageView btn_back;
    private LinearLayout bagalau, dostarmen_bolisu, jobaga_qoldau, qosymsha_turaly, til, aty_jondi_oz, avany_ozg, shygu,password;
    Dialog dialog, dialog2, dialog4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btn_back = findViewById(R.id.btn_back);
        bagalau = findViewById(R.id.bagalau);
        dostarmen_bolisu = findViewById(R.id.dostarmen_bolisu);
        jobaga_qoldau = findViewById(R.id.jobaga_qoldau);
        qosymsha_turaly = findViewById(R.id.qosymsha_turaly);
        til = findViewById(R.id.til);
        aty_jondi_oz = findViewById(R.id.aty_jondi_oz);
        avany_ozg = findViewById(R.id.avany_ozg);
        password = findViewById(R.id.password);
        shygu = findViewById(R.id.shygu);



        dialog = new Dialog(this);
        dialog2 = new Dialog(this);
        dialog4 = new Dialog(this);


        shygu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogShugu();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        avany_ozg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogImg();
            }
        });
        aty_jondi_oz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAty();
            }
        });
        til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogTil();
            }
        });
        jobaga_qoldau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogJobagaQoldau();
            }
        });
        qosymsha_turaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogQosymshaTuraly();
            }
        });
        dostarmen_bolisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareMessage = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID + "\n\n";
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "Поделиться с"));
            }
        });
        bagalau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
                    startActivity(intent);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void openDialogShugu() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(SettingsActivity.this);
        alertdialog.setTitle(R.string.shygu);
        alertdialog.setMessage(R.string.siz__akk);
        alertdialog.setPositiveButton(R.string.ia, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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

    public void openDialogImg() {
        Intent intent =  new Intent(SettingsActivity.this,ChangePhotoActivity.class);
        startActivity(intent);
    }

    public void openDialogAty() {
        Intent intent =  new Intent(SettingsActivity.this,ChangeActivity.class);
        startActivity(intent);
    }

    public void openDialogTil() {
        dialog4.setContentView(R.layout.dialog4);
        dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog4.show();
        LinearLayout qaz_til = dialog4.findViewById(R.id.qaz_til);
        LinearLayout ru_til = dialog4.findViewById(R.id.ru_til);
        qaz_til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tildi_ozgertu("kk");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        ru_til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tildi_ozgertu("ru");
                Intent intent2 = getIntent();
                finish();
                startActivity(intent2);
            }
        });
    }

    public void tildi_ozgertu(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

    public void openDialogQosymshaTuraly() {
        View view = getLayoutInflater().inflate(R.layout.dialog2, null);
        dialog2 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog2.setContentView(view);
        dialog2.show();
        dialog2.setContentView(R.layout.dialog2);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
        ImageView btn_back_qosymsha_turaly = dialog2.findViewById(R.id.btn_back_qosymsha_turaly);
        btn_back_qosymsha_turaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
    }

    public void openDialogJobagaQoldau() {
        dialog.setContentView(R.layout.dialog1);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView nomer = dialog.findViewById(R.id.nomer);
        TextView nomer_long = dialog.findViewById(R.id.nomer_long);
        ImageView nomer_copy = dialog.findViewById(R.id.nomer_copy);
        ImageView nomer_long_copy = dialog.findViewById(R.id.nomer_long_copy);
        nomer_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text View", nomer.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(SettingsActivity.this, R.string.koshirildi, Toast.LENGTH_SHORT).show();
            }
        });
        nomer_long_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text View", nomer_long.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(SettingsActivity.this, R.string.koshirildi, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(intent);
    }
}