package com.example.myapplication;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivities;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DostarAdapter extends RecyclerView.Adapter<DostarAdapter.MyHolder> {
    Context context;
    List<User> userList;

    public DostarAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup ViewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dostar_item,ViewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String userImage = userList.get(i).getImage();
        String userName = userList.get(i).getName();
        String userEmail = userList.get(i).getEmail();
//        Collections.sort(userList, Collections.reverseOrder());
        holder.name.setText(userName);
        holder.email.setText(userEmail);
        try{
            Picasso.get().load(userImage).placeholder(R.drawable.user_m)
                    .into(holder.imageView);
        }
        catch (Exception e ){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context.getApplicationContext());

                Intent myIntent = new Intent(view.getContext(),CallingActivity.class);
                myIntent.putExtra("mytext",userEmail);
                context.getApplicationContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends   RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView name,email;
       public MyHolder(@NonNull View itemView) {
           super(itemView);
           imageView = itemView.findViewById(R.id.img1);
           name = itemView.findViewById(R.id.txtName);
           email = itemView.findViewById(R.id.txtEmail);
       }
   }
}
