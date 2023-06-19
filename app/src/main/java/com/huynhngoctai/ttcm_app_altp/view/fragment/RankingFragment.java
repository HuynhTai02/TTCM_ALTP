package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.huynhngoctai.ttcm_app_altp.App;
import com.huynhngoctai.ttcm_app_altp.adapter.RankingAdapter;
import com.huynhngoctai.ttcm_app_altp.database.entities.ScoreUser;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentRankingBinding;

import java.util.List;

public class RankingFragment extends BaseFragment<FragmentRankingBinding> {
    public static String TAG = RankingFragment.class.getName();
    private List<ScoreUser> scoreUserList;
    private RankingAdapter rankingAdapter;

    @Override
    protected FragmentRankingBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRankingBinding.inflate(inflater, container, false);
    }


    @Override
    protected void initViews() {
        initAdapter();
        addEvents();
    }

    private void addEvents() {
        binding.ivBack.setOnClickListener(this);

    }

    private void initAdapter() {
        new Thread(() -> {
            scoreUserList = App.getInstance().getDatabase().getScoreUserDAO().getAllScoreUser();
            requireActivity().runOnUiThread(() -> {
                rankingAdapter = new RankingAdapter(scoreUserList);
                binding.rvRanking.setAdapter(rankingAdapter);
                Log.i(RankingFragment.TAG,scoreUserList.toString() + scoreUserList.size());
            });
        }).start();
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        callBack.showFragment(HomeFragment.TAG, false);
    }
}
