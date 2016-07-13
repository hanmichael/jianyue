package com.yiming.jianyue.old.controller.activity.xiaohua;

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
import com.yiming.jianyue.old.model.bean.juhe.Result;
import com.yiming.jianyue.old.model.bean.juhe.XiaoHua;
import com.yiming.jianyue.old.model.bean.juhe.XiaoHuaData;
import com.yiming.jianyue.old.view.adapter.recyclerview.XiaoHuaListAdapter;
import com.yiming.jianyue.old.controller.base.BaseActivity;
import com.yiming.jianyue.old.view.widget.MultiSwipeRefreshLayout;
import com.yiming.jianyue.old.model.api.juhe.JuheApi;
import com.yiming.jianyue.old.model.api.juhe.Config;
import com.yiming.jianyue.old.model.config.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wengyiming on 2015/12/22.
 */
public class XiaoHuaActivity extends BaseActivity {

    @Bind(R.id.swiperefresh)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, XiaoHuaActivity.class);
        activity.startActivity(intent);
    }

    private static final int MIN_SCROLL_TO_HIDE = 10;
    private boolean isHide = false;
    private int accummulatedDy = 0;
    private CompositeSubscription subscription = new CompositeSubscription();
    private JuheApi juheApi;
    private boolean isRequest = false;//request data status
    private static final int ANIMATION_DURATION = 2000;
    protected LinearLayoutManager mLayoutManager;
    protected List<XiaoHua> lists = new ArrayList<>();
    protected XiaoHuaListAdapter adapter;
    protected FlipInTopXAnimator animator;
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
        toolbar.setTitle("笑话大全");
        initRecyclerView();
        initRefreshLayout();
        juheApi = RxUtils.createApi(JuheApi.class, Config.XIAOHUA);
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
        //refactor recyclerview scroll

        if (lists.size() <= 0) {
            loadData(curPage);
        }

    }

    protected void loadMore() {
        curPage++;
        loadData(curPage);
    }

    protected void doSwapeRefresh() {
        loadData(1);
    }

    protected void initRefreshLayout() {
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
        adapter = new XiaoHuaListAdapter(lists, getActivity());
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        animator = new FlipInTopXAnimator();
        animator.setAddDuration(ANIMATION_DURATION);
        animator.setRemoveDuration(ANIMATION_DURATION);
        mRecyclerView.setItemAnimator(animator);

    }

    private void loadData(final int page) {
        isRequest = true;
        subscription.add(juheApi.getXiaoHua("desc", page, 20, String.valueOf(System.currentTimeMillis()).substring(0, 10), Config.XIAOHUA_APPKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<XiaoHuaData<XiaoHua>>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TTT", "onCompleted");
                        isRequest = false;
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, Config.TIME_LATE);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e("TTT", e.toString());
                        isRequest = false;
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, Config.TIME_LATE);
                    }

                    @Override
                    public void onNext(final Result<XiaoHuaData<XiaoHua>> t) {
                        Log.e("TTT", "onNext");
                        isRequest = false;
                        mSwipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, Config.TIME_LATE);
                        if (t.getErrorCode() == 0) {
                            if (page == 1) {
                                lists = t.getResult().getData();
                            } else {
                                lists.addAll(t.getResult().getData());
                            }
                            Toast.makeText(XiaoHuaActivity.this, "请求成功" + t.getResult().getData().size() + "", Toast.LENGTH_SHORT).show();
                            adapter.setLists(lists);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TTT", t.getReason());
                        }
                    }
                }));
    }
}
