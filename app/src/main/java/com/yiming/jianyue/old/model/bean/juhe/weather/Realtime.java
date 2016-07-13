package com.yiming.jianyue.old.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Realtime{

    private static final String FIELD_WIND = "wind";
    private static final String FIELD_MOON = "moon";
    private static final String FIELD_WEEK = "week";
    private static final String FIELD_DATA_UPTIME = "dataUptime";
    private static final String FIELD_CITY_CODE = "city_code";
    private static final String FIELD_CITY_NAME = "city_name";
    private static final String FIELD_TIME = "time";
    private static final String FIELD_DATE = "date";
    private static final String FIELD_WEATHER = "weather";


    @SerializedName(FIELD_WIND)
    private Wind mWind;
    @SerializedName(FIELD_MOON)
    private String mMoon;
    @SerializedName(FIELD_WEEK)
    private int mWeek;
    @SerializedName(FIELD_DATA_UPTIME)
    private int mDataUptime;
    @SerializedName(FIELD_CITY_CODE)
    private int mCityCode;
    @SerializedName(FIELD_CITY_NAME)
    private String mCityName;
    @SerializedName(FIELD_TIME)
    private String mTime;
    @SerializedName(FIELD_DATE)
    private String mDate;
    @SerializedName(FIELD_WEATHER)
    private Weather mWeather;


    public Realtime(){

    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setMoon(String moon) {
        mMoon = moon;
    }

    public String getMoon() {
        return mMoon;
    }

    public void setWeek(int week) {
        mWeek = week;
    }

    public int getWeek() {
        return mWeek;
    }

    public void setDataUptime(int dataUptime) {
        mDataUptime = dataUptime;
    }

    public int getDataUptime() {
        return mDataUptime;
    }

    public void setCityCode(int cityCode) {
        mCityCode = cityCode;
    }

    public int getCityCode() {
        return mCityCode;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTime() {
        return mTime;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    public void setWeather(Weather weather) {
        mWeather = weather;
    }

    public Weather getWeather() {
        return mWeather;
    }

    @Override
    public String toString(){
        return "wind = " + mWind + ", moon = " + mMoon + ", week = " + mWeek + ", dataUptime = " + mDataUptime + ", cityCode = " + mCityCode + ", cityName = " + mCityName + ", time = " + mTime + ", date = " + mDate + ", weather = " + mWeather;
    }


}