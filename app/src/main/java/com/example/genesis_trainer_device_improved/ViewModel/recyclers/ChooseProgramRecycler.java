package com.example.genesis_trainer_device_improved.ViewModel.recyclers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.R;

import java.util.ArrayList;

/*
 * this ChooseProgramRecycler is responsible for showing list of Connected Devices and putting next to them pictures of clients device that coresponds
 * to selected device
 *
 */
public class ChooseProgramRecycler extends RecyclerView.Adapter<ChooseProgramRecycler.MyViewHolder> {
    private static final String TAG = "ChooseProgramRecycler";

    public interface onDeviceClick {
        void onDeviceClicked(int position);

    }

 private onDeviceClick onDeviceClick;

    private int selectedPos = RecyclerView.NO_POSITION;
    private int connectedPosition= -1;
    Context context;
   ArrayList<String> devicesList;


    public ChooseProgramRecycler(ArrayList<String> devices, Context context, onDeviceClick onDeviceClick) {
        this.devicesList = devices;
        this.context=context;
        this.onDeviceClick = onDeviceClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.devices_recicler_content,parent,false);

       return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.device.setVisibility(View.VISIBLE);
        if (devicesList !=null && !devicesList.isEmpty()) {

            holder.device.setText(devicesList.get(position).toString());

            holder.device.setSelected(selectedPos == position);
            if (holder.device.isSelected()&& connectedPosition>-1){
                holder.connectionIndicator.setVisibility(View.VISIBLE);
                Log.d(TAG, "onBindViewHolder: pos " + connectedPosition);
            }else{
                holder.connectionIndicator.setVisibility(View.GONE);
            }

            connectedPosition=-1;
        }

    }

    public void notifyConnectionEstablished(int pos){
        connectedPosition=pos;
    }

    @Override
    public int getItemCount() {
        if (devicesList !=null){
        return devicesList.size();
        }else{
            return 0;
        }
    }

    public  void notifyData(ArrayList<String> data) {
        if (devicesList!=null){
            devicesList = data;
            notifyDataSetChanged();
        }
    }



     class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

      private Button device;
      private ImageView connectionIndicator;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);
             device = itemView.findViewById(R.id.btn_connectedDevice);
                connectionIndicator = itemView.findViewById(R.id.imageView_deviceConnectedIndicator);
                connectionIndicator.setVisibility(View.GONE);

             device.setTextColor(Color.BLUE);

             device.setOnClickListener(this);



         }



         @Override
         public void onClick(View v) {


             notifyItemChanged(selectedPos);
             selectedPos = getLayoutPosition();
             notifyItemChanged(selectedPos);

             onDeviceClick.onDeviceClicked(getAdapterPosition());



         }

     }
}
