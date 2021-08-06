package com.danikula.videocache.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.sample.databinding.FragmentVideoBinding;


import java.io.File;

public class GalleryVideoFragment extends Fragment implements CacheListener {

//    @FragmentArg String url;

//    @InstanceState int position;
//    @InstanceState boolean playerStarted;

    String url;

    public static GalleryVideoFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url",url);
        GalleryVideoFragment fragment = new GalleryVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    int position;
    boolean playerStarted;

    private boolean visibleForUser;

    private FragmentVideoBinding mBinding;

    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentVideoBinding.inflate(inflater);
        this.url= getArguments().getString("url");
        afterViewInjected();
        mBinding.progressBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekVideo();
            }
        });
        return mBinding.getRoot();
    }


    void afterViewInjected() {
        startProxy();

        if (visibleForUser) {
            startPlayer();
        }
    }

    private void startPlayer() {
        mBinding.videoView.seekTo(position);
        mBinding.videoView.start();
        playerStarted = true;
    }

    private void startProxy() {
        HttpProxyCacheServer proxy = App.getProxy(getActivity());
        proxy.registerCacheListener(this, url);
        mBinding.videoView.setVideoPath(proxy.getProxyUrl(url));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        visibleForUser = isVisibleToUser;
        if (mBinding.videoView != null) {
            if (visibleForUser) {
                startPlayer();
            } else if (playerStarted) {
                position = mBinding.videoView.getCurrentPosition();
                mBinding.videoView.pause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updater.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        updater.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mBinding.videoView.stopPlayback();
        App.getProxy(getActivity()).unregisterCacheListener(this);
    }

    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        mBinding.progressBar1.setSecondaryProgress(percentsAvailable);
    }

    private void updateVideoProgress() {
        int videoProgress =  mBinding.videoView.getCurrentPosition() * 100 /  mBinding.videoView.getDuration();
        mBinding.progressBar1.setProgress(videoProgress);
    }

    void seekVideo() {
        int videoPosition =  mBinding.videoView.getDuration() *  mBinding.progressBar1.getProgress() / 100;
        mBinding.videoView.seekTo(videoPosition);
    }

    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
