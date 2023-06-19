package com.huynhngoctai.ttcm_app_altp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.database.entities.ScoreUser;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private final List<ScoreUser> scoreUserList;
    private final int[] imageArray = {R.drawable.ic_top1, R.drawable.ic_top2, R.drawable.ic_top3,
            R.drawable.ic_top4, R.drawable.ic_top5, R.drawable.ic_top6, R.drawable.ic_top7,
            R.drawable.ic_top8, R.drawable.ic_top9, R.drawable.ic_top10, R.drawable.ic_top11,
            R.drawable.ic_top12, R.drawable.ic_top13, R.drawable.ic_top14, R.drawable.ic_top15,
            R.drawable.ic_top16, R.drawable.ic_top17, R.drawable.ic_top18, R.drawable.ic_top19,
            R.drawable.ic_top20};

    public RankingAdapter(List<ScoreUser> scoreUserList) {
        this.scoreUserList = scoreUserList;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new RankingViewHolder(rankingView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        ScoreUser scoreUser = scoreUserList.get(position);
        if (scoreUser == null) {
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formatNumber = decimalFormat.format(scoreUser.score);
        holder.tvMilestone.setText(formatNumber + " VND");

        Random random = new Random();
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        holder.tvNameUser.setTextColor(color);
        holder.tvNameUser.setText(scoreUser.nameUser);

        holder.ivTop.setImageResource(imageArray[position]);
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: "+scoreUserList.size());
        return scoreUserList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivTop;
        private final TextView tvNameUser;
        private final TextView tvMilestone;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTop = itemView.findViewById(R.id.iv_top);
            tvMilestone = itemView.findViewById(R.id.tv_milestone);
            tvNameUser = itemView.findViewById(R.id.tv_name_user);
        }
    }
}
