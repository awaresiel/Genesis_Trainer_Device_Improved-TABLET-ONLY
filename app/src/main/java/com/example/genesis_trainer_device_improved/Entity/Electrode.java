package com.example.genesis_trainer_device_improved.Entity;

    /*
     +Example (-> to device, <- from device):
    -> <WHO>
    <- <GODFATHER>
    -> <AMENO>
    <- <OK>
    -> <P175;125>
    <- <OK>
    -> <C1023;6;0;0;1;0;240;320;28;56>
    <- <OK>
    -> <S3>
    <- <OK>
    -> <C1023;6;0;0;1;0;240;320;28;56>
    <- <ERR>
    -> <L3;10;20>
    <- <OK>
    ...
    -> <T3>
    <- <OK>

    <WHO><GODFATHER><AMENO><OK> - communication initialization
    <P175;125> - configure pulse parameter for all channels. Pulse width 175mks, Period of pulses 125*100=12.5ms
    <C1023;6;0;0;1;0;240;320;28;56> - configure channels. Disallowed after start
    C1023 = number of channels (bit field) for configuring. 1023 = 2^0+2^1+2^2+2^3+2^4+2^5+2^6+2^7+2^8+2^9 that means that configuration will be applied for channels with number 0, 1, 3, 4, 5, 6, 7, 8, 9.
    6 - pulse amplitude growing speed in active time. Maximum aplitude reached to 6th pulse. If 0 - pulse amplitude will set to maximum. Example: if max value of amplitude set to 36,
     so amplitude of pulses will be 6, 12, 18, 24, 30, 36, 6, 12, ....
    0 - pulse amplitude growing speed in rest time.
    0 - delay of first pulse. Example: let first channel have delay 0 and second channel has a 240 delay. So then first channel in active mode, second channel will be in rest mode and viseversa.
    1 - pulse frequency in active time. Example: 1 - one pulse per 12.5ms (see <P..> settins), 2 - one pulse per 2*12.5ms, 3 - one pulse per 3*12.5ms...
    0 - pulse frequency in rest time.
    240 - active time. Mean 240*12.5ms = 3000ms = 3 seconds.
    320 - rest time. Mean 320*12.5ms = 4000ms = 4 seconds.
    28 - time of growing aplitude in start of active time. 28*12.5ms = 350ms. Must be less than active time.
    56 - time of decay aplitude in the end of active time. 56*12.5ms = 700ms. Must be less than (active time - time of growing aplitude)
    <S3> - start pulses on channels 0 and 1. 3 (bit field) = 2^0+2^1
    <L3;10;20> - configure aplitude of pulses on channels 0 and 1. Maximum value 100. Minimum - 0. 10 - applied to 0 channel, 20 - 1 channel
    <L6;12;21> - configure aplitude of pulses on channels 1 and 2. 12 applied to 1 channel, 21 - to 2 channel.
    <T3> - stop pulses on channels 0 and 1. 3 - bit field.

    first you send impulse length, and period with command <P1500;200>. That means that impulse length is 1500 mks, and time between impulses is 20ms (200*100 mks)
    Impulse can not be set at all channels at one time. So impulse will set at first channel, then at second...
    So impulse period must be large then impulse width * channels count

    parameters that we dont hardcode are: <C....,g,h,i,j> and duration of training
    parameters in current version that we hardcode <Ca,b,f,c> and <Px;y>

     */


import android.util.Log;

import java.nio.charset.StandardCharsets;

import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_CONFIGURE_IMPULSE;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_DELIMITER;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_END_OF_MESSAGE;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_RESET;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_SET_UP_LEVEL;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_SET_UP_WORKOUT;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_START;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ARDUINO_STOP_ELECTRODE;


public class Electrode {
    private static final String TAG = "Electrode";

  //  <P175;125> - configure pulse parameter for all channels. Pulse width 175mks, Period of pulses 125*100=12.5ms
    private int impulseWidth=0;  //in mikroseconds
    private int timeBetweenImpulses=0; //mili seconds

 // <C1023;6;0;0;1;0;240;320;28;56> - configure channels. Cant use it after start

  //  <Ca;b;c;d;e;f;g;h;i;j>
    //( Ca)
    private int electrodeNumber = 0;
    //(ca;b;) b -  pulse amplitude growing speed in active time. Maximum aplitude reached to 6th pulse.
             // If 0 - pulse amplitude will set to maximum. Example: if max value of amplitude set to 36,
             // so amplitude of pulses will be 6, 12, 18, 24, 30, 36, 6, 12, ....
    private int pulseAmpl_In_Active_Time = 0;
    //(ca;b;c) c=  pulse amplitude growing speed in rest time.
    private int pulseAmplitude_GrowthSpeed_InRestTime=0;
    //(ca;b;c;d) d= delay of first pulse. Example: let first channel have delay 0(channel=electrode) and second channel has a 240 delay.
                     // So then first channel in active mode, second channel will be in rest mode and viceversa.
    private int delay_Of_First_Pulse=0;
    //(ca;b;c;d;e) e= pulse frequency in active time. Example: 1 - one pulse per 12.5ms (see <P..> settings), 2 - one pulse per 2*12.5ms, 3 - one pulse per 3*12.5ms...
    private int pulseFrequency_ActiveTime=0;
    //(ca;b;c;d;e;f) f= pulse frequency in rest time.
    private int pulseFrequency_RestTime=0;
    //(ca;b;c;d;e;f;g) g= exmpl: 240 - active time. Mean 240*12.5ms = 3000ms = 3 seconds.
    private int active_time=0;
    //(ca;b;c;d;e;f;g;h) h= exmpl: 320 - rest time. Mean 320*12.5ms = 4000ms = 4 seconds.
    private int rest_time=0;
    //(ca;b;c;d;e;f;g;h;i) i= exmpl: 28 - time of growing aplitude in start of active time. 28*12.5ms = 350ms. Must be less than active time.
    private int timeOf_GrowingAmplitude=0;
    //(ca;b;c;d;e;f;g;h;i;j) exmpl: j= 56 - time of decay aplitude in the end of active time. 56*12.5ms = 700ms. Must be less than (active time - time of growing aplitude)
    private int timeOf_DecayAplitude=0;

    // levels <L(electrode number);(channel 1 level)...(channel10 lvl) lvl=0-100;
    private int levelStrength=0;


//    public Electrode(int whichElectrode)
//    {
//        this.electrodeNumber=whichElectrode;
//    }

    // <P175;125> - configure pulse parameter for most of channels.impulse length= 175mks, time between impulses 125*100=12.5ms
    // (need to be done first, cant be done if electrodes running)
    public byte[] setFrequency(int Xx, int Xy)
    {
       String message = ARDUINO_CONFIGURE_IMPULSE + Xx + ARDUINO_DELIMITER +
                                        Xy + ARDUINO_END_OF_MESSAGE;

        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);
        Log.d(TAG, "setFrequency: rawstring frequency " +new String(messageInBytes));
        return messageInBytes;
    }


    //<Ca;b;c;d;e;f;g;h;i;j> (need to be done second)
    public byte[] setTraining(int Ca,int Cb,int Cc,int Cd,int Ce,
                            int Cf,int Cg,int  Ch,int Ci,int Cj)
    {
        String message = ARDUINO_SET_UP_WORKOUT + Ca + ARDUINO_DELIMITER +
                Cb + ARDUINO_DELIMITER + Cc +
                ARDUINO_DELIMITER + Cd + ARDUINO_DELIMITER +Ce +
                ARDUINO_DELIMITER + Cf + ARDUINO_DELIMITER + Cg + ARDUINO_DELIMITER +
                Ch + ARDUINO_DELIMITER+ Ci + ARDUINO_DELIMITER + Cj + ARDUINO_END_OF_MESSAGE;

        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);

        return messageInBytes;
    }

    // example <S1023> = start all electrodes (done third)
    public byte[] startElectrodes(int whichElectrode)
    {
        String message = ARDUINO_START + whichElectrode + ARDUINO_END_OF_MESSAGE;

        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);
        return messageInBytes;

    }

    // example = on all channels <L1023;20;20;20;20;20;20;20;20;20;20> can be done last or before start
    public byte[] setLevel(int electrode,int levels)
    {
        String message = ARDUINO_SET_UP_LEVEL + electrode + ARDUINO_DELIMITER + levels+ ARDUINO_END_OF_MESSAGE;
//        StringBuilder message = new StringBuilder();
//                message.append(ARDUINO_SET_UP_LEVEL);
//                message.append(electrode);
//                message.append(ARDUINO_DELIMITER);
//        for (int level : levels){
//            message.append(level);
//            message.append(ARDUINO_DELIMITER);
//        }
//        message.append(ARDUINO_END_OF_MESSAGE);


//        byte[] messageInBytes = message.toString().getBytes();
        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);


        return messageInBytes;
    }

    public byte[] stopElectrode(int electrode)
    {
        String message = ARDUINO_STOP_ELECTRODE + electrode + ARDUINO_END_OF_MESSAGE;

        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);
        return messageInBytes;
    }
    public byte[] resetDevice()
    {
        String message = ARDUINO_RESET + ARDUINO_END_OF_MESSAGE;

        byte[] messageInBytes = message.getBytes(StandardCharsets.US_ASCII);
        return messageInBytes;
    }

}














