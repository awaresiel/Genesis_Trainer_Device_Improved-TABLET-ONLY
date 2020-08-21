package com.example.genesis_trainer_device_improved.Services;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommunicationThread extends Thread implements ICommunicationThread {
    private static final String TAG = "CommunicationThread";

    IBluetoothRxService iBluetoothRxServiceCallback;
    private final BluetoothSocket bluetoothSocket;
    private final OutputStream outputStream;
    private final InputStream inputStream;


    public CommunicationThread(BluetoothSocket bluetoothSocket, IBluetoothRxService c) throws IOException {

        this.iBluetoothRxServiceCallback = c;
        this.bluetoothSocket = bluetoothSocket;

        InputStream temInpStream = null;
        OutputStream tempOutStream = null;

        temInpStream = bluetoothSocket.getInputStream();
        tempOutStream = bluetoothSocket.getOutputStream();

        inputStream = temInpStream;
        outputStream = tempOutStream;
    }


    @Override
    public void run() {
        try {
            setupCommunication();
        } catch (IOException e) {
            iBluetoothRxServiceCallback.notifyBluetoothMessageArrived(bluetoothSocket.getRemoteDevice().getAddress(),"<ERROR>");
            iBluetoothRxServiceCallback.notifySocketDisconnected(bluetoothSocket.getRemoteDevice().getAddress());
            e.printStackTrace();
            closeStreams();
        }
    }

    private void setupCommunication() throws IOException {
        byte[] buffer = new byte[128];
        while (true) {
            if (bluetoothSocket.isConnected()) {
                int availableBytes = inputStream.available();

                if (availableBytes > 0) {
                    int read = inputStream.read(buffer, 0, availableBytes);
                    String address= bluetoothSocket.getRemoteDevice().getAddress();
                    iBluetoothRxServiceCallback.notifyBluetoothMessageArrived(address,new String(buffer, 0, read));
                }
            }
        }
    }



    @Override
    public void closeStreams()  {
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void disconnectSocket() throws IOException {
        if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
        bluetoothSocket.close();
        }
    }
}
