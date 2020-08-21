package com.example.genesis_trainer_device_improved.Entity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

public class TrainingTimer {
    private static final String TAG = "TrainingTimer";
    private int clientsId;
    private String address;
    private boolean paused;

    private ITimeNotify notifyTime;
    private CountDownTimer c;
    private long timeRemaining = 0;
    private long milisInFuture = 0;
    private long countDownInterval = 0;
    private Bundle reinitializeStates;


    public TrainingTimer(long millisInFuture, long countDownInterval, int clientsId) {
        this.milisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.clientsId = clientsId;
    }


    public void setInotifyCallback(ITimeNotify callback) {
        notifyTime = callback;
//        if (!paused){
            notifyTime.notifyReinitializeWidgets(reinitializeStates);
//        }
    }

    public void setReinitializeStates(Bundle b){
        Log.d(TAG, "setReinitializeStates: connected = "+b.getBoolean("isConnected"));
        reinitializeStates = b;
    }
    public Bundle getReinitializeStates(){
        return reinitializeStates;
    }

    private CountDownTimer countDownTimer(long milis) {
        if (c != null) {
            c.cancel();
            c = null;
        }

        c = new CountDownTimer(milis, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (paused) {
                    Log.d(TAG, "onTick: paused " + millisUntilFinished);
                    timeRemaining = millisUntilFinished;
                    cancel();
                }

                long mins = millisUntilFinished / 60000;
                long secs = millisUntilFinished % 60000 / 1000;

//        final String displaytime = secs>9? String.format("%d" + ":" + "%d", mins, secs): String.format("%d" + ":" + "0%d", mins, secs);

//                    final String displaytimeUnder10Seconds = String.format("%d" + ":" + "0%d", mins, secs);

                if (notifyTime != null) {
                    notifyTime.updateTimer(clientsId, mins, secs);
                    Log.d(TAG, "onTick: updating listener");
                }
                timeRemaining = millisUntilFinished;
              //  Log.d(TAG, "onTick: time remaining = " + timeRemaining);
            }

            @Override
            public void onFinish() {
                notifyTime.notifyMessage(clientsId, "Training done");
                paused = true;
                notifyTime.stoped();
                Log.d(TAG, "onFinish: finished");
            }
        };
        return c;
    }


    public void pause() {
        this.paused = true;
        cancel();
        notifyTime.paused();
    }

    public void continueTimer() {
        paused = false;
        Log.d(TAG, "continueTimer: timeremaining " + timeRemaining);
        countDownTimer(timeRemaining).start();
        notifyTime.resumed();
    }

    public void cancel() {
        if (c != null)
            c.cancel();
        paused =true;
        notifyTime.stoped();
    }


    public void startTimer() {
        paused = false;
        countDownTimer(milisInFuture).start();
        notifyTime.started();
    }

    public int getClientsId() {
        return clientsId;
    }

    public void setClientsId(int clientsId) {
        this.clientsId = clientsId;
    }

    public void setTime(long time){
        milisInFuture = time;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
