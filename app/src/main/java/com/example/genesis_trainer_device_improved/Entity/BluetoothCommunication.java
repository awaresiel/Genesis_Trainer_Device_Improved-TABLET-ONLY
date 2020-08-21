package com.example.genesis_trainer_device_improved.Entity;


  /*
    this class is to setup blue tooth communication
    it has 2 threads, incoming thread and sending thread
    one sends data thru handler(handler is responsible for sending messages between ui thread and other threads)
    other thread will recieve response (data) from the device
     */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.genesis_trainer_device_improved.helpers.Constants.STATE_CONNECTED;
import static com.example.genesis_trainer_device_improved.helpers.Constants.STATE_CONNECTION_FAILED;
import static com.example.genesis_trainer_device_improved.helpers.Constants.STATE_MESSAGE_RECIEVED;


public class BluetoothCommunication implements LifecycleObserver {

    private static final UUID CONNECT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothServerSocket bluetoothServerSocket;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private Messenger handler;
    private MessageExchange messageExchange;


    public BluetoothCommunication(BluetoothAdapter bluetoothAdapter, BluetoothDevice bluetoothDevice, Messenger handler)
//    public BluetoothCommunication(BluetoothAdapter bluetoothAdapter, BluetoothDevice bluetoothDevice)
    {
        this.bluetoothAdapter=bluetoothAdapter;
        this.handler=handler;
        this.bluetoothDevice = bluetoothDevice;


    }

    public void serverConnect()  {

//        try {
//            Server serverconnect = new Server();
//            serverconnect.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



    public void clientConnect(BluetoothDevice bluetoothDevice)
    {
        if (bluetoothDevice!=null){
        Client client = new Client(bluetoothDevice);
        client.start();
        }
    }

    public void writeMessage(byte[] bytes)
    {
        if (messageExchange !=null){
        messageExchange.write(bytes);
        }

    }

    public void releaseResource()
    {

    }




    private class Client extends Thread
    {
        BluetoothSocket bluetoothSocket;
        BluetoothDevice otherBluetoothDevice;

        public Client(BluetoothDevice bluetoothDevice)
        {
            otherBluetoothDevice = bluetoothDevice;
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(CONNECT_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            // cancel discovery cuz it uses large resources
            bluetoothAdapter.cancelDiscovery();
            try {
                bluetoothSocket.connect();
                Message message = Message.obtain();

                    message.what = STATE_CONNECTED;
                    handler.send(message);

                messageExchange = new MessageExchange(bluetoothSocket);
                messageExchange.start();

            } catch (Exception e) {
                e.printStackTrace();
                Message message =new Message();
                message.what = STATE_CONNECTION_FAILED;
                try {
                    handler.send(message);
                    bluetoothSocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }


    }


    private class MessageExchange extends Thread
    {
        private static final String TAG = "MessageExchange";
        private final BluetoothSocket bluetoothSocket;
        private final OutputStream outputStream;
        private final InputStream inputStream;

        public MessageExchange(BluetoothSocket bluetoothSocket)
        {
           this.bluetoothSocket=bluetoothSocket;
            InputStream temInp =null;
            OutputStream tempOut = null;

            try {
                temInp = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = temInp;
            outputStream = tempOut;
        }

        public void run()
        {
            byte[] buffer = new byte[128];
            int bytes;
            while (true)
            {
                try {
                        bytes = inputStream.read(buffer);
                        Message message = new Message();
                        message.what = STATE_MESSAGE_RECIEVED;
                        message.arg1=bytes;
                        message.arg2=-1;
                        message.obj=buffer;
                    handler.send(message);
//                    handler.obtain(STATE_MESSAGE_RECIEVED, bytes, -1, buffer).sendToTarget();


                } catch (Exception e) {

                    Log.d(TAG, "run: exception message = " + e.getMessage());
                    e.printStackTrace();
                    try {
                        bluetoothSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}












