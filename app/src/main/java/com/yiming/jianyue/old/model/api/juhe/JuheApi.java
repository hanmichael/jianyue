package com.yiming.jianyue.old.model.api.juhe;

import com.yiming.jianyue.old.model.bean.juhe.Data;
import com.yiming.jianyue.old.model.bean.juhe.Result;
import com.yiming.jianyue.old.model.bean.juhe.WeixinArticles;
import com.yiming.jianyue.old.model.bean.juhe.XiaoHua;
import com.yiming.jianyue.old.model.bean.juhe.XiaoHuaData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称：jianyue
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：15/11/16 下午10:34
 * 修改人：wengyiming
 * 修改时间：15/11/16 下午10:34
 * 修改备注：
 */
public interface JuheApi {
    @GET(Config.WEIXIN)
    Observable<Result<Data<WeixinArticles>>> getArticleList(@Query("pno") int pno, @Query("ps") int ps
            , @Query("dtype") String dataType, @Query("key") String key);


    @GET(Config.XIAOHUA)
    Observable<Result<XiaoHuaData<XiaoHua>>> getXiaoHua(@Query("sort") String sort, @Query("page") int page
            , @Query("pagesize") int pagesize, @Query("time") String time, @Query("key") String key);

    @GET(Config.WERTHER)
    Observable<Result> getWeather(@Query("cityname") String cityname, @Query("dtype") String dtype, @Query("key") String key);

//    http://op.juhe.cn/onebox/weather/query?cityname=%E4%B8%8A%E6%B5%B7&dtype=json&key=2de850b29cdfdcaa5a6c899f8817b812
}
