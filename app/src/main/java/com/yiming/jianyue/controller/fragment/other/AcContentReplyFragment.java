package com.yiming.jianyue.controller.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yiming.jianyue.AppClient;
import com.yiming.jianyue.R;
import com.yiming.jianyue.controller.base.BaseFragment;
import com.yiming.jianyue.model.api.acfun.AcApi;
import com.yiming.jianyue.model.api.acfun.AcString;
import com.yiming.jianyue.model.bean.acfun.AcContentReply;
import com.yiming.jianyue.utils.common.GlobalUtils;
import com.yiming.jianyue.utils.common.ViewUtils;
import com.yiming.jianyue.view.adapter.recyclerview.AcContentReplyRvAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentReplyFragment extends BaseFragment {

    public static AcContentReplyFragment startFragment(String contentId) {
        AcContentReplyFragment fragment = new AcContentReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.ac_fragment_content_reply_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String CONTENT_ID = "contentId";
    private boolean mIsPrepared;
    private String mContentId;
    private AcContentReplyRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_reply, container, false);
        ButterKnife.bind(this, view);

        mContentId = getArguments().getString(CONTENT_ID);

        if (mContentId == null) {
            GlobalUtils.showToastShort(getActivity(), "数据连接错误,重重试");
            return null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AcContentReplyRvAdapter();

//        mAdapter.setOnVideoPlayClickListener(new AcContentInfoRvAdapter.OnClickListener() {
//            @Override
//            public void onToggle(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType) {
//                if (position == 0) {
//                    GlobalUtils.showToastShort(getActivity(), "哇哈哈哈 " + userId);
//                } else {
//                    VideoPlayActivity.newInstance(getActivity(),
//                            videoId,
//                            danmakuId,
//                            sourceId,
//                            sourceType);
//                }
//            }
//        });

        mRecyclerView.setAdapter(mAdapter);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mIsPrepared = true;

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible || mContentId == null) {
            return;
        } else {
            if (mAdapter.getmAcContentReply() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult();
                    }
                });
            }
        }
    }

    private void getHttpResult() {
        //评论
        Call<AcContentReply> call = AcApi.getAcContentReply()
                .onResult(AcApi.buildAcContentReplyUrl(mContentId, AcString.PAGE_SIZE_NUM_50, AcString.PAGE_NO_NUM_1));

        call.enqueue(new Callback<AcContentReply>() {

            @Override
            public void onResponse(Response<AcContentReply> response, Retrofit retrofit) {
                AcContentReply acContentReply = response.body();
                if (acContentReply != null
                        && getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()) {
                    if (acContentReply.getData().getPage().getList().size() == 0) {
                        GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "并没有评论");
                    } else {
                        mAdapter.setContentReply(sortListReply(acContentReply));
                    }

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()) {
                    GlobalUtils.showToastShort(AppClient.getInstance().getApplicationContext(), "网络连接异常");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    private ArrayList<AcContentReply.DataEntity.Entity> sortListReply(AcContentReply acContentReply) {
        ArrayList<AcContentReply.DataEntity.Entity> replys = new ArrayList<>();
        List<Integer> replyIds = acContentReply.getData().getPage().getList();
        HashMap<String, AcContentReply.DataEntity.Entity> replyIdMap = acContentReply.getData().getPage().getMap();

        for (Integer id : replyIds) {
            replys.add(replyIdMap.get("c" + String.valueOf(id)));
        }

        for (AcContentReply.DataEntity.Entity reply : replys) {
            AcContentReply.DataEntity.Entity currentReply = reply;
            int quoteId = currentReply.getQuoteId();
            while (quoteId != 0 && currentReply.getQuoteReply() == null) {
                AcContentReply.DataEntity.Entity quoteReply = replyIdMap.get("c" + quoteId);
                currentReply.setQuoteReply(quoteReply);
                currentReply = quoteReply;
                quoteId = currentReply.getQuoteId();
            }
        }
        return replys;
    }
}


