package com.example.genesis_trainer_device_improved.ViewModel.recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;

import java.util.List;

/*
 * Clients_Side_Recycler responsible for setting list of selected clients and text names of selected devices next to names of clients
 */

public class Clients_Side_Recycler extends RecyclerView.Adapter<Clients_Side_Recycler.Client_Holder> {
    private static final String TAG = "Clients_Side_Recycler";

    public interface OnClientClicks {
        void onClientLongClick(Client client);

        void onClientClick(Client client);
    }

   // private int selectedPos = RecyclerView.NO_POSITION;
    private List<Client> clientList;
    private Context context;
    private OnClientClicks onClientClicks;
    private boolean initialized;


    public Clients_Side_Recycler(Context context, OnClientClicks onClientClicks) {
        this.context = context;
        this.onClientClicks = onClientClicks;
        Log.d(TAG, "Clients_Side_Recycler: constructor=====");

    }

    @NonNull
    @Override
    public Client_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlist_recycler_content, parent, false);
        Log.d(TAG, "onCreateViewHolder: inflate===========");
        return new Client_Holder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull Client_Holder holder, int position) {
        if (clientList != null && !clientList.isEmpty()) {
            holder.textView.setText(clientList.get(position).getClientName());
            holder.imageView.setBackground(null);
            holder.imageView.setImageBitmap(getBitmap(position));
          //  holder.frameLayout.setSelected(selectedPos == position);
            Log.d(TAG, "onBindViewHolder: clientName= " + clientList.get(position).getClientName() + "  -position = " + position);
            if (clientList.get(position).getDeviceAddress() != null) {
                holder.client_side_recycler_device.setVisibility(View.VISIBLE);
                holder.client_side_recycler_device.setText(clientList.get(position).getDeviceAddress());
            } else {
                holder.client_side_recycler_device.setVisibility(View.INVISIBLE);
            }
        }

    }

    private Bitmap getBitmap(int position) {
        if (clientList != null && clientList.get(position).getClientProfileImage() != null) {
            return BitmapFactory.decodeFile(clientList.get(position).getClientProfileImage());
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.icons_user_80);
        }
    }

    @Override
    public int getItemCount() {
        if (clientList != null) {
            Log.d(TAG, "getItemCount: size= " + clientList.size());
            return clientList.size();
        } else {
            Log.d(TAG, "getItemCount:=== else ");
            return 0;
        }
    }

    public void setClientList(List<Client> list) {
        Log.d(TAG, "setClientList: ======= " + list.size());
        clientList = list;
        notifyDataSetChanged();
    }


    public void clickOnPosition(int pos) {
        if (clientList.isEmpty())return;
        onClientClicks.onClientClick( clientList.get(pos));
       // setSelectedPos(pos);
    }
    public void clickOnClient(int id){
        if (clientList==null) return;
        for (Client c : clientList){
            if (c.getClientId() == id){
                onClientClicks.onClientClick(c);
            }
        }
    }

//    public int getSelectedPos() {
//        return selectedPos;
//    }
//
//    public void setSelectedPos(int selectedPos) {
//        this.selectedPos = selectedPos;
//    }
    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }


    public class Client_Holder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        ImageView imageView;
        TextView textView;
        FrameLayout frameLayout;
        TextView client_side_recycler_device;

        @Override
        public boolean onLongClick(View v) {
            if (clientList != null) {
                onClientClicks.onClientLongClick(clientList.get(getAdapterPosition()));

                return true;
            } else {
                return false;
            }

        }

        private DebouncedClickListener listener = new DebouncedClickListener(800) {
            @Override
            public void onDebouncedClick(View v) {
                if (clientList != null) {
                    onClientClicks.onClientClick( clientList.get(getAdapterPosition()));
                }
            }
        };


        public Client_Holder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "Client_Holder: ======== ");
            imageView = itemView.findViewById(R.id.client_side_content_img);
            textView = itemView.findViewById(R.id.client_side_recycler_name);
            frameLayout = itemView.findViewById(R.id.frameLayout_selection);
            client_side_recycler_device = itemView.findViewById(R.id.client_side_recycler_device);
            client_side_recycler_device.setVisibility(View.INVISIBLE);

            imageView.setOnLongClickListener(this);
            imageView.setOnClickListener(listener);
            frameLayout.setOnClickListener(listener);

            if (!initialized){
                clickOnPosition(0);
                initialized=true;
            }

        }

    }
}