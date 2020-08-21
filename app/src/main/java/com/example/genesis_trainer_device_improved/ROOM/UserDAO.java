package com.example.genesis_trainer_device_improved.ROOM;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.User;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(User user);

//    @Query("UPDATE User_Storage SET UserAddress = :user WHERE UserId = 1")
    @Update( onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> update(User user);
//    @Update( onConflict = 1)

    @Query("DELETE FROM User_Storage WHERE userId = :userId")
    Single<Integer> delete(int userId);

    @Query("SELECT * FROM User_Storage WHERE userId = :id")
    Maybe<User> loadUserById(int id);

    @Query("SELECT * FROM User_Storage WHERE UserEmail = :mail AND UserPassword = :pass")
    Maybe<User> loadUserByEmail(String mail, String pass);

    @Query("SELECT * FROM User_Storage ORDER BY userName ASC")
    Flowable<List<User>> loadAllUsers();

    @Query("SELECT COUNT(UserId) AS total FROM User_Storage")
    Single<Integer> getCount();




    @Insert
    Single<Long> addTrainer(Trainer trainer);

    @Update
    Single<Integer> updateTrainer(Trainer user);

    @Query("DELETE FROM Trainer_Storage WHERE trainerID = :trainerId")
    Single<Integer> deleteTrainer(int trainerId);

    @Query("SELECT * FROM Trainer_Storage WHERE trainerId = :id")
    Maybe<Trainer> loadTrainerById(int id);

    @Query("SELECT * FROM Trainer_Storage ORDER BY TrainerId ASC")
    Flowable<List<Trainer>> loadAllTrainers();

    @Query("SELECT COUNT(*) FROM Trainer_Storage")
    Single<Integer> getTrainerssCount();





    @Insert
    Single<Long> addClient(Client client);

    @Update
    Single<Integer> updateClient(Client client);

    @Query("DELETE FROM Client_Storage WHERE clientID = :clientId")
    Single<Integer> deleteClient(int clientId);

    @Query("SELECT * FROM Client_Storage WHERE clientId = :id")
    Maybe<Client> loadClientById(int id);

    @Query("SELECT * FROM Client_Storage WHERE clientId IN (:id)")
    Maybe<List<Client>> loadMultipleClientsById(int... id);

    @Query("SELECT * FROM Client_Storage ORDER BY ClientId DESC")
    Flowable<List<Client>> loadAllClients();

    @Query("SELECT COUNT(*) FROM Client_Storage")
    Single<Integer> getClientsCount();





    @Query("SELECT * FROM Client_Setting_Storage WHERE ClientId = :clientsID")
    Maybe<UserTrainingSettingsTable> getUserTrainingSettingTableByClientsId(int clientsID);

    @Update
    Single<Integer> updateUserTrainingStorage(UserTrainingSettingsTable userSettings);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Single<Long> addUserTrainingSettingsTable(UserTrainingSettingsTable userSettings);

    @Query("DELETE FROM Client_Setting_Storage WHERE clientID = :userSettingsID")
    Single<Integer> deleteUserTrainingSettingsTable(int userSettingsID);



    @Insert
    Single<Long> saveStatistics(Statistics statistics);

    @Query("SELECT * FROM statistics_table  ORDER BY StatisticsID DESC")
    Maybe<List<Statistics>> getAllStatistics();

    @Query("DELETE FROM Statistics_Table")
    Single<Integer>  deleteAllStatisticsEntries();

    @Query("SELECT COUNT(*) FROM Statistics_Table")
    Single<Integer> getStatisticsCount();
}
