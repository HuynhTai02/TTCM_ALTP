package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentRuleBinding;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SettingDialog;

public class RuleFragment extends BaseFragment<FragmentRuleBinding> {
    public static String TAG = RuleFragment.class.getName();
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);

    @Override
    protected FragmentRuleBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRuleBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        addEvents();
        voiceReadingRule();
    }

    private void voiceReadingRule() {
        if (!stateOfMusic) {
            binding.iv50.setVisibility(View.VISIBLE);
            binding.ivPhone.setVisibility(View.VISIBLE);
            binding.ivPercent.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                binding.ivStart.setVisibility(View.VISIBLE);
                binding.ivStart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slider));
            }, 1000);
            binding.lnRuleMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slider));
            animationSelectTrueAnswer(binding.tvLv5);
            animationSelectTrueAnswer(binding.tvLv10);
            animationSelectTrueAnswer(binding.tvLv15);
        } else {
            loadSoundRule();
        }
    }

    private void loadSoundRule() {
        //Animation
        binding.lnRuleMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slider_slow));

        startSoundRule();

        //Animation milestone 5 10 15
        new Handler().postDelayed(() -> {
            animationSelectTrueAnswer(binding.tvLv5);
            animationSelectTrueAnswer(binding.tvLv10);
            animationSelectTrueAnswer(binding.tvLv15);
        }, 4500);

        visibleIconHelp();
    }

    private void startSoundRule() {
        //Sound Rule
        MediaManager.getInstance().playSoundGame(R.raw.rule, mp -> {
            MediaManager.getInstance().playSoundGame(R.raw.ping, null);
            binding.ivStart.setVisibility(View.VISIBLE);
            binding.ivStart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slider));
        });
    }

    private void visibleIconHelp() {
        //Visible icon help
        new Handler().postDelayed(() -> binding.iv50.setVisibility(View.VISIBLE), 7600);

        new Handler().postDelayed(() -> binding.ivPhone.setVisibility(View.VISIBLE), 8400);

        new Handler().postDelayed(() -> binding.ivPercent.setVisibility(View.VISIBLE), 9400);
    }

    private void animationSelectTrueAnswer(View v) {
        Animation animation = new AlphaAnimation(2.0f, 0.0f);
        animation.setDuration(100);
        animation.setRepeatCount(10);
        v.startAnimation(animation);
    }

    private void addEvents() {
        binding.ivStart.setVisibility(View.INVISIBLE);
        binding.ivBack.setOnClickListener(this);
        binding.ivStart.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.iv_back) {
            MediaManager.getInstance().stopSoundGame();
            callBack.showFragment(HomeFragment.TAG, false);
        } else if (v.getId() == R.id.iv_start) {
            binding.ivStart.setEnabled(false);
            if (!stateOfMusic) {
                callBack.showFragment(PlayGameFragment.TAG, false);
            } else {
                MediaManager.getInstance().playSoundGame(R.raw.play, mp -> callBack.showFragment(PlayGameFragment.TAG, false));
            }
        }

        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
    }
}