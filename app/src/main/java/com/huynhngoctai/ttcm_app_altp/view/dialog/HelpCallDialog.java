package com.huynhngoctai.ttcm_app_altp.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.Constants;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogHelpCallBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnDialogListener;

public class HelpCallDialog extends Dialog {
    private final DialogHelpCallBinding binding;
    //Get state music ? true or false
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    private OnDialogListener onDialogListener;
    private String answerHelp;

    public HelpCallDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleThanks();
    }

    public void getAnswerCall(int idAnswerTrue) {
        if (!stateOfMusic) {
            showAnswer(idAnswerTrue);
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.time_call, mp -> showAnswer(idAnswerTrue));
        }
    }

    @SuppressLint("SetTextI18n")
    private void showAnswer(int idAnswerTrue) {
        answerHelp = Constants.ANSWER_ARRAY[idAnswerTrue - 1];

        if (!stateOfMusic) {
            binding.tvAnswer.setText("Đáp án là: " + answerHelp);
            binding.btThanks.setVisibility(View.VISIBLE);
        } else {
            MediaManager.getInstance().playSoundGame(MediaManager.HELP_CALL[idAnswerTrue - 1], mp -> {
                binding.tvAnswer.setText("Đáp án là: " + answerHelp);
                binding.btThanks.setVisibility(View.VISIBLE);
            });
        }
    }

    private void handleThanks() {
        binding.btThanks.setOnClickListener(v -> {
            dismiss();
            if (onDialogListener != null) {
                onDialogListener.onDialogClosed();
            }
        });
    }

    public void setDiaLogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
}