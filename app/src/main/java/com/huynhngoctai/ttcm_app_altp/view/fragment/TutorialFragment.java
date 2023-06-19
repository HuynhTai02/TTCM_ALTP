package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.adapter.TutorialAdapter;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentTutorialBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnPageChangeListener;

public class TutorialFragment extends BaseFragment<FragmentTutorialBinding> {
    public static String TAG = TutorialFragment.class.getName();

    @Override
    protected FragmentTutorialBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentTutorialBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        callAdapter();
        addDots(0);
        addEvents();
    }

    private void addEvents() {
        binding.slider.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                addDots(position);
            }
        });

        binding.ivBack.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        callBack.showFragment(HomeFragment.TAG, false);
    }

    private void addDots(int position) {
        TextView[] dots = new TextView[3];
        binding.dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            binding.dots.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.orange));
    }

    private void callAdapter() {
        TutorialAdapter tutorialAdapter = new TutorialAdapter(getActivity());
        binding.slider.setAdapter(tutorialAdapter);
    }
}