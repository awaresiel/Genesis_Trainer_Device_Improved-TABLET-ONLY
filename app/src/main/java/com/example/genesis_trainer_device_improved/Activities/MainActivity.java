package com.example.genesis_trainer_device_improved.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.genesis_trainer_device_improved.R;

public class MainActivity extends BaseActivityWithSwipeDismiss {
    private static final String TAG = "MainActivity";
    Button agree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        agree = findViewById(R.id.btn_1s_yes);

    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");


    }


    //public methods
    /*
     * on Button Agree click leads to ActivitySecondScreen.class
     */
    public void on_Agree_Click(View view) {
        Intent intent = new Intent(MainActivity.this, Activity_Second.class);

        startActivity(intent);
    }

    /*
     * on button Disagree show dialog that explains why you should agree
     */
    public void on_Disagee_Click(View view) {
        Log.d(TAG, "on_Disagee_Click: clicked");
        buildDialog().show();

    }

    /*
     * Dialog Builder
     */
    private AlertDialog buildDialog() {
        Log.d(TAG, "buildDialog: called");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.DisagreeDialog);
        alert.setPositiveButton(R.string.Agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: dialog - clicked ok");
                // return to terms of service screen
            }
        });
        return alert.create();
    }

}