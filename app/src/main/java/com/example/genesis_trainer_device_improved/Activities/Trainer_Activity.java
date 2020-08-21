package com.example.genesis_trainer_device_improved.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.recyclers.Trainer_activity_recyclerVIew;
import com.example.genesis_trainer_device_improved.ViewModel.trainer_model.ITrainerViewModel;
import com.example.genesis_trainer_device_improved.ViewModel.trainer_model.TrainerViewModel;
import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;

public class Trainer_Activity extends BaseActivityWithSwipeDismiss implements  Trainer_activity_recyclerVIew.TrainerClick {
    private static final String TAG = "Trainer_Activity";

    ImageButton createTrainer;
    ITrainerViewModel trainerViewModel;
    Trainer_activity_recyclerVIew myRecyclerView;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_activity);
        Log.d(TAG, "onCreate: created TrainerActivity");

        createTrainer = findViewById(R.id.imageButton_add_trainer);
        trainerViewModel = new TrainerViewModel(getApplication());
        trainerViewModel = new ViewModelProvider(this).get(TrainerViewModel.class);
        btnBack = findViewById(R.id.trainerActivity_btnBack);
        btnBack.setOnClickListener(debouncedClickListener);
        createTrainer = findViewById(R.id.imageButton_add_trainer);
        createTrainer.setOnClickListener(debouncedClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecycler();
        loadAllTrainers();
    }

    private void loadAllTrainers(){
       addDisposable( trainerViewModel.loadAllTrainers().subscribe( list-> trainerViewModel.setTrainers(list)));
       trainerViewModel.getTrainers().observe(this,trainers ->   myRecyclerView.setTrainers(trainers));
    }

    /*
     * Initiates Recyclerview that holds list of trainers, clicking on trainer launches Client (trainees) activity
     */

   private void initRecycler(){
       RecyclerView recyclerView = findViewById(R.id.trainer_activity_recycler);
       myRecyclerView = new Trainer_activity_recyclerVIew(this,this);
       recyclerView.setAdapter(myRecyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
       myRecyclerView.setClients(null);
   }

   /*
    * R.id.imageButton_add_trainer: launches create or in case of long click Edit Trainer Activity
    * R.id.trainerActivity_btnBack: back to previous Activity
    */
   DebouncedClickListener debouncedClickListener = new DebouncedClickListener(1000) {
       @Override
       public void onDebouncedClick(View v) {
           switch (v.getId()){
               case R.id.imageButton_add_trainer:
                   Intent startCreateTrainerActivity = new Intent(Trainer_Activity.this,Create_trainer_activity.class);
                   startCreateTrainerActivity.putExtra(Constants.EDIT_TRAINER,false);
                   startActivity(startCreateTrainerActivity);
                   break;
               case R.id.trainerActivity_btnBack:
                   onBackPressed();
                   break;
           }
       }
   };



    /*
     * Long clicking on trainer launches dialog, either delete trainer or lunch Create trainer activity flag that makes it EDITABLE activity
     * that updates the long clicked trainers profile
     */
    @Override
    public void onTrainerLongClick(final int idofclient, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.Edit_Trainer, (dialog, id) -> {

            Intent intent = new Intent(Trainer_Activity.this,Create_trainer_activity.class);
            intent.putExtra(Constants.EDIT_TRAINER,true);
            intent.putExtra(Constants.EDIT_TRAINER_ID,idofclient);
            startActivity(intent);


        });
        builder.setNegativeButton(R.string.Delete_Trainer, (dialog, id) -> {

            trainerViewModel.DeleteTrainer(idofclient).subscribe();
            view.setBackground(null);


        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
     * On Trainer clickking launches new activity Client_Activity
     */
    @Override
    public void onTrainerClick(int id, View view) {
        Intent intent = new Intent(Trainer_Activity.this,Client_Activity.class);
        intent.putExtra(Constants.CHOOSEN_TRAINER_ID, id);
        startActivity(intent);

    }


}
