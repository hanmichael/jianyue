package com.yiming.jianyue.old.model.bean.juhe.weather;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Info{

    private static final String FIELD_GANMAO = "ganmao";
    private static final String FIELD_KONGTIAO = "kongtiao";
    private static final String FIELD_CHUANYI = "chuanyi";
    private static final String FIELD_DAY = "day";
    private static final String FIELD_WURAN = "wuran";
    private static final String FIELD_DAWN = "dawn";
    private static final String FIELD_YUNDONG = "yundong";
    private static final String FIELD_NIGHT = "night";
    private static final String FIELD_XICHE = "xiche";
    private static final String FIELD_ZIWAIXIAN = "ziwaixian";


    @SerializedName(FIELD_GANMAO)
    private List<String> mGanmaos;
    @SerializedName(FIELD_KONGTIAO)
    private List<String> mKongtiaos;
    @SerializedName(FIELD_CHUANYI)
    private List<String> mChuanyis;
    @SerializedName(FIELD_DAY)
    private List<Integer> mDays;
    @SerializedName(FIELD_WURAN)
    private List<String> mWurans;
    @SerializedName(FIELD_DAWN)
    private List<Integer> mDawns;
    @SerializedName(FIELD_YUNDONG)
    private List<String> mYundongs;
    @SerializedName(FIELD_NIGHT)
    private List<Integer> mNights;
    @SerializedName(FIELD_XICHE)
    private List<String> mXiches;
    @SerializedName(FIELD_ZIWAIXIAN)
    private List<String> mZiwaixians;


    public Info(){

    }

    public void setGanmaos(List<String> ganmaos) {
        mGanmaos = ganmaos;
    }

    public List<String> getGanmaos() {
        return mGanmaos;
    }

    public void setKongtiaos(List<String> kongtiaos) {
        mKongtiaos = kongtiaos;
    }

    public List<String> getKongtiaos() {
        return mKongtiaos;
    }

    public void setChuanyis(List<String> chuanyis) {
        mChuanyis = chuanyis;
    }

    public List<String> getChuanyis() {
        return mChuanyis;
    }

    public void setDays(List<Integer> days) {
        mDays = days;
    }

    public List<Integer> getDays() {
        return mDays;
    }

    public void setWurans(List<String> wurans) {
        mWurans = wurans;
    }

    public List<String> getWurans() {
        return mWurans;
    }

    public void setDawns(List<Integer> dawns) {
        mDawns = dawns;
    }

    public List<Integer> getDawns() {
        return mDawns;
    }

    public void setYundongs(List<String> yundongs) {
        mYundongs = yundongs;
    }

    public List<String> getYundongs() {
        return mYundongs;
    }

    public void setNights(List<Integer> nights) {
        mNights = nights;
    }

    public List<Integer> getNights() {
        return mNights;
    }

    public void setXiches(List<String> xiches) {
        mXiches = xiches;
    }

    public List<String> getXiches() {
        return mXiches;
    }

    public void setZiwaixians(List<String> ziwaixians) {
        mZiwaixians = ziwaixians;
    }

    public List<String> getZiwaixians() {
        return mZiwaixians;
    }

    @Override
    public String toString(){
        return "ganmaos = " + mGanmaos + ", kongtiaos = " + mKongtiaos + ", chuanyis = " + mChuanyis + ", days = " + mDays + ", wurans = " + mWurans + ", dawns = " + mDawns + ", yundongs = " + mYundongs + ", nights = " + mNights + ", xiches = " + mXiches + ", ziwaixians = " + mZiwaixians;
    }


}