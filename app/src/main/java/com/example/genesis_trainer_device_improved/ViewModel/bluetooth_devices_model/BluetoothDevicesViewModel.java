package com.example.genesis_trainer_device_improved.ViewModel.bluetooth_devices_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.genesis_trainer_device_improved.Fragments.adapters.DeviceDisplayContent;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import java.util.List;

public class BluetoothDevicesViewModel extends BaseViewModel {

    private MutableLiveData<List<DeviceDisplayContent>> btDevices;

    public BluetoothDevicesViewModel(@NonNull Application application) {
        super(application);
        btDevices = new MutableLiveData<>();
    }

    public LiveData<List<DeviceDisplayContent>> getBtDevicesList(){
        return btDevices;
    }

    public void addBtDevicesList(List<DeviceDisplayContent> list){
      btDevices.postValue(list);
    }
}
