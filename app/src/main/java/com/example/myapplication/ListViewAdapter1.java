package com.example.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;

public class ListViewAdapter1 extends ArrayAdapter<String> {
    ArrayList<String>list;
    Context context;
    public ListViewAdapter1(Context context, ArrayList<String> items){
        super(context,R.layout.list_row,items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row,null);
            TextView number = convertView.findViewById(R.id.numberoflist);
            number.setText(position+1+".");
            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position));
            ImageView btn_more = convertView.findViewById(R.id.btn_more);
            btn_more.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    final PopupMenu popupMenu = new PopupMenu(context,btn_more);
                    popupMenu.getMenuInflater().inflate(R.menu.menu2,popupMenu.getMenu());
                    popupMenu.setForceShowIcon(true);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem){
                            switch (menuItem.getItemId()){
                                case R.id.delete:
                                    WriteActivity.removeItem(position);
                                    popupMenu.dismiss();
                                    break;
                                case R.id.edit:
                                    ClipboardManager clipboardManager =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clipData = ClipData.newPlainText("Text View",name.getText().toString());
                                    clipboardManager.setPrimaryClip(clipData);
                                    Toast.makeText(context.getApplicationContext(),R.string.koshirildi,Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
        return convertView;
    }
}
