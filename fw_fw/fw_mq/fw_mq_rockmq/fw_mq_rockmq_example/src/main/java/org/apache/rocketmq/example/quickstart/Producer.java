/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.example.quickstart;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.util.RockmqProps;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr(RockmqProps.nameServers);
        producer.setSendMsgTimeout(10000);
        producer.setAsyncSenderExecutor(Executors.newSingleThreadExecutor());
        producer.setRetryTimesWhenSendFailed(1); producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.setCompressMsgBodyOverHowmuch(10);
        producer.setDefaultTopicQueueNums(10);
        producer.setMaxMessageSize(1000);
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        producer.setSendLatencyFaultEnable(true);
        producer.setSendMessageWithVIPChannel(false);
        producer.setClientCallbackExecutorThreads(10);
        producer.setHeartbeatBrokerInterval(10);
        producer.setPollNameServerInterval(20);
        producer.setPullTimeDelayMillsWhenException(40);
        producer.start();
        producer.setCallbackExecutor(Executors.newSingleThreadExecutor());
        for (int i = 0; i < 1; i++) {
            try {
                Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //msg.setDelayTimeLevel(1);
                SendResult sendResult = producer.send(Lists.newArrayList(msg));
                log.info("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        //producer.shutdown();
    }
}
