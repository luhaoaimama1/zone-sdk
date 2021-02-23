package com.zone.keeplive.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zone.keeplive.activity.utils.NoVolumePlayer;

public class NoVolumeActivity extends Activity {
    private NoVolumePlayer noVolumePlayer = new NoVolumePlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noVolumePlayer.playCircle(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noVolumePlayer.destory();
    }
}
