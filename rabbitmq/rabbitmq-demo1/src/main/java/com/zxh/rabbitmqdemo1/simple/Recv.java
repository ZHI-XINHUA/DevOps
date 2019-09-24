package com.zxh.rabbitmqdemo1.simple;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.zxh.rabbitmqdemo1.base.ConnFactory;

/**
 * @ClassName 消费者
 * @Description  简单消息队列 demo
 *                  queue --> c
 * @Author xh.zhi
 * @Date 2019-9-24 16:29
 * @Version 1.0
 **/
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnFactory.create();
        Channel channel = connection.createChannel();
        //什么队列，与生产者的一致
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //接受消息（消费队列）
        DeliverCallback deliverCallback = (consumerTag,delivery) ->{
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] consumerTag '"+consumerTag+"'   Received '" + message + "'");
        };

        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("cancelCallback '"+consumerTag+"'");
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

        //关闭资源
        //connection.close();
    }
}
