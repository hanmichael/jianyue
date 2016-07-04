package com.yiming.jianyue.view.callback;


import com.yiming.jianyue.model.bean.acfun.AcReBanner;
import com.yiming.jianyue.model.bean.acfun.AcReHot;
import com.yiming.jianyue.model.bean.acfun.AcReOther;

/**
 * Created by succlz123 on 2015/7/28.
 */
public interface GetAcRecommendHttpResult {

    void onReBannerResult(AcReBanner result);

    void onAcReHotResult(AcReHot result);

    void onAcReAnimationResult(AcReOther result);

    void onAcReFunResult(AcReOther result);

    void onAcReMusicResult(AcReOther result);

    void onAcReGameResult(AcReOther result);

    void onAcReScienceResult(AcReOther result);

    void onAcReSportResult(AcReOther result);

    void onAcReTvResult(AcReOther result);

}
