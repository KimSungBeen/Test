package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.Home_Activity.receiver;

public class Stop_watch_Activity extends AppCompatActivity {

    //뷰 선언
    Button BTN_home, BTN_info, BTN_diary;
    Button BTN_start, BTN_stop, BTN_pause;

    TextView TV_timeLog;

    Chronometer chronometer; //시간 측정 위젯

    boolean isRunning; //스톱워치의 실행상태
    long timeOffset; //일시정지 후 재시작 까지 오차

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        SingleTon.broadcastReceiver(this, receiver);

        chronometer = findViewById(R.id.chronometer); //시간 측정 위젯

        //하단 메뉴버튼
        BTN_home    = findViewById(R.id.BTN_home);
        BTN_info    = findViewById(R.id.BTN_info);
        BTN_diary   = findViewById(R.id.BTN_diary);

        //스톱워치 버튼
        BTN_start   = findViewById(R.id.BTN_start);
        BTN_stop    = findViewById(R.id.BTN_stop);
        BTN_pause   = findViewById(R.id.BTN_pause);

        TV_timeLog  = findViewById(R.id.TV_timeLog); //시간 기록
    }

    @Override
    protected void onResume() {
        super.onResume();

        //홈 액티비티로 이동
        BTN_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stop_watch_Activity.this, Home_Activity.class);
                //FLAG_ACTIVITY_REORDER_TO_FRONT: 실행하려는 액티비티가 이미 스택에 존재하면 그 액티비티를
                //                                스택의 맨 위로 이동시켜서 실행하게만들어 줌
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        //운동정보 액티비티로 이동
        BTN_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stop_watch_Activity.this, Workout_info_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        //운동일지 액티비티로 이동
        BTN_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stop_watch_Activity.this, Workout_diary_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

//==================================================================================================

        //스톱워치 스타트
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning){
                    //setBase: 파라미터값으로 시간설정, SystemClock: (시스템)시간을 가져옴
                    //elapsedRealtime: 밀리초 단위로
                    chronometer.setBase(SystemClock.elapsedRealtime() - timeOffset); //시작 시간 설정
                    chronometer.start();
                    isRunning = true;
                }
            }
        });


        //스톱워치 일시정지
        BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning){
                    timeOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); //일시정지시간 저장

//                    시간표시
//                    long timeLog = (timeOffset / 1000);
//                    byte minutes = (byte) timeLog;
//                    minutes /=  60;
//                    byte seconds = (byte) timeLog;
//                    seconds %= 60;
//                    TV_timeLog.setText( minutes + "분" + seconds + "초" );

                    chronometer.stop();
                    isRunning = false;
                }
            }
        });

        //스톱워치 중지
        BTN_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                timeOffset = 0;

                TV_timeLog.setText("");
            }
        });

    }

    //==================================================================================================
}
