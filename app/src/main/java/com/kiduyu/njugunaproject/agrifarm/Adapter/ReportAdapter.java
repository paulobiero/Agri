package com.kiduyu.njugunaproject.agrifarm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiduyu.njugunaproject.agrifarm.Model.Application;
import com.kiduyu.njugunaproject.agrifarm.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    Context mcontext;
    private List<Application> applicationList;


    public ReportAdapter(Context context, List<Application> cList) {
        this.applicationList = cList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Application application = applicationList.get(position);

        holder.name.setText(application.getConsultant_name());
        holder.phone.setText(application.getConsultant_phone());
        holder.date1.setText(application.getDateValue());
        holder.time1.setText(application.getTime());
        }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, date1, time1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewname);
            phone = itemView.findViewById(R.id.textViewphone);
            date1 = itemView.findViewById(R.id.textViewdate);
            time1 = itemView.findViewById(R.id.textViewtime);
        }
    }
}
