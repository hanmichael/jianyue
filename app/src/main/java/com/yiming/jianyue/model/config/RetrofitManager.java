package com.yiming.jianyue.model.config;

import com.yiming.jianyue.model.api.acfun.AcString;
import com.yiming.jianyue.model.api.acfun.NewAcString;
import com.yiming.jianyue.model.api.juhe.Config;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitManager {
    private static Retrofit.Builder sInstance;

    public static Retrofit.Builder getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new Retrofit.Builder();
                }
            }
        }
        return sInstance;
    }

    private static Retrofit getRetrofit(String url) {
        Retrofit retrofit = RetrofitManager.getInstance()
                .client(OkHttpClientManager.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

        return retrofit;
    }

    public static Retrofit getWeiXin() {
        return RetrofitManager.getRetrofit(Config.WEIXIN);
    }
    public static Retrofit getAcFunICao() {
        return RetrofitManager.getRetrofit(AcString.URL_ACFUN_ICAO);
    }

    public static Retrofit getAcFunApiServer() {
        return RetrofitManager.getRetrofit(AcString.URL_ACFUN_API_SERVER);
    }

    public static Retrofit getAcFunTv() {
        return RetrofitManager.getRetrofit(AcString.URL_ACFUN_TV);
    }

    public static Retrofit getLeTv() {
        return RetrofitManager.getRetrofit(AcString.LETV_URL_BASE);
    }

    public static Retrofit getAiXiFanApi() {
        return RetrofitManager.getRetrofit(NewAcString.API_AIXIFAN_COM);
    }

    public static Retrofit getAiXiFanDanMu() {
        return RetrofitManager.getRetrofit(NewAcString.DANMU_AIXIFAN_COM);
    }
}
