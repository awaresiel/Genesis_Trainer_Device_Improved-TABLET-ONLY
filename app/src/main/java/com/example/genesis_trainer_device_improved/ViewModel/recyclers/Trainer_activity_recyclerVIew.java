package com.example.genesis_trainer_device_improved.ViewModel.recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.helpers.ImageHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/*
 * This Trainer_activity_recyclerVIew class is responsible for both Trainers Activity and Clients Activity
 */


public class Trainer_activity_recyclerVIew extends RecyclerView.Adapter<Trainer_activity_recyclerVIew.MyTrainerViewHolder> {
    private static final String TAG = "Trainer_activity_recycl";

    public interface TrainerClick {
        void onTrainerLongClick(int id, View view);

        void onTrainerClick(int id, View view);
    }

    private Context context;
    private List<Trainer> trainerList;
    private List<ClientWraper> clientList;
    TrainerClick trainerClick;
    ImageHelper imageHelper;
    private int selectedPos = 0;

    public Trainer_activity_recyclerVIew(Context context, TrainerClick trainerClick) {
        this.context = context;
        this.trainerClick = trainerClick;
        imageHelper = new ImageHelper();

    }


    @NonNull
    @Override
    public Trainer_activity_recyclerVIew.MyTrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trainer_activity_content, parent, false);
        return new MyTrainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Trainer_activity_recyclerVIew.MyTrainerViewHolder holder, int position) {

        if (trainerList != null && trainerList.size() > 0) {
            holder.textView.setText(trainerList.get(position).getTrainerName());
            holder.imageView.setBackground(null);
            holder.imageView.setImageBitmap(getTrainerBitmap(trainerList.get(position)));
        }

        if (clientList != null && clientList.size() > 0) {
            holder.textView.setText(clientList.get(position).getClient().getClientName());
            holder.imageView.setBackground(null);
            holder.imageView.setImageBitmap(getClientBitmap(clientList.get(position).getClient()));
            if (clientList.get(position).isSelected()) {
                setSelectedBackground(holder.imageView);
            } else {
                setUnselectedBackground(holder.imageView);
            }


        }
    }


    private Bitmap getTrainerBitmap(Trainer t) {
        if (trainerList != null && t.getTrainerProfileImage() != null) {
            return BitmapFactory.decodeFile(t.getTrainerProfileImage());
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.icons_user_80);
    }

    private Bitmap getClientBitmap(Client c) {
        if (clientList != null && c.getClientProfileImage() != null) {
            return BitmapFactory.decodeFile(c.getClientProfileImage());
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.icons_user_80);

    }


    public int getSelectedPos() {
        return selectedPos;
    }

    public void resetSelection(int pos) {
        selectedPos = pos;
    }

    @Override
    public int getItemCount() {
        if (trainerList != null) {
            return trainerList.size();

        } else if (clientList != null) {
            return clientList.size();
        }
        return 0;
    }


    public void setTrainers(List<Trainer> list) {
        trainerList = list;
        notifyDataSetChanged();
    }

    public void setClients(List<ClientWraper> list) {
        clientList = list;
        notifyDataSetChanged();
    }

    public void setSelectedBackground(View v) {
        v.setSelected(true);
        v.setPadding(10, 10, 10, 10);
        v.setBackground(context.getDrawable(R.drawable.color_selectors));
    }

    public void setUnselectedBackground(View v) {
        v.setSelected(false);
        v.setPadding(0, 0, 0, 0);
        v.setBackground(null);
    }

    class MyTrainerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        ImageView imageView;
        TextView textView;
        FrameLayout trainersFrame;

        @Override
        public boolean onLongClick(View v) {
            if (trainerList != null) {
                trainerClick.onTrainerLongClick(trainerList.get(getAdapterPosition()).getTrainerId(), v);
                return true;
            } else {
                trainerClick.onTrainerLongClick(clientList.get(getAdapterPosition()).getClient().getClientId(), v);

                return true;
            }

        }


        @Override
        public void onClick(View v) {
            if (trainerList != null) {
                trainerClick.onTrainerClick(trainerList.get(getAdapterPosition()).getTrainerId(), v);
            } else {
                trainerClick.onTrainerClick(clientList.get(getAdapterPosition()).getClient().getClientId(), v);

                if (!v.isSelected()) {
                    if (selectedPos <= 6) {
                        clientList.get(getAdapterPosition()).setSelected(true);
                        setSelectedBackground(v);
                        selectedPos++;
                    }

                } else {

                    clientList.get(getAdapterPosition()).setSelected(false);
                    setUnselectedBackground(v);
                    if (selectedPos>0)selectedPos--;
                }
                Log.d(TAG, "onClick: client name = "+ clientList.get(getAdapterPosition()).getClient().getClientName()+
                        " pos= "+ selectedPos + " selection "+v.isSelected());

            }

        }

        public MyTrainerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.TrainerActivity_Image);
            textView = itemView.findViewById(R.id.TrainerActivity_name);
            trainersFrame = itemView.findViewById(R.id.frameLayout_trainerFrame);

            imageView.setOnLongClickListener(this);
            imageView.setOnClickListener(this);
            // trainersFrame.setOnClickListener(this);
        }

    }
}
