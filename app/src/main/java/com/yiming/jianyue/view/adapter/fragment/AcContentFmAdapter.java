package com.yiming.jianyue.view.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yiming.jianyue.controller.fragment.other.AcContentInfoFragment;
import com.yiming.jianyue.controller.fragment.other.AcContentReplyFragment;
import com.yiming.jianyue.model.api.acfun.AcString;


/**
 * Created by succlz123 on 15/8/10.
 */
public class AcContentFmAdapter extends FragmentStatePagerAdapter {
    private String mContentId;

    public AcContentFmAdapter(FragmentManager fm, String contentId) {
        super(fm);
        this.mContentId = contentId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AcContentInfoFragment.newInstance(mContentId);
            case 1:
                return AcContentReplyFragment.startFragment(mContentId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcString.CONTENT_VIEW_PAGER_TITLE[position];
    }
}
