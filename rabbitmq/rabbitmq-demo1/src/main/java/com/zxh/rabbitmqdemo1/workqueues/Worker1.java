package com.zxh.rabbitmqdemo1.workqueues;

/**
 * @ClassName Worker
 * @Description 消费者
 * @Author xh.zhi
 * @Date 2019-9-25 9:08
 * @Version 1.0
 **/
public class Worker1 {


    public static void main(String[] args) throws Exception {
        Recv recv = new Recv();
        recv.recivemsg("work1");

    }


}
