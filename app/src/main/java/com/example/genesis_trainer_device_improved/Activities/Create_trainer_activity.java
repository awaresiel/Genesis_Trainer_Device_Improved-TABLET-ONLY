package com.example.genesis_trainer_device_improved.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.trainer_model.TrainerViewModel;
import com.example.genesis_trainer_device_improved.databinding.CreateTrainerActivityBinding;
import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.helpers.ImageHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Create_trainer_activity extends BaseActivityWithSwipeDismiss {
    private static final String TAG = "Create_trainer_activity";
    private static final String TRAINER_PHOTO_NAME = "trainer_photo_" ;

   private CreateTrainerActivityBinding layoutBinding;
    private boolean EDIT_TRAINER = false;
    private int TRAINER_ID;
    private String PROFILE_IMAGE_ADDRESS = "";
    private TrainerViewModel trainerViewModel;

    private ImageView trainerPhoto;
    private Bitmap trainerProfileBitmap;
    private EditText name, lastName, email, phone, notes;

    private Button buttonSaveTrainer, buttonBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = CreateTrainerActivityBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        EDIT_TRAINER = getIntent().getBooleanExtra(Constants.EDIT_TRAINER, false);
        TRAINER_ID = getIntent().getIntExtra(Constants.EDIT_TRAINER_ID, -1);

        Log.d(TAG, "onCreate: editorcreate = " + EDIT_TRAINER + " editTrainerID= " + TRAINER_ID);

        trainerViewModel = new ViewModelProvider(this).get(TrainerViewModel.class);
        addDisposable(trainerViewModel.getTrainersCount().subscribe(this::checkTrainerCount));

        instantiateVIews();

        if (EDIT_TRAINER && TRAINER_ID != -1) {
            addDisposable(trainerViewModel.loadTrainerById(TRAINER_ID).subscribe(this::editTrainerSetDataToFields));
        }
    }
    private void checkTrainerCount(int count){
        if (count >=200){
            createToast("Cant save Trainer, delete some Trainers");
            finish();
        }
    }

    private void instantiateVIews(){
        trainerPhoto = findViewById(R.id.imageView_createTrainerImage);
        trainerPhoto.setOnClickListener(listener);
        name = findViewById(R.id.et_createTrainer_name);
        lastName = findViewById(R.id.et_createTrainer_lastName);
        email = findViewById(R.id.et_createTrainer_email);
        phone = findViewById(R.id.et_createTrainer_mobile);
        notes = findViewById(R.id.et_createTrainer_notes);
        buttonSaveTrainer = findViewById(R.id.button_saveTrainer);
        buttonBack = findViewById(R.id.trainer_btn_back);
        buttonSaveTrainer.setOnClickListener(listener);
        buttonBack.setOnClickListener(listener);
    }

    /*
     * flag EDIT_TRAINER =false, if intent didnt deliver value true,
     *  then its create new Trainer activity, else it just updates trainers id and its fields
     *
     * imageView_createTrainerImage: clicking on image gives 2 options, take pic or chose from galery
     *
     * button_saveTrainer checks trainers fields, if they match it updates or creates new trainer depending on the flag matching the editOrCreate int field
     */

    private DebouncedClickListener listener = new DebouncedClickListener(800) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_createTrainerImage:
                    if (requestCameraPermission()) {
                        showDialogChoser();
                    }
                    break;

                case R.id.trainer_btn_back:
                    finish();
                    break;
                case R.id.button_saveTrainer:
                    createTrainer();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        adjustToScreenSize(layoutBinding.getRoot(),0.5f,0.5f);
    }

    private void createTrainer() {
        if (editTextNotEmpty(name)) {

            Trainer trainer = new Trainer();

            trainer.setTrainerName(parseEditText(name));

            if (editTextNotEmpty(lastName)) trainer.setTrainerSurname(parseEditText(lastName));

            if (isEmailValid(parseEditText(email))) trainer.setTrainerEmail(parseEditText(email));

            if (!isEmailValid(parseEditText(email))) trainer.setTrainerEmail("Email not valid format");

                if (editTextNotEmpty(lastName))  trainer.setTrainerPhone(parseEditText(phone));

            if (editTextNotEmpty(notes))  trainer.setTrainerNotes(parseEditText(notes));

                 saveTrainerPhoto();

            if (stringNotEmpty(PROFILE_IMAGE_ADDRESS)) trainer.setTrainerProfileImage(PROFILE_IMAGE_ADDRESS);

            if (!EDIT_TRAINER) {
                addDisposable(trainerViewModel.insertTrainer(trainer).subscribe());
                createToast("Trainer Created");
            } else {
                trainer.setTrainerId(TRAINER_ID);
                addDisposable( trainerViewModel.updateTrainer(trainer).subscribe());
                createToast("Trainer Updated");
            }

        } else {
            createToast("Trainer name is mandatory");

        }
    }


    private void showDialogChoser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Take Photo", (dialog, id) -> {

            takePicture();

        });
        builder.setNegativeButton("From Phone", (dialog, id) -> {

            chosePhotoFromStorage();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void chosePhotoFromStorage() {
        Intent chosePhoto = new Intent(Intent.ACTION_GET_CONTENT);
        chosePhoto.setType("image/*");
        startActivityForResult(chosePhoto, Constants.PICK_USER_PROFILE_IMAGE);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: REQUEST_IMAGE_CAPTURE");
            if (data != null) {
              Bundle extras = data.getExtras();
                onImageCaptured(extras);
            }
        }
        if (requestCode == Constants.PICK_USER_PROFILE_IMAGE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: PICK_USER_PROFILE_IMAGE");
            if (data != null) {
                Log.d(TAG, "onActivityResult: data !=null");
              Uri extras = data.getData();
                onPhotoPickedFromStorage(extras);
            }
        }
    }

    private void onImageCaptured(Bundle extras){
        if (extras != null) {
            trainerProfileBitmap = (Bitmap) extras.get("data");
            if (trainerProfileBitmap != null) {
                trainerPhoto.setImageBitmap(trainerProfileBitmap);
            }
        }
    }

    private void saveTrainerPhoto(){
        File dir= imageHelper.getTrainerImageDirectory(this);

        File imageFile = imageHelper.createImageFile(trainerProfileBitmap,TRAINER_PHOTO_NAME+getCurrentDateAndTime() ,"jpg",dir);
        if (imageFile!=null)
       PROFILE_IMAGE_ADDRESS = imageFile.getAbsolutePath();
                Log.d(TAG, "onActivityResult: serialised image = " + PROFILE_IMAGE_ADDRESS);
    }


    private void loadTrainerPhoto(String address){
        trainerProfileBitmap = BitmapFactory.decodeFile(address);
        trainerPhoto.setImageBitmap(trainerProfileBitmap);
    }

    private void onPhotoPickedFromStorage(Uri extras){
        if (extras != null) {
            Log.d(TAG, "onPhotoPickedFromStorage: extras !=null");
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(extras);
                Bitmap imageBitmapFromGalery = BitmapFactory.decodeStream(inputStream);
                if (imageBitmapFromGalery != null) {
                    trainerProfileBitmap = Bitmap.createScaledBitmap(imageBitmapFromGalery, 200, 200, false);
                    trainerPhoto.setImageBitmap(trainerProfileBitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * parse all fields from existing trainer and set all the fields to matching trainers id fields
     */
    private void editTrainerSetDataToFields(Trainer trainer) {
        if (trainer != null) {
            if (trainer.getTrainerProfileImage() != null) {
                PROFILE_IMAGE_ADDRESS = trainer.getTrainerProfileImage();
                loadTrainerPhoto(PROFILE_IMAGE_ADDRESS);
            }
            name.setText(trainer.getTrainerName());
            lastName.setText(trainer.getTrainerSurname());
            email.setText(trainer.getTrainerEmail());
            Log.d(TAG, "editTrainerSetDataToFields: email is = " + trainer.getTrainerEmail());
            phone.setText(trainer.getTrainerPhone());
            notes.setText(trainer.getTrainerNotes());
        }
    }


}
