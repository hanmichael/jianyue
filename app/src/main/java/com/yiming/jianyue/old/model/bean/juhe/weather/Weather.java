package com.yiming.jianyue.old.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Weather{

    private static final String FIELD_IMG = "img";
    private static final String FIELD_WEEK = "week";
    private static final String FIELD_HUMIDITY = "humidity";
    private static final String FIELD_INFO = "info";
    private static final String FIELD_NONGLI = "nongli";
    private static final String FIELD_TEMPERATURE = "temperature";
    private static final String FIELD_DATE = "date";


    @SerializedName(FIELD_IMG)
    private int mImg;
    @SerializedName(FIELD_WEEK)
    private String mWeek;
    @SerializedName(FIELD_HUMIDITY)
    private int mHumidity;
    @SerializedName(FIELD_INFO)
    private String mInfo;
    @SerializedName(FIELD_NONGLI)
    private String mNongli;
    @SerializedName(FIELD_TEMPERATURE)
    private int mTemperature;
    @SerializedName(FIELD_DATE)
    private String mDate;


    public Weather(){

    }

    public void setImg(int img) {
        mImg = img;
    }

    public int getImg() {
        return mImg;
    }

    public void setWeek(String week) {
        mWeek = week;
    }

    public String getWeek() {
        return mWeek;
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setNongli(String nongli) {
        mNongli = nongli;
    }

    public String getNongli() {
        return mNongli;
    }

    public void setTemperature(int temperature) {
        mTemperature = temperature;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString(){
        return "img = " + mImg + ", week = " + mWeek + ", humidity = " + mHumidity + ", info = " + mInfo + ", nongli = " + mNongli + ", temperature = " + mTemperature + ", date = " + mDate;
    }


}