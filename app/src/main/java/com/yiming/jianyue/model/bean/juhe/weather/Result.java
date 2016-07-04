package com.yiming.jianyue.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Result{

    private static final String FIELD_REASON = "reason";
    private static final String FIELD_ERROR_CODE = "error_code";
    private static final String FIELD_RESULT = "result";


    @SerializedName(FIELD_REASON)
    private String mReason;
    @SerializedName(FIELD_ERROR_CODE)
    private int mErrorCode;
    @SerializedName(FIELD_RESULT)
    private Result mResult;


    public Result(){

    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public String getReason() {
        return mReason;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public Result getResult() {
        return mResult;
    }

    @Override
    public String toString(){
        return "reason = " + mReason + ", errorCode = " + mErrorCode + ", result = " + mResult;
    }


}