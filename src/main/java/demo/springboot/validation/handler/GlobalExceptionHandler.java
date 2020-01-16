package demo.springboot.validation.handler;

import demo.springboot.validation.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author dean.lee
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * post请求参数校验抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        //获取异常中随机一个异常信息
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return JsonResult.fail(defaultMessage);
    }

    /**
     * get请求参数校验抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult bindExceptionHandler(BindException e){
        //获取异常中随机一个异常信息
      //  String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        //获取异常中所有的异常信息
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for(FieldError fieldError:fieldErrors ){
            log.info(fieldError.getDefaultMessage());
            sb.append(fieldError.getDefaultMessage()+"    ");
        }

        return JsonResult.fail(sb.toString());
    }

    /**
     * 请求方法中校验抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult constraintViolationExceptionHandler(ConstraintViolationException e){
        //获取异常中第一个错误信息
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return JsonResult.fail(message);
    }
}
