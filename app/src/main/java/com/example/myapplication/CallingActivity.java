package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CallingActivity extends AppCompatActivity {
    MediaPlayer player;
    ImageView img_bas_tartu,img_jauap_beru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        img_bas_tartu = findViewById(R.id.img_bas_tartu);
        img_jauap_beru = findViewById(R.id.img_jauap_beru);
            if (player == null) {
                player = MediaPlayer.create(this, R.raw.music);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        repeat();
                    }
                });
            }
            player.start();

            img_bas_tartu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStop();
                }
            });
            img_jauap_beru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStop();
                }
            });
            }
        private void repeat() {
            player.start();
        }
        private void stopPlayer() {
            if (player != null) {
                player.release();
                player = null;
            }
        }
        @Override
        protected void onStop() {
            super.onStop();
            stopPlayer();
            Intent intent = new Intent(CallingActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
