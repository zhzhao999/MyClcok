/*
 * http://www.zhzhao.top
 */
package top.zhzhao.myclock.util.response;

import java.io.Serializable;

/**
 * 普通返回封装
 *@param <T> 具体实体对象
 *@author zhzhao
 *@version $ Id: ResponseVO.java,V 0.1 2019/7/9 19:22 zhzhao Exp $
 */
public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = 134474305226738346L;
    // 接口响应状态码
    private String repCode;
    // 接口调用返回信息
    private String repMsg;

    private T datas;

    public ResponseVO() {
    }

    public ResponseVO(String repCode, String repMsg) {
        this.repCode = repCode;
        this.repMsg = repMsg;
    }

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public String getRepMsg() {
        return repMsg;
    }

    public void setRepMsg(String repMsg) {
        this.repMsg = repMsg;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "{" + "repCode='" + repCode + '\'' + ", repMsg='" + repMsg + '\''
               + ", datas=" + datas + '}';
    }
}
