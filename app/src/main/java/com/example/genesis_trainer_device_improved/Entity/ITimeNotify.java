package com.example.genesis_trainer_device_improved.Entity;

import android.os.Bundle;

/*
  This interface is responsible for notifying timerstates related to training timer
 {@link  com.example.genesis_trainer_device_improved.Entity.TrainingTimer}
 */

public interface ITimeNotify {
        void updateTimer(int clientsId, long h, long m);
        void notifyMessage(int clientsId, String message);
        void started();
        void stoped();
        void paused();
        void resumed();
        void notifyReinitializeWidgets(Bundle states);
    }

