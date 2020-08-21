package com.example.genesis_trainer_device_improved.ViewModel;

import android.os.SystemClock;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class DebouncedClickListener implements View.OnClickListener {

    private final long minimumIntervalMillis;
    private long timeOfLastClick;


    public abstract void onDebouncedClick(View v);

    public DebouncedClickListener(long minimumIntervalMillis) {
        this.minimumIntervalMillis = minimumIntervalMillis;
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - timeOfLastClick < minimumIntervalMillis){
            return;
        }else{
            onDebouncedClick(v);
        }
        timeOfLastClick = SystemClock.elapsedRealtime();
    }
}
