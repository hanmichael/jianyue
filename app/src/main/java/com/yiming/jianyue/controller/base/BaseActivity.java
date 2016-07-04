package com.yiming.jianyue.controller.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;
import com.yiming.jianyue.AppClient;
import com.yiming.jianyue.R;
import com.yiming.jianyue.model.config.BusProvider;

import butterknife.ButterKnife;

/**
 * Created by wengyiming on 2015/11/18.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static String TAG = "BaseActivity";

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract Activity getActivity();

    public abstract void initView(Bundle savedInstanceState);

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        TAG = getActivity().getClass().getSimpleName();
        ButterKnife.bind(getActivity());
        BusProvider.getInstance().register(this);
        initToolBar();
        initView(savedInstanceState);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null)
            return;
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(getActivity());
        super.onDestroy();
        BusProvider.getInstance().unregister(getActivity());
        RefWatcher refWatcher = AppClient.getRefWatcher(getActivity());
        refWatcher.watch(getActivity());
    }
}
