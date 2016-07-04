package com.yiming.jianyue.view.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yiming.jianyue.controller.fragment.tab.AcBangumiFragment;
import com.yiming.jianyue.controller.fragment.tab.AcEssayFragment;
import com.yiming.jianyue.controller.fragment.tab.AcNavigationFragment;
import com.yiming.jianyue.controller.fragment.tab.AcRecommendFragment;
import com.yiming.jianyue.model.api.acfun.AcString;


/**
 * Created by succlz123 on 15/8/10.
 */
public class AcMainFmAdapter extends FragmentPagerAdapter {

    public AcMainFmAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AcRecommendFragment();
            case 1:
                return new AcNavigationFragment();
            case 2:
                return new AcBangumiFragment();
            case 3:
                return AcEssayFragment.newInstance(AcString.ESSAY);
        }
        return null;
    }

    @Override
    public int getCount() {
        return AcString.MAIN_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcString.MAIN_TITLES[position];
    }
}
