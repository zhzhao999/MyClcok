/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.util.response;


/**
 *生成ResponseVO对象的工具类
 *@author zhzhao
 *@version $ Id: ResponseVOUtils.java,V 0.1 2019/7/9 19:23 zhzhao Exp $
 */
public class ResponseVOUtils {

    //禁止实例化
    private ResponseVOUtils() {
    }

    /**
     * 正确返回（含其他Data数据）
     *
     * @param datas 返回的数据
     * @return ResponseVO
     */
    public static <T> ResponseVO<T> success(T datas) {
        ResponseVO<T> vo = new ResponseVO<T>("200","操作成功");
        vo.setDatas(datas);
        return vo;
    }

    /**
     * 正确返回（无参）
     *
     * @return ResponseVO
     */
    public static <T> ResponseVO<T> success() {
        return new ResponseVO<T>("200","操作成功");
    }


    /**
     * 自定义异常返回VO
     *
     * @param errMsg 错误信息
     * @return ResponseVO
     */
    public static <T> ResponseVO<T> commonError(String errMsg) {
        ResponseVO<T> vo = new ResponseVO<T>("400", errMsg);
        return vo;
    }

    /**
     * 自定义Error
     *
     * @param : errMsg 错误信息
     * @return : ResponseVO
     */
    public static <T> ResponseVO<T> error(String code,String errMsg) {
        return new ResponseVO<T>(code, errMsg);
    }

}
