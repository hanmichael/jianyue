package com.yiming.jianyue.old.controller.fragment.other;

import com.yiming.jianyue.R;
import com.yiming.jianyue.old.controller.base.BaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/18.
 */
public class AcMainFragment extends BaseFragment {

    public static AcMainFragment newInstance(String channelType) {
        return new AcMainFragment();
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main, container, false);
        ButterKnife.bind(this, view);


        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}

