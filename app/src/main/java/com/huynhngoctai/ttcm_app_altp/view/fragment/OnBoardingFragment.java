package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.adapter.BoardingAdapter;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentOnboardingBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnPageChangeListener;
public class OnBoardingFragment extends BaseFragment<FragmentOnboardingBinding> {
    public static String TAG = OnBoardingFragment.class.getName();
    public static final String STATE_FIRST = "STATE_FIRST";

    @Override
    protected FragmentOnboardingBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentOnboardingBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        callAdapter();
        checkFirst();
        addDots(0);
        addEvents();
    }

    private void addEvents() {
        binding.skipBtn.setOnClickListener(v -> {
            CommonUtils.getInstance().savePref(STATE_FIRST, true);
            callBack.showFragment(HomeFragment.TAG, false);
        });
        binding.getStartedBtn.setOnClickListener(v -> {
            CommonUtils.getInstance().savePref(STATE_FIRST, true);
            callBack.showFragment(HomeFragment.TAG, false);
        });

        binding.slider.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeSelectedDots(position);
            }
        });
    }

    private void changeSelectedDots(int position) {
        addDots(position);

        if (position == 0) {
            binding.getStartedBtn.setVisibility(View.INVISIBLE);
            binding.skipBtn.setVisibility(View.VISIBLE);
            binding.idGif1.setVisibility(View.VISIBLE);
            binding.idGif2.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            binding.getStartedBtn.setVisibility(View.INVISIBLE);
            binding.skipBtn.setVisibility(View.VISIBLE);
            binding.idGif1.setVisibility(View.VISIBLE);
            binding.idGif2.setVisibility(View.INVISIBLE);
        } else {
            binding.idGif1.setVisibility(View.INVISIBLE);
            binding.idGif2.setVisibility(View.VISIBLE);
            binding.getStartedBtn.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slider));
            binding.getStartedBtn.setVisibility(View.VISIBLE);
            binding.skipBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void checkFirst() {
        boolean isFirst = CommonUtils.getInstance().getPrefDefaultFalse(STATE_FIRST);
        if (isFirst) {
            callBack.showFragment(HomeFragment.TAG, false);
        }
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
        BoardingAdapter boardingAdapter = new BoardingAdapter(getActivity());
        binding.slider.setAdapter(boardingAdapter);
    }
}