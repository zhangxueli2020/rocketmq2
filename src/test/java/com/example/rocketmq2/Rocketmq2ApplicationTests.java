package com.example.rocketmq2;

import com.example.rocketmq2.jpa.TUser;
import com.example.rocketmq2.jpa.UserService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Rocketmq2ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(Rocketmq2ApplicationTests.class);

    @Autowired
    private TransactionMQProducer transactionMQProducer;
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void sendTransactionMsg() throws MQClientException, RemotingException, MQBrokerException, InterruptedException{

        TUser user = userService.findByName("a");
        user.setMoney(Optional.ofNullable(user.getMoney()).orElse(0) + 100);
        userService.saveUser(user);
        String msg = "demo msg test";
        logger.info("开始发送消息："+msg);
        Message sendMsg = new Message("Db2Topic","tagRollback",msg.getBytes());
        //默认3秒超时
        SendResult sendResult = transactionMQProducer.sendMessageInTransaction(sendMsg, null);
        logger.info("消息发送响应信息："+sendResult.toString());
    }

}
