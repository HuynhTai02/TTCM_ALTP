package com.huynhngoctai.ttcm_app_altp.view.dialog;


import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogSettingBinding;

public class SettingDialog extends Dialog {
    public DialogSettingBinding binding;
    public static final String STATE_BACKGROUND = "STATE_BACKGROUND";
    public static final String STATE_VOICE_READING = "STATE_VOICE_READING";
    public static final String STATE_DARK_MODE = "STATE_DARK_MODE";

    public SettingDialog(@NonNull Context context) {
        super(context);
        binding = DialogSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        checkStateBG();
        checkStateVoiceReading();
        checkStateDarkMode();
        addEvents();
    }

    private void addEvents() {
        clickEventBG();
        clickEventVoiceReading();
        clickEventDarkMode();
    }

    private void checkStateDarkMode() {
        boolean stateOfDarkMode = CommonUtils.getInstance().getPrefDefaultFalse(SettingDialog.STATE_DARK_MODE);
        if (!stateOfDarkMode) {
            binding.ivOnDark.setImageLevel(0);
        } else {
            binding.ivOnDark.setImageLevel(1);
        }
    }

    private void clickEventDarkMode() {
        binding.ivOnDark.setOnClickListener(v -> {
            binding.ivOnDark.setImageLevel(binding.ivOnDark.getDrawable().getLevel() == 0 ? 1 : 0);
            if (binding.ivOnDark.getDrawable().getLevel() == 0) {
                CommonUtils.getInstance().savePref(STATE_DARK_MODE, false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (binding.ivOnDark.getDrawable().getLevel() == 1) {
                CommonUtils.getInstance().savePref(STATE_DARK_MODE, true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }

    private void checkStateBG() {
        boolean stateOfBG = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (stateOfBG) {
            binding.ivOnMusic.setImageLevel(0);
        } else {
            binding.ivOnMusic.setImageLevel(1);
        }
    }

    private void clickEventBG() {
        binding.ivOnMusic.setOnClickListener(v -> {
            binding.ivOnMusic.setImageLevel(binding.ivOnMusic.getDrawable().getLevel() == 0 ? 1 : 0);
            if (binding.ivOnMusic.getDrawable().getLevel() == 0) {
                MediaManager.getInstance().resumeBG();
                CommonUtils.getInstance().savePref(STATE_BACKGROUND, true);
            } else {
                MediaManager.getInstance().pauseBG();
                CommonUtils.getInstance().savePref(STATE_BACKGROUND, false);
            }
        });
    }

    private void checkStateVoiceReading() {
        boolean stateOfRead = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
        if (stateOfRead) {
            binding.ivReadingOn.setImageLevel(0);
        } else {
            binding.ivReadingOn.setImageLevel(1);
        }
    }

    private void clickEventVoiceReading() {
        binding.ivReadingOn.setOnClickListener(v -> {
            binding.ivReadingOn.setImageLevel(binding.ivReadingOn.getDrawable().getLevel() == 0 ? 1 : 0);
            if (binding.ivReadingOn.getDrawable().getLevel() == 0) {
                MediaManager.getInstance().resumeSound();
                CommonUtils.getInstance().savePref(STATE_VOICE_READING, true);
            } else {
                MediaManager.getInstance().pauseSoundGame();
                CommonUtils.getInstance().savePref(STATE_VOICE_READING, false);
            }
        });
    }
}