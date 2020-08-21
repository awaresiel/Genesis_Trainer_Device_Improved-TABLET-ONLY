package com.example.genesis_trainer_device_improved.ViewModel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.genesis_trainer_device_improved.R;


public class MyProgressDialog extends DialogFragment {
    private static final String TAG = "MyProgressDialog";

    ProgressBar waitingProgressBar;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_progressbar, container, false);
        waitingProgressBar=view.findViewById(R.id.waiting_progressBar);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onPause() {
        dismiss();
        super.onPause();
    }


}
