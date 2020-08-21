package com.example.genesis_trainer_device_improved.Services;

import android.bluetooth.BluetoothDevice;

import java.util.List;

public interface IBluetoothStates {
    void  bluetoothOff(String message);
    void  bluetoothTurningOff(String message);
    void  bluetoothTurningOn(String message);
    void  bluetoothOn(List<BluetoothDevice> list);

}
