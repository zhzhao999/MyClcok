package top.zhzhao.myclock.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhzhao.myclock.util.ExceptionUtils;
import top.zhzhao.myclock.util.response.ResponseVO;
import top.zhzhao.myclock.util.response.ResponseVOUtils;

/**
 * 异常处理类
 * controller层异常无法捕获处理，需要自己处理
 * @author zhzhao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ResponseVO handleCustomException(CustomException e){
        if (StringUtils.isNotBlank(e.getErrorMsg())){
            log.error("自定义异常 -- " + e.getErrorMsg());
            return ResponseVOUtils.commonError(e.getErrorMsg());
        }else {
            log.error("自定义异常 -- " + e.getResponse().getRepMsg());
            return e.getResponse();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseVO handException(Exception e){
        log.error("全局异常 -- " + ExceptionUtils.getExceptionDetail(e));
        return ResponseVOUtils.error("500",e.getMessage());
    }



}
