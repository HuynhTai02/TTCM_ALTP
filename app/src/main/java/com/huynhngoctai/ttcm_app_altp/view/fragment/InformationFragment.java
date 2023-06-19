package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.databinding.FragmentInfomationBinding;

public class InformationFragment extends BaseFragment<FragmentInfomationBinding> {
    public static final String TAG = InformationFragment.class.getName();
    @Override
    protected FragmentInfomationBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentInfomationBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        callBack.showFragment(HomeFragment.TAG,false);
    }
}