package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.App;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment<FragmentSplashBinding> {
    private static final long TIME_DELAY = 2000;
    public static String TAG = SplashFragment.class.getName();

    @Override
    protected FragmentSplashBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSplashBinding.inflate(inflater, container, false);
    }


    @Override
    protected void initViews() {
        binding.imvLogo.startAnimation(AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(), R.anim.zoom));

        new Handler().postDelayed(() -> callBack.showFragment(OnBoardingFragment.TAG, false), TIME_DELAY);
        MediaManager.getInstance().stopBG();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> callBack.showFragment(OnBoardingFragment.TAG, false), TIME_DELAY);
        MediaManager.getInstance().stopBG();
    }
}