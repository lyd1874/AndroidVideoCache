package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.danikula.videocache.sample.databinding.ActivityVideoGalleryBinding;


public class VideoGalleryActivity extends FragmentActivity {

    private ActivityVideoGalleryBinding mBinding;


    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("lyd"," onCreate ---4");
        mBinding = ActivityVideoGalleryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        afterViewInjected();
    }

    void afterViewInjected() {
        ViewsPagerAdapter viewsPagerAdapter = new ViewsPagerAdapter(this);
        mBinding.viewPager.setAdapter(viewsPagerAdapter);
        mBinding.viewPagerIndicator.setViewPager(mBinding.viewPager);
    }

    private static final class ViewsPagerAdapter extends FragmentStatePagerAdapter {

        public ViewsPagerAdapter(FragmentActivity activity) {
            super(activity.getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Video video = Video.values()[position];
            return GalleryVideoFragment.newInstance(video.url);
        }

        @Override
        public int getCount() {
            return Video.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Video.values()[position].name();
        }
    }
}
