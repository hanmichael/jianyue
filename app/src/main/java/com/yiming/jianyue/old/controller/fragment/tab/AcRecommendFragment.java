package com.yiming.jianyue.old.controller.fragment.tab;

import com.yiming.jianyue.AppClient;
import com.yiming.jianyue.R;
import com.yiming.jianyue.old.controller.activity.acfun.AcContentActivity;
import com.yiming.jianyue.old.controller.activity.acfun.AcPartitionActivity;
import com.yiming.jianyue.old.controller.base.BaseFragment;
import com.yiming.jianyue.old.model.api.acfun.AcApi;
import com.yiming.jianyue.old.model.api.acfun.AcString;
import com.yiming.jianyue.old.model.bean.acfun.AcReBanner;
import com.yiming.jianyue.old.model.bean.acfun.AcReHot;
import com.yiming.jianyue.old.model.bean.acfun.AcReOther;
import com.yiming.jianyue.old.utils.common.GlobalUtils;
import com.yiming.jianyue.old.utils.common.ViewUtils;
import com.yiming.jianyue.old.view.adapter.recyclerview.AcRecommendRvAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by succlz123 on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
    private boolean mIsPrepared;
    private AcRecommendRvAdapter mAdapter;
    private AcApi.onAcRecommend acRecommend;

    @Bind(R.id.ac_fragment_recommend_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_recommend, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 0 | position == 1 | position == 6 | position == 9 | position == 12
                        | position == 15 | position == 18 | position == 21 | position == 24) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new AcRecommendRvAdapter.MyDecoration());
        mAdapter = new AcRecommendRvAdapter();
        //解决viewpager里滑动导致swipeReFreshLayout的出现
        mAdapter.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mAdapter.setOnClickListener(new AcRecommendRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, String partitionType, String contentId) {
                if (partitionType != null) {
                    //启动分区页面
                    AcPartitionActivity.startActivity(getActivity(), partitionType);
                } else if (contentId != null) {
                    //启动视频信息页面
                    AcContentActivity.startActivity(getActivity(), contentId);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcString.BANNER);
                getHttpResult(AcString.HOT);
                getHttpResult(AcString.ANIMATION);
                getHttpResult(AcString.FUN);
                getHttpResult(AcString.MUSIC);
                getHttpResult(AcString.GAME);
                getHttpResult(AcString.SCIENCE);
                getHttpResult(AcString.SPORT);
                getHttpResult(AcString.TV);
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
            if (mAdapter.getAcReBanner() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(AcString.BANNER);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
            if (mAdapter.getAcReHot() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(AcString.HOT);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
            if (mAdapter.getAcReAnimation() == null) {
                getHttpResult(AcString.ANIMATION);
            }
            if (mAdapter.getAcReFun() == null) {
                getHttpResult(AcString.FUN);
            }
            if (mAdapter.getAcReMusic() == null) {
                getHttpResult(AcString.MUSIC);
            }
            if (mAdapter.getAcReGame() == null) {
                getHttpResult(AcString.GAME);
            }
            if (mAdapter.getAcReScience() == null) {
                getHttpResult(AcString.SCIENCE);
            }
            if (mAdapter.getAcReSport() == null) {
                getHttpResult(AcString.SPORT);
            }
            if (mAdapter.getAcReTv() == null) {
                getHttpResult(AcString.TV);
            }
        }

    }

    private void getHttpResult(String httpGetType) {
        acRecommend = AcApi.getAcRecommend();

        if (TextUtils.equals(httpGetType, AcString.BANNER)) {
            //首页横幅
            Call<AcReBanner> call = acRecommend.onAcReBannerResult(AcApi.buildAcReBannerUrl());
            call.enqueue(new Callback<AcReBanner>() {
                @Override
                public void onResponse(Call<AcReBanner> call, Response<AcReBanner> response) {
                    AcReBanner acReBanner = response.body();
                    if (acReBanner != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onReBannerResult(acReBanner);
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AcReBanner> call, Throwable t) {
                    if (getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.HOT)) {
            //首页热门焦点
            Call<AcReHot> call = acRecommend.onAcReHotResult(AcApi.buildAcReHotUrl
                    (AcString.PAGE_NO_NUM_1));
            call.enqueue(new Callback<AcReHot>() {
                @Override
                public void onResponse(Call<AcReHot> call, Response<AcReHot> response) {
                    AcReHot acReHot = response.body();
                    if (acReHot != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReHotResult(acReHot);
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AcReHot> call, Throwable t) {
                    if (getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.ANIMATION)) {
            //动画区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.ANIMATION, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReOther = response.body();
                    if (acReOther != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReAnimationResult(acReOther);
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
                            && !getActivity().isFinishing()) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.FUN)) {
            //娱乐区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.FUN, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReFun = response.body();
                    if (acReFun != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReFunResult(acReFun);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.MUSIC)) {
            //音乐区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.MUSIC, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReMusic = response.body();
                    if (acReMusic != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReMusicResult(acReMusic);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.GAME)) {
            //游戏区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.GAME, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReGame = response.body();
                    if (acReGame != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReGameResult(acReGame);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        } else if (TextUtils.equals(httpGetType, AcString.SCIENCE)) {
            //科学区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.SCIENCE, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReScience = response.body();
                    if (acReScience != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReScienceResult(acReScience);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });

        } else if (TextUtils.equals(httpGetType, AcString.SPORT)) {
            //体育区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.SPORT, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReSport = response.body();
                    if (acReSport != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReSportResult(acReSport);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });

        } else if (TextUtils.equals(httpGetType, AcString.TV)) {
            //影视区
            Call<AcReOther> call = acRecommend.onAcReOtherResult(AcApi.buildAcReOtherUrl
                    (AcString.TV, AcString.LAST_POST, AcString.ONE_WEEK));
            call.enqueue(new Callback<AcReOther>() {
                @Override
                public void onResponse(Call<AcReOther> call, Response<AcReOther> response) {
                    AcReOther acReTv = response.body();
                    if (acReTv != null
                            && getActivity() != null
                            && !getActivity().isDestroyed()
                            && !getActivity().isFinishing()) {
                        mAdapter.onAcReTvResult(acReTv);
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
                            && !getActivity().isFinishing()) {
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }

            });
        }
    }
}

