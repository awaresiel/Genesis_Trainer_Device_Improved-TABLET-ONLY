package com.example.genesis_trainer_device_improved.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.recyclers.Statistics_Recycler;
import com.example.genesis_trainer_device_improved.ViewModel.users_model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends BaseActivityWithSwipeDismiss {
    private static final String TAG = "StatisticsActivity";

    RecyclerView recyclerView;
    Statistics_Recycler statistics_recycler;
    UserViewModel userViewModel;
    List<Statistics> allStatistics;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        btnBack = findViewById(R.id.statistics_back);

        userViewModel = new UserViewModel(getApplication());
        allStatistics = new ArrayList<>();
        loadStatistics();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.statistics_recycler);
        statistics_recycler = new Statistics_Recycler(allStatistics,this);
        recyclerView.setAdapter(statistics_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadStatistics(){
      // allStatistics = userViewModel.getAllStatistics();
        initRecycler();
        Log.d(TAG, "loadStatistics: statistics size = " + allStatistics.size());
    }


}
