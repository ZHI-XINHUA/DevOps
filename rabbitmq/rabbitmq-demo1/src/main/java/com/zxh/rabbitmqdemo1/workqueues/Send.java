package com.zxh.rabbitmqdemo1.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.zxh.rabbitmqdemo1.base.ConnFactory;



/**
 * @ClassName NewTask
 * @Description Work queues demo
 *           生产者
 * @Author xh.zhi
 * @Date 2019-9-25 8:57
 * @Version 1.0
 **/
public class Send {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        String[] messages = new String[]{"First message.","Second message..","Third message...","Fourth message....","Fifth message....."};
        for (String message:messages) {
            sendMsg(message);
        }
    }

    public static void sendMsg(String msg) throws Exception{
        Connection connection = ConnFactory.create();
        try(Channel channel = connection.createChannel()){
            boolean durable = true; //持久化消息
            channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
            String message = String.join(" hello : ", msg);

            //发送持久化消息
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");
        }

        //关闭资源
        //connection.close();
    }


}
