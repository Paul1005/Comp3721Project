package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.media.AudioManager;
import android.widget.Toast;


import com.esri.core.internal.tasks.ags.v;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.mediaPlayer;
import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenSettings extends Activity {
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    public static boolean mute = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_settings);

        controlVolume();
    }

    private void controlVolume() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopmus(View view){
        volumeSeekbar = (SeekBar) findViewById(R.id.seekBar1);
        volumeSeekbar.setProgress(0);
    }

    public void playmus(View view){
        volumeSeekbar = (SeekBar) findViewById(R.id.seekBar1);
        volumeSeekbar.setProgress(100);
    }

    public void aboutGame(View view){
        soundFX.start();
        Intent aboutGame = new Intent(ScreenSettings.this,ScreenDiscovery.class);
        startActivity(aboutGame);

    }

    public void buttonBackClick(View view) {
        soundFX.start();
        finish();
    }
}
