package com.huynhngoctai.ttcm_app_altp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.huynhngoctai.ttcm_app_altp.Constants;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogHelpPercentBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnDialogListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelpPercentDialog extends Dialog {
    private static final int TIME_DELAY = 6000;
    public DialogHelpPercentBinding binding;
    private OnDialogListener onDialogListener;

    public void setDiaLogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    public HelpPercentDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpPercentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureChart();
        handleThanks();
    }

    private void handleThanks() {
        binding.btThanks.setOnClickListener(v -> {
            dismiss();
            if (onDialogListener != null) {
                onDialogListener.onDialogClosed();
            }
        });
    }

    private void configureChart() {
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Constants.ANSWER_ARRAY));
        xAxis.setTextColor(Color.WHITE);

        binding.barChart.setFitBars(true);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setDragEnabled(false);
        binding.barChart.setScaleEnabled(false);
        binding.barChart.setPinchZoom(false);
//        binding.barChart.setTouchEnabled(false);
        binding.barChart.animateY(TIME_DELAY);
    }

    public void updateChartData(int idAnswerTrue, List<Integer> listAnswerHidden) {
        float percentA, percentB, percentC, percentD;
        float remainingPercent = Constants.TOTAL_PERCENT;

        // Calculate percentage for each answer
        // AnswerTrue always > 3 Answer False
        switch (idAnswerTrue) {
            case 1:
                percentA = getRandomPercent(50, 60);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentB;
                percentC = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            case 2:
                percentB = getRandomPercent(45, 60);
                remainingPercent -= percentB;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentC = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            case 3:
                percentC = getRandomPercent(60, 90);
                remainingPercent -= percentC;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            default:
                percentD = getRandomPercent(50, 70);
                remainingPercent -= percentD;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                percentC = Constants.TOTAL_PERCENT - percentA - percentB - percentD;
                break;
        }

        //Check list Answer Hidde != null ?
        if (!listAnswerHidden.isEmpty()) {
            switch (idAnswerTrue) {
                case 1:
                    percentA = getRandomPercent(50, 100);
                    percentB = Constants.TOTAL_PERCENT - percentA;
                    percentC = Constants.TOTAL_PERCENT - percentA;
                    percentD = Constants.TOTAL_PERCENT - percentA;
                    break;
                case 2:
                    percentB = getRandomPercent(50, 70);
                    percentA = Constants.TOTAL_PERCENT - percentB;
                    percentC = Constants.TOTAL_PERCENT - percentB;
                    percentD = Constants.TOTAL_PERCENT - percentB;
                    break;
                case 3:
                    percentC = getRandomPercent(50, 60);
                    percentA = Constants.TOTAL_PERCENT - percentC;
                    percentB = Constants.TOTAL_PERCENT - percentC;
                    percentD = Constants.TOTAL_PERCENT - percentC;
                    break;
                default:
                    percentD = getRandomPercent(50, 65);
                    percentA = Constants.TOTAL_PERCENT - percentD;
                    percentB = Constants.TOTAL_PERCENT - percentD;
                    percentC = Constants.TOTAL_PERCENT - percentD;
                    break;
            }
        }

        //BarEntry to store data for each column
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, percentA));
        entries.add(new BarEntry(1f, percentB));
        entries.add(new BarEntry(2f, percentC));
        entries.add(new BarEntry(3f, percentD));

        //Remove hidden answers
        for (int answerIndex : listAnswerHidden) {
            entries.get(answerIndex - 1).setY(0f);
        }

        //BarDataSet to store data and configure for the columns of chart
        BarDataSet dataSet = new BarDataSet(entries, "Đáp án");
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextSize(12f);
        dataSet.setBarBorderWidth(0.8f);

        //BarData containing the configured BarDataSet
        BarData barData = new BarData(dataSet);
        binding.barChart.setData(barData);
        binding.barChart.invalidate();

        new Handler().postDelayed(() -> binding.btThanks.setVisibility(View.VISIBLE), TIME_DELAY);
    }

    //Random integer between min max
    private float getRandomPercent(float min, float max) {
        Random random = new Random();
        return random.nextInt((int) (max - min + 1)) + min;
    }
}