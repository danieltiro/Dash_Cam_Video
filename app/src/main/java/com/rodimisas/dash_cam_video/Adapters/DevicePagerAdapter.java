package com.rodimisas.dash_cam_video.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rodimisas.dash_cam_video.Fragments.MapDeviceFragment;
import com.rodimisas.dash_cam_video.Fragments.VideoFragment;

public class DevicePagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    Bundle args;
    public DevicePagerAdapter(FragmentManager fm, int numberOfTabs, Bundle args) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.args = args;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment videoFragment = new VideoFragment();
                videoFragment.setArguments(args);
                return videoFragment;
            case 1:
                Fragment mapFragment = new MapDeviceFragment();
                mapFragment.setArguments(args);
                return mapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
