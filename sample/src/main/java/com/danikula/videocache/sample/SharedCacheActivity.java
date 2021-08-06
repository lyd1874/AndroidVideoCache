package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.danikula.videocache.sample.databinding.ActivityMultipleVideosBinding;

public class SharedCacheActivity extends FragmentActivity {

    private ActivityMultipleVideosBinding mBinding;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.e("lyd"," onCreate ---5");
        mBinding = ActivityMultipleVideosBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        if (state == null) {
            addVideoFragment(Video.ORANGE_1, R.id.videoContainer0);
            addVideoFragment(Video.ORANGE_1, R.id.videoContainer1);
            addVideoFragment(Video.ORANGE_1, R.id.videoContainer2);
            addVideoFragment(Video.ORANGE_1, R.id.videoContainer3);
        }
    }

    private void addVideoFragment(Video video, int containerViewId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, VideoFragment.newInstance(video.url))
                .commit();
    }
}
