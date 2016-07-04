package com.yiming.jianyue.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Wind{

    private static final String FIELD_WINDSPEED = "windspeed";
    private static final String FIELD_OFFSET = "offset";
    private static final String FIELD_DIRECT = "direct";
    private static final String FIELD_POWER = "power";


    @SerializedName(FIELD_WINDSPEED)
    private String mWindspeed;
    @SerializedName(FIELD_OFFSET)
    private String mOffset;
    @SerializedName(FIELD_DIRECT)
    private String mDirect;
    @SerializedName(FIELD_POWER)
    private String mPower;


    public Wind(){

    }

    public void setWindspeed(String windspeed) {
        mWindspeed = windspeed;
    }

    public String getWindspeed() {
        return mWindspeed;
    }

    public void setOffset(String offset) {
        mOffset = offset;
    }

    public String getOffset() {
        return mOffset;
    }

    public void setDirect(String direct) {
        mDirect = direct;
    }

    public String getDirect() {
        return mDirect;
    }

    public void setPower(String power) {
        mPower = power;
    }

    public String getPower() {
        return mPower;
    }

    @Override
    public String toString(){
        return "windspeed = " + mWindspeed + ", offset = " + mOffset + ", direct = " + mDirect + ", power = " + mPower;
    }


}