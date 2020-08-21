package com.example.genesis_trainer_device_improved.ViewModel.users_model;

/*
 * This class is a communication class between ROOM and Views, it will update All views related to user:
 * It will be responsibe for updating User related data on other threads and Userdatabase
 */


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.User;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UserViewModel extends BaseViewModel implements IUserViewModel {
    private static final String TAG = "UserViewModel";



    public UserViewModel(Application application) {
        super(application);

    }


    @Override
    public Maybe<User> loadUserById(int ID) {

       return userDAO.loadUserById(ID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Maybe<User> loadUserByEmailAndPassword(String email, String pass) {

        return userDAO.loadUserByEmail(email,pass).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Long> insertUser(User user) {

      return  userDAO.insert(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Integer> DeleteUser(int userID) {

        return userDAO.delete(userID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Single<Integer> updateUser(User user) {

            return  userDAO.update(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Integer> getCount() {
          return  userDAO.getCount().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<User>> loadAllUsers() {

        return userDAO.loadAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }








}