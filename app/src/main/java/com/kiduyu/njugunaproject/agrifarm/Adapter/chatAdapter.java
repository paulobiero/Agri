package com.kiduyu.njugunaproject.agrifarm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.njugunaproject.agrifarm.R;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;


public class chatAdapter extends RecyclerView.Adapter<chatAdapter.viewHolder> {


    ArrayList<String> subLocations;
    Context context;
    Activity activity;
    private String county;

    public chatAdapter(ArrayList<String> subLocations, Context context, String county) {
        this.subLocations = subLocations;
        this.context=context;
        this.county=county;
    }

    @NonNull

    @Override
    public chatAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_location_item,parent,false);

        return new chatAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final chatAdapter.viewHolder holder, final int position) {
        ImageView imageView=holder.itemView.findViewById(R.id.location_title);
        final TextView name=holder.itemView.findViewById(R.id.location_name);
        final TextView accommodationCount=holder.itemView.findViewById(R.id.accommodation_count);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .endConfig()
                .round();
        ;
        TextDrawable ic1 = builder.build(String.valueOf(subLocations.get(position).charAt(0)), color1);
        imageView.setImageDrawable(ic1);

    }





    @Override
    public int getItemCount() {
        return subLocations.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
