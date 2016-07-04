package com.yiming.jianyue.view.adapter.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yiming.jianyue.R;
import com.yiming.jianyue.utils.common.GlobalUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/9/1.
 */
public class DownLoadRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClickListener mOnClickListener;

    private String mName;
    private int mProgress;
    private long mCacheSize;
    private long mTotalSize;
    private long mAverageSpeed;
    private long mRealTimeSpeed;

    private HashMap<String, Integer> downloadMap = new HashMap<>();
    private boolean mIsNotify;

//    private ArrayList<NBDownloadRequest> mNBDownloadRequests = new ArrayList<>();

//    public ArrayList<NBDownloadRequest> getNBDownloadRequests() {
//        return mNBDownloadRequests;
//    }

//    public void setNBDownloadRequests(List<NBDownloadRequest> downloadRequestList) {
//        this.mNBDownloadRequests = new ArrayList<>(downloadRequestList);
//        notifyDataSetChanged();
//    }

//    public void deleteRequests(int position) {
//        if (mNBDownloadRequests.size() != 0) {
//            mNBDownloadRequests.remove(position);
//            notifyDataSetChanged();
//        }
//    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    long time = System.currentTimeMillis();

    public void setProgress(String name, int progress, long cacheSize, long totalSize, long averageSpeed, long realTimeSpeed) {
        mName = name;
        this.mProgress = progress;
        this.mCacheSize = cacheSize;
        this.mTotalSize = totalSize;
        this.mAverageSpeed = averageSpeed;
        this.mRealTimeSpeed = realTimeSpeed;

        if (downloadMap.size() != 0 && downloadMap.get(name) != null) {
            notifyItemChanged(downloadMap.get(name));
        } else if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time) >= 1) {
            time = System.currentTimeMillis();
            notifyDataSetChanged();
        }
    }

    public void setFinish(String name) {

    }

    public String getName() {
        return mName;
    }

    public int getProgress() {
        return mProgress;
    }

    public long getCacheSize() {
        return mCacheSize;
    }

    public long getTotalSize() {
        return mTotalSize;
    }

    public long getAverageSpeed() {
        return mAverageSpeed;
    }

    public long getRealTimeSpeed() {
        return mRealTimeSpeed;
    }

    public interface OnClickListener {

        void onToggle(View view, int position, String name);

        void onDelete(View view, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class DownloadVH extends RecyclerView.ViewHolder {
        @Bind(R.id.download_tv_title)
        TextView tvTitle;

        @Bind(R.id.download_tv_file_size)
        TextView tvFileSize;

        @Bind(R.id.download_progress_bar)
        ProgressBar progressBar;

        @Bind(R.id.download_btn_toggle)
        Button btnToggle;

        @Bind(R.id.download_btn_delete)
        Button btnDelete;

        public DownloadVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View downloadView = inflater.inflate(R.layout.hahh, parent, false);

        return new DownloadVH(downloadView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DownloadVH) {

//                 String sourceId = downloadRequest.getName();
            String filePath = "c";

            ((DownloadVH) holder).tvTitle.setText("");
            ((DownloadVH) holder).progressBar.setMax(100);
            DecimalFormat df = new DecimalFormat("0.00");

            if (filePath != null) {
                float cacheSize = (float) 11 / 1024 / 1024;
                float totalSize = (float) 11 / 1024 / 1024;

                if (totalSize != 0) {
                    ((DownloadVH) holder).tvFileSize.setText(df.format(cacheSize) + "M" + "/" + df.format(totalSize) + "M");
                    ((DownloadVH) holder).progressBar.setProgress((int) (cacheSize / totalSize * 100));
                }

                if (cacheSize == totalSize) {
                    ((DownloadVH) holder).tvFileSize.setText("");
                    ((DownloadVH) holder).btnToggle.setText("完成");
                    ((DownloadVH) holder).progressBar.setProgress(100);
                } else if (cacheSize < totalSize) {
                    ((DownloadVH) holder).btnToggle.setText("继续");
                }
            }

            if (mName != null && TextUtils.equals(mName, "")) {

                downloadMap.put(mName, position);

                float cacheSize = (float) mCacheSize / 1024 / 1024;
                float totalSize = (float) mTotalSize / 1024 / 1024;

                ((DownloadVH) holder).tvFileSize.setText(df.format(cacheSize) + "M" + "/" + df.format(totalSize) + "M");
                ((DownloadVH) holder).progressBar.setProgress(mProgress);

                ((DownloadVH) holder).btnToggle.setText("下载");
            }

            ((DownloadVH) holder).btnToggle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });

            ((DownloadVH) holder).btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        int itemCount = mNBDownloadRequests.size();
//        if (itemCount > 0) {
//            return itemCount;
//        }
        return 0;
    }

    //处理cardView中间的margin
    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2px(parent.getContext(), 7);
            if (position == 1 | position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
