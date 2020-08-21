package com.example.genesis_trainer_device_improved.ViewModel.recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.R;

import java.util.List;

public class Statistics_Recycler extends RecyclerView.Adapter<Statistics_Recycler.StatisticsHolder> {
    private static final String TAG = "Statistics_Recycler";
    List<Statistics> allStatistics;
    Context context;

    public Statistics_Recycler(List<Statistics> allStatistics, Context context) {
        this.allStatistics = allStatistics;
        this.context = context;
    }

    @NonNull
    @Override
    public StatisticsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.statistics_content,parent,false);

        return new StatisticsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsHolder holder, int position) {
        if (allStatistics!=null && !allStatistics.isEmpty()){
            holder.name.setText(allStatistics.get(position).getName());
            holder.trainingName.setText(allStatistics.get(position).getTrainingName());
            long time = Long.parseLong(allStatistics.get(position).getDuration());
         //   double timeInMinutes = time/60000.0;

//            Log.d(TAG, "onBindViewHolder: timeInMinutes " + timeInMinutes + " original= " + time);
            Log.d(TAG, "onBindViewHolder: timeInMinutes " + time + " original= " + time);
            holder.duration.setText(String.valueOf(time));
            holder.trainerName.setText(allStatistics.get(position).getTrainerName());
            holder.date.setText(allStatistics.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        if (allStatistics !=null && !allStatistics.isEmpty()){
        return allStatistics.size();
        }else{
            return 0;
        }
    }

    public class StatisticsHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView trainingName;
        TextView duration;
        TextView trainerName;
        TextView date;

        public StatisticsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_statistics_clientName);
            trainingName = itemView.findViewById(R.id.tv_statistics_trainingName);
            duration = itemView.findViewById(R.id.tv_statistics_trainingDuration);
            trainerName = itemView.findViewById(R.id.tv_statistics_trainerName);
            date = itemView.findViewById(R.id.tv_statistics_trainingDate);
        }
    }
}
