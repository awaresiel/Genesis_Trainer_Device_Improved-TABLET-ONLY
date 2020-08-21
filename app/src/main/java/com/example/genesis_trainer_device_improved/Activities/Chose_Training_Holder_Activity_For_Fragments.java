package com.example.genesis_trainer_device_improved.Activities;


import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
import com.example.genesis_trainer_device_improved.Fragments.ITrainingFragment;
import com.example.genesis_trainer_device_improved.Fragments.TrainingFragment;
import com.example.genesis_trainer_device_improved.Fragments.adapters.DeviceDisplayContent;
import com.example.genesis_trainer_device_improved.Fragments.ISuit_Muscle_Selection_fragment;
import com.example.genesis_trainer_device_improved.Fragments.Suit_Muscle_Selection_fragment;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.Services.BluetoothService;
import com.example.genesis_trainer_device_improved.Services.IBluetoothRxService;
import com.example.genesis_trainer_device_improved.Services.IBluetoothStates;
import com.example.genesis_trainer_device_improved.Services.IBluetoothTraining;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.MyProgressDialog;
import com.example.genesis_trainer_device_improved.ViewModel.bluetooth_devices_model.BluetoothDevicesViewModel;
import com.example.genesis_trainer_device_improved.ViewModel.clients_model.ClientsVIewModel;
import com.example.genesis_trainer_device_improved.ViewModel.recyclers.Clients_Side_Recycler;
import com.example.genesis_trainer_device_improved.ViewModel.trainer_model.TrainerViewModel;
import com.example.genesis_trainer_device_improved.ViewModel.training_settings_model.TrainingSettingsViewModel;
import com.example.genesis_trainer_device_improved.databinding.ChoseTrainingHolderActivityForFragmentsBinding;
import com.example.genesis_trainer_device_improved.Fragments.Dialog_Add_Client;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.genesis_trainer_device_improved.helpers.Constants.CHOOSEN_TRAINER_ID;
import static com.example.genesis_trainer_device_improved.helpers.Constants.TABLE_CLIENT_IDS;


public class Chose_Training_Holder_Activity_For_Fragments extends BaseActivityWithSwipeDismiss implements Clients_Side_Recycler.OnClientClicks,
        IBluetoothStates, IBluetoothTraining {

    private static final String TAG = "Chose_Training_Holder_A";

    private Suit_Muscle_Selection_fragment trainingSetupFragment;
    private ISuit_Muscle_Selection_fragment fragmentListener;
    private ITrainingFragment fragmentTrainingListener;
    private TrainingFragment trainingFragment;
    //    TODO delete selected trainer
    private Trainer selectedTrainer;
    private Client selectedClient;
    private List<Client> allClients;
    private Clients_Side_Recycler ClientsRecyclerView;
    private ClientsVIewModel clientsVIewModel;
    private TrainerViewModel trainerViewModel;
    private TrainingSettingsViewModel trainingSettingsViewModel;
    private BluetoothDevicesViewModel bluetoothDevicesViewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private IBluetoothRxService mService;
    private boolean isSettingUpTrainingDone;
    private boolean bound;
    private ArrayList<Integer> idsOfSelectedUsers;
    private int requestCode = 0;
    private Toolbar toolbar;
    private ChoseTrainingHolderActivityForFragmentsBinding layoutBinding;
    private Dialog_Add_Client dialog_add_client;
    private MyProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ChoseTrainingHolderActivityForFragmentsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        setListeners();
        bound = false;
        isSettingUpTrainingDone = false;
        allClients = new ArrayList<>();
        startBoundService();
        idsOfSelectedUsers = new ArrayList<>();
        Intent intent = getIntent();
        if (intent.hasExtra(TABLE_CLIENT_IDS)) {
            idsOfSelectedUsers = intent.getIntegerArrayListExtra(TABLE_CLIENT_IDS);
            initRecyclerForClient();
            loadAllClients();
            initTrainingTable();
            int trainerID = getIntent().getIntExtra(CHOOSEN_TRAINER_ID, -1);
            loadTrainer(trainerID);
        }
        setUpMenuDrawer();
    }

    private void setListeners() {
        //TODO find all views and set click listeners
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationViewMenu);
        layoutBinding.buttonAddNewClient.setOnClickListener(listener);
        layoutBinding.buttonSwitchDevice.setOnClickListener(listener);
        layoutBinding.buttonSwitchSuit.setOnClickListener(listener);
        layoutBinding.buttonSaveTraining.setOnClickListener(listener);
        layoutBinding.buttonLoadTraining.setOnClickListener(listener);
        layoutBinding.buttonSwitchDevice.setTag("genesis_black_device");
        layoutBinding.buttonSwitchSuit.setTag("genesis_small_picture_suit_upper_part");

    }

    private void switchSuits() {
        if (Objects.equals(layoutBinding.buttonSwitchSuit.getTag(), "genesis_small_picture_suit_upper_part")) {
            if (trainingFragment != null) fragmentTrainingListener.setTextForGroupTrainingSuits();
            layoutBinding.buttonSwitchSuit.setTag("genesis_small_picture_suit");
        } else {
            fragmentTrainingListener.setTextForPersonalSuit();
            layoutBinding.buttonSwitchSuit.setTag("genesis_small_picture_suit_upper_part");
        }
    }

    private void startBoundService() {
        bindService(new Intent(this, BluetoothService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    /*
        TODO refactor names of client recycler adapter
     */


    private void launchAddClientWindow() {
        if (idsOfSelectedUsers.size() == 7) {
            createToast("Only 7 Clients are able to be added at the same time, please delete some clients");
            return;
        } else {
            if (dialog_add_client != null) dialog_add_client = null;
            dialog_add_client = new Dialog_Add_Client();
            dialog_add_client.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullHeightDialog);
            dialog_add_client.show(getSupportFragmentManager(), Dialog_Add_Client.class.getSimpleName());
        }

    }

    DebouncedClickListener listener = new DebouncedClickListener(1000) {
        @Override
        public void onDebouncedClick(View v) {

            switch (v.getId()) {
                case R.id.button_addNewClient:
                    if (fragmentListener == null) return;
                    Log.d(TAG, "onDebouncedClick: button_addNewClient clicked");
                    launchAddClientWindow();
                    break;
                case R.id.button_switchDevice:
                    if (fragmentTrainingListener == null) return;
//                              TODO swith between devices, white one and black one
                    break;
                case R.id.button_switchSuit:
                    if (fragmentTrainingListener == null) return;
                    switchSuits();
//                              TODO switch between Suits, one have shoulder etc (12 el) other (10el)
                    break;
                case R.id.button_saveTraining:
                    Log.d(TAG, "onDebouncedClick: button_saveTraining");
//                    if (fragmentTrainingListener == null) return;
                    if (fragmentTrainingListener == null) return;
                    Log.d(TAG, "onDebouncedClick:fragmentTrainingListener != null");
                    saveCurrentTraining();
//                              TODO save training and users ID, replace existing
                    break;
                case R.id.button_loadTraining:
                    Log.d(TAG, "onDebouncedClick:button_loadTraining ");
//                    if (fragmentTrainingListener == null) return;
                    if (fragmentTrainingListener == null) return;
                    Log.d(TAG, "onDebouncedClick: fragmentTrainingListener!=null");
                    fragmentTrainingListener.loadTraining();
//                              TODO load training under users id
                    break;

            }
        }
    };


    /*
      When saving training for later we put * 1000 to clients id to identify save table for later, if dosent exist it means training was never saved for leter
      otherwise we save trainings just with normal clients id and replace on each other save
     */
    private void saveCurrentTraining() {
        UserTrainingSettingsTable table = fragmentTrainingListener.getCurrentTraining();
        Log.d(TAG, "saveCurrentTraining: before  if (table != null) ");
        if (table != null) {
            Log.d(TAG, "saveCurrentTraining: after if (table != null)");
            int saveId = table.getClientId() * 1000;
            table.setClientId(saveId);
            addDisposable(getTrainingSettingsModel().addUserTrainingSettingsTable(table)
                    .subscribe(s -> {Log.d(TAG, "saveCurrentTraining: "+ table.getClientId());
                        table.setClientId(saveId/1000);
                    }, e -> showStackTraceError(e, "saveCurrentTraining")));
        }
    }

    public void showStackTraceError(Throwable er, String caller) {
        Log.d(TAG, "showStackTraceError: error= " + caller + "  " + er.getMessage());
        Log.d(TAG, "showStackTraceError: error= " + caller + "  " + er.getCause());
        for (StackTraceElement e : er.getStackTrace()) {

            Log.d(TAG, "showStackTraceError: error trace getMethodName -> " + caller + "  " + e.getMethodName());
            Log.d(TAG, "showStackTraceError: error trace getClassName -> " + caller + "  " + e.getClassName());
            Log.d(TAG, "showStackTraceError: error trace getLineNumber -> " + caller + "  " + e.getLineNumber());


        }
    }


    public void messageDevice(byte[] message, String btAddress) {
         if (bound) mService.writeMessage(message, btAddress);
    }

    public void notifyRecyclerReset() {
        ClientsRecyclerView.notifyDataSetChanged();
    }


    private void initTrainingTable() {
        trainingSettingsViewModel = new ViewModelProvider(this).get(TrainingSettingsViewModel.class);
    }

    private void loadTrainer(int id) {
        trainerViewModel = getTrainerModel();
        if (id != -1) {
            addDisposable(trainerViewModel.loadTrainerById(id).subscribe(trainer -> {
                trainerViewModel.setTrainer(trainer);
                selectedTrainer = trainer;
            }));
        }
    }

    public void loadAllClients() {
        clientsVIewModel = getClientModel();
        int[] ids =getArrayIdsOfSelectedUsers(idsOfSelectedUsers);
        if (ids==null) return;
        if (clientsVIewModel.getClients().getValue() == null)
           addDisposable(  clientsVIewModel.loadMultipleClientsById(ids).subscribe(clientList -> clientsVIewModel.addClients(clientList)) );

        observeClients();
    }

    private void observeClients() {
        clientsVIewModel.getClients().observe(this, clients -> {
            if (clients==null)return;
            if (clients.isEmpty()) finish();
            allClients = clients;
            for (Client c : clients)
            Log.d(TAG, "observeClients:loadall " + c.getDeviceAddress() + " name-> "+ c.getClientName() + " id "+c.getClientId());
            ClientsRecyclerView.setClientList(clients);
        });
    }

    public void addClientToList(Client c) {
        allClients.remove(c);
        allClients.add(c);
        getClientModel().addClients(allClients);
    }

    public List<Client> getAllClientList() {
        return allClients;
    }

    public ArrayList<Integer> getIdsOfSelectedUsers() {
        return idsOfSelectedUsers;
    }

    private int[] getArrayIdsOfSelectedUsers(ArrayList<Integer> list){
        if (list==null || list.isEmpty()) return null;
        int[] ids = new int[list.size()];
        for (int i = 0; i < list.size(); i++) ids[i] = list.get(i);
        return ids;
    }
/*
  *  add new users to live data without deleting previous ones
 */
    public void setIdsOfSelectedUsers(ArrayList<Integer> list) {
        idsOfSelectedUsers = list;
        int[] ids =getArrayIdsOfSelectedUsers(list);
        if (ids==null) return;
      addDisposable(  getClientModel().loadMultipleClientsById(ids).subscribe(l-> {
          for (Client c: l){
              if (!allClients.contains(c)){
                  allClients.add(c);
              }
          }
          getClientModel().addClients(allClients);
      }));
    }

    

    public ClientsVIewModel getClientModel() {
        if (clientsVIewModel == null) {
            clientsVIewModel = new ViewModelProvider(this).get(ClientsVIewModel.class);
        }
        return clientsVIewModel;
    }

    public TrainerViewModel getTrainerModel() {
        if (trainerViewModel == null) {
            trainerViewModel = new ViewModelProvider(this).get(TrainerViewModel.class);
        }
        return trainerViewModel;
    }

    public BluetoothDevicesViewModel getDevicesModel() {
        if (bluetoothDevicesViewModel == null) {
            bluetoothDevicesViewModel = new ViewModelProvider(this).get(BluetoothDevicesViewModel.class);
        }
        return bluetoothDevicesViewModel;
    }

    public TrainingSettingsViewModel getTrainingSettingsModel() {
        if (trainingSettingsViewModel == null) {
            trainingSettingsViewModel = new ViewModelProvider(this).get(TrainingSettingsViewModel.class);
        }

        return trainingSettingsViewModel;
    }


    private void setUpMenuDrawer() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        deatachFragments();
        mService.cancelAllTimers();
        finish();
        super.onBackPressed();
    }


    @Override
    public void bluetoothOff(String message) {
        getDevicesModel().addBtDevicesList(showListWhenBtOff());
    }

    @Override
    public void bluetoothTurningOff(String message) {
        getDevicesModel().addBtDevicesList(showListWhenBtOff());
    }

    @Override
    public void bluetoothTurningOn(String message) {
        getDevicesModel().addBtDevicesList(showListWhenBtOff());
    }

    @Override
    public void bluetoothOn(List<BluetoothDevice> list) {
        getDevicesModel().addBtDevicesList(getDevicesList());
    }


    public List<DeviceDisplayContent> getDevicesList() {
        ArrayList<DeviceDisplayContent> names = new ArrayList<>();
        List<BluetoothDevice> list = mService.initBluetoothDevice();
        if (list == null) return showListWhenBtOff();
        for (BluetoothDevice d : list) names.add(new DeviceDisplayContent(false, d));

        return names;
    }

    public List<DeviceDisplayContent> showListWhenBtOff() {
        ArrayList<DeviceDisplayContent> names = new ArrayList<>();
        names.add(0, new DeviceDisplayContent(false, null));
        return names;
    }


    private void trainingSetupFragment() {
        if (trainingSetupFragment != null) trainingSetupFragment = null;
        saveTrainingOnSwitching();
        trainingSetupFragment = new Suit_Muscle_Selection_fragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, trainingSetupFragment)
//                .addToBackStack(null)
                .commit();
//        adjustToScreenSize(layoutBinding.fragmentsContainer, 0.5f, 0.5f);
    }

    private void trainingFragment() {
       saveTrainingOnSwitching();
          trainingFragment = new TrainingFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, trainingFragment)
//                .addToBackStack(null)
                .commit();

    }

    private void saveTrainingOnSwitching(){
        if (trainingFragment != null) {
            UserTrainingSettingsTable table = fragmentTrainingListener.getCurrentTraining();
            fragmentTrainingListener.saveTimerStates();
            if (table == null) return;
            Log.d(TAG, "trainingFragment:saveCurrentTraining id= "+table.getClientId());
            addDisposable(getTrainingSettingsModel().addUserTrainingSettingsTable(table)
                    .subscribe(s -> Log.d(TAG, "saveCurrentTraining: "), e -> showStackTraceError(e, "trainingFragment")));
            trainingFragment = null;
        }
    }

    public IBluetoothRxService getService(){
        return mService;
    }

    public void setTrainingListener(ITrainingFragment l){
        fragmentTrainingListener = l;
    }

    public void setTrainingSetupListener(ISuit_Muscle_Selection_fragment l){
        fragmentListener = l;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("initialized", ClientsRecyclerView.isInitialized());


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (ClientsRecyclerView != null) {
             ClientsRecyclerView.setInitialized(savedInstanceState.getBoolean("initialized"));
        }

    }


    public void setMenuButtonsVisibility(boolean visible) {
        if (visible) {
            layoutBinding.buttonLoadTraining.setVisibility(View.VISIBLE);
            layoutBinding.buttonSaveTraining.setVisibility(View.VISIBLE);
            layoutBinding.buttonSwitchDevice.setVisibility(View.VISIBLE);
            layoutBinding.buttonSwitchSuit.setVisibility(View.VISIBLE);
        } else {
            layoutBinding.buttonLoadTraining.setVisibility(View.GONE);
            layoutBinding.buttonSaveTraining.setVisibility(View.GONE);
            layoutBinding.buttonSwitchDevice.setVisibility(View.GONE);
            layoutBinding.buttonSwitchSuit.setVisibility(View.GONE);
        }

    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            BluetoothService.MyBinder btService = (BluetoothService.MyBinder) service;
            mService = btService.getService();
            mService.setNotifyStatesChangeListener(Chose_Training_Holder_Activity_For_Fragments.this);
            mService.setNotifyTrainingListener(Chose_Training_Holder_Activity_For_Fragments.this);
            bound = true;
            getDevicesModel().addBtDevicesList(getDevicesList());
        }


        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            unbindService(mConnection);
            bound = false;

        }
    };

    private void initRecyclerForClient() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_userList);
        ClientsRecyclerView = new Clients_Side_Recycler(this, this);
        recyclerView.setAdapter(ClientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    }

    @Override
    public void onClientLongClick(Client c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Client?");
        builder.setPositiveButton("Remove", (dialog, id) -> {
            idsOfSelectedUsers.remove(Integer.valueOf(c.getClientId()));
            getClientModel().removeClientFromLiveData(c);
           mService.deleteTimer(c.getClientId());
            deatachFragments();
        });
        builder.setNegativeButton("cancel", (dialog, id) -> dialog.dismiss());
        builder.show();
    }


    private void deatachFragments() {
        if (trainingSetupFragment != null && trainingSetupFragment.isAdded()) {
            Log.d(TAG, "deatachFragments: trainingSetupFragment is added ");
            trainingSetupFragment.onDestroyView();
        }
        if (trainingFragment != null && trainingFragment.isAdded()) {
            Log.d(TAG, "deatachFragments: trainingFragment is added ");
            trainingFragment.onDestroyView();
        }
    }


    @Override
    public void onClientClick(Client client) {
        Log.d(TAG, "onClientClick: entering");
        clientsVIewModel.addClient(client);
        selectedClient = client;
        if (client.getDeviceAddress() == null) {
            trainingSetupFragment();
        } else {
            trainingFragment();
        }
    }

    private void showProgress(){
        if (dialog==null)
            dialog = new MyProgressDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),MyProgressDialog.class.getName());
    }

    private void cancelProgress(){
        if (dialog!=null) dialog.dismiss();
    }

    @Override
    public void showProgressDialog() {
        showProgress();
    }

    @Override
    public void cancelProgressDialog() {
        cancelProgress();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
