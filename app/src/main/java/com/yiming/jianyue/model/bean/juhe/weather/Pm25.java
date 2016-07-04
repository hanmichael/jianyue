package com.yiming.jianyue.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Pm25{

    private static final String FIELD_KEY = "key";
    private static final String FIELD_PM10 = "pm10";
    private static final String FIELD_DATE_TIME = "dateTime";
    private static final String FIELD_QUALITY = "quality";
    private static final String FIELD_PM25 = "pm25";
    private static final String FIELD_DES = "des";
    private static final String FIELD_CITY_NAME = "cityName";
    private static final String FIELD_CUR_PM = "curPm";
    private static final String FIELD_SHOW_DESC = "show_desc";
    private static final String FIELD_LEVEL = "level";


    @SerializedName(FIELD_KEY)
    private String mKey;
    @SerializedName(FIELD_PM10)
    private int mPm10;
    @SerializedName(FIELD_DATE_TIME)
    private String mDateTime;
    @SerializedName(FIELD_QUALITY)
    private String mQuality;
    @SerializedName(FIELD_PM25)
    private int mPm25;
    @SerializedName(FIELD_DES)
    private String mDe;
    @SerializedName(FIELD_CITY_NAME)
    private String mCityName;
    @SerializedName(FIELD_CUR_PM)
    private int mCurPm;
    @SerializedName(FIELD_SHOW_DESC)
    private int mShowDesc;
    @SerializedName(FIELD_LEVEL)
    private int mLevel;


    public Pm25(){

    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getKey() {
        return mKey;
    }

    public void setPm10(int pm10) {
        mPm10 = pm10;
    }

    public int getPm10() {
        return mPm10;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setQuality(String quality) {
        mQuality = quality;
    }

    public String getQuality() {
        return mQuality;
    }

    public void setPm25(int pm25) {
        mPm25 = pm25;
    }

    public int getPm25() {
        return mPm25;
    }

    public void setDe(String de) {
        mDe = de;
    }

    public String getDe() {
        return mDe;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCurPm(int curPm) {
        mCurPm = curPm;
    }

    public int getCurPm() {
        return mCurPm;
    }

    public void setShowDesc(int showDesc) {
        mShowDesc = showDesc;
    }

    public int getShowDesc() {
        return mShowDesc;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getLevel() {
        return mLevel;
    }

    @Override
    public String toString(){
        return "key = " + mKey + ", pm10 = " + mPm10 + ", dateTime = " + mDateTime + ", quality = " + mQuality + ", pm25 = " + mPm25 + ", de = " + mDe + ", cityName = " + mCityName + ", curPm = " + mCurPm + ", showDesc = " + mShowDesc + ", level = " + mLevel;
    }


}