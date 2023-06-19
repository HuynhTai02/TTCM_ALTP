package com.huynhngoctai.ttcm_app_altp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.huynhngoctai.ttcm_app_altp.R;


public class BoardingAdapter extends PagerAdapter {

    private final LayoutInflater layoutInflater;

    public BoardingAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    int[] imageArray = {
            R.drawable.ic_onboarding1,
            R.drawable.ic_onboarding2,
            R.drawable.ic_onboarding3
    };

    int[] tittleArray = {
            R.string.txt_title1,
            R.string.txt_title2,
            R.string.txt_title3
    };

    int[] descripArray = {
            R.string.txt_description1,
            R.string.txt_description2,
            R.string.txt_description3
    };

    @Override
    public int getCount() {
        return tittleArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = layoutInflater.inflate(R.layout.item_boarding, container, false);

        ImageView imageView = v.findViewById(R.id.imageOnboarding);
        TextView txtTittle = v.findViewById(R.id.textTitle);
        TextView txtDescription = v.findViewById(R.id.textDescription);

        imageView.setImageResource(imageArray[position]);
        txtTittle.setText(tittleArray[position]);
        txtDescription.setText(descripArray[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
