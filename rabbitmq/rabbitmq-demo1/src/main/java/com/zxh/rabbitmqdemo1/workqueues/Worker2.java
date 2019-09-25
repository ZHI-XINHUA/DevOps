package com.zxh.rabbitmqdemo1.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.zxh.rabbitmqdemo1.base.ConnFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Worker
 * @Description 消费者
 * @Author xh.zhi
 * @Date 2019-9-25 9:08
 * @Version 1.0
 **/
public class Worker2 {


    public static void main(String[] args) throws Exception {
        Recv recv = new Recv();
        recv.recivemsg("work2");

    }


}
