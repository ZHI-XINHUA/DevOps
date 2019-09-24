package com.zxh.rabbitmqdemo1.base;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;



/**
 * @ClassName ConnFactory
 * @Description 创建连接
 * @Author xh.zhi
 * @Date 2019-9-24 16:10
 * @Version 1.0
 **/
public class ConnFactory {
    private static final String IP_ADDREDD = "192.168.3.47";
    private static final int PORT = 5672; //rabbitmq 默认端口5672
    private static final String username = "admin";
    private static final String password = "123456";

    public static Connection create(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDREDD);
        factory.setPort(PORT);
        factory.setUsername(username);
        factory.setPassword(password);
        //创建连接
        try {
            return factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
