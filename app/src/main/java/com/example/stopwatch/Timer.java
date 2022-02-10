package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Timer extends Fragment {
    String TAG = "main";
    TextView txt;
    Button btn_input_ok;
    EditText et_input;
    int num;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_timer, container, false);
        et_input = v.findViewById(R.id.et_input);
        Log.e("TAG", "findViewById :ㅅㅅ 1");
        txt = v.findViewById(R.id.txt);
        Log.e("TAG", "findViewById :ㅅㅅ 2");

        btn_input_ok = v.findViewById(R.id.btn_input_ok);
        Log.e("TAG", "findViewById :ㅅㅅㅅ 3");
        btn_input_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "내용 :ㅅㅅ 4");
                try {
                    num = Integer.parseInt(et_input.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("TAG", "내용 :ㅅㅅ 5");

                SharedPreferences sh = getActivity().getSharedPreferences("n", Context.MODE_PRIVATE);
                sh.edit().putInt("num", num).apply();
                Log.e("TAG", "내용 :ㅅㅅ 6");
                // Intent intent = new Intent(getActivity(), BroadCast.class);
                Log.e("TAG", "내용 :ㅅㅅ 7");
                requireActivity().startService(new Intent(getContext(), BroadcastService.class));
                Log.e("TAG", "내용 :ㅅㅅ 8");
                Log.i(TAG, "Started Service");
            }
        });

        return v;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Update GUI
            Log.e("TAG", "내용 : updateGUI" );
            updateGUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broadcast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
        Log.i(TAG, "Unregistered broadcast receiver");
    }

    @Override
    public void onStop() {
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            // Receiver was probably already
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {

        getActivity().stopService(new Intent(getActivity(), BroadcastService.class));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 1000);
            Log.i(TAG, "Countdown seconds remaining : " + millisUntilFinished / 1000);

            txt.setText(Long.toString(millisUntilFinished / 1000));
            SharedPreferences pref = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

            pref.edit().putLong("time", millisUntilFinished).apply();
        }
    }
}