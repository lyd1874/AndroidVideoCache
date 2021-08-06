package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.danikula.videocache.sample.databinding.ActivitySingleVideoBinding;


public class SingleVideoActivity extends FragmentActivity {

    private ActivitySingleVideoBinding mBinding;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.e("lyd"," onCreate ---2");
        mBinding = ActivitySingleVideoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        if (state == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, VideoFragment.newInstance(Video.ORANGE_1.url))
                    .commit();
        }
    }
}
