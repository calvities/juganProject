package com.jg.project.comm.exception;

/**
 * mq异常处理
 * @ClassName RocketMqException
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 17:55
 * @Version 1.0
 **/
public class RocketMqException extends RuntimeException {

    public RocketMqException() {
        super();
    }

    public RocketMqException(String message, Throwable cause) {
        super(message, cause);
    }

    public RocketMqException(String message) {
        super(message);
    }

    public RocketMqException(Throwable cause) {
        super(cause);
    }
}
