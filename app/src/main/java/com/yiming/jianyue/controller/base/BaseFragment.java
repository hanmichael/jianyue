package com.yiming.jianyue.controller.base;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;
import com.yiming.jianyue.AppClient;

import butterknife.ButterKnife;

/**
 * 项目名称：jianyue
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：15/11/17 下午9:48
 * 修改人：wengyiming
 * 修改时间：15/11/17 下午9:48
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        RefWatcher refWatcher = AppClient.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}

