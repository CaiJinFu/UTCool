package com.caijinfu.utcool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 测试周期的Activity
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
public class TestLifeCycleActivity extends AppCompatActivity {

    private static final String TAG = "TestLifeCycleActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, TestLifeCycleActivity.class);
        context.startActivity(starter);
    }

    private String lifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_life_cycle);
        lifecycle = "onCreate";
        Log.i(TAG, "onCreate: ");
    }

    public String getLifecycleState() {
        return lifecycle;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        lifecycle = "onStart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        lifecycle = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        lifecycle = "onPause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
        lifecycle = "onStop";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
        lifecycle = "onRestart";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        lifecycle = "onDestroy";
    }
}