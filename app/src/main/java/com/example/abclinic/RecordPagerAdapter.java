package com.example.abclinic;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RecordPagerAdapter extends FragmentPagerAdapter {
    private String[] datas;
    private int currentPosition = -1;

    public RecordPagerAdapter(@NonNull FragmentManager fm, String... datas) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.datas = datas;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RecordFragment.getInstance(datas[position]);
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ghi chú";
            case 1:
                return "Chẩn đoán";
            case 2:
                return "Kê đơn";
            default:
                return null;
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        if (position != currentPosition && container instanceof CustomViewPager) {
            Fragment fragment = (Fragment) object;
            CustomViewPager pager = (CustomViewPager) container;

            if (fragment != null && fragment.getView() != null) {
                currentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}