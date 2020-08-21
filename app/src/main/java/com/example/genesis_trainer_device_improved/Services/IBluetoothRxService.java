package com.example.genesis_trainer_device_improved.Services;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.genesis_trainer_device_improved.Entity.TrainingTimer;

import java.util.List;

public interface IBluetoothRxService {
    void notifyBluetoothMessageArrived(String address, String msg);

    void notifySocketConnected(BluetoothSocket s);

    void notifySocketDisconnected(String address);

    List<BluetoothDevice> initBluetoothDevice();

    void setNotifyStatesChangeListener(IBluetoothStates l);

    void setNotifyTrainingListener(IBluetoothTraining l);

    void setNotifyDeviceConnectionStatesListener(IDeviceConnectionStates l);

    void connect(String address);

    boolean enableBluetooth();

    void writeMessage(byte[] msg, String address);

    void createNewCounter(long time, int id);

     TrainingTimer getTimer(int id);

    void deleteTimer(int id);

    void cancelAllTimers();

    void startTraining(byte[] trainingFrequency, byte[] trainingSetup, byte[] electredsToStart, String address);
}
