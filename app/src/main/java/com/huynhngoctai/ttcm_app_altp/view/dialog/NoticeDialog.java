package com.huynhngoctai.ttcm_app_altp.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.huynhngoctai.ttcm_app_altp.databinding.DialogNoticeBinding;

public class NoticeDialog extends Dialog {
    private final DialogNoticeBinding binding;

    public NoticeDialog(@NonNull Context context) {
        super(context);
        binding = DialogNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void createCustomDialog(boolean isOutSidTouch, String title, String description, String btNameNo, String btNameYes, View.OnClickListener onClickListener) {
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding.tvName.setText(title);
        binding.tvDescription.setText(description);
        setCanceledOnTouchOutside(isOutSidTouch);
        setCancelable(isOutSidTouch);

        if(btNameNo == null){
            binding.btNo.setVisibility(View.GONE);
        }else {
            binding.btNo.setText(btNameNo);
        }

        if(btNameYes == null){
            binding.btYes.setVisibility(View.GONE);
        }else {
            binding.btYes.setText(btNameYes);
        }

        binding.btYes.setOnClickListener(onClickListener);
        binding.btNo.setOnClickListener(onClickListener);
    }
}

