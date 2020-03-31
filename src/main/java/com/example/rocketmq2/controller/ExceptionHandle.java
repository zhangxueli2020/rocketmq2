package com.example.rocketmq2.controller;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//import org.springframework.security.access.AccessDeniedException;

/**
 * Created by zhengping.zhu
 * on 2017/5/7.
 * 错误定义
 */
@RestControllerAdvice
public class ExceptionHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

	@Autowired
	private TransactionMQProducer transactionMQProducer;
    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionGet( Exception e) throws MQClientException {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	LOGGER.error("异常:"+e);
        resultMap.put("StatusCode", -100);	//未知错误
		String msg = "msg tagRollback";
		Message sendMsg = new Message("Db2Topic", "tagRollback", msg.getBytes());
		//默认3秒超时
		TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(sendMsg, null);
		LOGGER.info("sendResult:"+sendResult.toString());
		return resultMap;
    }
    
    
}
