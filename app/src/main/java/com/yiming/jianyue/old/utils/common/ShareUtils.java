package com.yiming.jianyue.old.utils.common;

import android.content.Context;
import android.text.TextUtils;

import com.yiming.jianyue.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wengyiming on 2015/11/19.
 */
public class ShareUtils {

    public static void showShare(Context ctx, String imageUrl, String url, String webTitle) {
        ShareSDK.initSDK(ctx);
        OnekeyShare oks = new OnekeyShare();
        oks.addHiddenPlatform("WechatFavorite");
        oks.addHiddenPlatform("Facebook");
        oks.addHiddenPlatform("Twitter");


        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(webTitle);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(webTitle);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (!TextUtils.isEmpty(imageUrl)) {
            oks.setImageUrl(imageUrl);
        }
//        oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("yiming的" + ctx.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(ctx);
    }
}
