package com.yiming.jianyue.old.view.adapter.fragment;

import com.yiming.jianyue.old.controller.fragment.tab.AcBangumiFragment;
import com.yiming.jianyue.old.controller.fragment.tab.AcEssayFragment;
import com.yiming.jianyue.old.controller.fragment.tab.AcNavigationFragment;
import com.yiming.jianyue.old.controller.fragment.tab.AcRecommendFragment;
import com.yiming.jianyue.old.model.api.acfun.AcString;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


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
                return new AcRecommendFragment();//首页推荐
            case 1:
                return new AcNavigationFragment();//分区导航
            case 2:
                return new AcBangumiFragment();//新番专题
            case 3:
                return AcEssayFragment.newInstance(AcString.ESSAY);//文章专区 暂时不可用
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
