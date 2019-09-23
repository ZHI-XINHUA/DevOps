package com.zxh.rabbitmqdemo1.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitProducer
 * @Description 生产者
 * @Author xh.zhi
 * @Date 2019-9-23 15:23
 * @Version 1.0
 **/
public class RabbitProducer {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME="queue_demo";
    private static final String IP_ADDREDD = "192.168.3.46";
    private static final int PORT = 5672; //rabbitmq 默认端口5672

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDREDD);
        factory.setPort(PORT);
        factory.setUsername("admin");
        factory.setPassword("admin123");
        //创建连接
        Connection connection = factory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //创建一个type=“direct”、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        //发送一条持久化消息
        String message = "hello world!";
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

        //关闭资源
        channel.close();
        connection.close();


    }


}
