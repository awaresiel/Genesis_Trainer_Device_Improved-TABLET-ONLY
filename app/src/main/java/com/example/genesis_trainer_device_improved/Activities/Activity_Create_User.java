package com.example.genesis_trainer_device_improved.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.example.genesis_trainer_device_improved.Entity.User;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.users_model.IUserViewModel;
import com.example.genesis_trainer_device_improved.ViewModel.users_model.UserViewModel;
import com.example.genesis_trainer_device_improved.databinding.ActivityCreateUserBinding;
import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.helpers.DateInput;
import com.example.genesis_trainer_device_improved.helpers.ImageHelper;
import com.example.genesis_trainer_device_improved.retrofit.APIService;
import com.example.genesis_trainer_device_improved.retrofit.ApiUtils;
import com.example.genesis_trainer_device_improved.retrofit.CallbackWrapper;
import com.example.genesis_trainer_device_improved.retrofit.ServerResponse;
import com.example.genesis_trainer_device_improved.retrofit.UserLogIn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

//import leakcanary.AppWatcher;

public class Activity_Create_User extends BaseActivityWithSwipeDismiss {

    private static final String TAG = "Activity_Create_User";
    private static final String APP_OWNER_IMAGE = "app_owner_image";


    //  boolean canUpdate;
    private EditText name, lastName, birthday, gender, address, email, weight, height, mobile, postcode, userPassword, userRepeatPassword;
    private ImageButton userPicture;
    private Bitmap ProfilePhoto;
    private IUserViewModel userViewModel;
    private ImageHelper imageHelper;
    private Button btnback, btnSubmit;
    private APIService apiService;
    private String imei;
    private List<User> userlist;
    private ActivityCreateUserBinding layoutBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: image ");
        layoutBinding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        imageHelper = new ImageHelper();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userlist = new ArrayList<>();

        String deviceID = getIntent().getStringExtra("imei");
        if (deviceID != null) imei = deviceID;
        apiService = ApiUtils.getAPIService();
        instantiateWidgets();
        loadAllUsers();

          birthday.addTextChangedListener(new DateInput() {
              @Override
              public void checkDateInput(String parsedText,int selection) {
                  birthday.setText(parsedText);
                  birthday.setSelection(selection);
              }
          });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adjustToScreenSize(layoutBinding.getRoot(),0.5f,0.5f);
    }

    // public methods
    /*
     * Checks if user exist, if user exist parse its fields and set it to our layout fields, We use glide for setting images.
     * Users photo is saved as a serialised string into table, lated loaded and turned into bitmap thru class BitmapSerialiser
     */

    private void loadAllUsers() {
        addDisposable(userViewModel.loadAllUsers().subscribe(list -> {
                    // userViewModel.loadAllUsers().subscribe(list -> {
                    userlist = list;
                    populateFieldsIfUserExists();
                    Log.d(TAG, "loadAllUsers: list = " + list.get(0).getUserEmail());
                }, error -> {
                    Log.d(TAG, "loadAllUsers: cant load users " + error.getCause());
                }
        ));
    }
    /*
     * Button Submit is responsible for checking if fields are empty, if email is valid and save user, or if there is already user in the table then
     * update the user
     */

    public void onClick_Btn_Submit(View view) {
        Log.d(TAG, "onClick_Btn_Submit: ======");
        if (!fieldsEmptyCheck() &&
                isValidDateFormat("dd/MM/yyyy", birthday.getText().toString().trim()) &&
               isEmailValid(email.getText().toString().trim()) &&
                passwordMatch(userPassword, userRepeatPassword)) {
            Log.d(TAG, "onClick_Btn_Submit: ===");
            loadUserByLogin(email.getText().toString().trim(), userPassword.getText().toString());

        }
    }

    private void loadUserByLogin(String email, String password) {

        addDisposable(userViewModel.loadUserByEmailAndPassword(email, password)
                .doOnSubscribe(disposable -> saveUserBitmap())
                .subscribe(user -> {
                    createToast("User loaded");
                    updateNewUser(createNewUser());
                    registerRetrofit(user.getUserEmail(), user.getUserPassword(), imei);


                }, error -> {
                    Log.d(TAG, "loadUserByLogin: error " + error.getCause());
                    error.printStackTrace();

                }, () -> {
                    userViewModel.insertUser(createNewUser()).subscribe();
                    createToast("User created");
                    Log.d(TAG, "loadUserByLogin: success");
                }));

    }


    private void updateNewUser(User u) {
        addDisposable(userViewModel.updateUser(u).subscribe(success -> {
            createToast(u.getUserEmail() + getString(R.string.same_updating));
        }, error -> {
            createToast("Cant update the user");
            Log.d(TAG, "updateNewUser: error " + error.getCause());
        }));
    }

    private void registerRetrofit(String email, String password, String imei) {
        final UserLogIn userLogIn = new UserLogIn(email, password, imei);

        Call<ServerResponse> call = apiService.registerUser(userLogIn.getLogin(), userLogIn.getPassword(), userLogIn.getImei());

        call.enqueue(new CallbackWrapper<ServerResponse>((throwable, response, cal) -> {
            if (response == null) return;
            if (response.body() != null) {
                if (!response.body().getState().equals("login_busy")) {

                    createToast("Profile Created");
                } else {
                    createToast("Profile not Created");
                }
                Log.d(TAG, "onResponse Call request = " + call.request());

                Log.d(TAG, "onResponse Response code = " + response.code());

                Log.d(TAG, "onResponse Response body= " + response.body().getState());
                Log.d(TAG, "onResponse Response message= " + response.body());
                Log.d(TAG, "onResponse: url response = " + call.request().url().encodedPath().toString());
                Log.d(TAG, "onResponse: url encodedPath = " + call.request().url().encodedPath().toString());

                Log.d(TAG, "onResponse: url encodedPathSegments = " + call.request().url().encodedPathSegments().toString());
                finish();
            }
            if (throwable != null) {
                throwable.printStackTrace();
                call.cancel();
            }


        }));

    }

    public void onclick_btnBack(View v) {
        onBackPressed();
    }


    /*
     * When user clicks on photo widget it launches 2 options, either take pic from phone camera or take photo from phone galery
     */
    public void onClick_userPicture(View view) {

        if (requestCameraPermission() && requestStoragePermission())
            showDialogOnUserImageClick();


    }


    private void showDialogOnUserImageClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Take Photo", (dialog, id) -> {

            takePictureWithCamera();

            dialog.dismiss();
        });

        builder.setNegativeButton("From Phone", (dialog, id) -> {

            chosePhotoFromDirectory();
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void chosePhotoFromDirectory() {
        Intent chosePhoto = new Intent(Intent.ACTION_GET_CONTENT);
        chosePhoto.setType("image/*");
        startActivityForResult(chosePhoto, Constants.PICK_USER_PROFILE_IMAGE);

    }

    private void takePictureWithCamera() {
     Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (requestCameraPermission()) {
                imageHelper.deleteDirAndItsContent(imageHelper.getUserImageDirectory(this));
                userPicture.setImageBitmap(null);
                startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
            } else {
                requestCameraPermission();
            }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: image result");

        if (resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: result ok");
            switch (requestCode) {
                case Constants.REQUEST_IMAGE_CAPTURE:
                    Log.d(TAG, "onActivityResult: REQUEST_IMAGE_CAPTURE");
                    Bundle extras;

                    if (data == null) {
                        Log.d(TAG, "onActivityResult: image data == null");
                    } else if (data != null) {
                        Log.d(TAG, "onActivityResult: image data !=null ");
                        extras = data.getExtras();
                        takenPicture(extras);
                    }
                    break;

                case Constants.PICK_USER_PROFILE_IMAGE:

                    if (data != null) {
                        Uri imageUri = data.getData();
                        try {
                            selectPictureFromStorage(imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }
        } else {
            createToast(getString(R.string.you_haven_picked_pic));
        }


    }

    private void takenPicture(Bundle extras) {
        Log.d(TAG, "takenPicture: image");
        if (extras != null) {
            Bitmap bitmap = (Bitmap) extras.get("data");
            if (bitmap != null) {
               ProfilePhoto= Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                Glide.with(this).load(ProfilePhoto).into(userPicture);



            }
        }
    }

    private void saveUserBitmap(){
        if (ProfilePhoto==null) return;
        File directory = imageHelper.getUserImageDirectory(this);

        addDisposable(Maybe.fromCallable(() -> {
            return imageHelper.createImageFile(ProfilePhoto, APP_OWNER_IMAGE, ".jpg", directory);
        })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(file -> {
                    Glide.with(this).load(imageHelper.getBitmapFromFile(file)).into(userPicture);
                }, error -> {
                    Log.d(TAG, "takenPicture: error image " + error.getCause());
                }, () -> {
                    Log.d(TAG, "takenPicture: image is null on success called!!!");
                }));
    }

    private void selectPictureFromStorage(Uri imageUri) throws IOException {
        Log.d(TAG, "selectPictureFromStorage: image");
        if (imageUri != null) {
            Log.d(TAG, "selectPictureFromStorage: image uri = " + imageUri.toString());
            Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ProfilePhoto = Bitmap.createScaledBitmap(b, 400, 400, false);
            Glide.with(this).load(ProfilePhoto).into(userPicture);
        }
    }

    private void loadProfileImageOnStartup(ImageView userPicture) {
        Log.d(TAG, "loadProfileImageOnStartup:======= image");
        File imageFile = imageHelper.loadIfImageExists(imageHelper.getUserImageDirectory(this), APP_OWNER_IMAGE);
        if (imageFile == null) return;
        Glide.with(this).load(imageHelper.getBitmapFromFile(imageFile)).into(userPicture);
    }

    /*
     * parse all fields and crate new user
     */


    private User createNewUser() {
        User createdUser = new User();
        createdUser.setUserName(parseEditText(name));
        createdUser.setUserId(1);
        createdUser.setUserSurname(parseEditText(lastName));
        createdUser.setUserEmail(parseEditText(email));
        createdUser.setUserPhone(parseEditText(mobile));
        createdUser.setUserAddress(parseEditText(address));

        createdUser.setUserBirthday(parseEditText(birthday));
        createdUser.setUserHeight(parseStringForInt(height));
        createdUser.setUserWeight(parseStringForInt(weight));
        createdUser.setUserGender(parseEditText(gender));
        createdUser.setUserPassword(parseEditText(userPassword));

        createdUser.setUserPostalCode(parseStringForInt(postcode));
        return createdUser;
    }


    /*
     * checks if fields are empty thru methods from ProfileParserClass
     */

    private boolean fieldsEmptyCheck() {
        boolean notEmptyCheck = fieldsEmptyCheck(name, lastName, birthday, gender, address, email, weight, height, mobile, postcode);
        if (notEmptyCheck) {
            return false;
        } else {
            createToast(getString(R.string.all_fields_must_be_filled));
            return true;
        }
    }

   


    private void instantiateWidgets() {
        name = findViewById(R.id.createUser_name);
        lastName = findViewById(R.id.createUser_lastName);
        birthday = findViewById(R.id.createUser_birthday);
        gender = findViewById(R.id.createUser_gender);
        address = findViewById(R.id.createUser_address);
        email = findViewById(R.id.createUser_email);
        weight = findViewById(R.id.createUser_weight);
        height = findViewById(R.id.createUser_height);
        mobile = findViewById(R.id.createUser_mobile);
        postcode = findViewById(R.id.createUser_postcode);
        userPassword = findViewById(R.id.createUser_password);
        userRepeatPassword = findViewById(R.id.createdUser_repeatPassword);
        userPicture = findViewById(R.id.imageButton_userPicture);
        btnback = findViewById(R.id.buttonBack_createUserProfile);
        btnSubmit = findViewById(R.id.btn_Create_user_sbmit);


    }

    private void populateFieldsIfUserExists() {

        if (userlist != null && !userlist.isEmpty()) {

            User user = userlist.get(0);

            name.setText(user.getUserName());
            lastName.setText(user.getUserSurname());
            birthday.setText(user.getUserBirthday());
            gender.setText(user.getUserGender());
            address.setText(user.getUserAddress());
            email.setText(user.getUserEmail());
            weight.setText(String.valueOf(user.getUserWeight()));
            height.setText(String.valueOf(user.getUserHeight()));
            mobile.setText(String.valueOf(user.getUserPhone()));
            postcode.setText(String.valueOf(user.getUserPostalCode()));
            userPassword.setText(user.getUserPassword());
            userRepeatPassword.setText(user.getUserPassword());
            loadProfileImageOnStartup(userPicture);
            Log.d(TAG, "CheckIfUserExists: user.get id = " + user.getUserId());

        }

    }

    @Override
    protected void onDestroy() {
        userViewModel = null;
        imageHelper = null;
        super.onDestroy();
    }
}