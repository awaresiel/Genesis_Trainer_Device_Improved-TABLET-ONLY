package com.example.genesis_trainer_device_improved.Fragments.adapters;

import android.bluetooth.BluetoothDevice;
import android.graphics.drawable.Drawable;

public class DeviceDisplayContent {
    boolean imageVisibility;
    BluetoothDevice device;

    public DeviceDisplayContent(boolean image, BluetoothDevice device) {
        this.imageVisibility = image;
        this.device = device;
    }

    public boolean getImageVisibility() {
        return imageVisibility;
    }

    public void setImageVisibility(boolean imageVisibility) {
        this.imageVisibility = imageVisibility;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public String getDeviceName() {
        if (device == null) return "Turn on bluetooth";
        return device.getName();
    }

    public void setDevice(BluetoothDevice d) {
        this.device = d;
    }
}
