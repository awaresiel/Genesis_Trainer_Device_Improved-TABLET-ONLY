//package com.example.genesis_trainer_device_improved.ViewModel.recyclers;
//
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.genesis_trainer_device_improved.Activities.Activity_Training;
//import com.example.genesis_trainer_device_improved.Activities.App;
//import com.example.genesis_trainer_device_improved.Entity.Client;
//import com.example.genesis_trainer_device_improved.Entity.Electrode;
//import com.example.genesis_trainer_device_improved.Entity.Statistics;
//import com.example.genesis_trainer_device_improved.Entity.Trainer;
//import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
//import com.example.genesis_trainer_device_improved.Fragments.Fragment_Sign_In;
//import com.example.genesis_trainer_device_improved.R;
//import com.example.genesis_trainer_device_improved.ViewModel.users_model.UserViewModel;
//import com.example.genesis_trainer_device_improved.helpers.BitmapSerialiser;
//import com.example.genesis_trainer_device_improved.helpers.Constants;
//import com.google.gson.Gson;
//
//import java.lang.ref.WeakReference;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Objects;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import pl.droidsonroids.gif.GifDrawable;
//import pl.droidsonroids.gif.GifImageView;
//
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_1;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_10;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_11;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_12;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_2;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_3;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_4;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_5;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_6;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_7;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_8;
//import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_9;
//import static java.lang.Thread.currentThread;
//import static java.lang.Thread.sleep;
//
//public class Group_Training_Recycler extends RecyclerView.Adapter<Group_Training_Recycler.Group_Training_ViewHolder> {
//    private static final String TAG = "Group_Training_Recycler";
//
//    public interface CommunicationWithTrainingActivity {
//        void message(byte[] message, String addressOfDevice);
//
//        void reconnectDevice(String address);
//
//        void removeClient(Client client, int position);
//
//
//    }
//
//    CommunicationWithTrainingActivity communication;
//
//    WeakReference<Context> context;
//    /*
//     key = client id
//     value = timeRemaining
//     */
////     private HashMap<Integer, Long> timeRemainingMap;
////    private HashMap<Integer, int[]> clientsSetup;
//    private ArrayList<Client> clientsList;
//    private Electrode electrode;
//
//    public boolean remove = false;
//    //   private boolean initialiseStates=true;
//    public int positionToRemove = -1;
//
//    private ArrayList<BluetoothDevice> devicesList;
//    public boolean pauseAlltimers = false;
//    private UserViewModel userViewModel;
//    private Trainer trainer;
////    Group_Training_ViewHolder holder;
////    int pos;
//
//
//    public Group_Training_Recycler(Context context, CommunicationWithTrainingActivity communicationWithTrainingActivity,
//                                   ArrayList<Client> clientsList, ArrayList<BluetoothDevice> devices
//            , Trainer trainer) {
//        this.context = new WeakReference<>(context);
////        this.userViewModel = model;
//        this.trainer = trainer;
////        this.clientAddressMap=clientAddressMap;
////        clientsSetup =((App)context.getApplicationContext()).getCheckboxTable().get(position);
//        communication = communicationWithTrainingActivity;
//        this.clientsList = clientsList;
//        electrode = new Electrode();
////        this.countDownTimer = countDownTimer;
//
//        this.devicesList = devices;
////        timeRemainingMap = new HashMap<>();
//        Log.d(TAG, "Group_Training_Recycler: constructor called");
//
////        for (Client client : clientsList){
////            Log.d(TAG, "Group_Training_Recycler: forclientid "+ client.getClientId() +" setupdata = " +
////                clientsSetup.get(client.getClientId())[0]+ " "+ clientsSetup.get(client.getClientId())[1]+" "+
////                clientsSetup.get(client.getClientId())[2]+" "+ clientsSetup.get(client.getClientId())[3]);
////
////    }
//    }
//
//    @NonNull
//    @Override
//    public Group_Training_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder: holderconstructor");
//        View view = LayoutInflater.from(context.get()).inflate(R.layout.activity_training, parent, false);
//        Log.d(TAG, "onCreateViewHolder: viewtype = " +viewType );
//        Log.d(TAG, "onCreateViewHolder: activity_training = " +R.layout.activity_training );
//        return new Group_Training_ViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull final Group_Training_ViewHolder holder, int position) {
//
//        if (clientsList != null && devicesList != null) {
//
////            if (remove) {
////                Log.d(TAG, "onBindViewHolder: remove true");
////
////                holder.saveStatistics();
//////                holder.saveTraining(clientsList.get(holder.getOldPosition()).getClientId());
////                Log.d(TAG, "onBindViewHolder: " + holder.getOldPosition());
////                Log.d(TAG, "onBindViewHolder: " + holder.getLayoutPosition());
////                Log.d(TAG, "onBindViewHolder: " + holder.getAdapterPosition());
////
////                if (holder.start.getText().equals(context.get().getString(R.string.stop)) &&
////                        holder.stop.getText().toString().equals(context.get().getString(R.string.pause))) {
////
////                    holder.stop.callOnClick();
////                    holder.countDownTimer.cancel();
////                    holder.timerCounter.setText("00:00");
////
////
//////                    holder.isPaused=true;
////                    ((GifDrawable) holder.gifImageView.getDrawable()).stop();
////                    Log.d(TAG, "onBindViewHolder: stop position = " + position);
////                }
////                holder.imageView_remove.setVisibility(View.VISIBLE);
////
////            } else {
////               // holder.isPaused=false;
//////                if (holder.timeRemaining>0){
////                if (holder.stop.getText().equals(context.get().getString(R.string.Resume))){
////                 //   holder.countDown(holder.timeRemaining).start();
////                  //  holder.startScheduledGif();
////
////                    holder.stop.callOnClick();
////
//////                    holder.countDown(holder.timeRemaining).start();
////
////                }
//
////                holder.checkBox_remove.setVisibility(View.GONE);
//
//          //  }
//
//
////            holder.setIsRecyclable(false);
//            holder.setClientFromClientList(clientsList.get(position));
//
//            initUserInfo(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).getTrainingEnviroment(),
//                    clientsList.get(position).getClientId(),holder);
//            holder.timerCounter.setText("00:00");
////            holder.setTimeDisplayText(holder.timerCounter);
//            holder.chest.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isChest());
//            holder.hands.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isHands());
//            holder.abs.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isAbdomen());
//            holder.quadriceps.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isQuadriceps());
//            holder.trapesious.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isTrapez());
//            holder.back.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isBack());
//            holder.lowerBack.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isLowerBack());
//            holder.glutes.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isGlutes());
//            holder.hamstrings.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isHamstrings());
//            holder.extra.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isExtra());
//            holder.triceps.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isTriceps());
//            holder.shoulder.setChecked(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isShoulder());
//            holder.timeOfStimulation_ms = ((App) context.get().getApplicationContext()).getCheckboxTable().get(position).timeOfStimulation_ms;
//            holder.duration = ((App) context.get().getApplicationContext()).getCheckboxTable().get(position).duration;
//            holder.restTime_ms = ((App) context.get().getApplicationContext()).getCheckboxTable().get(position).restTime_ms;
//            holder.whichElectrodes = ((App) context.get().getApplicationContext()).getCheckboxTable().get(position).whichElectrodes;
//            holder.usersName.setText(clientsList.get(position).getClientName());
//            Glide.with(context.get()).load(getClientBitmap(position)).into(holder.userProfilePhoto);
//
//
//            //  Log.d(TAG, "onBindViewHolder: getDeviceFromAddress" + devicesList.get(position).getName());
//
//                holder.tv_showDeviceName.setText(devicesList.get(position).getName());
//
////            holder.tv_showDeviceName.setText(devicesList.get(position).getName());
//            Log.d(TAG, "onBindViewHolder: device name = "+ devicesList.get(position).getName()+
//                    " address = " + devicesList.get(position).getAddress());
//
//
//           initialiseStates(position,holder);
//           holder.start.setText(context.get().getString(R.string.start));
//           holder.stop.setText(context.get().getString(R.string.pause));
//            ((GifDrawable) holder.gifImageView.getDrawable()).stop();
////
////           if (timeRemainingMap.containsKey(holder.holdersClient.getClientId())){
////               Long l = timeRemainingMap.get(holder.holdersClient.getClientId());
////               if (l !=null){
////               holder.timeRemaining =l;
////               }
////           }
//
//            Log.d(TAG, "onBindViewHolder: binding clientName = " + clientsList.get(position).getClientName() + " ,position= "+ position);
//        }
//
//
//    }
//
//
//    private Bitmap getClientBitmap(int position) {
//        if (clientsList != null && clientsList.get(position).getClientProfileImage() != null) {
//            Bitmap bitmap = BitmapSerialiser.getBitmapFromString(new Gson().
//                    fromJson(clientsList.get(position).getClientProfileImage(), String.class));
//            return bitmap;
//        } else {
//            return BitmapFactory.decodeResource(context.get().getResources(), R.drawable.icons_user_80);
//        }
//    }
//
//    public Client clientSelectedForRemoval() {
//
//        if (positionToRemove != -1) {
//            return clientsList.get(positionToRemove);
//        } else {
//            return null;
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//
//        if (clientsList != null) {
//            Log.d(TAG, "getItemCount: clientlist size = " + clientsList.size());
//            return clientsList.size();
//
//        } else {
//            return 0;
//        }
//
//
//    }
//    public void saveAllStates(){
//
//    }
//
//    public void setListOfClients(ArrayList<Client> list) {
//        clientsList = list;
////        Log.d(TAG, "setListOfClients: setting list " + list);
//        //notifyDataSetChanged();
//    }
//
//    public void setListOfDevices(ArrayList<BluetoothDevice> devices) {
//        devicesList = devices;
////        notifyDataSetChanged();
//        for (int i=0;i<devicesList.size();i++)
//        Log.d(TAG, "setListOfClients: setting list " + devicesList.get(i).getName());
//
//    }
//
//    public class Group_Training_ViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener,
//            CompoundButton.OnCheckedChangeListener, SeekBar.OnTouchListener {
//
//        private CheckBox chest, hands, abs, quadriceps, trapesious, back, lowerBack, glutes, hamstrings, extra, triceps, shoulder;
//
//        private SeekBar sb_chest, sb_hands, sb_abs, sb_quadriceps, sb_trapesious, sb_back, sb_lowerBack,
//
//                sb_glutes, sb_hamstrings, sb_extra, sb_increaseAllLevels, sb_triceps, sb_shoulder;
//
//        private Button start, stop, timerCounter;
//
//        private ImageButton imageButton_personal_deviceImg, imageButton_GroupTrainningImage,button_currently_connected_device;
//
//        private EditText timerLimit_minutes, timerlimit_seconds;
//
//        private TextView impulseDisplay, impulsePauseDisplay, strengthText_increaseAllLevels, srengthText_chest, srengthText_hands, srengthText_abs, srengthText_quadriceps,
//                srengthText_trapesius, srengthText_back, srengthText_lowerback, srengthText_glutes, srengthText_hamstrings, tv_showDeviceName,
//                srengthText_extra, usersName, currentTrainingName, strengthText_triceps, strengthText_shoulder;
//
//        private TextView textChest, textHands, textAbs, textQuads, textTraps, textSpina, textLowerBack, textHams, textGlutes, textExtra, textShoulder, textTriceps;
//
//        private String trainingName = "";
//
//        public volatile boolean isPaused = true;
//
//        private ImageView userProfilePhoto, incAllImpulses, decAllImpulses, saveTraining, lodTraining;
//
//        GifImageView gifImageView;
//
//        ImageView imageView_remove;
//
//        private int whichElectrodes = 0;
//
//        ScheduledExecutorService fixedRateTimer;
//        //       ScheduledExecutorService  pausefixedRateTimer ;
////       Timer fixedRateTimer ;
//        int allowedProgress;
//        //       Timer pauseFixedRateTimer ;
//        private boolean switchElectrodeNumberBoolean = true;
//        Fragment_Sign_In   fragment;
//
//        private long timeRemaining = 0;
//
//        long duration = 0;
//        int timeOfStimulation_ms = 0;
//        int restTime_ms = 0;
//        long saveDuration;
//        Handler myHandler;
//        private CountDownTimer countDownTimer;
////TODO TESTING, if dont work delete later
//        Client holdersClient;
//
//        public Group_Training_ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            myHandler = new Handler(Looper.getMainLooper());
//            if (getAdapterPosition()!=-1){
//                Log.d(TAG, "Group_Training_ViewHolder: holderconstructor clientname=  " +
//                        clientsList.get(getAdapterPosition()).getClientName() + " pos= " +getAdapterPosition());
//
//            }
//
//
//             Log.d(TAG, "onBindViewHolder: holderconstructor whichelectrode = " + whichElectrodes);
//            initializeWidgets(itemView);
//
//            stop.setEnabled(false);
//            ((GifDrawable) gifImageView.getDrawable()).stop();
//
//            //stopGif();
//            Log.d(TAG, "Group_Training_ViewHolder: useraddressmape = " + ((App) context.get().getApplicationContext()).getGetUsersAddressMap().entrySet());
////            btAddress = ((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId());
//            //((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId());
//
//
//        }
//
//       public void setClientFromClientList(Client client){
//
//               holdersClient=client;
//
//
//
//               Log.d(TAG, "setHolderListOfClients: client name = " + holdersClient.getClientName() + " pos " +getAdapterPosition());
//
//        }
//        public void setTimeDisplayText(Button display){
//            timerCounter = display;
//        }
//
//
//
//        private synchronized void startGif() {
//
//            if (isPaused || pauseAlltimers) {
//
//                ((GifDrawable) gifImageView.getDrawable()).stop();
//                Log.d(TAG, "startGif: returning from start");
//
//                return;
//            }
//
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//                    if (isPaused || pauseAlltimers) {
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        Log.d(TAG, "run: interupting ispaused");
//                        currentThread().interrupt();
//                    }
//                    Log.d(TAG, "run:  entering");
//                    ((GifDrawable) gifImageView.getDrawable()).start();
//                    try {
//
//                        sleep(timeOfStimulation_ms * 1000);
//                        Log.d(TAG, "run: pausing for " + timeOfStimulation_ms * 1000);
//                        stopGif();
//                        Log.d(TAG, "run: interupting start");
//                        currentThread().interrupt();
//
//                    } catch (InterruptedException e) {
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        currentThread().interrupt();
//
//                        e.printStackTrace();
//                    }
//                }
//            };
//           final Thread t = new Thread(r);
//
//            t.start();
//
//
//
//            Log.d(TAG, "startGif: while loop run gif");
//
//        }
//
//        private synchronized void stopGif() {
//            if (isPaused || pauseAlltimers) {
//
//                ((GifDrawable) gifImageView.getDrawable()).stop();
//                Log.d(TAG, "stopGif: returning from stop");
//                currentThread().interrupt();
//                return;
//            }
//
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//                    if (isPaused || pauseAlltimers) {
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        Log.d(TAG, "run: interupting ispaused");
//                        currentThread().interrupt();
//                    }
//                    Log.d(TAG, "run: entering stopgif");
//                    ((GifDrawable) gifImageView.getDrawable()).stop();
//
//                    try {
//                        sleep(restTime_ms * 1000);
//                        Log.d(TAG, "run: pausing for " + restTime_ms * 1000);
//                        startGif();
//                        Log.d(TAG, "run: interupting");
//                        currentThread().interrupt();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        currentThread().interrupt();
//                    }
//                }
//            };
//            Thread t = new Thread(r);
//            t.start();
//
//
//        }
//
//        private void startScheduledGif() {
//
//            if (restTime_ms != 0 && timeOfStimulation_ms != 0) {
//                try {
//
//                    startGif();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            } else {
//                ((GifDrawable) gifImageView.getDrawable()).start();
//            }
//
//        }
//
//        public CountDownTimer countDown(long miliseconds) {
//            if (countDownTimer!=null){
//                countDownTimer.cancel();
//                countDownTimer=null;
//            }
//
////        final CountDownTimer countDownTimer = new CountDownTimer(miliseconds, 1000) {
//            countDownTimer = new CountDownTimer(miliseconds, 1000) {
//                long milisuntil;
//                public void onTick(long millisUntilFinished) {
////                if (isPaused || isCanceled) {
//                     milisuntil=millisUntilFinished;
//                    if (isPaused || pauseAlltimers) {
//
//                        Log.d(TAG, "onTick: ispaused timeremaining =" + timeRemaining);
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        checkIfSecurityTimeLimitPassed();
////                        checkIfSecurityTimeLimitPassed(duration-=millisUntilFinished);
//                        saveStatistics();
//                        cancel();
//                        switchElectrodeNumberBoolean=false;
////
//
//                    } else {
//                        long mins = millisUntilFinished / 60000;
//                        long secs = millisUntilFinished % 60000 / 1000;
//
//
//                    final String displaytime = secs>9? String.format("%d" + ":" + "%d", mins, secs): String.format("%d" + ":" + "0%d", mins, secs);
//
////                    final String displaytimeUnder10Seconds = String.format("%d" + ":" + "0%d", mins, secs);
//
//                         Runnable runnable = new Runnable() {
//                            @Override
//                            public void run() {
//
//                                timerCounter.setText(displaytime);
////                                display.setText(displaytime);
//
//                            }
//                        };
//                        timerCounter.post(runnable);
//                        if (getLayoutPosition() != -1) {
////                            Log.d(TAG, "onTick: milis= " + millisUntilFinished + " client name= " + clientsList.get(getLayoutPosition()).getClientName());
//
//                        }
//
//                        Log.d(TAG, "onTick: milis= " + millisUntilFinished + " client name= " + holdersClient.getClientName() + " id= " +holdersClient.getClientId());
//
//                        timerLimit_minutes.setEnabled(false);
//                        timerlimit_seconds.setEnabled(false);
//                        timeRemaining = millisUntilFinished;
//
//                        // Log.d(TAG, "onTick: timeremaining ="+timeRemaining);
//                    }
//                }
//
//
//                public void onFinish() {
//                    timerCounter.setText("00:00");
//                    start.setText(R.string.start);
//                    stop.setEnabled(false);
//                    lodTraining.setEnabled(false);
////                    communication.message(electrode.stopElectrode(Constants.ALL_ELECTRODES),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//                    Log.d(TAG, "onFinish: getLayoutPosition = " + getLayoutPosition());
////                    if (getAdapterPosition() >-1)
////                    if (getLayoutPosition()!=-1) communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
////                    if (getLayoutPosition()!=-1)
//                        communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                    stopGif();
//                    isPaused = true;
//                    switchElectrodeNumberBoolean=false;
//                    ((GifDrawable) gifImageView.getDrawable()).stop();
//                    checkIfSecurityTimeLimitPassed();
//                    Log.d(TAG, "onFinish: duration= " + duration +" milisuntil= " +milisuntil);
//
//
//                    Log.d(TAG, "onFinish: save duration = " + saveDuration + " duration= " +duration);
//                    saveStatistics();
//                    cancel();
//
//
//                }
//            };
//
//            return countDownTimer;
//        }
//
//        private void calculateSaveDuration(){
//
//            saveDuration = duration - timeRemaining;
//
//        }
//
//        private void checkIfSecurityTimeLimitPassed(){
//            long timee=duration-timeRemaining;
//            Boolean b=  ((App)context.get().getApplicationContext()).minusTime(timee);
//
//            if (!b){
//                showFragmentSignIn();
//            }
//        }
//
//
//        private void showFragmentSignIn() {
//            String deviceID =  Settings.Secure.getString(context.get().getContentResolver(), Settings.Secure.ANDROID_ID);
//            pauseAlltimers = true;
//            if (fragment == null) {
//                Log.d(TAG, "showFragmentSignIn: first if");
//                fragment = new Fragment_Sign_In();
//            }
//                if (fragment !=null && fragment.isAdded()) {
//                    Log.d(TAG, "showFragmentSignIn: second if");
//                    fragment.onDetach();
//
//                    fragment = new Fragment_Sign_In();
//                    Log.d(TAG, "showFragmentSignIn: else");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imei", deviceID);
//                    fragment.setArguments(bundle);
//                    Log.d(TAG, "showFragmentSignIn: ");
//                    fragment.show(((Activity_Training) context.get()).getSupportFragmentManager(), Fragment_Sign_In.class.getSimpleName());
//
//                } else {
//                    //   if (deviceID !=null && fragment!=null) {
//                    // fragment = new Fragment_Sign_In();
//                    Log.d(TAG, "showFragmentSignIn: else");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imei", deviceID);
//                    fragment.setArguments(bundle);
//                    Log.d(TAG, "showFragmentSignIn: ");
//                    fragment.show(((Activity_Training) context.get()).getSupportFragmentManager(), Fragment_Sign_In.class.getSimpleName());
//
//                    // }
//                }
//
//        }
//
//        View.OnClickListener buttonsListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//
//
//                    case R.id.startTraining_btn_Start:
//                        //  Button btnStarted = ((Button) v);
//                        Log.d(TAG, "onClick: start btn");
//                        if (((App)context.get().getApplicationContext()).getTimer()>0) {
//                            Log.d(TAG, "onClick: start>0");
//                            stop.setEnabled(true);
//                            pauseAlltimers=false;
//                            lodTraining.setEnabled(true);
//                            Log.d(TAG, "onClick: start clicked client name= " + clientsList.get(getAdapterPosition()).getClientName());
//                            if (start.getText().toString().equals(context.get().getString(R.string.start)) && timerLimit_minutes.getText().length() > 0
//                                    && Integer.parseInt(timerLimit_minutes.getText().toString().trim())>0 &&
//                                    timerlimit_seconds.getText().length() > 0
//                                   ) {
//                                Log.d(TAG, "onClick: start pressed username= " + clientsList.get(getAdapterPosition()).getClientName() + " adapter pos= " + getAdapterPosition());
//                                Log.d(TAG, "onClick: ");
//                                switchElectrodeNumberBoolean = false;
//                                String timeMinutes = timerLimit_minutes.getText().toString().trim();
//                                String timeSeconds = timerlimit_seconds.getText().toString().trim();
//                                Log.d(TAG, "onClick: timerLimit_minutes = " + timeMinutes + ":" + timeSeconds);
//
//                                String time = timeMinutes + timeSeconds;
//
//                                int timeMin = Integer.parseInt(timeMinutes);
//                                int timeSec = Integer.parseInt(timeSeconds);
//
//                                duration = timeMin;
//                                timerLimit_minutes.setText(duration + "");
//                                duration *= 60000;
//
//                                timeSec *= 1000;
//
//                                duration += timeSec;
//
//                                countDown(duration).start();
////                                countDownTimer.start();
//                                // FIRST RESET ALL ELECTROES
//                                myHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.d(TAG, "run: handler first post ");
////                                        if (getLayoutPosition()!=-1)
//                                            communication.message("<RST>".getBytes(),
////                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//                                    }
//                                }, 0);
//                                // SETUP FREQUENCY
//                                myHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.d(TAG, "run: handler first post ");
////                                        if (getLayoutPosition()!=-1)
////                                            communication.message(((App) context.get().getApplicationContext()).getGetUsersTrainingFrequencyMap().get(clientsList.get(getLayoutPosition()).getClientId()),
//                                            communication.message(((App) context.get().getApplicationContext()).getGetUsersTrainingFrequencyMap().get(holdersClient.getClientId()),
////                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//                                    }
//                                }, 300);
//                                // SETUP <C> PARAMETER
//                                myHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.d(TAG, "run: handler second post");
////                                        if (getLayoutPosition()!=-1)
////                                            communication.message(((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().get(clientsList.get(getLayoutPosition()).getClientId()),
//                                            communication.message(((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().get(holdersClient.getClientId()),
//                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//
//                                    }
//                                }, 1400);
//                                // START ALL ELECTRODES <S> PARAMETER
//                                myHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Log.d(TAG, "run: handler third post");
////                                    communication.message(electrode.startElectrodes(Constants.ALL_ELECTRODES),
////                                        if (getLayoutPosition()!=-1)
//                                            communication.message(electrode.startElectrodes(Constants.FIRST_TEN_ELECTRODES),
////                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                                                ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//                                        startScheduledGif();
//                                    }
//                                }, 2000);
//                                // (not using who here)=communication.message("<WHO>".getBytes(),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//                                //setup frequency starting on that one
//                                // communication.message(((App)context.getApplicationContext()).getGetUsersTrainingFrequencyMap().get(clientsList.get(getAdapterPosition()).getClientId()),
//                                //        ((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//                                //setup training <C...>
////                            communication.message(((App)context.getApplicationContext()).getGetUsersTrainingSetupMap().get(clientsList.get(getAdapterPosition()).getClientId()),
////                                    ((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//                                //start electrodes
////                            communication.message(electrode.startElectrodes(1023),
////                                    ((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//
//                                stop.setEnabled(true);
//                                start.setText(context.get().getString(R.string.stop));
//                                stop.setText(context.get().getString(R.string.pause));
//                                enableDisableAllSeekbars(true);
//                                isPaused = false;
//
//
//                            } else if (start.getText().toString().equals(context.get().getString(R.string.stop)) && !isPaused) {
//                                Log.d(TAG, "onClick: start pressed username= " + clientsList.get(getAdapterPosition()).getClientName() + " adapter pos= " + getAdapterPosition());
//                                Log.d(TAG, "onClick: ");
//                                setAllProgressTo(0);
//                                lodTraining.setEnabled(false);
//                                start.setText(context.get().getString(R.string.start));
//                                stop.setText(context.get().getString(R.string.pause));
//                                countDownTimer.onFinish();
//                                countDownTimer.cancel();
////                                c.cancel();
//                                ((GifDrawable) gifImageView.getDrawable()).stop();
//
//                              //  saveDuration = duration - timeRemaining;
//                                timeRemaining = duration;
////                            communication.message(electrode.stopElectrode(Constants.ALL_ELECTRODES),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//                               // if (getAdapterPosition() >-1)
////                                if (getLayoutPosition()!=-1)
////                                    communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                                    communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//                                timerLimit_minutes.setEnabled(true);
//                                timerlimit_seconds.setEnabled(true);
//                                stop.setEnabled(false);
//                                enableDisableAllSeekbars(false);
//                                isPaused = true;
//                                switchElectrodeNumberBoolean = true;
////                                saveStatistics();
//                            }
//
//                        }else{
//                            Log.d(TAG, "onClick: show fragment start");
//                            showFragmentSignIn();
//                        }
//                        Log.d(TAG, "onClick: start clicked");
//                        break;
//
//
//                    case R.id.startTraining_btn_Stop:
//                        if (!isPaused) {
//                            enableDisableAllSeekbars(isPaused);
//                            lodTraining.setEnabled(false);
//                            isPaused = true;
//                            start.setText(context.get().getString(R.string.start));
//                            stop.setText(context.get().getString(R.string.Resume));
//                            timerLimit_minutes.setEnabled(true);
//                            timerlimit_seconds.setEnabled(true);
//
////                            timeRemainingMap.put(holdersClient.getClientId(),timeRemaining);
//                            Log.d(TAG, "onClick: putting client name= "+ holdersClient.getClientName() + " timeRemaining "+ timeRemaining );
////
//                            // sb_increaseAllLevels.setProgress(0);
////                            if (getAdapterPosition() >-1)
////                            communication.message(electrode.stopElectrode(Constants.ALL_ELECTRODES),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
////                            if (getLayoutPosition()!=-1)
//                                communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                                communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//
//
//                        } else if (isPaused) {
//                            isPaused = false;
//                            pauseAlltimers=false;
//                            lodTraining.setEnabled(true);
//                            start.setText(context.get().getString(R.string.stop));
//                            stop.setText(context.get().getString(R.string.pause));
//                            countDown(timeRemaining).start();
////                            countDownTimer.onTick(timeRemaining);
////                            countDownTimer.start();
////                            startGifAsGif();
//                            startScheduledGif();
//                            Log.d(TAG, "onClick: pausebtn timeremaining =" + timeRemaining);
//                            enableDisableAllSeekbars(true);
////                            communication.message(electrode.startElectrodes(Constants.ALL_ELECTRODES),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
////                            if (getAdapterPosition() >-1)
////                            if (getLayoutPosition()!=-1)
//                                communication.message(electrode.startElectrodes(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                                communication.message(electrode.startElectrodes(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                        }
//                        Log.d(TAG, "onClick: stop clicked");
//                        break;
//
//                    case R.id.button_saveTraining:
//
////                        if (getLayoutPosition()!=-1)
////                            saveTrainingInMemory(clientsList.get(getLayoutPosition()).getClientId());
//                            saveTrainingInMemory(holdersClient.getClientId());
//
//                        break;
//                    case R.id.button_loadTraining:
////                        if (!isPaused)
////                        if (getLayoutPosition()!=-1)
////                            loadTraining(clientsList.get(getLayoutPosition()).getClientId());
//                            loadTraining(holdersClient.getClientId());
//                        break;
//
//                    case R.id.button_currently_connected_device:
////                        communication.message(electrode.stopElectrode(Constants.ALL_ELECTRODES),((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
////                        if (getAdapterPosition() >-1)
////                        if (getLayoutPosition()!=-1)
////                            communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                            communication.message(electrode.stopElectrode(Constants.FIRST_TEN_ELECTRODES), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//
////                        if (getAdapterPosition() >-1)
////                        if (getLayoutPosition()!=-1)
//                            communication.reconnectDevice(((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                            communication.reconnectDevice(((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                        break;
//
//                    case R.id.imageButton_incAllImpulses:
//
//                        if (!isPaused) {
//                            //sb_increaseAllLevels.incrementProgressBy(1);
//
//                            if (chest.isChecked()) sb_chest.incrementProgressBy(1);
//
//                            if (hands.isChecked()) sb_hands.incrementProgressBy(1);
//
//                            if (abs.isChecked()) sb_abs.incrementProgressBy(1);
//
//                            if (quadriceps.isChecked()) sb_quadriceps.incrementProgressBy(1);
//
//                            if (trapesious.isChecked()) sb_trapesious.incrementProgressBy(1);
//
//                            if (back.isChecked()) sb_back.incrementProgressBy(1);
//
//                            if (lowerBack.isChecked()) sb_lowerBack.incrementProgressBy(1);
//
//                            if (glutes.isChecked()) sb_glutes.incrementProgressBy(1);
//
//                            if (hamstrings.isChecked()) sb_hamstrings.incrementProgressBy(1);
//
//                            if (extra.isChecked()) sb_extra.incrementProgressBy(1);
//                            if (shoulder.isChecked()) sb_shoulder.incrementProgressBy(1);
//                            if (triceps.isChecked()) sb_triceps.incrementProgressBy(1);
//
////                            if (getAdapterPosition() >-1)
////                            if (getLayoutPosition()!=-1)
//                                communication.message(new String("<U" + whichElectrodes + ">").getBytes(), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                                communication.message(new String("<U" + whichElectrodes + ">").getBytes(), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                            Log.d(TAG, "onClick: whichElectrodes = " + whichElectrodes);
//                        }
//                        break;
//                    case R.id.imageButton_decAllImpulses:
//                        // if (sb_increaseAllLevels.getProgress()>0){
////                        if (!isPaused) {
//                        if (!isPaused) {
//                            // sb_increaseAllLevels.incrementProgressBy(-1);
//                            if (chest.isChecked()) sb_chest.incrementProgressBy(-1);
//
//                            if (hands.isChecked()) sb_hands.incrementProgressBy(-1);
//
//                            if (abs.isChecked()) sb_abs.incrementProgressBy(-1);
//
//                            if (quadriceps.isChecked()) sb_quadriceps.incrementProgressBy(-1);
//
//                            if (trapesious.isChecked()) sb_trapesious.incrementProgressBy(-1);
//
//                            if (back.isChecked()) sb_back.incrementProgressBy(-1);
//
//                            if (lowerBack.isChecked()) sb_lowerBack.incrementProgressBy(-1);
//
//                            if (glutes.isChecked()) sb_glutes.incrementProgressBy(-1);
//
//                            if (hamstrings.isChecked()) sb_hamstrings.incrementProgressBy(-1);
//
//                            if (extra.isChecked()) sb_extra.incrementProgressBy(-1);
//                            if (shoulder.isChecked()) sb_shoulder.incrementProgressBy(-1);
//                            if (triceps.isChecked()) sb_triceps.incrementProgressBy(-1);
////                            if (getAdapterPosition() >-1)
////                            if (getLayoutPosition()!=-1)
////                                communication.message(new String("<D" + whichElectrodes + ">").getBytes(), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                                communication.message(new String("<D" + whichElectrodes + ">").getBytes(), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
//                        }
//
//                        break;
//
//                    case R.id.imageButton_personal_deviceImg:
//                        if (switchElectrodeNumberBoolean) {
//                            if (Objects.equals(imageButton_personal_deviceImg.getTag(), "genesis_black_device")) {
//                                Constants.FIRST_TEN_ELECTRODES = 1023;
//                                if (shoulder.isChecked()){
//                                shoulder.performClick();
//                                }
//                                if (triceps.isChecked()) {
//                                    triceps.performClick();
//                                }
//                                sb_shoulder.setVisibility(View.GONE);
//                                sb_triceps.setVisibility(View.GONE);
//                                strengthText_shoulder.setVisibility(View.GONE);
//                                strengthText_triceps.setVisibility(View.GONE);
//                                triceps.setVisibility(View.GONE);
//                                shoulder.setVisibility(View.GONE);
//                                textTriceps.setVisibility(View.GONE);
//                                textShoulder.setVisibility(View.GONE);
//
//                                //  imageButton_GroupTrainningImage.setImageResource(0);
//                                imageButton_personal_deviceImg.setImageResource(R.drawable.genesis_white_device);
//                                imageButton_personal_deviceImg.setTag("genesis_white_device");
//                                byte[] training =  ((App) context.get().getApplicationContext()).getTrainingSetup(  Constants.FIRST_TEN_ELECTRODES);
////                                if (getLayoutPosition()!=-1)
////                                    ((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().put(clientsList.get(getLayoutPosition()).getClientId(),training);
//                                    ((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().put(holdersClient.getClientId(),training);
//                            } else {
//                                sb_shoulder.setVisibility(View.VISIBLE);
//                                sb_triceps.setVisibility(View.VISIBLE);
//                                strengthText_shoulder.setVisibility(View.VISIBLE);
//                                strengthText_triceps.setVisibility(View.VISIBLE);
//                                triceps.setVisibility(View.VISIBLE);
//                                shoulder.setVisibility(View.VISIBLE);
//                                textTriceps.setVisibility(View.VISIBLE);
//                                textShoulder.setVisibility(View.VISIBLE);
//                                //     imageButton_GroupTrainningImage.setImageResource(0);
//                                imageButton_personal_deviceImg.setImageResource(R.drawable.genesis_black_device);
//                                imageButton_personal_deviceImg.setTag("genesis_black_device");
//                                Constants.FIRST_TEN_ELECTRODES = 4095;
//
//                                byte[] training =  ((App) context.get().getApplicationContext()).getTrainingSetup(  Constants.FIRST_TEN_ELECTRODES);
////                                if (getLayoutPosition()!=-1)
////                                    ((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().put(clientsList.get(getLayoutPosition()).getClientId(),training);
//                                    ((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().put(holdersClient.getClientId(),training);
//
//
//                            }
//                        }
//                        break;
//                    case R.id.imageButton_GroupTrainningImage:
//                        if (Objects.equals(imageButton_GroupTrainningImage.getTag(), "genesis_small_picture_suit_upper_part")){
//                            setTextForGroupTrainingSuits();
//                            imageButton_GroupTrainningImage.setImageResource(R.drawable.genesis_small_picture_suit);
//                            imageButton_GroupTrainningImage.setTag("genesis_small_picture_suit");
//                        }else{
//                            setTextForPersonalSuit();
//                            imageButton_GroupTrainningImage.setImageResource(R.drawable.genesis_small_picture_suit_upper_part);
//                            imageButton_GroupTrainningImage.setTag("genesis_small_picture_suit_upper_part");
//                        }
//
//                        break;
//                    case R.id.imageview_removeClient:
//                            //  holdersClient=null;
//                        remove = true;
//                        isPaused=true;
//                        positionToRemove = getAdapterPosition();
//                        saveStatistics();
//                        ((GifDrawable) gifImageView.getDrawable()).stop();
//                        if (start.getText().equals(context.get().getString(R.string.stop))) {
//
//                            if (countDownTimer != null) {
//                                countDownTimer.cancel();
//                                start.callOnClick();
//
//                                Log.d(TAG, "onClick: first if");
//
//                            }
//                        }
//                        communication.removeClient(clientsList.get(getLayoutPosition()), getLayoutPosition());
//                           remove = false;
//                           isPaused=false;
//                            // fragment=null;
//
//
//
//                        break;
//                }
//            }
//        };
//
//        public void saveTraining(int ClientID) {
//            if (getLayoutPosition()!=-1){
//                UserTrainingSettingsTable table = ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition());
//            table.setClientId(ClientID);
//            table.setTrainingName(trainingName);
//            table.setTrainingFrequency(((App) context.get().getApplicationContext()).getGetUsersTrainingFrequencyMap().get(ClientID));
//            table.setTrainingSetup(((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().get(ClientID));
//            int[] trainingParameters = ((App) context.get().getApplicationContext()).getGetUsersSetupMap().get(ClientID);
//            if (trainingParameters != null) {
//                trainingParameters[3] = whichElectrodes;
//            }
//            ((App) context.get().getApplicationContext()).getGetUsersSetupMap().put(ClientID, trainingParameters);
//            table.setClientTrainingEnviroment(((App) context.get().getApplicationContext()).getGetUsersSetupMap().get(ClientID));
//            table.setChest(chest.isChecked());
//            table.setHands(hands.isChecked());
//            table.setAbdomen(abs.isChecked());
//            table.setQuadriceps(quadriceps.isChecked());
//            table.setTrapez(trapesious.isChecked());
//            table.setBack(back.isChecked());
//            table.setLowerBack(lowerBack.isChecked());
//            table.setHamstrings(hamstrings.isChecked());
//            table.setGlutes(glutes.isChecked());
//            table.setExtra(extra.isChecked());
//            table.setTriceps(triceps.isChecked());
//            table.setShoulder(shoulder.isChecked());
//
//            //  whichElectrodes=0;
//            Log.d(TAG, "saveTraining: called");
//            }
//        }
//
//        public void saveStatistics() {
//
//            try {
//                int pos;
//                pos = getAdapterPosition();
//                if (pos ==-1){
//                    pos = getOldPosition();
//                }
////                calculateSaveDuration();
//                saveDuration = duration - timeRemaining;
////                    if (pos != -1 && TimeUnit.MILLISECONDS.toMinutes(saveDuration) >0) {
//                    if ( TimeUnit.MILLISECONDS.toMinutes(saveDuration) >0) {
//                        Statistics statistics = new Statistics();
//                        Date c = Calendar.getInstance().getTime();
//                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                        String formattedDate = df.format(c);
//                        statistics.setDate(formattedDate);
//                        statistics.setDuration(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(saveDuration)));
////                        statistics.setName(clientsList.get(pos).getClientName());
//                        statistics.setName(holdersClient.getClientName());
//                        statistics.setTrainerName(trainer.getTrainerName() + " ,total time= " + TimeUnit.MILLISECONDS.toMinutes(((App) context.get().getApplicationContext()).getTimer()));
//                        statistics.setTrainingName(trainingName);
//
////                        userViewModel.saveStatistics(statistics);
//                        Log.d(TAG, "saveStatistics: saving statistics + pos= " +pos);
//                        Log.d(TAG, "saveStatistics: saveDuration 1 = " + saveDuration);
//                    }
//                Log.d(TAG, "saveStatistics: saveDuration ==== " + saveDuration + " client name "+ holdersClient.getClientName() +
//                        " duration= " +duration + " timeremaining= " + TimeUnit.MILLISECONDS.toMinutes(timeRemaining));
//                Log.d(TAG, "saveStatistics: saveDuration 222 = " + saveDuration);
//
//            }catch(Exception e){
//
//                    e.printStackTrace();
////                clientsList.remove(getAdapterPosition());
//                }
//            }
//
//        private void saveTrainingInMemory(int ClientID) {
//
//            UserTrainingSettingsTable table = new UserTrainingSettingsTable();
//            table.setClientId(ClientID);
//            table.setTrainingName(trainingName);
//            table.setTrainingFrequency(((App) context.get().getApplicationContext()).getGetUsersTrainingFrequencyMap().get(ClientID));
//            table.setTrainingSetup(((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().get(ClientID));
//            int[] trainingParameters = ((App) context.get().getApplicationContext()).getGetUsersSetupMap().get(ClientID);
//            if (trainingParameters != null) {
//                trainingParameters[3] = whichElectrodes;
//                Long l = duration;
//                trainingParameters[0]=l.intValue();
//                trainingParameters[1]=timeOfStimulation_ms;
//                trainingParameters[2]=restTime_ms;
//
//
//            }
//
//            ((App) context.get().getApplicationContext()).getGetUsersSetupMap().put(ClientID, trainingParameters);
//            table.setClientTrainingEnviroment(((App) context.get().getApplicationContext()).getGetUsersSetupMap().get(ClientID));
//            table.setChest(chest.isChecked());
//            table.setHands(hands.isChecked());
//            table.setAbdomen(abs.isChecked());
//            table.setQuadriceps(quadriceps.isChecked());
//            table.setTrapez(trapesious.isChecked());
//            table.setBack(back.isChecked());
//            table.setLowerBack(lowerBack.isChecked());
//            table.setHamstrings(hamstrings.isChecked());
//            table.setGlutes(glutes.isChecked());
//            table.setExtra(extra.isChecked());
//            table.setTriceps(triceps.isChecked());
//            table.setShoulder(shoulder.isChecked());
//
//            table.setChestLevel(sb_chest.getProgress());
//            table.setHandsLevel(sb_hands.getProgress());
//            table.setAbdomenLevel(sb_abs.getProgress());
//            table.setQuadricepsLevel(sb_quadriceps.getProgress());
//            table.setTrapezLevel(sb_trapesious.getProgress());
//            table.setBackLevel(sb_back.getProgress());
//            table.setLowerBackLevel(sb_lowerBack.getProgress());
//            table.setHamstringsLevel(sb_hamstrings.getProgress());
//            table.setGlutesLevel(sb_glutes.getProgress());
//            table.setExtraLevel(sb_extra.getProgress());
//            table.setTricepsLevel(sb_triceps.getProgress());
//            table.setShoulderLevel(sb_shoulder.getProgress());
//            table.setIncreaseAllLevelsLevel(sb_increaseAllLevels.getProgress());
//
//           // todo() userViewModel.addUserTrainingSettingsTable(table);
//
//
//            Toast.makeText(context.get().getApplicationContext(), R.string.Training_Saved, Toast.LENGTH_SHORT).show();
//
//        }
////    int count=1;
//        //  private  ScheduledFuture<?> future;
//        private void loadTraining(int clientID) {
//            if (fixedRateTimer != null && !fixedRateTimer.isShutdown()) {
//                fixedRateTimer.shutdownNow();
//            }
//            final UserTrainingSettingsTable table = new UserTrainingSettingsTable(); //todo shouldnt be here, just for now
////                    userViewModel.loadTrainingSettingsByID(clientID);
//            if (table != null) {
//                trainingName = table.getTrainingName();
//                currentTrainingName.setText(table.getTrainingName());
//                ((App) context.get().getApplicationContext()).getGetUsersTrainingFrequencyMap().put(table.getClientId(), table.getTrainingFrequency());
//                ((App) context.get().getApplicationContext()).getGetUsersTrainingSetupMap().put(table.getClientId(), table.getTrainingSetup());
//                ((App) context.get().getApplicationContext()).getGetUsersSetupMap().put(table.getClientId(), table.getTrainingEnviroment());
//
//                timeOfStimulation_ms=table.getTrainingEnviroment()[1];
//                restTime_ms=table.getTrainingEnviroment()[2];
//                duration=table.getTrainingEnviroment()[0];
//                timerLimit_minutes.setText("");
//
//               whichElectrodes = table.getTrainingEnviroment()[3];
//
//                impulsePauseDisplay.setText(String.valueOf(restTime_ms));
//                impulseDisplay.setText(String.valueOf(timeOfStimulation_ms));
//                currentTrainingName.setText(trainingName);
//                countDownTimer.onTick(duration);
//                timerLimit_minutes.setText(String.valueOf(duration/=60000));
//                duration*=60000;
//
//                Log.d(TAG, "loadTraining: timerlimit = " +timerLimit_minutes.getText());
//                Log.d(TAG, "loadTraining: timerlimit duration = " +duration);
//
//
//                chest.setChecked(table.isChest());
//                hands.setChecked(table.isHands());
//                abs.setChecked(table.isAbdomen());
//                quadriceps.setChecked(table.isQuadriceps());
//                trapesious.setChecked(table.isTrapez());
//                back.setChecked(table.isBack());
//                lowerBack.setChecked(table.isLowerBack());
//                hamstrings.setChecked(table.isHamstrings());
//                glutes.setChecked(table.isGlutes());
//                extra.setChecked(table.isExtra());
//                triceps.setChecked(table.isTriceps());
//                shoulder.setChecked(table.isShoulder());
//
//                sb_increaseAllLevels.setProgress(table.getIncreaseAllLevelsLevel());
//
//
//                ArrayList<Integer> levels = new ArrayList<>();
//                levels.add(table.getChestLevel());
//                levels.add(table.getAbdomenLevel());
//                levels.add(table.getHandsLevel());
//                levels.add(table.getQuadricepsLevel());
//                levels.add(table.getTrapezLevel());
//                levels.add(table.getBackLevel());
//                levels.add(table.getLowerBackLevel());
//                levels.add(table.getHamstringsLevel());
//                levels.add(table.getGlutesLevel());
//                levels.add(table.getExtraLevel());
//                levels.add(table.getTricepsLevel());
//                levels.add(table.getShoulderLevel());
//
//
//                Collections.sort(levels);
//                final int highestLevel = levels.get(11);
//                Log.d(TAG, "loadTraining: levels = " +levels);
//
//
//               //         communication.message(electrode.setLevel(ELECTRODE_1,5),
////                                ((App)context.getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//
//                fixedRateTimer = Executors.newScheduledThreadPool(1);
////                future = fixedRateTimer.scheduleAtFixedRate(new Runnable() {
//                fixedRateTimer.scheduleAtFixedRate(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        restoreStates(table.getChestLevel(),  5, sb_chest,highestLevel,ELECTRODE_1);
//                        restoreStates(table.getHandsLevel(), 5, sb_hands,highestLevel,ELECTRODE_9);
//                        restoreStates(table.getAbdomenLevel(), 5, sb_abs,highestLevel,ELECTRODE_8);
//                        restoreStates(table.getQuadricepsLevel(), 5, sb_quadriceps,highestLevel,ELECTRODE_10);
//                        restoreStates(table.getTrapezLevel(), 5, sb_trapesious,highestLevel,ELECTRODE_2);
//                        restoreStates(table.getBackLevel(), 5, sb_back,highestLevel,ELECTRODE_5);
//                        restoreStates(table.getLowerBackLevel(), 5, sb_lowerBack,highestLevel,ELECTRODE_7);
//                        restoreStates(table.getHamstringsLevel(), 5, sb_hamstrings,highestLevel,ELECTRODE_3);
//                        restoreStates(table.getGlutesLevel(), 5, sb_glutes,highestLevel,ELECTRODE_4);
//                        restoreStates(table.getExtraLevel(), 5, sb_extra,highestLevel,ELECTRODE_6);
//                        restoreStates(table.getTricepsLevel(), 5, sb_triceps,highestLevel,ELECTRODE_12);
//                        restoreStates(table.getShoulderLevel(), 5, sb_shoulder,highestLevel,ELECTRODE_11);
//
//
//                    }
//                }, 0, 2, TimeUnit.SECONDS);
//
//
//            }
//        }
//
//        private void restoreStates(int level, int increment, SeekBar s1, int highestLevel, int el) {
////            if (level > s1.getProgress()) {
//            while (level > s1.getProgress()) {
//                if (isPaused){
//                    if (fixedRateTimer!=null && !fixedRateTimer.isShutdown())
//                        fixedRateTimer.shutdownNow();
//                    break;
//                }
//                s1.incrementProgressBy(increment);
//
////            if (getLayoutPosition()!=-1)
////                communication.message(electrode.setLevel(el,increment),
//                communication.message(electrode.setLevel(el,increment),
//                                ((App)context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                                ((App)context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//                increment+=5;
//                try {
//                    sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if ( s1.getProgress()>=level) {
//                    Log.d(TAG, "restoreStates: highestlevel= " +highestLevel);
//                    s1.setProgress(level);
////                    if (getLayoutPosition()!=-1)
////                        communication.message(electrode.setLevel(el,level),
//                        communication.message(electrode.setLevel(el,level),
//                            ((App)context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////                            ((App)context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getLayoutPosition()).getClientId()));
//
//                    Log.d(TAG, "run:loadTraining shutdown");
////                currentThread().interrupt();
//                    if (fixedRateTimer!=null && !fixedRateTimer.isShutdown()){
//                    fixedRateTimer.shutdownNow();
//                    Log.d(TAG, "run: isshut?= " + fixedRateTimer.isShutdown());
//                    }
//                    break;
//                }
//            }
//
//
//        }
//
//        private void stateCondition(boolean isChecked, int Id) {
////           if (isChecked) {
////               whichElectrodes += electrodeNumber;
////               ((App) Objects.requireNonNull(context).getApplicationContext()).setCheckbox(Id, true);
////
////           } else {
////               whichElectrodes -= electrodeNumber;
//            // ((App) Objects.requireNonNull(context).getApplicationContext()).setCheckbox(Id, isChecked);
//
////           }
//
//
//            Log.d(TAG, ": client id = " + clientsList.get(getLayoutPosition()).getClientId());
//
//        }
//
//
//
////        public void initUserInfo(int[] userInfo, int clientsId) {
////            Log.d(TAG, "initUserInfo: ");
////            if (userInfo != null) {
////                duration = Objects.requireNonNull(userInfo)[0];
////                Log.d(TAG, "initUserInfo: duration = " + duration);
////                // saveDuration = intent.getIntExtra(DURATION, -1);
////
////                timeOfStimulation_ms = Objects.requireNonNull(userInfo)[1];
////                restTime_ms = Objects.requireNonNull(userInfo)[2];
////                whichElectrodes = Objects.requireNonNull(userInfo)[3];
////                Log.d(TAG, "initUserInfo: whichElectrodes = " + whichElectrodes);
////                Log.d(TAG, "initUserInfo: timeOfStimulation_ms = " + timeOfStimulation_ms);
////                Log.d(TAG, "initUserInfo: restTime_ms = " + restTime_ms);
////                Log.d(TAG, "initUserInfo: duration = " + duration);
////
////                String str = "" + duration;
////
////
////                // byteCurrentTraining = intent.getByteArrayExtra(INTNENT_BYTE_ARRAY_ARDUINO_KEY);
////                trainingName = ((App) context.get().getApplicationContext()).getUserSubProgramChoice().get(clientsId);
////
////                impulsePauseDisplay.setText(String.valueOf(restTime_ms));
////                impulseDisplay.setText(String.valueOf(timeOfStimulation_ms));
////                currentTrainingName.setText(trainingName);
////                timerLimit_minutes.setText(str);
//////                button_currently_connected_device.setText("reconnect with device");
////
////                duration *= 60000;
////            }
////
////        }
//
//
//        private void checkboxState(boolean state, SeekBar seekBar, int electrodeNumber) {
//            if (!state) {
////                seekBar.setMax(100);
//                whichElectrodes -= electrodeNumber;
//                seekBar.setEnabled(false);
//            } else {
//
////                seekBar.setMax(100);
//                seekBar.setEnabled(true);
//                whichElectrodes += electrodeNumber;
//            }
//
//            Log.d(TAG, "checkboxState: whichelectrodes = " + whichElectrodes);
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            int action = event.getAction();
//
//            switch (action) {
//
//                case MotionEvent.ACTION_DOWN:
//                    // Disallow ScrollView to intercept touch events.
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//
//                    break;
//
//                case MotionEvent.ACTION_UP:
//                    // Allow ScrollView to intercept touch events.
//                    v.getParent().requestDisallowInterceptTouchEvent(false);
//
//                    break;
//            }
//
//            // Handle Seekbar touch events.
//            v.onTouchEvent(event);
//            return true;
//        }
//
//
////        public void saveAllCbStates(){
////
////            stateCondition(chest.isChecked(),R.id.checkBox_chest);
////            stateCondition(hands.isChecked(), R.id.checkBox_hands);
////            stateCondition(abs.isChecked(), R.id.checkBox_abs);
////            stateCondition(quadriceps.isChecked(), R.id.checkBox_quadriceps);
////            stateCondition(trapesious.isChecked(), R.id.checkBox_trapesious);
////            stateCondition(back.isChecked(), R.id.checkBox_back);
////            stateCondition(lowerBack.isChecked(), R.id.checkBox_lowerBack);
////            stateCondition(hamstrings.isChecked(), R.id.checkBox_hamstrings);
////            stateCondition(glutes.isChecked(), R.id.checkBox_Glutes);
////            stateCondition(extra.isChecked(), R.id.checkBox_extra);
////            stateCondition(shoulder.isChecked(), R.id.checkBox_shoulders);
////            stateCondition(triceps.isChecked(), R.id.checkBox_triceps);
////            ((App) Objects.requireNonNull(context).getApplicationContext())
////                    .saveClientsIDwithCheckedINfo(clientsList.get(getAdapterPosition()).getClientId());
////            Log.d(TAG, "saveAllCbStates: save all states called");
////        }
//
//        /*
//         * onProgressChanged is used for seekbars
//         */
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            setTextLevel(progress, seekBar.getId());
//            setProgress();
//            if (seekBar.getProgress() >= allowedProgress) {
//                seekBar.setProgress(allowedProgress);
//            }
//        }
//
//        private void setTextLevel(int progress, int id) {
//            switch (id) {
//                case R.id.seekBar_chest:
//                    srengthText_chest.setText(progress + "%");
//                    break;
//                case R.id.seekBar_hands:
//                    srengthText_hands.setText(progress + "%");
//                    break;
//                case R.id.seekBar_abs:
//                    srengthText_abs.setText(progress + "%");
//                    break;
//                case R.id.seekBar_quadriceps:
//                    srengthText_quadriceps.setText(progress + "%");
//                    break;
//                case R.id.seekBar_trapesius:
//                    srengthText_trapesius.setText(progress + "%");
//                    break;
//                case R.id.seekBar_back:
//                    srengthText_back.setText(progress + "%");
//                    break;
//                case R.id.seekBar_lowerback:
//                    srengthText_lowerback.setText(progress + "%");
//                    break;
//                case R.id.seekbar_hamstrings:
//                    srengthText_hamstrings.setText(progress + "%");
//                    break;
//                case R.id.seekBar_gluteus:
//                    srengthText_glutes.setText(progress + "%");
//                    break;
//                case R.id.seekBar_extra:
//                    srengthText_extra.setText(progress + "%");
//                    break;
//                case R.id.seekBar_Triceps:
//                    strengthText_triceps.setText(progress + "%");
//                    break;
//                case R.id.seekBar_Shoulder:
//                    strengthText_shoulder.setText(progress + "%");
//                    break;
//                case R.id.seekBar_increase_all_levels:
//
//                    strengthText_increaseAllLevels.setText(progress + "%");
//
//
//                    break;
//            }
//
//        }
//
//        /*
//         * onStartTrackingTouch is used for seekbars but we wont need this
//         */
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            if (seekBar.getProgress() >= allowedProgress) {
//                seekBar.setProgress(allowedProgress);
//            }
//            Log.d(TAG, "onStartTrackingTouch: ");
//        }
//
//
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            switch (buttonView.getId()) {
//                case R.id.cb_chest:
//                    //1-chest q
//                    checkboxState(isChecked, sb_chest, ELECTRODE_1);
//                    stateCondition(isChecked, R.id.checkBox_chest);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setChest(isChecked);
//
//                    break;
//                case R.id.cb_hands:
//                    //256-hams q,1,
//                    stateCondition(isChecked, R.id.checkBox_hands);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setHands(isChecked);
//                    checkboxState(isChecked, sb_hands, ELECTRODE_9);
//                    break;
//                case R.id.cb_abs:
//                    //128-glutes q
//                    stateCondition(isChecked, R.id.checkBox_abs);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setAbdomen(isChecked);
//                    checkboxState(isChecked, sb_abs, ELECTRODE_8);
//                    break;
//                case R.id.cb_quadriceps:
//                    //512-triceps
//                    stateCondition(isChecked, R.id.checkBox_quadriceps);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setQuadriceps(isChecked);
//                    checkboxState(isChecked, sb_quadriceps, ELECTRODE_10);
//                    break;
//                case R.id.cb_trapesius:
//                    //2-biceps q1,1
//                    checkboxState(isChecked, sb_trapesious, ELECTRODE_2);
//                    stateCondition(isChecked, R.id.checkBox_trapesious);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setTrapez(isChecked);
//
//                    break;
//                case R.id.cb_back:
//                    //16-trapesius
//                    checkboxState(isChecked, sb_back, ELECTRODE_5);
//                    stateCondition(isChecked, R.id.checkBox_back);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setBack(isChecked);
//                    break;
//                case R.id.cb_lowerBack:
//                    //64 q
//                    checkboxState(isChecked, sb_lowerBack, ELECTRODE_7);
//                    stateCondition(isChecked, R.id.checkBox_lowerBack);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setLowerBack(isChecked);
//                    break;
//                case R.id.cb_hamstrings:
//                    //4
//                    checkboxState(isChecked, sb_hamstrings, ELECTRODE_4);
//                    stateCondition(isChecked, R.id.checkBox_hamstrings);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setHamstrings(isChecked);
//                    break;
//                case R.id.cb_glutes:
//                    //8-abdomen qq
//                    checkboxState(isChecked, sb_glutes, ELECTRODE_3);
//                    stateCondition(isChecked, R.id.checkBox_Glutes);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setGlutes(isChecked);
//
//                    break;
//                case R.id.cb_extra:
//                    //32-back
//                    checkboxState(isChecked, sb_extra, ELECTRODE_6);
//                    stateCondition(isChecked, R.id.checkBox_extra);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setExtra(isChecked);
//                    break;
//                case R.id.cb_Shoulder:
//                    //10240 - shoulder q
//                    checkboxState(isChecked, sb_shoulder, ELECTRODE_11);
//                    stateCondition(isChecked, R.id.checkBox_shoulders);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setShoulder(isChecked);
//                    break;
//                case R.id.cb_Triceps:
//                    //2048-extra
//                    checkboxState(isChecked, sb_triceps, ELECTRODE_12);
//                    stateCondition(isChecked, R.id.checkBox_triceps);
//                    ((App) context.get().getApplicationContext()).getCheckboxTable().get(getLayoutPosition()).setTriceps(isChecked);
//                    break;
//
//
//
//            }
//
////            stateCondition(chest.isChecked(),R.id.checkBox_chest);
////            stateCondition(hands.isChecked(), R.id.checkBox_hands);
////            stateCondition(abs.isChecked(), R.id.checkBox_abs);
////            stateCondition(quadriceps.isChecked(), R.id.checkBox_quadriceps);
////            stateCondition(trapesious.isChecked(), R.id.checkBox_trapesious);
////            stateCondition(back.isChecked(), R.id.checkBox_back);
////            stateCondition(lowerBack.isChecked(), R.id.checkBox_lowerBack);
////            stateCondition(hamstrings.isChecked(), R.id.checkBox_hamstrings);
////            stateCondition(glutes.isChecked(), R.id.checkBox_Glutes);
////            stateCondition(extra.isChecked(), R.id.checkBox_extra);
////            stateCondition(shoulder.isChecked(), R.id.checkBox_shoulders);
////            stateCondition(triceps.isChecked(), R.id.checkBox_triceps);
////            ((App) Objects.requireNonNull(context).getApplicationContext())
////                    .saveClientsIDwithCheckedINfo(clientsList.get(getAdapterPosition()).getClientId());
//            Log.d(TAG, "onCheckedChanged: whichElectrode = " + whichElectrodes);
//
//        }
//
//        /*
//         * onStopTrackingTouch is used for seekbars. once user stop seekbar change we send signal to device what level to set for electrodes
//         */
//
//
//        public void seekbarStateMessage(int levelEl, int electrodeNumber) {
//            communication.message(electrode.setLevel(electrodeNumber, levelEl), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(holdersClient.getClientId()));
////            communication.message(electrode.setLevel(electrodeNumber, levelEl), ((App) context.get().getApplicationContext()).getGetUsersAddressMap().get(clientsList.get(getAdapterPosition()).getClientId()));
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            int level = seekBar.getProgress();
//            int id = seekBar.getId();
//
//            switch (id) {
//                case R.id.seekBar_chest:
//                    seekbarStateMessage(level, ELECTRODE_1);
//                    break;
//                case R.id.seekBar_hands:
//                    seekbarStateMessage(level, ELECTRODE_9);
//                    break;
//                case R.id.seekBar_abs:
//                    seekbarStateMessage(level, ELECTRODE_8);
//                    break;
//                case R.id.seekBar_quadriceps:
//                    seekbarStateMessage(level, ELECTRODE_10);
//                    break;
//                case R.id.seekBar_trapesius:
//                    seekbarStateMessage(level, ELECTRODE_2);
//                    break;
//                case R.id.seekBar_back:
//                    seekbarStateMessage(level, ELECTRODE_5);
//                    break;
//                case R.id.seekBar_lowerback:
//                    seekbarStateMessage(level, ELECTRODE_7);
//                    break;
//                case R.id.seekBar_gluteus:
//                    seekbarStateMessage(level,ELECTRODE_3 );
//                    break;
//                case R.id.seekbar_hamstrings:
//                    seekbarStateMessage(level,ELECTRODE_4 );
//                    break;
//                case R.id.seekBar_extra:
//                    seekbarStateMessage(level, ELECTRODE_6);
//                    break;
//                case R.id.seekBar_Shoulder:
//                    seekbarStateMessage(level, ELECTRODE_11);
//                    break;
//                case R.id.seekBar_Triceps:
//                    seekbarStateMessage(level, ELECTRODE_12);
//                    break;
//                case R.id.seekBar_increase_all_levels:
//                    setProgress();
//                    break;
//
//            }
//
//            Log.d(TAG, "onStopTrackingTouch: message progress = " + level);
//
//        }
//
//        private void setAllProgressTo(int lvl){
////            if (sb_chest.getVisibility() == View.VISIBLE)
//                sb_chest.setProgress(lvl);
//            sb_hands.setProgress(lvl);
//            sb_abs.setProgress(lvl);
//            sb_extra.setProgress(lvl);
//            sb_quadriceps.setProgress(lvl);
//            sb_trapesious.setProgress(lvl);
//            sb_back.setProgress(lvl);
//            sb_lowerBack.setProgress(lvl);
//            sb_glutes.setProgress(lvl);
//            sb_hamstrings.setProgress(lvl);
//            sb_shoulder.setProgress(lvl);
//            sb_triceps.setProgress(lvl);
//        }
//
//
//        private void setProgress() {
//            allowedProgress = sb_increaseAllLevels.getProgress();
////    if (chest.isChecked()) sb_chest.setMax(sb_increaseAllLevels.getProgress());
////    sb_chest.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (hands.isChecked()) sb_hands.setMax(sb_increaseAllLevels.getProgress());
////    sb_hands.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (abs.isChecked()) sb_abs.setMax(sb_increaseAllLevels.getProgress());
////    sb_abs.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (quadriceps.isChecked()) sb_quadriceps.setMax(sb_increaseAllLevels.getProgress());
////    sb_quadriceps.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (trapesious.isChecked()) sb_trapesious.setMax(sb_increaseAllLevels.getProgress());
////    sb_trapesious.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (back.isChecked()) sb_back.setMax(sb_increaseAllLevels.getProgress());
////    sb_back.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (lowerBack.isChecked()) sb_lowerBack.setMax(sb_increaseAllLevels.getProgress());
////    sb_lowerBack.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (glutes.isChecked()) sb_glutes.setMax(sb_increaseAllLevels.getProgress());
////    sb_glutes.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (hamstrings.isChecked()) sb_hamstrings.setMax(sb_increaseAllLevels.getProgress());
////    sb_hamstrings.setMax(sb_increaseAllLevels.getProgress());
////
//////                    if (extra.isChecked()) sb_extra.setMax(sb_increaseAllLevels.getProgress());
////    sb_extra.setMax(sb_increaseAllLevels.getProgress());
//////                    if (shoulder.isChecked()) sb_shoulder.setMax(sb_increaseAllLevels.getProgress());
////    sb_shoulder.setMax(sb_increaseAllLevels.getProgress());
//////                    if (triceps.isChecked()) sb_triceps.setMax(sb_increaseAllLevels.getProgress());
////    sb_triceps.setMax(sb_increaseAllLevels.getProgress());
//            //  seekbarStateMessage(sb_increaseAllLevels.getProgress(),whichElectrodes);
//        }
//
//        private void initializeWidgets(View view) {
//
//            userProfilePhoto = view.findViewById(R.id.imageView_display_user);
//            usersName = view.findViewById(R.id.textview_user_Image_name);
//            imageView_remove = view.findViewById(R.id.imageview_removeClient);
//            imageView_remove.setOnClickListener(buttonsListener);
////            checkBox_remove.setVisibility(View.GONE);
//
//
////
//            saveTraining = view.findViewById(R.id.button_saveTraining);
//            saveTraining.setOnClickListener(buttonsListener);
//            lodTraining = view.findViewById(R.id.button_loadTraining);
//            lodTraining.setEnabled(false);
//            lodTraining.setOnClickListener(buttonsListener);
//            currentTrainingName = view.findViewById(R.id.tv_currentTrainingName);
//            gifImageView = view.findViewById(R.id.imageView_gif);
//
//
//            //checkboxes
//            chest = view.findViewById(R.id.cb_chest);
//            chest.setOnCheckedChangeListener(this);
//
//
//            hands = view.findViewById(R.id.cb_hands);
//            hands.setOnCheckedChangeListener(this);
//
//            abs = view.findViewById(R.id.cb_abs);
//            abs.setOnCheckedChangeListener(this);
//
//            quadriceps = view.findViewById(R.id.cb_quadriceps);
//            quadriceps.setOnCheckedChangeListener(this);
//
//            trapesious = view.findViewById(R.id.cb_trapesius);
//            trapesious.setOnCheckedChangeListener(this);
//
//            back = view.findViewById(R.id.cb_back);
//            back.setOnCheckedChangeListener(this);
//
//            lowerBack = view.findViewById(R.id.cb_lowerBack);
//            lowerBack.setOnCheckedChangeListener(this);
//
//            glutes = view.findViewById(R.id.cb_glutes);
//            glutes.setOnCheckedChangeListener(this);
//
//            hamstrings = view.findViewById(R.id.cb_hamstrings);
//            hamstrings.setOnCheckedChangeListener(this);
//
//            extra = view.findViewById(R.id.cb_extra);
//            extra.setOnCheckedChangeListener(this);
//
//            shoulder = view.findViewById(R.id.cb_Shoulder);
//            shoulder.setOnCheckedChangeListener(this);
//
//            triceps = view.findViewById(R.id.cb_Triceps);
//            triceps.setOnCheckedChangeListener(this);
//
//            //seekbars
//            sb_chest = view.findViewById(R.id.seekBar_chest);
//            sb_chest.setOnSeekBarChangeListener(this);
//            sb_chest.setMax(100);
//
//            sb_chest.setOnTouchListener(this);
//
//            sb_hands = view.findViewById(R.id.seekBar_hands);
//            sb_hands.setOnSeekBarChangeListener(this);
//            sb_hands.setMax(100);
//
//            sb_hands.setOnTouchListener(this);
//
//            sb_abs = view.findViewById(R.id.seekBar_abs);
//            sb_abs.setOnSeekBarChangeListener(this);
//            sb_abs.setMax(100);
//
//            sb_abs.setOnTouchListener(this);
//
//            sb_quadriceps = view.findViewById(R.id.seekBar_quadriceps);
//            sb_quadriceps.setOnSeekBarChangeListener(this);
//            sb_quadriceps.setMax(100);
//
//            sb_quadriceps.setOnTouchListener(this);
//
//            sb_trapesious = view.findViewById(R.id.seekBar_trapesius);
//            sb_trapesious.setOnSeekBarChangeListener(this);
//            sb_trapesious.setMax(100);
//
//            sb_trapesious.setOnTouchListener(this);
//
//
//            sb_triceps = view.findViewById(R.id.seekBar_Triceps);
//            sb_triceps.setOnSeekBarChangeListener(this);
//            sb_triceps.setMax(100);
//
//            sb_triceps.setOnTouchListener(this);
//
//            sb_shoulder = view.findViewById(R.id.seekBar_Shoulder);
//            sb_shoulder.setOnSeekBarChangeListener(this);
//            sb_shoulder.setMax(100);
//
//
//            sb_shoulder.setOnTouchListener(this);
//
//
//            sb_back = view.findViewById(R.id.seekBar_back);
//            sb_back.setOnSeekBarChangeListener(this);
//            sb_back.setMax(100);
//
//            sb_back.setOnTouchListener(this);
//
//            sb_lowerBack = view.findViewById(R.id.seekBar_lowerback);
//            sb_lowerBack.setOnSeekBarChangeListener(this);
//            sb_lowerBack.setMax(100);
//
//            sb_lowerBack.setOnTouchListener(this);
//
//            sb_glutes = view.findViewById(R.id.seekBar_gluteus);
//            sb_glutes.setOnSeekBarChangeListener(this);
//            sb_glutes.setMax(100);
//
//            sb_glutes.setOnTouchListener(this);
//
//            sb_hamstrings = view.findViewById(R.id.seekbar_hamstrings);
//            sb_hamstrings.setOnSeekBarChangeListener(this);
//            sb_hamstrings.setMax(100);
//
//            sb_hamstrings.setOnTouchListener(this);
//
//            sb_extra = view.findViewById(R.id.seekBar_extra);
//            sb_extra.setOnSeekBarChangeListener(this);
//            sb_extra.setMax(100);
//
//            sb_extra.setOnTouchListener(this);
//
//            sb_increaseAllLevels = view.findViewById(R.id.seekBar_increase_all_levels);
//            sb_increaseAllLevels.setEnabled(true);
//            sb_increaseAllLevels.setMax(100);
//            sb_increaseAllLevels.setOnSeekBarChangeListener(this);
//            sb_increaseAllLevels.setOnTouchListener(this);
//
//            // buttons
//            start = view.findViewById(R.id.startTraining_btn_Start);
//
//            stop = view.findViewById(R.id.startTraining_btn_Stop);
//            timerLimit_minutes = view.findViewById(R.id.startTraining_EditText_timeLimit);
//            timerlimit_seconds = view.findViewById(R.id.startTraining_EditText_timeLimit_seconds);
//            timerCounter = view.findViewById(R.id.startTraining_btn_TimeCounter);
//            impulseDisplay = view.findViewById(R.id.tv_impulseStrength);
//            impulsePauseDisplay = view.findViewById(R.id.tv_impulsePausa);
//            button_currently_connected_device = view.findViewById(R.id.button_currently_connected_device);
//            tv_showDeviceName = view.findViewById(R.id.tv_showDeviceName);
//            incAllImpulses = view.findViewById(R.id.imageButton_incAllImpulses);
//            incAllImpulses.setOnClickListener(buttonsListener);
//            decAllImpulses = view.findViewById(R.id.imageButton_decAllImpulses);
//            decAllImpulses.setOnClickListener(buttonsListener);
//
//            start.setOnClickListener(buttonsListener);
//            stop.setOnClickListener(buttonsListener);
//            button_currently_connected_device.setOnClickListener(buttonsListener);
//
//            timerCounter.setEnabled(false);
//
//            srengthText_chest = view.findViewById(R.id.strengthtext_chest);
//            srengthText_hands = view.findViewById(R.id.strengthtext_hands);
//            srengthText_abs = view.findViewById(R.id.strengthtext_abs);
//            srengthText_quadriceps = view.findViewById(R.id.strengthtext_quadriceps);
//            srengthText_trapesius = view.findViewById(R.id.strengthtext_trapesius);
//            srengthText_back = view.findViewById(R.id.strengthtext_back);
//            srengthText_lowerback = view.findViewById(R.id.strengthtext_lowerBack);
//            srengthText_glutes = view.findViewById(R.id.strengthtext_glutes);
//            srengthText_hamstrings = view.findViewById(R.id.strengthtext_hamstrings);
//            srengthText_extra = view.findViewById(R.id.strengthtext_extra);
//            strengthText_increaseAllLevels = view.findViewById(R.id.strengthText_increaseAllLevels);
//            strengthText_triceps = view.findViewById(R.id.strengthtext_triceps);
//            strengthText_shoulder = view.findViewById(R.id.strengthtext_shoulder);
//
//            textChest = view.findViewById(R.id.textViewNameCHest);
//            textHands = view.findViewById(R.id.textView_nameHands);
//            textAbs = view.findViewById(R.id.textView_nameAbs);
//            textQuads = view.findViewById(R.id.textView_nameQuads);
//            textTraps = view.findViewById(R.id.textView_nameTrapez);
//            textSpina = view.findViewById(R.id.cb_back);
//            textLowerBack = view.findViewById(R.id.textView_nameLowerBack);
//            textHams = view.findViewById(R.id.textView_nameHams);
//            textGlutes = view.findViewById(R.id.textView_nameGlutes);
//            textExtra = view.findViewById(R.id.textView_nameExtra);
//            textShoulder = view.findViewById(R.id.textView_nameShoulder);
//            textTriceps = view.findViewById(R.id.textView_nameTriceps);
//
//            imageButton_personal_deviceImg = view.findViewById(R.id.imageButton_personal_deviceImg);
//            imageButton_personal_deviceImg.setTag("genesis_black_device");
//            imageButton_personal_deviceImg.setOnClickListener(buttonsListener);
//            imageButton_GroupTrainningImage = view.findViewById(R.id.imageButton_GroupTrainningImage);
//            imageButton_GroupTrainningImage.setTag("genesis_small_picture_suit_upper_part");
//            imageButton_GroupTrainningImage.setOnClickListener(buttonsListener);
//
//
//            setTextForPersonalSuit();
//
//            Log.d(TAG, "initializeWidgets: ");
//
//        }
//
//        private void enableDisableAllSeekbars(boolean enable) {
////            sb_chest.setEnabled(enable);
////
////            sb_hands.setEnabled(enable);
////
////            sb_abs.setEnabled(enable);
////
////            sb_quadriceps.setEnabled(enable);
////
////            sb_trapesious.setEnabled(enable);
////
////            sb_back.setEnabled(enable);
////
////            sb_lowerBack.setEnabled(enable);
////
////            sb_glutes.setEnabled(enable);
////
////            sb_hamstrings.setEnabled(enable);
////
////            sb_extra.setEnabled(enable);
////
////            sb_triceps.setEnabled(enable);
////
////            sb_shoulder.setEnabled(enable);
//
//        }
//
//
//
//    }
//
//
//
//    public void initialiseStates(int position, Group_Training_ViewHolder holder) {
//
//
//        if (position!=-1) {
//            holder. sb_chest.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isChest());
//            holder.   sb_chest.setMax(100);
//            holder. sb_hands.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isHands());
//            holder.  sb_hands.setMax(100);
//            holder.  sb_abs.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isAbdomen());
//            holder.  sb_abs.setMax(100);
//            holder.  sb_quadriceps.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isQuadriceps());
//            holder.   sb_quadriceps.setMax(100);
//            holder.   sb_trapesious.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isTrapez());
//            holder.  sb_trapesious.setMax(100);
//            holder.   sb_back.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isBack());
//            holder.   sb_back.setMax(100);
//            holder.   sb_lowerBack.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isLowerBack());
//            holder.    sb_lowerBack.setMax(100);
//            holder.  sb_glutes.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isGlutes());
//            holder.    sb_glutes.setMax(100);
//            holder.    sb_hamstrings.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isHamstrings());
//            holder.   sb_hamstrings.setMax(100);
//            holder.  sb_extra.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isExtra());
//            holder.   sb_extra.setMax(100);
//            holder.  sb_triceps.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isTriceps());
//            holder.  sb_triceps.setMax(100);
//            holder.  sb_shoulder.setEnabled(((App) context.get().getApplicationContext()).getCheckboxTable().get(position).isShoulder());
//            holder.   sb_shoulder.setMax(100);
//
//
//        }
//    }
//
//
//    public void initUserInfo(int[] userInfo, int clientsId,Group_Training_ViewHolder holder) {
//        Log.d(TAG, "initUserInfo: ");
//        if (userInfo != null) {
//            holder.duration = Objects.requireNonNull(userInfo)[0];
//            Log.d(TAG, "initUserInfo: duration = " + holder.duration);
//            // saveDuration = intent.getIntExtra(DURATION, -1);
//
//            holder. timeOfStimulation_ms = Objects.requireNonNull(userInfo)[1];
//            holder. restTime_ms = Objects.requireNonNull(userInfo)[2];
//            holder. whichElectrodes = Objects.requireNonNull(userInfo)[3];
//            Log.d(TAG, "initUserInfo: whichElectrodes = " + holder.whichElectrodes);
//            Log.d(TAG, "initUserInfo: timeOfStimulation_ms = " +holder. timeOfStimulation_ms);
//            Log.d(TAG, "initUserInfo: restTime_ms = " +holder. restTime_ms);
//            Log.d(TAG, "initUserInfo: duration = " + holder.duration);
//
//            String str = "" + holder.duration;
//
//
//            // byteCurrentTraining = intent.getByteArrayExtra(INTNENT_BYTE_ARRAY_ARDUINO_KEY);
//            holder. trainingName = ((App) context.get().getApplicationContext()).getUserSubProgramChoice().get(clientsId);
//
//            holder. impulsePauseDisplay.setText(String.valueOf(holder.restTime_ms));
//            holder. impulseDisplay.setText(String.valueOf(holder.timeOfStimulation_ms));
//            holder. currentTrainingName.setText(holder.trainingName);
//            holder. timerLimit_minutes.setText(str);
////                button_currently_connected_device.setText("reconnect with device");
//
//            holder. duration *= 60000;
//        }
//
//    }
//
//
//
//
//    @Override
//    public void onViewDetachedFromWindow(@NonNull Group_Training_ViewHolder holder) {
//        Log.d(TAG, "onViewDetachedFromWindow: deataching getLayoutPosition " + holder.getLayoutPosition());
//        Log.d(TAG, "onViewDetachedFromWindow: deataching getAdapterPosition " + holder.start.getText() +" " + holder.trainingName);
//
//        if(holder.fragment !=null && holder.fragment.isAdded()){
//            holder.fragment.onDestroyView();
//            holder.fragment =null;
//        }
//        super.onViewDetachedFromWindow(holder);
//    }
//
//    @Override
//    public void onViewRecycled(@NonNull Group_Training_ViewHolder holder) {
//
//        Log.d(TAG, "onViewDetachedFromWindow: recycled =======");
////        holder.countDownTimer=null;
////        holder.fixedRateTimer=null;
////        holder.fragment=null;
////        holder.holdersClient=null;
////
//
//        super.onViewRecycled(holder);
//    }
//
//    @Override
//    public void onViewAttachedToWindow(@NonNull Group_Training_ViewHolder holder) {
//        Log.d(TAG, "onViewDetachedFromWindow: onViewAttachedToWindow " + holder.start.getText() +" " + holder.trainingName);
//
//        super.onViewAttachedToWindow(holder);
//    }
//}
