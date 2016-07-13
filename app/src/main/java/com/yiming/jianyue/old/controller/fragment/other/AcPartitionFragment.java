package com.yiming.jianyue.old.controller.fragment.other;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.yiming.jianyue.old.view.adapter.recyclerview.AcPartitionRvAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by succlz123 on 2015/7/27.
 */
public class AcPartitionFragment extends BaseFragment {

    public static AcPartitionFragment newInstance(String channelType) {
        AcPartitionFragment fragment = new AcPartitionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int TYPE_RECOMMEND_HOT = 0;
    private static final int TYPE_MOST_POPULAR = 1;
    private static final int TYPE_LAST_POST = 2;

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcPartitionRvAdapter mAdapter;
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
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 1 | position == 2 | position == 3 | position == 4) {
                    return 1;
                }
                return 2;
            }
        });
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcPartitionRvAdapter.MyDecoration());
        mAdapter = new AcPartitionRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AcPartitionRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                AcContentActivity.startActivity(getActivity(), contentId);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {

                    getHttpResult(TYPE_LAST_POST, "" + mPagerNoNum);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }

            }
        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(TYPE_MOST_POPULAR, null);
                getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    /**
     * 延迟加载 默认只在fragment显示时才去发起http请求 使viewPager只加载一页
     */
    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
            if (mAdapter.getAcMostPopular() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(TYPE_MOST_POPULAR, null);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
            if (mAdapter.getAcLastPost() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
        }
    }

    public void setHttpOrder() {
        getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
    }

    private void getHttpResult(int type, final String pagerNoNum) {
        SharedPreferences settings
                = getActivity().getSharedPreferences(getString(R.string.app_name), Activity.MODE_PRIVATE);
        String order
                = settings.getString(AcString.ORDER_BY, AcString.TIME_ORDER);
        //人气最旺
        if (type == TYPE_MOST_POPULAR) {
            Call<AcReOther> call = AcApi.getAcPartition().onResult(AcApi.buildAcPartitionUrl
                    (mPartitionType,
                            AcString.POPULARITY,
                            AcString.ONE_WEEK,
                            AcString.PAGE_SIZE_NUM_10,
                            AcString.PAGE_NO_NUM_1));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReOther = response.body();
                    if (acReOther != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()
                            && AcPartitionFragment.this.getUserVisibleHint()) {
                        if (acReOther.getData() != null
                                && acReOther.getData().getPage().getList().size() != 0) {
                            mAdapter.setAcMostPopular(acReOther);
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
                            && AcPartitionFragment.this.getUserVisibleHint()) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新太快或者网络连接异常");
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }
                }

            });
        }
        //最新发布
        if (type == TYPE_LAST_POST) {
            Call<AcReOther> call = AcApi.getAcPartition().onResult(AcApi.buildAcPartitionUrl
                    (mPartitionType,
                            order,
                            AcString.ONE_WEEK,
                            AcString.PAGE_SIZE_NUM_10,
                            pagerNoNum));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReOther = response.body();
                    if (acReOther != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()
                            && AcPartitionFragment.this.getUserVisibleHint()) {
                        if (acReOther.getData() != null) {
                            if (acReOther.getData().getPage().getList().size() != 0) {
                                if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                    mAdapter.addDate(acReOther);
                                } else {
                                    mAdapter.setAcLastPost(acReOther);
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
                            && AcPartitionFragment.this.getUserVisibleHint()) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新太快或者网络连接异常");
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }
                }
            });
        }
    }
}
