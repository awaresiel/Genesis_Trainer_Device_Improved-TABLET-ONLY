package com.example.genesis_trainer_device_improved.ViewModel.clients_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClientsVIewModel extends BaseViewModel implements IClientViewModel {
    private static final String TAG = "ClientsVIewModel";

    private MutableLiveData<Client> client;
    private MutableLiveData<List<Client>> clients;
    private MutableLiveData<List<ClientWraper>> wrappedClients;


    public ClientsVIewModel(@NonNull Application application) {
        super(application);
        client = new MutableLiveData<>();
        clients = new MutableLiveData<>();
        wrappedClients = new MutableLiveData<>();
    }

    public LiveData<Client> getClient() {
        return client;
    }

    public void removeClientFromLiveData(Client c){
        List<Client> previous = clients.getValue();
        if (previous == null)return;
            previous.remove(c);
            clients.postValue(previous);

    }

    public void removeWrappedClientFromLiveData(ClientWraper c){
        List<ClientWraper> previous = wrappedClients.getValue();
        if (previous == null)return;
        previous.remove(c);
        wrappedClients.postValue(previous);

    }


    @Override
    public LiveData<List<Client>> getClients() {
        return clients;
    }

    @Override
    public void addWrappedClients(List<ClientWraper> c) {
        wrappedClients.postValue(c);
    }

    @Override
    public LiveData<List<ClientWraper>> getWrappedClients() {
        return wrappedClients;
    }

    @Override
    public void addClients(List<Client> c){
        clients.postValue(c);
    }

    @Override
    public void addClient(Client c){
        client.postValue(c);
    }

    @Override
    public Single<Integer> DeleteClient(int clientID) {
        return  userDAO.deleteClient(clientID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> updateClient(Client client) {

       return userDAO.updateClient(client).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Long> insertClient(Client client) {
         return userDAO.addClient(client).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Maybe<Client> loadClientById(int ID) {
      return userDAO.loadClientById(ID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Maybe<List<Client>> loadMultipleClientsById(int... ID) {
        return userDAO.loadMultipleClientsById(ID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<Client>> loadAllClients() {

       return userDAO.loadAllClients().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> getCount() {

        return userDAO.getClientsCount().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
