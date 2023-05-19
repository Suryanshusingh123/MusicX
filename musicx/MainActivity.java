package com.example.musicx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    AudioManager audiomanager;



    public void play(View view)
    {
        player.start();
    }
    public void pause(View view)
    {
    player.pause();
    }
    public void stop(View view)
    {
    player.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player=MediaPlayer.create(this,R.raw.love);//associating our song with media player

        audiomanager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);//isse hume apne system ki audio service mil jayegi
        int maxvol=audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//apni music ki max value humne int mei leliya hai
        int currvol=audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);//apni music ki max value humne int mei leliya hai

        SeekBar seekvol=findViewById(R.id.volume);
        seekvol.setMax(maxvol);//setting max volume
        seekvol.setProgress(currvol);//setting current volume of system as progress

        //now we will write code so that when we move the seekbar the volume changes accordingly
        seekvol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar seekprog=findViewById(R.id.progress);
        seekprog.setMax(player.getDuration());//setting the max of seekbar


        //jaise music play hoga to see baar khud change hota rhe uske liye hum ek timer lagayenge
         new Timer().scheduleAtFixedRate(new TimerTask() {
              @Override
              public void run() {
                    seekprog.setProgress(player.getCurrentPosition());
              }
          },0,1000);
        seekprog.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)// so that this codes executes only when the user is changing it
                { player.seekTo(i); }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}