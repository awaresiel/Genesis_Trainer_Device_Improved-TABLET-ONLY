package com.example.genesis_trainer_device_improved.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.clients_model.ClientsVIewModel;
import com.example.genesis_trainer_device_improved.ViewModel.recyclers.Trainer_activity_recyclerVIew;
import com.example.genesis_trainer_device_improved.ViewModel.training_settings_model.TrainingSettingsViewModel;
import com.example.genesis_trainer_device_improved.databinding.ClientActivityBinding;
import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.genesis_trainer_device_improved.helpers.Constants.TABLE_CLIENT_IDS;


public class Client_Activity extends BaseActivityWithSwipeDismiss implements Trainer_activity_recyclerVIew.TrainerClick {
    private static final String TAG = "Client_Activity";

   private ClientActivityBinding layoutBinding;
    private  ImageButton createClient;
    private  ClientsVIewModel clientsVIewModel;
    private  TrainingSettingsViewModel trainingSettingsViewModel;
    private  Trainer_activity_recyclerVIew myRecyclerView;
    private   ArrayList<Integer> selectedClientIds;
    private   List<ClientWraper> allClients;
    private  Button button, buttonBack;
    private  int trainersID = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ClientActivityBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        Log.d(TAG, "onCreate: created ClientActivity");

        allClients = new ArrayList<>();
        selectedClientIds = new ArrayList<>();
        createClient = findViewById(R.id.imageButton_create_newClient);
        clientsVIewModel = new ViewModelProvider(this).get(ClientsVIewModel.class);

        trainingSettingsViewModel = new ViewModelProvider(this).get(TrainingSettingsViewModel.class);
        Intent trainersId = getIntent();
        trainersID = trainersId.getIntExtra(Constants.CHOOSEN_TRAINER_ID, -1);

        createClient = findViewById(R.id.imageButton_create_newClient);
        button = findViewById(R.id.button_goToTraining);
        buttonBack = findViewById(R.id.buttonBack_clientActivity);
        button.setOnClickListener(listener);
        buttonBack.setOnClickListener(listener);
        createClient.setOnClickListener(listener);

    }


    private void loadAllClients() {
        addDisposable(clientsVIewModel.loadAllClients().subscribe(list -> {
            Log.d(TAG, "loadAllClients: ");
            if (clientsVIewModel.getWrappedClients().getValue()==null)
            clientsVIewModel.addWrappedClients(addClientsToClientWrapper(list));
        }));

        clientsVIewModel.getWrappedClients().observe(this,clients -> {
            if (clients ==null)return;
            allClients = clients;
            myRecyclerView.setClients(clients);
            addSelectedClients(clients);
        });

    }

    private void addSelectedClients(List<ClientWraper> clients){
        selectedClientIds = new ArrayList<>();
        for (ClientWraper c:clients){
            if (c.isSelected())
                selectedClientIds.add(c.getClient().getClientId());
        }
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.client_activity_recycler);
        myRecyclerView = new Trainer_activity_recyclerVIew(this, this);
        recyclerView.setAdapter(myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        myRecyclerView.setTrainers(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
       resetSelection();
    }

    /*
     * only 7 clients max can be selected, if user launches another activity, deletes client then reset selection to 0
     */
    public void resetSelection() {
        if (myRecyclerView!=null)
            clientsVIewModel.addWrappedClients(null);
         initRecycler();
        loadAllClients();

    }

    /*
     * imageButton_add_client2:  adding new client unselects selection and launches Create_Client_Activity
     *
     * button_goToTraining: goes to Chose_Training_Holder_Activity_For_Fragments (holds 2 fragments for setting up device
     * first putIntegerArrayListExtra puts list of selected clients for next activity, clients are loaded from ids of clients
     * second extra is chosen trainers ID
     * and third just so can only one instance of activity be launched at the same time to prevent multiple click multiple acitivties.
     */

    DebouncedClickListener listener = new DebouncedClickListener(800) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_create_newClient:

                    Intent startCreateClientActivity = new Intent(Client_Activity.this, Create_Client_Activity.class);
                    startCreateClientActivity.putExtra(Constants.EDIT_CLIENT, 0);
                    startActivity(startCreateClientActivity);
                    break;
                case R.id.button_goToTraining:
                    Log.d(TAG, "onDebouncedClick: before if "+selectedClientIds);
                    Log.d(TAG, "onDebouncedClick: before if "+selectedClientIds.isEmpty());
                    if (selectedClientIds != null && !selectedClientIds.isEmpty()) {
                        Log.d(TAG, "onDebouncedClick: selectedClientIds != null && !selectedClientIds.isEmpty()");
                        launchSetupTrainingActivity();
                    }

                    break;
                case R.id.buttonBack_clientActivity:
                    onBackPressed();
            }
        }
    };


    private void launchSetupTrainingActivity(){
        Intent intent = new Intent(Client_Activity.this, Chose_Training_Holder_Activity_For_Fragments.class);
        intent.putIntegerArrayListExtra(TABLE_CLIENT_IDS, selectedClientIds);
        intent.putExtra(Constants.CHOOSEN_TRAINER_ID, trainersID);
        startActivity(intent);
    }


    /*
     * on long click either delete client or edit client all same like trainers activity
     */
    @Override
    public void onTrainerLongClick(final int idofclient, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.Edit_Client, (dialog, id) -> {

            Intent intent = new Intent(Client_Activity.this, Create_Client_Activity.class);
            intent.putExtra(Constants.EDIT_CLIENT, true);
            intent.putExtra(Constants.EDIT_CLIENT_ID, idofclient);
            startActivity(intent);


        });
        builder.setNegativeButton(R.string.Delete_Client, (dialog, id) -> {

           clientsVIewModel.DeleteClient(idofclient).subscribe();
           trainingSettingsViewModel.deleteUserTrainingSettingsTable(idofclient).subscribe();



            view.setBackground(null);

            resetSelection();
        


        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putInt("selectedPosition",myRecyclerView. getSelectedPos());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myRecyclerView.resetSelection(savedInstanceState.getInt("selectedPosition"));
    }

    // this is same ontrainer click that works on user and sends you to next activity where u chose training options ( one recicler view implememnted for
    // both acitiities due to similarity
    @Override
    public void onTrainerClick(int clientID, View view) {
        if (selectedClientIds.contains(clientID)) {
            selectedClientIds.remove(Integer.valueOf(clientID));
            Log.d(TAG, "onTrainerClick: removing ID = " + clientID);


        } else if (selectedClientIds.size()!=7) {
          selectedClientIds.add(clientID);
           Log.d(TAG, "onTrainerClick: adding client = " + clientID);
        } else {
            Toast.makeText(this, R.string.selection_limit_7_clients, Toast.LENGTH_SHORT).show();
        }


    }


}
