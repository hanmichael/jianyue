package com.yiming.jianyue.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;


public class Life{

    private static final String FIELD_INFO = "info";
    private static final String FIELD_DATE = "date";


    @SerializedName(FIELD_INFO)
    private Info mInfo;
    @SerializedName(FIELD_DATE)
    private String mDate;


    public Life(){

    }

    public void setInfo(Info info) {
        mInfo = info;
    }

    public Info getInfo() {
        return mInfo;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString(){
        return "info = " + mInfo + ", date = " + mDate;
    }


}