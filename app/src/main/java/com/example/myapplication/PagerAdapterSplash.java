package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerAdapterSplash extends PagerAdapter {
    Context context;
    public PagerAdapterSplash(Context context){
        this.context = context;

    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(LinearLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slide_layout_img = (ImageView)view.findViewById(R.id.slide_layout_img);
        TextView slide_layout_txt = (TextView) view.findViewById(R.id.slide_layout_txt);

        ImageView ind1 = view.findViewById(R.id.ind1);
        ImageView ind2 = view.findViewById(R.id.ind2);
        ImageView ind3 = view.findViewById(R.id.ind3);
        switch (position){
            case 0:
                slide_layout_img.setImageResource(R.drawable.otyru);
                ind1.setImageResource(R.drawable.selected_slide);
                ind2.setImageResource(R.drawable.unselected_slide);
                ind3.setImageResource(R.drawable.unselected_slide);
                slide_layout_txt.setText(R.string.qoljetimdi);
                break;
            case 1:
                slide_layout_img.setImageResource(R.drawable.otyru2);
                ind1.setImageResource(R.drawable.unselected_slide);
                ind2.setImageResource(R.drawable.selected_slide);
                ind3.setImageResource(R.drawable.unselected_slide);
                slide_layout_txt.setText(R.string.tiimdi_pai);
                break;
            case 2:
                slide_layout_img.setImageResource(R.drawable.otyru3);
                ind1.setImageResource(R.drawable.unselected_slide);
                ind2.setImageResource(R.drawable.unselected_slide);
                ind3.setImageResource(R.drawable.selected_slide);
                slide_layout_txt.setText(R.string.test_tap);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
