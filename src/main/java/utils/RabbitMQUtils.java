package utils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author user
 * @Description
 * @create 2021-04-30 10:05
 */
public class RabbitMQUtils {
    //工厂对象
    private static ConnectionFactory connectionFactory;

    //类加载的时候就会执行
    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.2.234");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");  //虚拟主机
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
    }
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnectionAndChanel(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
