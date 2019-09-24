package com.zxh.rabbitmqdemo1.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmqdemo1.base.ConnFactory;



/**
 * @ClassName Send  生产者
 * @Description  简单消息队列 demo
 *                   p --> queue
 * @Author xh.zhi
 * @Date 2019-9-24 16:14
 * @Version 1.0
 **/
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        //创建连接
        Connection connection = ConnFactory.create();
        //创建一个channel
        try (Channel channel = connection.createChannel()) {
            //什么一个队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "Hello World!";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        //关闭资源
        //connection.close();
    }
}
