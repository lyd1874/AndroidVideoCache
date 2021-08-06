package com.danikula.videocache.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;


import com.danikula.videocache.sample.databinding.ActivityMenuBinding;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends FragmentActivity {

    private ActivityMenuBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.cleanCacheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearCacheButtonClick();
            }
        });
        onViewInjected();
    }

    void onViewInjected() {
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, buildListData());
        mBinding.listView.setAdapter(adapter);

        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClicked(position);
            }
        });
    }

    @NonNull
    private List<ListEntry> buildListData() {
        return Arrays.asList(
                new ListEntry("Single Video", SingleVideoActivity.class),
                new ListEntry("Multiple Videos", MultipleVideosActivity.class),
                new ListEntry("Video Gallery with pre-caching", VideoGalleryActivity.class),
                new ListEntry("Shared Cache", SharedCacheActivity.class)
        );
    }

    private void skip(Class clazz) {
        Log.e("lyd", " skip " + clazz.getSimpleName() + "  " + clazz.toString());
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    void onListItemClicked(int position) {
        ListEntry item = (ListEntry) mBinding.listView.getAdapter().getItem(position);
        startActivity(new Intent(this, item.activityClass));
    }

    void onClearCacheButtonClick() {
        try {

            Utils.cleanVideoCacheDir(this);
        } catch (IOException e) {
            Log.e(null, "Error cleaning cache", e);
            Toast.makeText(this, "Error cleaning cache", Toast.LENGTH_LONG).show();
        }
    }

    private static final class ListEntry {

        private final String title;
        private final Class activityClass;

        public ListEntry(String title, Class activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
