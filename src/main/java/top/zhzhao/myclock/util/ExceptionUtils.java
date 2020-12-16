/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.util;

/**
 * 异常处理工具类
 *@author zhzhao
 *@version $ Id: ExceptionUtils.java,V 0.1 2019/10/28 19:19 zhzhao Exp $
 */
public class ExceptionUtils {

    /**
     * 获取异常详细信息
     */
    public static  String getExceptionDetail(Exception e){
        StringBuilder stringBuffer = new StringBuilder(e.toString() + ":: \n");
        StackTraceElement[] msgs = e.getStackTrace();
        int length = msgs.length;
        for (StackTraceElement msg : msgs) {
            stringBuffer.append("\t").append(msg.toString()).append("\n");
        }
        return stringBuffer.toString();
    }
}
