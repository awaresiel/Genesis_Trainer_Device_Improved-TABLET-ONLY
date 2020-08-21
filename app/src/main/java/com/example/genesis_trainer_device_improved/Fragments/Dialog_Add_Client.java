package com.example.genesis_trainer_device_improved.Fragments;


import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genesis_trainer_device_improved.Activities.Chose_Training_Holder_Activity_For_Fragments;
import com.example.genesis_trainer_device_improved.Activities.Create_Client_Activity;
import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.clients_model.ClientsVIewModel;
import com.example.genesis_trainer_device_improved.ViewModel.recyclers.Trainer_activity_recyclerVIew;
import com.example.genesis_trainer_device_improved.databinding.ClientActivityBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class Dialog_Add_Client extends DialogFragment implements Trainer_activity_recyclerVIew.TrainerClick {
    private static final String TAG = "Dialog_Add_Client";

    private ClientActivityBinding binding;
    private Trainer_activity_recyclerVIew adapter;
    private Chose_Training_Holder_Activity_For_Fragments activity;
    private List<ClientWraper> allClients;
    private ArrayList<Integer> idsOfSelectedUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ClientActivityBinding.inflate(getLayoutInflater());
        Log.d(TAG, "onCreateView: ");
        setCancelable(true);
        if (getActivity() instanceof Chose_Training_Holder_Activity_For_Fragments) {
            activity = (Chose_Training_Holder_Activity_For_Fragments) getActivity();
            allClients = new ArrayList<>();
            idsOfSelectedUsers = new ArrayList<>();
            idsOfSelectedUsers.addAll(activity.getIdsOfSelectedUsers());
            binding.imageButtonCreateNewClient.setOnClickListener(l);
            binding.buttonGoToTraining.setOnClickListener(l);
            binding.buttonBackClientActivity.setOnClickListener(l);
            setupRecyclerView();
            initClients();
        }

        return binding.getRoot();
    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart: onBindViewHolder");
        super.onStart();
        resetSelection();
    }

    public void resetSelection() {
        if (adapter != null)
            activity.getClientModel().addWrappedClients(null);
        setupRecyclerView();
        initClients();
    }


    private void setupRecyclerView() {
        Log.d(TAG, "setupRecyclerView: ");
        RecyclerView list = binding.clientActivityRecycler;
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        adapter = new Trainer_activity_recyclerVIew(getContext(), this);
        list.setAdapter(adapter);
        adapter.resetSelection(activity.getIdsOfSelectedUsers().size());
    }


    private void initClients() {
        ClientsVIewModel model = activity.getClientModel();

        activity.addDisposable(model.loadAllClients().subscribe(list -> {
            allClients = activity.addClientsToClientWrapper(list);

            if (model.getWrappedClients().getValue() == null) {
                List<ClientWraper> l1 = activity.addClientsToClientWrapper(activity.getAllClientList());
                for (ClientWraper c : l1) allClients.remove(c);
                model.addWrappedClients(allClients);
            }
        }));

        model.getWrappedClients().observe(getViewLifecycleOwner(), clients -> {
            allClients = clients;

            adapter.setClients(clients);
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null)
            outState.putInt("selectedPosition", adapter.getSelectedPos());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey("selectedPosition")) {
            if (adapter != null)
                adapter.resetSelection(savedInstanceState.getInt("selectedPosition"));
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = null;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onTrainerLongClick(int id, View view) {
        Log.d(TAG, "onTrainerLongClick: clicked " + id);
    }

    @Override
    public void onTrainerClick(int id, View view) {

        if (idsOfSelectedUsers != null) {
            if (idsOfSelectedUsers.contains(id)) {
                idsOfSelectedUsers.remove(Integer.valueOf(id));
            } else if (idsOfSelectedUsers.size() <= 6) {
                idsOfSelectedUsers.add(id);
            } else {
                activity.createToast(activity.getString(R.string.selection_limit_7_clients));
            }
        }
        Log.d(TAG, "onTrainerClick: clicked " + id);
    }


    DebouncedClickListener l = new DebouncedClickListener(800) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_create_newClient:

                    Intent intent = new Intent(activity, Create_Client_Activity.class);
                    activity.startActivity(intent);
                    break;
                case R.id.button_goToTraining:

                    activity.setIdsOfSelectedUsers(idsOfSelectedUsers);
                    Log.d(TAG, "onDebouncedClick: ids of selected user "+idsOfSelectedUsers.size());
                    dismiss();
                    break;
                case R.id.buttonBack_clientActivity:
                    dismiss();
                    break;
            }
        }
    };


}
