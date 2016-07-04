package com.yiming.jianyue.controller.activity.acfun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yiming.jianyue.R;
import com.yiming.jianyue.controller.fragment.other.AcPartitionFragment;
import com.yiming.jianyue.model.api.acfun.AcString;
import com.yiming.jianyue.controller.base.BaseActivity;
import com.yiming.jianyue.view.adapter.fragment.AcPartitionFmAdapter;

import butterknife.Bind;

/**
 * Created by succlz123 on 2015/7/27.
 */
public class AcPartitionActivity extends BaseActivity {

    public static void startActivity(Context activity, String partitionType) {
        Intent intent = new Intent(activity, AcPartitionActivity.class);
        intent.putExtra(AcString.CHANNEL_IDS, partitionType);
        activity.startActivity(intent);
    }

    @Bind(R.id.fragment_partition_tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.fragment_partition_viewpager)
    ViewPager mViewPager;

    private String mPartitionType;

    @Override
    public int getLayoutRes() {
        return R.layout.ac_activity_partition;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //根据partitionType来判断什么分区
        toolbar.setTitle(mPartitionType);
        mPartitionType = getIntent().getStringExtra(AcString.CHANNEL_IDS);
        AcPartitionFmAdapter adapter = new AcPartitionFmAdapter(getSupportFragmentManager(), mPartitionType);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        if (TextUtils.equals(mPartitionType, AcString.TITLE_HOT)) {
            mTabLayout.setVisibility(View.GONE);
        }
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setHttpOrder(String order, String title) {
        SharedPreferences settings = getSharedPreferences(getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(AcString.ORDER_BY, order);
        editor.putString(AcString.TITLE, title);
        editor.commit();

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.getUserVisibleHint() && fragment instanceof AcPartitionFragment) {
                ((AcPartitionFragment) fragment).setHttpOrder();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!TextUtils.equals(mPartitionType, AcString.TITLE_HOT)
                && !TextUtils.equals(mPartitionType, AcString.TITLE_RANKING)) {
            MenuItem timeOrderItem = menu.add(Menu.NONE, 1, 100, AcString.TITLE_TIME_ORDER);
            MenuItem lastPostItem = menu.add(Menu.NONE, 2, 100, AcString.TITLE_LAST_POST);
            MenuItem mostReplyItem = menu.add(Menu.NONE, 3, 100, AcString.TITLE_MOST_REPLY);
            MenuItem popularityItem = menu.add(Menu.NONE, 4, 100, AcString.TITLE_POPULARITY);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case 1:
                setHttpOrder(AcString.TIME_ORDER, AcString.TITLE_TIME_ORDER);
                break;
            case 2:
                setHttpOrder(AcString.LAST_POST, AcString.TITLE_LAST_POST);
                break;
            case 3:
                setHttpOrder(AcString.MOST_REPLY, AcString.TITLE_MOST_REPLY);
                break;
            case 4:
                setHttpOrder(AcString.POPULARITY, AcString.TITLE_POPULARITY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
