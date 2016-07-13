package com.yiming.jianyue.old.view.adapter.recyclerview;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yiming.jianyue.AppClient;
import com.yiming.jianyue.R;
import com.yiming.jianyue.old.model.bean.acfun.AcContentReply;
import com.yiming.jianyue.old.utils.common.GlobalUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/4.
 */
public class AcContentReplyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AcContentReply.DataEntity.Entity> mAcContentReply;
    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String userId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public ArrayList<AcContentReply.DataEntity.Entity> getmAcContentReply() {
        return mAcContentReply;
    }

    public void setContentReply(ArrayList<AcContentReply.DataEntity.Entity> acContentReply) {
        this.mAcContentReply = acContentReply;
        notifyDataSetChanged();
    }

    public class TitleInfoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_rv_content_reply_name_tv)
        TextView tvName;

        @Bind(R.id.ac_rv_content_reply_time_tv)
        TextView tvTime;

        @Bind(R.id.ac_rv_content_reply_reply_tv)
        TextView tvReply;

        @Bind(R.id.ac_rv_content_reply_user_img)
        SimpleDraweeView userImg;

        @Bind(R.id.quote_reply_linear_layout)
        LinearLayout linearLayout;

        @Bind(R.id.cv_content_reply)
        CardView cardView;

        public TitleInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View titleInfoView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_content_reply, parent, false);

        return new TitleInfoViewHolder(titleInfoView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcContentReply != null) {
            AcContentReply.DataEntity.Entity entity = mAcContentReply.get(position);
            if(entity.getAvatar()!=null){
                ((TitleInfoViewHolder) holder).userImg
                        .setImageURI(Uri.parse(entity.getAvatar()));
            }
            ((TitleInfoViewHolder) holder).tvName
                    .setText("#" + (entity.getFloor() + " " + entity.getUsername()));
            ((TitleInfoViewHolder) holder).tvTime
                    .setText(GlobalUtils.getDateToString(entity.getTime()));
            ((TitleInfoViewHolder) holder).tvReply
                    .setText(Html.fromHtml(entity.getContent()));

            ((TitleInfoViewHolder) holder).linearLayout.removeAllViews();

            int deep = entity.getDeep();
            int height = Math.min(3, deep);

            List<View> viewList = new ArrayList<>();

            while (height > 0) {
                entity = entity.getQuoteReply();

                TextView floorAndNameTv
                        = new TextView(AppClient.getInstance().getApplicationContext());
                LinearLayout.LayoutParams floorAndNameTvLp
                        = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                floorAndNameTvLp
                        .setMargins(0, GlobalUtils.dip2px(AppClient.getInstance().getApplicationContext(), 8f), 0, 0);
                floorAndNameTv
                        .setLayoutParams(floorAndNameTvLp);
                floorAndNameTv
                        .setTextSize(12);
                floorAndNameTv
                        .setTextColor(AppClient.getInstance().getApplicationContext().getResources().getColor(R.color.font_blue));
                floorAndNameTv
                        .setText("   #" + entity.getFloor() + " " + entity.getUsername());

                TextView quoteContentTv
                        = new TextView(AppClient.getInstance().getApplicationContext());
                LinearLayout.LayoutParams quoteContentTvLp
                        = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                quoteContentTvLp
                        .setMargins(0, GlobalUtils.dip2px(AppClient.getInstance().getApplicationContext(), 4f), 0, GlobalUtils.dip2px(AppClient.getInstance().getApplicationContext(), 8f));
                quoteContentTv
                        .setLayoutParams(quoteContentTvLp);
                floorAndNameTv
                        .setTextSize(12);
                quoteContentTv
                        .setTextColor(AppClient.getInstance().getApplicationContext().getResources().getColor(R.color.font_light));
                quoteContentTv
                        .setText("   " + Html.fromHtml(entity.getContent()));

                View driverView
                        = new View(AppClient.getInstance().getApplicationContext());
                LinearLayout
                        .LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalUtils.dip2px(AppClient.getInstance().getApplicationContext(), 1f));
                driverView
                        .setLayoutParams(viewLayoutParams);
                driverView
                        .setBackgroundColor(AppClient.getInstance().getApplicationContext().getResources().getColor(R.color.android_base));

                viewList.add(driverView);
                viewList.add(quoteContentTv);
                viewList.add(floorAndNameTv);

                height--;
            }

            ListIterator<View> iterator = viewList.listIterator(viewList.size());
            while (iterator.hasPrevious()) {
                ((TitleInfoViewHolder) holder).linearLayout.addView(iterator.previous());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcContentReply != null) {
            return mAcContentReply.size();
        }
        return 0;
    }
}
