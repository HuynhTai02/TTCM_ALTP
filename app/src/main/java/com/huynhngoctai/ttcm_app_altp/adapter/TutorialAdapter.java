package com.huynhngoctai.ttcm_app_altp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.huynhngoctai.ttcm_app_altp.R;

public class TutorialAdapter extends PagerAdapter {

    private final LayoutInflater layoutInflater;

    public TutorialAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    int[] imageArray = {
            R.drawable.ic_help1,
            R.drawable.ic_help2,
            R.drawable.ic_thanks
    };

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = layoutInflater.inflate(R.layout.item_tutorial, container, false);

        ImageView imageView = v.findViewById(R.id.iv_name);
        imageView.setImageResource(imageArray[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
