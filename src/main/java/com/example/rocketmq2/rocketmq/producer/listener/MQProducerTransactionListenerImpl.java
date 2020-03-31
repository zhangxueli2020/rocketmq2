package com.example.rocketmq2.rocketmq.producer.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MQProducerTransactionListenerImpl implements TransactionListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(MQProducerTransactionListenerImpl.class);


	/**
	 * 本地事务处理
	 */
	@Override
	public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		LOGGER.info("本地事务处理:" + msg.getTags());
		if (msg.getTopic().equals("Db2Topic")) {
			if (msg.getTags().equals("tagCommit2")) {
				LOGGER.info("执行事务tagCommit：");
				return LocalTransactionState.COMMIT_MESSAGE;
			} else if (msg.getTags().equals("tagRollback2")) {
				LOGGER.info("执行事务tagRollback：");
				return LocalTransactionState.ROLLBACK_MESSAGE;
			} else if (msg.getTags().equals("tagUnknow2")) {
				LOGGER.info("执行事务tagUnknow：");
				return LocalTransactionState.UNKNOW;
			}
		}
		return LocalTransactionState.ROLLBACK_MESSAGE;
	}

	/**
	 * 检查
	 */
	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt msg) {
		LOGGER.info("正在检查Tag:" + msg.getTags());
		if (msg.getTopic().equals("Db2Topic")) {
			if (msg.getTags().equals("tagCommit2")) {
				LOGGER.info("执行事务tagCommit：");
				return LocalTransactionState.COMMIT_MESSAGE;
			} else if (msg.getTags().equals("tagRollback2")) {
				LOGGER.info("执行事务tagRollback：");
				return LocalTransactionState.ROLLBACK_MESSAGE;
			} else if (msg.getTags().equals("tagUnknow2")) {
				LOGGER.info("执行事务tagUnknow：");
				return LocalTransactionState.UNKNOW;
			}
		}
		return LocalTransactionState.ROLLBACK_MESSAGE;
	}


}
