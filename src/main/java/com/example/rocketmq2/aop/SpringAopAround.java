package com.example.rocketmq2.aop;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.rocketmq2.rocketmq.consumer.listener.MQConsumeMsgListenerProcessor;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component("logger")
@Aspect
public class SpringAopAround {

    private static final Logger logger = LoggerFactory.getLogger(SpringAopAround.class);

    @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Pointcut("execution(* com.example.rocketmq2.controller.*.*(..))")
    public void excudeService() {
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "excudeService()", throwing = "e")
    public void afterThrowingPrintLog(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("SpringAopAround异常抛出：当前登陆人操作代号" + request.getAttribute("loginUser"));
        logger.info("request.getContextPath():"+request.getContextPath());
    }


    @AfterReturning(pointcut = "excudeService()", returning = "r")
    public void doAfterReturning(JoinPoint joinPoint, Object r) throws Throwable {
        // 处理完请求，返回内容
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("AfterReturning:" + request.getAttribute("loginUser"));

//        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                if(CollectionUtils.isEmpty(msgs)){
//                    logger.info("接受到的消息为空，不处理，直接返回成功");
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//                MessageExt messageExt = msgs.get(0);
//                logger.info("接受到的消息为："+messageExt.toString());
//                if (messageExt.getTags().equals("tagRollback2")){
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
    }

    private static String getException(Throwable e){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return  baos.toString();
    }
}






