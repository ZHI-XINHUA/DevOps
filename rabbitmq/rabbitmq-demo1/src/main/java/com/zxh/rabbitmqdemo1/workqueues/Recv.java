package com.zxh.rabbitmqdemo1.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.zxh.rabbitmqdemo1.base.ConnFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Worker
 * @Description 消费者接受消息
 * @Author xh.zhi
 * @Date 2019-9-25 9:08
 * @Version 1.0
 **/
public class Recv {
    private static final String TASK_QUEUE_NAME = "task_queue";


    /**
     * 消费消息
     * @param worker
     * @throws Exception
     */
    public void recivemsg(String worker) throws Exception{
        Connection connection = ConnFactory.create();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" ["+worker+"] Waiting for messages. To exit press CTRL+C");

        //一次只接受一条未加密的消息
        channel.basicQos(1);

        DeliverCallback deliverCallback = ((consumerTag, deliver) -> {
            String message = new String(deliver.getBody(),"utf-8");
            System.out.println(" ["+worker+"] Received '" + message + "'  "+getCurTime());
            try {
                doWork(message);
            } finally {
                System.out.println(" ["+worker+"] Done");
                //消息确认
                channel.basicAck(deliver.getEnvelope().getDeliveryTag(),false);
            }
        });

        //消费消息
        channel.basicConsume(TASK_QUEUE_NAME,false,deliverCallback,consumerTag -> {});
    }

    //处理消息
    private  void doWork(String message) {
        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private  String getCurTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
}
