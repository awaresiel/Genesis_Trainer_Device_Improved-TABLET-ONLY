package com.example.genesis_trainer_device_improved.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.genesis_trainer_device_improved.ROOM.UserDAO;
import com.example.genesis_trainer_device_improved.ROOM.UserDatabase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends AndroidViewModel {

    public UserDAO userDAO;

    public BaseViewModel(@NonNull Application application) {
        super(application);

        UserDatabase database = UserDatabase.getUserDatabaseInstance(application);
        userDAO = database.userDAO();

    }


    @Override
    protected void onCleared() {

        super.onCleared();
    }
}
