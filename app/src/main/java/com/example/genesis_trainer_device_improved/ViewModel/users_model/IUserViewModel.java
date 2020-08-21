package com.example.genesis_trainer_device_improved.ViewModel.users_model;

import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.Entity.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IUserViewModel {


     Maybe<User> loadUserById(int ID);
     Maybe<User> loadUserByEmailAndPassword(String email, String password);
     Single<Long> insertUser( User user);
     Single<Integer> DeleteUser( int userID);
     Single<Integer> updateUser( User user);
     Single<Integer> getCount();
     Flowable<List<User>> loadAllUsers();











}
