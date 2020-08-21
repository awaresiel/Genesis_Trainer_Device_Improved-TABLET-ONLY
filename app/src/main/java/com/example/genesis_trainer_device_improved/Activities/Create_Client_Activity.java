package com.example.genesis_trainer_device_improved.Activities;

import android.Manifest;
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
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.clients_model.ClientsVIewModel;
import com.example.genesis_trainer_device_improved.databinding.CreateClientActivityBinding;
import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.genesis_trainer_device_improved.helpers.Constants.CAMERA_REQUEST;

public class Create_Client_Activity extends BaseActivityWithSwipeDismiss {
    private static final String TAG = "Create_client_activity";
    private static final String CLIENT_PHOTO_NAME = "client_photo_";
    private String PROFILE_IMAGE_ADDRESS = "";

    private boolean EDIT_CLIENT = false;
    private int CLIENT_ID;

    private ClientsVIewModel clientsVIewModel;
    private Bitmap clientBitmap;
    private ImageView clientPhoto;

    private EditText name, lastName, email, phone, notes;
    private Button buttonSaveClient, btnback;
    private CreateClientActivityBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = CreateClientActivityBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        instantiateViews();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);

        EDIT_CLIENT = getIntent().getBooleanExtra(Constants.EDIT_CLIENT, false);
        CLIENT_ID = getIntent().getIntExtra(Constants.EDIT_CLIENT_ID, -1);

        if (EDIT_CLIENT && CLIENT_ID != -1) {
            addDisposable(clientsVIewModel.loadClientById(CLIENT_ID).subscribe(this::editClientSetDataToFields));

        }

    }

    private void checkClientCount(int count){
        if (count >=200){
            createToast("Cant save Client, delete some Clients");
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adjustToScreenSize(layoutBinding.getRoot(),0.5f,0.5f);
    }

    private void instantiateViews() {
        clientsVIewModel = new ViewModelProvider(this).get(ClientsVIewModel.class);
        addDisposable(clientsVIewModel.getCount().subscribe(this::checkClientCount));

        clientPhoto = findViewById(R.id.imageView_createClientImage);
        clientPhoto.setOnClickListener(listener);
        name = findViewById(R.id.et_createClient_name);
        lastName = findViewById(R.id.et_createClient_lastName);
        email = findViewById(R.id.et_createClient_email);
        phone = findViewById(R.id.et_createClient_mobile);
        notes = findViewById(R.id.et_createClient_notes);
        buttonSaveClient = findViewById(R.id.button_saveClient);
        btnback = findViewById(R.id.createClient_btn_back);
        buttonSaveClient.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
    }

    private void chosePhotoFromStorage() {
        Intent chosePhoto = new Intent(Intent.ACTION_GET_CONTENT);
        chosePhoto.setType("image/*");
        startActivityForResult(chosePhoto, Constants.PICK_USER_PROFILE_IMAGE);
    }

    private void showDialogChooser() {
        if (requestCameraPermission()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Take Photo", (dialog, id) -> takePicture());
            builder.setNegativeButton("From Phone", (dialog, id) -> chosePhotoFromStorage());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    private void createClient() {
        if (editTextNotEmpty(name)) {

            Client client = new Client();

            client.setClientName(parseEditText(name));

            if (editTextNotEmpty(lastName)) client.setClientSurname(parseEditText(lastName));

            if (isEmailValid(parseEditText(email))) client.setClientEmail(parseEditText(email));

            if (!isEmailValid(parseEditText(email)))
                client.setClientEmail("Email not valid format");

            if (editTextNotEmpty(lastName)) client.setClientPhone(parseEditText(phone));

            if (editTextNotEmpty(notes)) client.setClientNotes(parseEditText(notes));

            saveClientPhoto();

            if (stringNotEmpty(PROFILE_IMAGE_ADDRESS))
                client.setClientProfileImage(PROFILE_IMAGE_ADDRESS);

            if (!EDIT_CLIENT) {
                addDisposable(clientsVIewModel.insertClient(client).subscribe());
                createToast("Client Created");
            } else {
                client.setClientId(CLIENT_ID);
                addDisposable(clientsVIewModel.updateClient(client).subscribe());
                createToast("Client Updated");
            }

        } else {
            createToast("Client name is mandatory");

        }
    }

    private void saveClientPhoto() {
        File dir = imageHelper.getClientImageDirectory(this);
            if (clientBitmap ==null) return;
        File imageFile = imageHelper.createImageFile(clientBitmap, CLIENT_PHOTO_NAME + getCurrentDateAndTime(), "jpg", dir);
        if (imageFile != null)
            PROFILE_IMAGE_ADDRESS = imageFile.getAbsolutePath();
        Log.d(TAG, "onActivityResult: serialised image = " + PROFILE_IMAGE_ADDRESS);
    }


    private DebouncedClickListener listener = new DebouncedClickListener(800) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_createClientImage:
                    showDialogChooser();

                    break;

                case R.id.createClient_btn_back:
                    finish();
                    break;

                case R.id.button_saveClient:
                    createClient();
                    finish();
                    break;
            }
        }
    };

    private void loadClientPhoto(String address) {
        clientBitmap = BitmapFactory.decodeFile(address);
        clientPhoto.setImageBitmap(clientBitmap);
    }

    private void onPhotoPickedFromStorage(Uri extras) {
        if (extras != null) {
            Log.d(TAG, "onPhotoPickedFromStorage: extras !=null");
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(extras);
                Bitmap imageBitmapFromGalery = BitmapFactory.decodeStream(inputStream);
                if (imageBitmapFromGalery != null) {
                    clientBitmap = Bitmap.createScaledBitmap(imageBitmapFromGalery, 200, 200, false);
                    clientPhoto.setImageBitmap(clientBitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                onImageCaptured(extras);
            }
        }
        if (requestCode == Constants.PICK_USER_PROFILE_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Log.d(TAG, "onActivityResult: data !=null");
                Uri extras = data.getData();
                onPhotoPickedFromStorage(extras);
            }
        }
    }


    private void onImageCaptured(Bundle extras) {
        if (extras != null) {
            clientBitmap = (Bitmap) extras.get("data");
            if (clientBitmap != null) {
                clientPhoto.setImageBitmap(clientBitmap);
            }
        }
    }

    private void editClientSetDataToFields(Client client) {
        if (client != null) {
            if (client.getClientProfileImage() != null) {
                PROFILE_IMAGE_ADDRESS = client.getClientProfileImage();
                loadClientPhoto(PROFILE_IMAGE_ADDRESS);
            }
            name.setText(client.getClientName());
            lastName.setText(client.getClientSurname());
            email.setText(client.getClientEmail());
            phone.setText(client.getClientPhone());
            notes.setText(client.getClientNotes());
        }
    }

}


