package com.example.genesis_trainer_device_improved.ViewModel.clients_model;

import androidx.lifecycle.LiveData;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IClientViewModel {


    Single<Integer> DeleteClient(int clientID);
    Single<Integer> updateClient( Client client);
    Single<Long> insertClient( Client client);
    Maybe<Client> loadClientById(int ID);
    Maybe<List<Client>> loadMultipleClientsById(int... ID);
    Flowable<List<Client>> loadAllClients();
     void addClients(List<Client> c);
     void addWrappedClients(List<ClientWraper> c);
     void addClient(Client c);
     LiveData<Client> getClient();
    LiveData<List<Client>> getClients();
    LiveData<List<ClientWraper>> getWrappedClients();
    Single<Integer> getCount();
}
