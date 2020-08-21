package com.example.genesis_trainer_device_improved.Services;

import java.io.IOException;

public interface ICommunicationThread {
    void closeStreams() throws IOException;
    void disconnectSocket() throws IOException;
}
