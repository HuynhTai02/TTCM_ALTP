package com.huynhngoctai.ttcm_app_altp.view.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogConfirmSelectAnsBinding;


public class ConfirmSelectAnsDialog extends Dialog {
    private final DialogConfirmSelectAnsBinding binding;
    public static final String STATE_CONFIRM = "STATE_CONFIRM";
    private OnConfirmationChangeListener confirmationChangeListener;

    public interface OnConfirmationChangeListener {
        void onConfirmationChanged(boolean confirmed);
    }

    public ConfirmSelectAnsDialog(@NonNull Context context) {
        super(context);
        binding = DialogConfirmSelectAnsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        checkStateConfirm();
        addEvents();
    }

    private void checkStateConfirm() {
        boolean stateOfBG = CommonUtils.getInstance().getPrefDefaultTrue(ConfirmSelectAnsDialog.STATE_CONFIRM);
        if (stateOfBG) {
            binding.ivConfirm.setImageLevel(0);
        } else {
            binding.ivConfirm.setImageLevel(1);
        }
    }

    private void addEvents() {
        binding.ivConfirm.setOnClickListener(v -> {
            binding.ivConfirm.setImageLevel(binding.ivConfirm.getDrawable().getLevel() == 0 ? 1 : 0);
            boolean confirmed = (binding.ivConfirm.getDrawable().getLevel() == 0);
            if (confirmed) {
                CommonUtils.getInstance().savePref(STATE_CONFIRM, true);
            } else {
                CommonUtils.getInstance().savePref(STATE_CONFIRM, false);
            }

            if (confirmationChangeListener != null) {
                confirmationChangeListener.onConfirmationChanged(confirmed);
            }
        });
    }

    public void setOnConfirmationChangeListener(OnConfirmationChangeListener listener) {
        confirmationChangeListener = listener;
    }
}