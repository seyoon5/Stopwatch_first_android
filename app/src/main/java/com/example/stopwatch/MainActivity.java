package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private StopWatch stopWatch;
    private Timer timer;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager(); // getSupportFragmentManager 얘를 통해서 fragmentManager 가 프래그먼트에 접근이 가능

        stopWatch = new StopWatch();
        timer = new Timer();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, timer).commit();
    }

    public void clickHandler(View view) {
        transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_fragmentA:
                transaction.replace(R.id.frameLayout, timer).commit();
                break;
            case R.id.btn_fragmentB:
                transaction.replace(R.id.frameLayout, stopWatch).commit();
                break;
        }
    }
    // 오오 !!! 깃 수정
    // 오늘도 화이팅!
}