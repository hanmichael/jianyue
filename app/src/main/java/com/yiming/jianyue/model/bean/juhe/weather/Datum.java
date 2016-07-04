package com.yiming.jianyue.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Datum{

    private static final String FIELD_LIFE = "life";
    private static final String FIELD_REALTIME = "realtime";
    private static final String FIELD_IS_FOREIGN = "isForeign";
    private static final String FIELD_JINGQU = "jingqu";
    private static final String FIELD_PM25 = "pm25";
    private static final String FIELD_WEATHER = "weather";
    private static final String FIELD_DATE = "date";


    @SerializedName(FIELD_LIFE)
    private Life mLife;
    @SerializedName(FIELD_REALTIME)
    private Realtime mRealtime;
    @SerializedName(FIELD_IS_FOREIGN)
    private int mIsForeign;
    @SerializedName(FIELD_JINGQU)
    private String mJingqu;
    @SerializedName(FIELD_PM25)
    private Pm25 mPm25;
    @SerializedName(FIELD_WEATHER)
    private List<Weather> mWeathers;
    @SerializedName(FIELD_DATE)
    private String mDate;


    public Datum(){

    }

    public void setLife(Life life) {
        mLife = life;
    }

    public Life getLife() {
        return mLife;
    }

    public void setRealtime(Realtime realtime) {
        mRealtime = realtime;
    }

    public Realtime getRealtime() {
        return mRealtime;
    }

    public void setIsForeign(int isForeign) {
        mIsForeign = isForeign;
    }

    public int getIsForeign() {
        return mIsForeign;
    }

    public void setJingqu(String jingqu) {
        mJingqu = jingqu;
    }

    public String getJingqu() {
        return mJingqu;
    }

    public void setPm25(Pm25 pm25) {
        mPm25 = pm25;
    }

    public Pm25 getPm25() {
        return mPm25;
    }

    public void setWeathers(List<Weather> weathers) {
        mWeathers = weathers;
    }

    public List<Weather> getWeathers() {
        return mWeathers;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString(){
        return "life = " + mLife + ", realtime = " + mRealtime + ", isForeign = " + mIsForeign + ", jingqu = " + mJingqu + ", pm25 = " + mPm25 + ", weathers = " + mWeathers + ", date = " + mDate;
    }


}