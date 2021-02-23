package com.zone.keeplive.activity.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.zone.R;

public class NoVolumePlayer {
    private MediaPlayer mediaPlayer;

    public void playCircle(Context context) {
        //播放无声音乐
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.novioce);
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        play();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
                play();
            }
        }
    }

    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void destory() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
