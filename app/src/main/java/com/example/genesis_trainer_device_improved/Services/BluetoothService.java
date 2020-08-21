package com.example.genesis_trainer_device_improved.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

//import com.example.genesis_trainer_device_improved.Activities.Activity_Training;
import com.example.genesis_trainer_device_improved.Activities.Chose_Training_Holder_Activity_For_Fragments;
import com.example.genesis_trainer_device_improved.Entity.TrainingTimer;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.genesis_trainer_device_improved.helpers.Constants.ACTION_START_SERVICE;


public class BluetoothService extends Service implements IBluetoothRxService {
    private static final String TAG = "BluetoothService";


    private static final int NOTIFICATION_ID = 101;
    private static String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION";
    private static String NOTIFICATION_CHANNEL_DESCRIPTION = "TRAFFIC LIMIT REACHED, SENDING TEXT..";
    private static String CHANNEL_ID = "100";

    private ICommunicationThread communicationThread;
    private IConnectionThread connectionThread;
    private IBluetoothStates notifyBluetoothStateChange;
    private  IBluetoothTraining notifyTraining;
    private  IDeviceConnectionStates notifyDeviceConnectionStates;

    private MyBinder binder;
    private boolean serviceRunning = false;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private Set<BluetoothDevice> connectedDevices;
    private List<BluetoothDevice> devices;

    //   private List<BluetoothSocket> bluetoothSockets;
//    private List<String> mBtDeviceAddresses;
    private HashMap<String, Thread> mBtConnectionThreads;
    private HashMap<String, BluetoothSocket> mBtSockets;
    private List<TrainingTimer> timers;
    private CompositeDisposable disposable;

    @Override
    public void onCreate() {
        super.onCreate();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        binder = new MyBinder();
        timers = new ArrayList<>();
        devices = new ArrayList<>();
        // bluetoothSockets= new ArrayList<>();
        // mBtDeviceAddresses= new ArrayList<>();
        mBtConnectionThreads = new HashMap<>();
        mBtSockets = new HashMap<>();
        startNotificationService();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_START_SERVICE)) {
            registerRecievers();
            initBluetoothDevice();

        } else {
//          stopService();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {

        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    @Override
    public void setNotifyStatesChangeListener(IBluetoothStates l) {
        notifyBluetoothStateChange = l;
        Log.d(TAG, "setNotifyStatesChangeListener: getDevicesList " + l);
    }

    @Override
    public void setNotifyTrainingListener(IBluetoothTraining l) {
        notifyTraining = l;
        Log.d(TAG, "setNotifyStatesChangeListener: getDevicesList " + l);
    }

    @Override
    public void setNotifyDeviceConnectionStatesListener(IDeviceConnectionStates l) {
        notifyDeviceConnectionStates = l;
        Log.d(TAG, "setNotifyStatesChangeListener: getDevicesList " + l);
    }

    private void registerRecievers() {
        Log.d(TAG, "registerRecievers: ");
        IntentFilter AdapterState = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        IntentFilter AdapterConnectedDevicesStates = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        registerReceiver(bluetoothStateListener, AdapterState);
        registerReceiver(bluetoothStateListener, AdapterConnectedDevicesStates);
    }

    private final BroadcastReceiver bluetoothStateListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action == null || notifyBluetoothStateChange==null) return;

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        notifyBluetoothStateChange.bluetoothOff("turn on bluetooth and connect to the device");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        notifyBluetoothStateChange.bluetoothTurningOff("turning off...");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        notifyBluetoothStateChange.bluetoothOn(initBluetoothDevice());
                        Log.d(TAG, "onReceive: state on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        notifyBluetoothStateChange.bluetoothTurningOn("turning on...");
                        Log.d(TAG, "onReceive: state turning on");
                        break;
                }
            }
            if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {

                final int adapterConnections = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR);

                switch (adapterConnections) {
                    case BluetoothAdapter.STATE_DISCONNECTING:
                        notifyBluetoothStateChange.bluetoothOn(initBluetoothDevice());
                        break;
                    case BluetoothAdapter.STATE_DISCONNECTED:
                        notifyBluetoothStateChange.bluetoothOn(initBluetoothDevice());
                        Log.d(TAG, "onReceive: disconnected");

                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        notifyBluetoothStateChange.bluetoothOn(initBluetoothDevice());
                        Log.d(TAG, "onReceive: state connected new Device");

                        break;
                }

            }

        }
    };

    @Override
    public List<BluetoothDevice> initBluetoothDevice() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "This device dosent have bluetooth turned on", Toast.LENGTH_SHORT).show();
        } else {
            devices = new ArrayList<>();
            connectedDevices = bluetoothAdapter.getBondedDevices();
            if (!connectedDevices.isEmpty()) {
                devices.addAll(connectedDevices);
                return devices;

            }
        }
        return null;
    }

    @Override
    public boolean enableBluetooth() {
        return bluetoothAdapter.enable();
    }

    @Override
    public void connect(String address) {

        if (bluetoothAdapter.isEnabled() && devices != null ) {
            for (BluetoothDevice d: devices){
                if (d!=null && d.getAddress().equals(address)){
                    bluetoothAdapter.cancelDiscovery();
                    connectionThread = new ConnectionThread(d, this);
                    connectionThread.startThread();
                    if (mBtConnectionThreads.containsKey(d.getAddress())){
                        Thread t = mBtConnectionThreads.get(d.getAddress());
                        if (t !=null) t.interrupt();
                        mBtConnectionThreads.remove(d.getAddress());
                        deleteConnectedStateFromTimers(d.getAddress());
                        Toast.makeText(this, "Device is already connected. Disconnecting..", Toast.LENGTH_SHORT).show();
                    }else{
                        mBtConnectionThreads.put(d.getAddress(),(Thread) connectionThread);
                    }

                }
            }
        }
    }

    private void deleteConnectedStateFromTimers(String address){
        for (TrainingTimer t : timers){
            if (t.getAddress().equals(address)){
                t.getReinitializeStates().putBoolean("isConnected",false);
            }
        }
    }

    @Override
    public void createNewCounter(long time,int id){
        timers.add(new TrainingTimer(time,1000,id));
    }

    @Override
    public TrainingTimer getTimer(int id){
        for (TrainingTimer t : timers){
            if (t.getClientsId() == id)
                return t;
        }
        return null;
    }

    @Override
    public void deleteTimer(int id){
        for (TrainingTimer t : timers){
            if (t.getClientsId() == id){
                t.cancel();
              timers.remove(t);
            }

        }
    }

    @Override
    public void cancelAllTimers(){
        for (TrainingTimer t : timers){
            t.cancel();
        }
    }

    public void startTraining(byte[] trainingFrequency, byte[] trainingSetup,byte[] electrodsToStart,String address){
        writeMessage("<RST>".getBytes(), address);
        addDisposable(
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS)
                        .doOnSubscribe(s -> notifyTraining.showProgressDialog()).doOnComplete(()->notifyTraining.cancelProgressDialog())
                        .subscribe(s -> {
                            switch (s.intValue()) {
                                case 0:
                                    writeMessage(trainingFrequency, address);
                                    break;
                                case 1:
                                    writeMessage(trainingSetup, address);
                                    break;
                                case 2:
                                    writeMessage(electrodsToStart, address);
                                    break;
                            }

                        }, Throwable::printStackTrace)

        );
    }


    @Override
    public void notifySocketConnected(BluetoothSocket s) {
        Log.d(TAG, "notifySocketConnected: ");
        mBtSockets.put(s.getRemoteDevice().getAddress(), s);
        try {
            communicationThread = new CommunicationThread(s, this);
            notifyDeviceConnectionStates.deviceConnected(s.getRemoteDevice().getAddress());

        } catch (IOException e) {
            notifyDeviceConnectionStates.deviceDisconnected(s.getRemoteDevice().getAddress());
            e.printStackTrace();
        }
    }

    @Override
    public void notifySocketDisconnected(String address) {
        notifyDeviceConnectionStates.deviceDisconnected(address);
    }

    @Override
    public void notifyBluetoothMessageArrived(String address, String msg) {
        firstConnectionHandshake(msg, address);
    }


    private void firstConnectionHandshake(String msg, String address) {
        switch (msg) {
            case "<GODFATHER>":
                writeMessage("<AMENO>".getBytes(),address);
                break;
            case "<OK>":
                break;
            case "<ERROR>":
                writeMessage("<WHO>".getBytes(),address);
                break;
        }
    }

    @Override
    public void writeMessage(byte[] bytes,String address) {
        long milis = SystemClock.elapsedRealtime();
        Log.d(TAG, "messageDevice: message => " + new String(bytes)+ "   ======= address => "+address+"   seconds = "+  TimeUnit.MILLISECONDS.toSeconds(milis));

        try {
            BluetoothSocket s = mBtSockets.get(address);

            if (s != null && s.getOutputStream() != null) {
                s.getOutputStream().write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNotificationService() {
        if (serviceRunning) {
            return;
        } else {
            createNotificationChannel();

            Intent notificationIntent = new Intent(getApplicationContext(), Chose_Training_Holder_Activity_For_Fragments.class);
            notificationIntent.setAction(ACTION_START_SERVICE);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Training in progress...")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setOngoing(false)
                    .setAutoCancel(true)
                    .build();

            startForeground(NOTIFICATION_ID, notification);


        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = NOTIFICATION_CHANNEL_NAME;
            String descriptionText = NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);

            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        mBtConnectionThreads.entrySet().forEach(v->v.getValue().interrupt());
        disposeDisposables();
        super.onDestroy();

    }

    @Override
    public boolean onUnbind(Intent intent) {
           unregisterReceiver(bluetoothStateListener);
        return super.onUnbind(intent);
    }

    public void addDisposable(Disposable d){
        if(disposable ==null) disposable = new CompositeDisposable();
        disposable.add(d);
    }
    public void disposeDisposables(){
        if (disposable!=null)  disposable.clear();

    }
}
