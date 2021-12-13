package com.example.driverapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudentAdapterClass extends RecyclerView.Adapter<StudentViewHolder> {

    ArrayList<StudentModel> data;
    Context context;

    public StudentAdapterClass(ArrayList<StudentModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentViewHolder holder, int position) {
        final StudentModel temp = data.get(position);

        holder.tv1.setText(data.get(position).getName());
        holder.tv2.setText(data.get(position).getDesc());
        holder.img.setImageResource(data.get(position).getImg());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewStdDetail.class);
                intent.putExtra("Name", temp.getName());
                intent.putExtra("phone", temp.getPhone());
                intent.putExtra("hadd", temp.getHadd());
                intent.putExtra("iadd", temp.getIadd());
                intent.putExtra("ilat", temp.getIlat());
                intent.putExtra("ilon", temp.getIlon());
                intent.putExtra("hlat", temp.getHlat());
                intent.putExtra("hlon", temp.getHlon());
                intent.putExtra("ind", "fromStdClass");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

