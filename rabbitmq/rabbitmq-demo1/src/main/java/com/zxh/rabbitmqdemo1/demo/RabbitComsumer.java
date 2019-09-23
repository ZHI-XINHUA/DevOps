package com.zxh.rabbitmqdemo1.demo;

import ch.qos.logback.core.util.TimeUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitComsumer
 * @Description rabbit 消费者
 * @Author xh.zhi
 * @Date 2019-9-23 15:40
 * @Version 1.0
 **/
public class RabbitComsumer {
    private static final String QUEUE_NAME="queue_demo";
    private static final String IP_ADDRESS="192.168.3.46";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin123");
        //这里的连接方式与生产者的demo略有不同
        Connection connection = factory.newConnection(new Address[]{new Address(IP_ADDRESS,PORT)});
        //创建信道
        Channel channel = connection.createChannel();
        //设置客户端最多接收未被ack的消息个数
        channel.basicQos(64);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message:"+ new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                channel.basicAck(envelope.getDeliveryTag(),false);
            }

        };

        channel.basicConsume(QUEUE_NAME,consumer);
        //等待回调函数执行完毕之后，关闭资源
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();

    }
}
