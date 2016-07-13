package com.yiming.jianyue.old.controller.activity.weather;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.yiming.jianyue.R;
import com.yiming.jianyue.old.controller.base.BaseActivity;
import com.yiming.jianyue.old.model.bean.juhe.Result;
import com.yiming.jianyue.old.model.api.juhe.JuheApi;
import com.yiming.jianyue.old.model.api.juhe.Config;
import com.yiming.jianyue.old.model.config.RxUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wengyiming on 2015/12/22.
 */
public class WeatherActivity extends BaseActivity {

    private CompositeSubscription subscription = new CompositeSubscription();
    private JuheApi juheApi;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_weather;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        juheApi = RxUtils.createApi(JuheApi.class, Config.WERTHER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription = RxUtils.getNewCompositeSubIfUnsubscribed(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscription);
    }

    private void getWeather() {
        subscription.add(juheApi.getWeather("上海", "json", Config.WEATHER_APPKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TTTT", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TTTT", e.toString());
                    }

                    @Override
                    public void onNext(Result datumResult) {
                        Log.e("TTTT", datumResult.toString());
                        if (datumResult.getErrorCode() == 0) {
                            Log.e("TTTT", datumResult.getResult().toString());

                        } else {
                            Log.e("TTTT onNext", datumResult.getReason());
                        }
                    }
                }));

    }
}
