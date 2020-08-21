package com.example.genesis_trainer_device_improved.Services;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConnectionThread extends Thread implements IConnectionThread {
    private static final String TAG = "ConnectionThread";

    private static final UUID CONNECT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private IBluetoothRxService iBluetoothRxService;
    private BluetoothDevice d;

    public ConnectionThread(BluetoothDevice d, IBluetoothRxService iBluetoothRxService) {
        this.iBluetoothRxService = iBluetoothRxService;
        this.d = d;

    }

    @Override
    public void run() {
        try {
            makeConnection();
        } catch (IOException e) {
            iBluetoothRxService.notifySocketDisconnected(d.getAddress());
            e.printStackTrace();
        }
    }

    private void makeConnection() throws IOException {
        BluetoothSocket bluetoothSocket = d.createRfcommSocketToServiceRecord(CONNECT_UUID);
        if (bluetoothSocket==null){
            return;
        }
        bluetoothSocket.connect();

        if (bluetoothSocket.isConnected()) {
            Log.d(TAG, "connectAllSockets: socket.isConnected ");
            iBluetoothRxService.notifySocketConnected(bluetoothSocket);
        }
    }

    @Override
    public void stopThread(){
        Thread.currentThread().interrupt();
    }

    @Override
    public void startThread() {
        start();
    }
}
