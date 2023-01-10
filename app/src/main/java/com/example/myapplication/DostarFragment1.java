package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DostarFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dostar1, container, false);
        CardView btn_dostar_tabu = (CardView) v.findViewById(R.id.btn_dostar_tabu);
        CardView btn_menin_dostarym = (CardView) v.findViewById(R.id.btn_menin_dostarym);
        btn_dostar_tabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getContext(),DostarTabuActivity.class);
            startActivity(intent);
            }
        });
        btn_menin_dostarym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return v;

    }
}