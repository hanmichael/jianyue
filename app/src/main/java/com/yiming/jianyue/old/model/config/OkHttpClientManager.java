package com.yiming.jianyue.old.model.config;

import com.orhanobut.logger.Logger;
import com.yiming.jianyue.old.model.api.acfun.NewAcString;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by succlz123 on 15/9/15.
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClient sInstance;

    public static OkHttpClient getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (sInstance == null) {

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Logger.e(message);
                        }
                    });
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                    logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    sInstance = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .addInterceptor(logging)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new LoggingInterceptor())
                            .build();
//                    sInstance = new OkHttpClient().Builder().
//                    //cookie enabled
////                    .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
//                    //从主机读取数据超时
//                    readTimeout(15, TimeUnit.SECONDS).
//                    //连接主机超时
//                    connectTimeout(20, TimeUnit.SECONDS).interceptors().add(new LoggingInterceptor()).build();
                }
            }
        }
        return sInstance;
    }


    public void xx(String url) {

        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .addHeader(NewAcString.APP_VERSION, NewAcString.APP_VERSION_NUM)
                .addHeader(NewAcString.DEVICETYPE, NewAcString.DEVICETYPE_NUM)
                .addHeader(NewAcString.MARKET, NewAcString.MARKET_NAME)
                .addHeader(NewAcString.PRODUCTID, NewAcString.PRODUCTID_2000)
                .addHeader(NewAcString.RESOLUTION, NewAcString.RESOLUTION_WIDTH_HEIGHT)
                .addHeader(NewAcString.UUID, NewAcString.UUID_NUM);

        Request request = builder.build();

        Call call = OkHttpClientManager.getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().toString();
            }
        });
    }

    /**
     * see http://stackoverflow.com/questions/24952199/okhttp-enable-logs
     */
    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.v("OkHttp", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.v("OkHttp", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
