package com.example.driverapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PickupAttendanceAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    ArrayList<StudentModel> data;
    Context context;


    public PickupAttendanceAdapter(ArrayList<StudentModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pickuprow, parent, false);
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
                String Phone_No  = temp.getPhone();
                String Name  = temp.getName();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                try {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Attendance");
                    AttendanceDBHelperClass add = new AttendanceDBHelperClass(
                            Name,
                            "Present",
                            "-"
                            ,date
                    );
                    ref.child(Phone_No).child(date).setValue(add);
                    Toast.makeText(context,"Marked",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    System.out.println((e.getMessage()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}


