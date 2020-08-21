package com.example.genesis_trainer_device_improved.Services;

public interface IDeviceConnectionStates {
    void  deviceConnected(String address);
    void deviceDisconnected(String address);
}
