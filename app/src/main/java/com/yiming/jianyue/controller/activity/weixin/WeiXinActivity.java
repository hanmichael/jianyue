package com.yiming.jianyue.controller.activity.weixin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.yiming.jianyue.R;
import com.yiming.jianyue.model.bean.juhe.Data;
import com.yiming.jianyue.model.bean.juhe.Result;
import com.yiming.jianyue.model.bean.juhe.WeixinArticles;
import com.yiming.jianyue.utils.common.GlobalUtils;
import com.yiming.jianyue.utils.common.ViewUtils;
import com.yiming.jianyue.view.adapter.recyclerview.ArticleListAdapter;
import com.yiming.jianyue.controller.base.BaseActivity;
import com.yiming.jianyue.view.widget.MultiSwipeRefreshLayout;
import com.yiming.jianyue.model.api.juhe.JuheApi;
import com.yiming.jianyue.model.api.juhe.Config;
import com.yiming.jianyue.model.config.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wengyiming on 2015/12/16.
 */
public class WeiXinActivity extends BaseActivity {

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, WeiXinActivity.class);
        activity.startActivity(intent);
    }

    @Bind(R.id.swiperefresh)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    protected ArticleListAdapter adapter;
    protected LinearLayoutManager mLayoutManager;
    protected List<WeixinArticles> lists = new ArrayList<>();
    private static final int MIN_SCROLL_TO_HIDE = 10;
    private boolean isHide = false;
    private int accummulatedDy = 0;
    private CompositeSubscription subscription = new CompositeSubscription();
    private JuheApi juheApi;
    private boolean isRequest = false;//request data status
    protected boolean isScroll = false;//is RecyclerView scrolling
    protected int curPage = 1;//default

    @Override
    public int getLayoutRes() {
        return R.layout.activity_weixin;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setTitle("微信精选");
        initRefreshLayout();
        initRecyclerView();
        juheApi = RxUtils.createApi(JuheApi.class, Config.WEIXIN);
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 0);
    }

    protected void init() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScroll = newState == RecyclerView.SCROLL_STATE_SETTLING;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                mSwipeRefreshLayout.setRefreshing(mLayoutManager
//                        .findFirstCompletelyVisibleItemPosition() == 0);
                if (!recyclerView.canScrollVertically(1) && !isRequest) {
                    loadMore();
                }
                if (dy > 0) {
                    accummulatedDy = accummulatedDy > 0 ? accummulatedDy + dy : dy;
                    if (accummulatedDy > MIN_SCROLL_TO_HIDE && !isHide) {
                        isHide = true;
                    }
                } else if (dy < 0) {
                    accummulatedDy = accummulatedDy < 0 ? accummulatedDy + dy : dy;
                    if (accummulatedDy < (0 - MIN_SCROLL_TO_HIDE) && isHide) {
                        isHide = false;
                    }
                }
            }
        });
        if (lists.size() <= 0) {
            loadData(curPage);
        }

    }

    private void loadMore() {
        curPage++;
        loadData(curPage);
    }

    private void loadData(final int page) {
        isRequest = true;
        subscription.add(juheApi.getArticleList(page, 20, "json", Config.WEIXIN_APPKEY)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<Data<WeixinArticles>>>() {
                    @Override
                    public void call(Result<Data<WeixinArticles>> t) {
                        isRequest = false;
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, Config.TIME_LATE);
                        if (t.getErrorCode() == 0) {
                            if (page == 1) {
                                lists = t.getResult().getLists();
                            } else {
                                lists.addAll(t.getResult().getLists());
                            }
                            Toast.makeText(getApplicationContext(), "请求成功" + t.getResult().getLists().size() + "", Toast.LENGTH_SHORT).show();
                            adapter.setLists(lists);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("TTT",throwable.toString());
                        GlobalUtils.showToastShort(WeiXinActivity.this, "网络连接异常");
                        ViewUtils.setSwipeRefreshFailed(mSwipeRefreshLayout);
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription = RxUtils.getNewCompositeSubIfUnsubscribed(subscription);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscription);
    }

    protected void doSwapeRefresh() {
        loadData(1);
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.md_orange_700, R.color.md_red_500,
                R.color.md_indigo_900, R.color.md_green_700);
        mSwipeRefreshLayout.setSwipeableChildren(R.id.recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doSwapeRefresh();
            }
        });
    }

    protected void initRecyclerView() {
        adapter = new ArticleListAdapter(lists, this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}
