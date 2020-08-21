package com.example.genesis_trainer_device_improved.Fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.genesis_trainer_device_improved.R;

import java.util.List;

public class DeviceDisplayAdapter extends BaseAdapter {

   private Context context;
   private List<DeviceDisplayContent> values;

    public DeviceDisplayAdapter(@NonNull Context context, @NonNull List<DeviceDisplayContent> objects) {

        this.context = context;
        this.values = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item ==null){
          item =  LayoutInflater.from(context).inflate(R.layout.spinner_item,parent,false);
          if (values.size()>position) {
              DeviceDisplayContent content = values.get(position);
              ImageView image = item.findViewById(R.id.imageView_deviceConnectedIndicator2);
              image.setImageDrawable(context.getDrawable(R.drawable.device_connected_img));
              TextView text = item.findViewById(R.id.spinnerItemTextview);
              text.setText(content.getDeviceName());
              if (content.getImageVisibility()) {
                  image.setVisibility(View.VISIBLE);
              } else {
                  image.setVisibility(View.INVISIBLE);
              }
          }
        }
        return item;
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        if (values.size()>position){
        return values.get(position);
        }else{
            return values.get(0);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
