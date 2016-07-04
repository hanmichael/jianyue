package com.yiming.jianyue.model.bean.juhe;

import com.google.gson.annotations.SerializedName;


public class XiaoHua {

    private static final String FIELD_UPDATETIME = "updatetime";
    private static final String FIELD_HASH_ID = "hashId";
    private static final String FIELD_UNIXTIME = "unixtime";
    private static final String FIELD_CONTENT = "content";


    @SerializedName(FIELD_UPDATETIME)
    private String mUpdatetime;
    @SerializedName(FIELD_HASH_ID)
    private String mHashId;
    @SerializedName(FIELD_UNIXTIME)
    private int mUnixtime;
    @SerializedName(FIELD_CONTENT)
    private String mContent;


    public XiaoHua() {

    }

    public void setUpdatetime(String updatetime) {
        mUpdatetime = updatetime;
    }

    public String getUpdatetime() {
        return mUpdatetime;
    }

    public void setHashId(String hashId) {
        mHashId = hashId;
    }

    public String getHashId() {
        return mHashId;
    }

    public void setUnixtime(int unixtime) {
        mUnixtime = unixtime;
    }

    public int getUnixtime() {
        return mUnixtime;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return "updatetime = " + mUpdatetime + ", hashId = " + mHashId + ", unixtime = " + mUnixtime + ", content = " + mContent;
    }


}