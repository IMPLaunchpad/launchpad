package com.example.launchpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    Button[][] buttonList = new Button[4][4];

    SoundPool[][] soundPoolList = new SoundPool[4][4];

//    MediaPlayer[][] mediaPlayers = new MediaPlayer[4][4];

    Integer[][] viewIdList = new Integer[4][4];

    Map<Integer, Integer> viewIdIndexMap = new HashMap<>();

    Integer[][] audioIdList = {
            {R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4_1},
            {R.raw.no, R.raw.no, R.raw.a4_2, R.raw.a4_3},
            {R.raw.a5, R.raw.a6, R.raw.a7, R.raw.a8_1},
            {R.raw.no, R.raw.no, R.raw.a8_2, R.raw.a8_3}
    };

    Integer[][] soundIds = new Integer[4][4];

    Map<Integer, Integer> keyMap = new HashMap<Integer, Integer>() {{
        put(111, 0);
        put(8, 1);
        put(9, 2);
        put(10, 3);
        put(11, 4);
        put(12, 5);
        put(13, 6);
        put(14, 7);
        put(15, 8);
        put(16, 9);
        put(7, 10);
        put(69, 11);
        put(70, 12);
        put(67, 13);
        put(61, 14);
        put(45, 15);
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewIdList[0][0] = R.id.button00;
        viewIdList[0][1] = R.id.button01;
        viewIdList[0][2] = R.id.button02;
        viewIdList[0][3] = R.id.button03;

        viewIdList[1][0] = R.id.button10;
        viewIdList[1][1] = R.id.button11;
        viewIdList[1][2] = R.id.button12;
        viewIdList[1][3] = R.id.button13;

        viewIdList[2][0] = R.id.button20;
        viewIdList[2][1] = R.id.button21;
        viewIdList[2][2] = R.id.button22;
        viewIdList[2][3] = R.id.button23;

        viewIdList[3][0] = R.id.button30;
        viewIdList[3][1] = R.id.button31;
        viewIdList[3][2] = R.id.button32;
        viewIdList[3][3] = R.id.button33;


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int buttonId = viewIdList[i][j];
                int audioId = audioIdList[i][j];

                buttonList[i][j] = findViewById(buttonId);
                buttonList[i][j].setBackgroundColor(getResources().getColor(R.color.Gray));

                soundPoolList[i][j] = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

                soundPoolList[i][j].setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        int i = count / 4;
                        int j = count % 4;

                        //buttonList[i][j].setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.shape));
                        count++;
                    }
                });

                soundIds[i][j] = soundPoolList[i][j].load(MainActivity.this, audioId, 0);
                //mediaPlayers[i][j] = MediaPlayer.create(MainActivity.this, audioIdList[i][j]);

                viewIdIndexMap.put(viewIdList[i][j], i * 4 + j);

            }
        }


//        Button.OnClickListener onClickListener = new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = viewIdIndexMap.get(v.getId());
//
//                int i = id / 4;
//                int j = id % 4;
//////                try {
//////                    mediaPlayers[i][j].prepare();
//////                } catch (IOException e) {
//////                    e.printStackTrace();
//////                }
//////                mediaPlayers[i][j].start();
////                System.out.println("id = " + id);
////                System.out.println("i = " + i);
////                System.out.println("j = " + j);
//                //mediaPlayers[i][j].start();
//                soundPoolList[i][j].play(soundIds[i][j], 1f, 1f, 0,0, 1f);
//            }
//        };
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                buttonList[i][j].setOnClickListener(onClickListener);
//            }
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //if ((keyCode >= 7) && (keyCode <= 22)) {
            //int key = keyCode - 7;

            //int i = key / 4;
            //int j = key % 4;

            //soundPoolList[i][j].play(soundIds[i][j], 1f, 1f, 0,0, 1f);

        if(keyCode != 0){
            Integer key = keyMap.get(keyCode);

            if (key == null) return false;

            int i = key / 4;
            int j = key % 4;

            buttonList[i][j].setBackgroundColor(getResources().getColor(R.color.purple_500));

            soundPoolList[i][j].play(soundIds[i][j], 1f, 1f, 0,0, 1f);

            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode != 0){
            Integer key = keyMap.get(keyCode);

            if (key == null) return false;

            int i = key / 4;
            int j = key % 4;

            buttonList[i][j].setBackgroundColor(getResources().getColor(R.color.Gray));

            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                soundPoolList[i][j].release();
                //mediaPlayers[i][j].release();
            }
        }
    }
}