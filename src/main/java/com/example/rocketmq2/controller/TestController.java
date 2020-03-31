package com.example.rocketmq2.controller;

import com.example.rocketmq2.jpa.TUser;
import com.example.rocketmq2.jpa.UserService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import org.apache.rocketmq.common.ServiceState;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DefaultMQPushConsumer consumer;

    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;


    @RequestMapping("/add")
    @Transactional
    public String add(String name, Integer money, boolean eflag)  throws  Exception {
        TUser user = userService.findByName(name);
        user.setMoney(Optional.ofNullable(user.getMoney()).orElse(0) + money);
        userService.saveUser(user);
        if (eflag) {
            throw new RuntimeException();
        }
        return "ok";
    }
}
