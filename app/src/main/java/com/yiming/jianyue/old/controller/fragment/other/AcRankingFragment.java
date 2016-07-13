package com.yiming.jianyue.old.controller.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yiming.jianyue.AppClient;
import com.yiming.jianyue.R;
import com.yiming.jianyue.old.controller.activity.acfun.AcContentActivity;
import com.yiming.jianyue.old.controller.base.BaseFragment;
import com.yiming.jianyue.old.model.api.acfun.AcApi;
import com.yiming.jianyue.old.model.api.acfun.AcString;
import com.yiming.jianyue.old.model.bean.acfun.AcReOther;
import com.yiming.jianyue.old.utils.common.GlobalUtils;
import com.yiming.jianyue.old.utils.common.ViewUtils;
import com.yiming.jianyue.old.view.adapter.recyclerview.AcRankingRvAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by succlz123 on 2015/7/27.
 */
public class AcRankingFragment extends BaseFragment {

    public static AcRankingFragment newInstance(String channelType) {
        AcRankingFragment fragment = new AcRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcRankingRvAdapter mAdapter;
    private GridLayoutManager mManager;
    private int mPagerNoNum = 1;

    @Bind(R.id.ac_fragment_partition_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);
        ButterKnife.bind(this, view);
        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcRankingRvAdapter.MyDecoration());
        mAdapter = new AcRankingRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AcRankingRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                AcContentActivity.startActivity(getActivity(), contentId);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {

                    getHttpResult("" + mPagerNoNum);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcString.PAGE_NO_NUM_1);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
            if (mAdapter.getmAcReOther() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(AcString.PAGE_NO_NUM_1);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
        }
    }

    private void getHttpResult(final String pagerNoNum) {
        //排行榜
        Call<AcReOther> call = AcApi.getAcRanking().onRankingResult(AcApi.buildAcRankingUrl
                (mPartitionType, pagerNoNum));
        call.enqueue(new Callback<AcReOther>() {
            @Override
            public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                AcReOther acReOther = response.body();
                if (acReOther != null
                        && getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcRankingFragment.this.getUserVisibleHint()) {
                    if (acReOther.getData() != null) {
                        if (acReOther.getData().getPage().getList().size() != 0) {
                            if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                mAdapter.addAcReOtherDate(acReOther);
                            } else {
                                mAdapter.setmAcReOther(acReOther);
                            }
                            mPagerNoNum++;
                        } else {
                            GlobalUtils.showToastShort(getActivity(), "没有更多了 (´･ω･｀)");
                        }
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<AcReOther> call, Throwable t) {
                if (getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcRankingFragment.this.getUserVisibleHint()) {
                    GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }

        });
    }
}
