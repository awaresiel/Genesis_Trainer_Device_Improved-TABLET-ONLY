package com.example.genesis_trainer_device_improved.ViewModel;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


import com.example.genesis_trainer_device_improved.helpers.SharedPreferencesHelper;

import java.util.concurrent.atomic.AtomicBoolean;

public class HumanAnimationer implements LifecycleObserver {
    //public abstract class HumanAnimationer extends AnimationDrawable {
    private static final String TAG = "HumanAnimationer";

    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    private Context context;
    private static final Object LOCK = new Object();
    // private AtomicBoolean isFirstThreadRuning = new AtomicBoolean(false);
    private AtomicBoolean loadingThreadRunning = new AtomicBoolean(true);
    public AtomicBoolean isRotating = new AtomicBoolean(false);
    private final Handler handler = new Handler();
    private Thread firstHumanRotation;
    private Thread secondHumanRotation;
    Thread drawing;

    private long mLastClickTime = 0;
    private long mLastClickTime2 = 0;
    private Lifecycle lifecycle;

//    private int current;
//    private int reqWidth;
//    private int reqHeight;
//    private int totalTime;
    // int i;

//    private int[] humanRotationIds = new int[]{R.drawable.human_1, R.drawable.human_2, R.drawable.human_3, R.drawable.human_4, R.drawable.human_5, R.drawable.human_6,
//            R.drawable.human_7, R.drawable.human_8, R.drawable.human_9, R.drawable.human_10, R.drawable.human_11, R.drawable.human_12, R.drawable.human_13,
//            R.drawable.human_14, R.drawable.human_15, R.drawable.human_16, R.drawable.human_17, R.drawable.human_18, R.drawable.human_19, R.drawable.human_20,
//            R.drawable.human_21, R.drawable.human_22, R.drawable.human_23, R.drawable.human_24, R.drawable.human_25, R.drawable.human_26, R.drawable.human_27,
//            R.drawable.human_28, R.drawable.human_29, R.drawable.human_30, R.drawable.human_31, R.drawable.human_32, R.drawable.human_33, R.drawable.human_34,
//            R.drawable.human_35, R.drawable.human_36, R.drawable.human_37, R.drawable.human_38, R.drawable.human_39, R.drawable.human_40, R.drawable.human_41,
//            R.drawable.human_42, R.drawable.human_43, R.drawable.human_44, R.drawable.human_45, R.drawable.human_46};
//
//    private int[] humanRotationIdsFront = new int[]{R.drawable.human_1, R.drawable.human_3, R.drawable.human_5, R.drawable.human_7, R.drawable.human_9, R.drawable.human_11,
//            R.drawable.human_13, R.drawable.human_16, R.drawable.human_19,};
//
//    private int[] humanRotationIdsback = new int[]{R.drawable.human_21, R.drawable.human_24, R.drawable.human_27, R.drawable.human_30, R.drawable.human_33, R.drawable.human_36,
//            R.drawable.human_39, R.drawable.human_42, R.drawable.human_46,};
//
//    public HumanAnimationer(ImageView imageView, final Context context, Lifecycle lifecycle) {
//        this.imageView = imageView;
//        this.context = context;
//        animationDrawable = new AnimationDrawable();
//        this.lifecycle = lifecycle;
//
//       SharedPreferencesHelper.RemoveFromSharedPreferences(context,"SaveBooleanState","isRotating");
////        firstRotationThread();
//
//
//    }

    Runnable runnableRotation = new Runnable() {
        @Override
        public void run() {
            firstRotationThread();
            handler.post(new Runnable() {
                @Override
                public void run() {

                    imageView.setBackground(null);
                    imageView.setImageDrawable(animationDrawable);
                    animationDrawable.setOneShot(true);
                    animationDrawable.start();
                    animationDrawable = new AnimationDrawable();
                }
            });


        }


    };

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void restore(){

     isRotating.set( SharedPreferencesHelper.getFromSharedPreferencesBoolean("SaveBooleanState",
             "isRotating",context));

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPaused(){


            SharedPreferencesHelper.saveToSharedPreferencesBoolean("SaveBooleanState",
                    "isRotating", isRotating.get(), context);


    }


    public void firstRotationThread() {

        //       synchronized (LOCK) {
//            if (!loadingThreadRunning.get()) {
//                try {
//                    Log.d(TAG, "firstRotationThread: locking + loadingThreadBoolean= " + loadingThreadRunning.get());
//                    LOCK.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        if (!isRotating.get()) {
//            for (int i = 0; i < humanRotationIdsFront.length; i++) {
//                Drawable drawable = context.getResources().getDrawable(humanRotationIdsFront[i], null);
//                animationDrawable.addFrame(drawable, 40);
//            }
//
//            isRotating.set(true);
//            //  loadingThreadRunning.set(false);
//            //      LOCK.notify();
//           // Log.d(TAG, "firstRotationThread: boolean rotation to back rottt = " + isRotating.get());
//
//        } else {
//            for (int j = 0; j < humanRotationIdsback.length; j++) {
//
//                Drawable drawable = context.getResources().getDrawable(humanRotationIdsback[j], null);
//                animationDrawable.addFrame(drawable, 40);
//
//            }
            isRotating.set(false);

            //  loadingThreadRunning.set(false);
            //            LOCK.notify();
       // }

       // Log.d(TAG, "firstRotationThread: notifying , loadingThreadBoolean = " + loadingThreadRunning.get());
        Log.d(TAG, "firstRotationThread: from back to front(false) or(true to back from front pos) boolean rottt = " + isRotating.get());
//            LOCK.notify();

//        }
    }

    public void roateExec() {
        firstHumanRotation = new Thread(runnableRotation);
        firstHumanRotation.setPriority(Thread.MAX_PRIORITY);
        firstHumanRotation.start();
        // startDrawing();
    }


//
//    public  void executeRotationToBack() {
//        synchronized (LOCK) {
//            if (loadingThreadRunning.get()) {
//                try {
//                    Log.d(TAG, "executeRotationToBack: locking");
//                    LOCK.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            final long timestamp = System.currentTimeMillis();
//
//            imageView.setBackground(null);
//            imageView.setImageDrawable(animationDrawable);
//            animationDrawable.setOneShot(true);
//            animationDrawable.start();
//            animationDrawable = new AnimationDrawable();
//
//            loadingThreadRunning.set(true);
//
//                Log.d(TAG, "executeRotationToBack: notifying ,loadingThreadBoolean =  " + loadingThreadRunning.get());
//
//            LOCK.notify();
//
//            Log.d("ANIM-TAG", "Time to render all frames:" + (System.currentTimeMillis() - timestamp) + "ms");
//        }
//
//    }
//
//
//
//
//    Runnable exectureRotation = new Runnable() {
//        @Override
//        public void run() {
//
//            executeRotationToBack();
//
//        }
//    };
//
//    // currently not used, fastest performance with one thread, no need for more threads
//    public void startDrawing(){
//
//        drawing = new Thread() {
//
//            @Override
//            public void run() {
//                handler.post(exectureRotation);
//
//            }
//
//        };
//
//       // drawing.setPriority(Thread.MAX_PRIORITY);
//        drawing.start();
//    }


}

