package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StopWatch extends Fragment {

    TextView myRec, myOutput;
    Button myBtnStart, myBtnRec;

    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;

    int cur_Status = Init; // 현재 상태를 저장할 변수를 초기화 함.
    int myCount = 1;
    long myBaseTime;
    long myPauseTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_stopwatch, container, false);

        myOutput = v.findViewById(R.id.time_out);
        myRec = v.findViewById(R.id.record);
        myBtnStart = v.findViewById(R.id.btn_start);
        myBtnRec = v.findViewById(R.id.btn_rec);

        myBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur_Status) {
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime(); // 시작점으로 부터 경과된 시간
                        System.out.println(myBaseTime);
                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        Log.e("TAG", "내용 : click1" );
                        Log.e("TAG", "내용 : click2" );
                        myTimer.sendEmptyMessage(0);
                        Log.e("TAG", "내용 : click3" );
                        myBtnStart.setText("멈춤"); //버튼의 문자"시작"을 "멈춤"으로 변경
                        myBtnRec.setEnabled(true); //기록버튼 활성
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        myTimer.removeMessages(0); // removeMessages 는 핸들러를 멈추라고 명령하는 코드임.
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        myBtnRec.setText("리셋");
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now - myPauseTime);
                        myBtnStart.setText("멈춤");
                        myBtnRec.setText("기록");
                        cur_Status = Run;
                        break;
                }
            }
        });

        myBtnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur_Status) {
                    case Run:
                        String str = myRec.getText().toString();
                        str += String.format("%d. %s\n", myCount, getTimeOut());
                        myRec.setText(str);
                        myCount++; //카운트 증가
                        break;
                    case Pause:
                        //핸들러를 멈춤
                        myTimer.removeMessages(0);
                        myBtnStart.setText("시작");
                        myBtnRec.setText("기록");
                        myOutput.setText("00:00:00");
                        cur_Status = Init;
                        myCount = 1;
                        myRec.setText("");
                        myBtnRec.setEnabled(false);
                        break;
                }
            }
        });
        return v;
    }


    @SuppressLint("HandlerLeak")
    Handler myTimer = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("TAG", "내용 : handler" );

            myOutput.setText(getTimeOut());
            // sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송함.
            Log.e("TAG", "내용 : sendEmptyMessage" );
            myTimer.sendEmptyMessage(0);
        }
    };

    // 현재 시간을 계속 구해서 출력하는 메소드
    String getTimeOut() {
        long now = SystemClock.elapsedRealtime(); // elapsedRealtime()는 디바이스가 부팅된 시점부터 현재까지의 시간간격을 ms단위로 출력한다
        long outTime = now - myBaseTime;
        Log.e("TAG", "내용 : outTime" +outTime);
        String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 60, outTime / 1000 % 60, outTime % 1000 / 10);
        return easy_outTime;  // % 명령시작을 의미, 0:채워질 문자, 2:총 자릿수, d:십진수로된 수        % 는 나머지 연산, 9%5 = 4 /9나누기5의 나머지는 4
    }
}