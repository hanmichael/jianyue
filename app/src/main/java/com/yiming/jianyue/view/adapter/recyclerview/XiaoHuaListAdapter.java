package com.yiming.jianyue.view.adapter.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiming.jianyue.R;
import com.yiming.jianyue.model.bean.juhe.XiaoHua;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：jianyue
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：15/11/16 下午11:17
 * 修改人：wengyiming
 * 修改时间：15/11/16 下午11:17
 * 修改备注：
 */
public class XiaoHuaListAdapter extends RecyclerView.Adapter<XiaoHuaListVH> implements XiaoHuaListVH.ArticleItemClickListener {
    private static final String TAG = "ArticleListAdapter";

    public void setLists(final List<XiaoHua> mLists) {
        lists = mLists;
    }

    private List<XiaoHua> lists = new ArrayList<>();
    private Activity mActivity;
    private XiaoHuaListVH.ArticleItemClickListener listener;

    public XiaoHuaListAdapter(List<XiaoHua> lists) {
        this.lists = lists;
    }

    public XiaoHuaListAdapter(List<XiaoHua> lists, Activity mActivity) {
        this(lists);
        this.mActivity = mActivity;
        setListener(this);
    }

    public XiaoHuaListAdapter(List<XiaoHua> lists, Activity mActivity, int channnel) {
        this(lists, mActivity);
        setListener(this);
    }

    @Override
    public XiaoHuaListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.xiaohua_list_item, parent, false);
        XiaoHuaListVH vh = new XiaoHuaListVH(v, listener);
        vh.title = (TextView) v.findViewById(R.id.title);
        vh.content = (TextView) v.findViewById(R.id.content);
        return vh;
    }

    @Override
    public void onBindViewHolder(XiaoHuaListVH holder, int position) {
        final XiaoHua article = lists.get(position);
        if (article == null) {
            return;
        }
        holder.title.setText(article.getContent());
        holder.content.setText(String.valueOf(article.getUpdatetime()));

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public XiaoHuaListVH.ArticleItemClickListener getListener() {
        return listener;
    }

    public void setListener(XiaoHuaListVH.ArticleItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {
    }
}
