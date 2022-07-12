package com.caijinfu.utcool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.caijinfu.utcool.ui.main.MainFragment;

/**
 * MainActivity
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commitNow();
        }

    }
}