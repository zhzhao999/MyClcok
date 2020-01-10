package top.zhzhao.myclock.exception;

import top.zhzhao.myclock.util.response.ResponseVO;

/**
 * 自定义异常
 * @author zhzhao
 * Created at 2018/8/24.
 */
public class CustomException extends RuntimeException{
    private ResponseVO response;
    private String     errorMsg;

    public CustomException(ResponseVO response) {
        this.response = response;
    }
    public CustomException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ResponseVO getResponse() {
        return response;
    }

    public void setResponse(ResponseVO response) {
        this.response = response;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
