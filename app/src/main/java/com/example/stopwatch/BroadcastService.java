package com.example.stopwatch;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BroadcastService extends Service {

    private String TAG = "BroadcastService";
    public static final String COUNTDOWN_BR = "com.example.stopwatch";

    Intent intent = new Intent(COUNTDOWN_BR);
    CountDownTimer countDownTimer = null;

    SharedPreferences pref;
    long currentMillis;
    int num;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "내용 : 1");
        pref = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        Log.e("TAG", "내용 : 2 " + pref.getString(getPackageName(), ""));
        currentMillis = pref.getLong("time", 0);
        Log.e("TAG", "내용 : 3");
        if (currentMillis / 1000 == 0) {
            Log.e("TAG", "내용 :4 ");
            currentMillis = num;
        }
        Log.e("TAG", "내용 : 5");
        SharedPreferences sh = getSharedPreferences("n", MODE_PRIVATE);
        Log.e("TAG", "내용 : 6");
        num = sh.getInt("num", 0);
        Log.e("TAG", "내용 : 7");

        countDownTimer = new CountDownTimer(num * 1000, 1000) {  // countdowntimer 클래스 객체생성하면 시작시간 정하고 간격 정하고, 온 틱, 온피니시 메서드 자동 생성
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("TAG", "내용 : 8");
                Log.i(TAG, "Countdown seconds remaining%%%%%" + millisUntilFinished / 1000); // 온틱메서드는 미리 초 단위로 남은시간을 인자로 받아옴.
                intent.putExtra("countdown", millisUntilFinished);
                Log.e("TAG", "내용 : 9");
                sendBroadcast(intent);
                Log.e("TAG", "내용 : 10");

            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
            }
        };
        Log.e("TAG", "내용 : 11");
        countDownTimer.start();
    }


    @Override
    public void onDestroy() {
        Log.e("TAG", "내용 : 12");
        //countDownTimer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TAG", "내용 : 13");
        return null;
    }
}