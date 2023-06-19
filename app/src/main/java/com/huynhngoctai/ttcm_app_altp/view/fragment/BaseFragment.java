package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.huynhngoctai.ttcm_app_altp.view.OnMainCallBack;

public abstract class BaseFragment<B extends ViewBinding>
        extends Fragment implements View.OnClickListener {
    protected Context context;
    protected B binding;
    protected OnMainCallBack callBack;

    public final void setOnMainCallBack(OnMainCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public final void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        binding = initViewBinding(inflater, container);
        initViews();
        return binding.getRoot();
    }

    protected abstract B initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract void initViews();

    @Override
    public final void onClick(View v) {
        clickView(v);
    }

    protected void clickView(View v) {
        //do nothing
    }
}