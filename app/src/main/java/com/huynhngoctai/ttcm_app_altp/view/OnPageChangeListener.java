package com.huynhngoctai.ttcm_app_altp.view;

import androidx.viewpager.widget.ViewPager;

public interface OnPageChangeListener extends ViewPager.OnPageChangeListener {
    @Override
    default void onPageScrollStateChanged(int state) {

    }

    @Override
    default void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
