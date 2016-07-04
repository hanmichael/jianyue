package com.yiming.jianyue.model.bean.juhe;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：jianyue
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：15/11/17 下午10:41
 * 修改人：wengyiming
 * 修改时间：15/11/17 下午10:41
 * 修改备注：
 */
public class XiaoHuaData<Type> implements Serializable {
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_DATA)
    private List<Type> mData;


    public XiaoHuaData() {

    }

    public void setData(List<Type> data) {
        mData = data;
    }

    public List<Type> getData() {
        return mData;
    }

    @Override
    public String toString() {
        return "data = " + mData;
    }
}
