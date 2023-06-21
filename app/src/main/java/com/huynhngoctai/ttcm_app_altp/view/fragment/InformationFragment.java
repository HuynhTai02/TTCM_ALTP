package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentInfomationBinding;

public class InformationFragment extends BaseFragment<FragmentInfomationBinding> {
    public static final String TAG = InformationFragment.class.getName();

    @Override
    protected FragmentInfomationBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentInfomationBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        addEvents();
    }

    private void addEvents() {
        binding.ivBack.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.iv_back) {
            callBack.showFragment(HomeFragment.TAG, false);
        } else if (v.getId() == R.id.iv_share) {
            shareToLink(v);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void shareToLink(View v) {
                String link = "https://github.com/HuynhTai02/TTCM_ALTP.git";
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, link);
                shareIntent.setType("text/plain");

                // Tạo một Intent mới để hiển thị danh sách các ứng dụng chia sẻ khả dụng trên thiết bị
                Intent shareChooser = Intent.createChooser(shareIntent, null);

                //Kiểm tra có ứng dụng chia sẻ khả dụng trên thiết bị không?
                if (shareIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(shareChooser);
                } else {
                    Toast.makeText(v.getContext(), "Không có ứng dụng khả dụng nào để chia sẻ", Toast.LENGTH_SHORT).show();
                }
    }
}