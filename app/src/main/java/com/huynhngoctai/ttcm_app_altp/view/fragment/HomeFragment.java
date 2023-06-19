package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentHomeBinding;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SettingDialog;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    public static String TAG = HomeFragment.class.getName();

    @Override
    protected FragmentHomeBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }


    @Override
    protected void initViews() {
        MediaManager.getInstance().playBG(R.raw.welcomegame, mp -> MediaManager.getInstance().playBG(R.raw.bgmusic));
        addEvents();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (!stateOfMusic) {
            MediaManager.getInstance().pauseBG();
        }
    }

    private void addEvents() {
        binding.ivPlayGame.setOnClickListener(this);
        binding.ivInfo.setOnClickListener(this);
        binding.ivHelp.setOnClickListener(this);
        binding.ivRank.setOnClickListener(this);
        binding.ivSetting.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.iv_play_game) {
            showRuleFragment();
        } else if (v.getId() == R.id.iv_help) {
            showHelpFragment();
        } else if (v.getId() == R.id.iv_info) {
            showInfoFragment();
        } else if (v.getId() == R.id.iv_rank) {
            showRankFragment();
        } else if (v.getId() == R.id.iv_setting) {
            showSettingDialog();
        }

        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
    }

    private void showSettingDialog() {
        SettingDialog settingDialog = new SettingDialog(context);
        settingDialog.show();
    }

    private void showRuleFragment() {
        MediaManager.getInstance().stopBG();
        callBack.showFragment(RuleFragment.TAG, true);
    }

    private void showRankFragment() {
        callBack.showFragment(RankingFragment.TAG, true);
    }

    private void showHelpFragment() {
        callBack.showFragment(TutorialFragment.TAG, true);
    }

    private void showInfoFragment() {
        callBack.showFragment(InformationFragment.TAG, true);
    }
}

