package t02_workquene;

import com.rabbitmq.client.*;
import com.sun.jndi.ldap.LdapURL;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-04-30 17:21
 */

public class Customer1 {

    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1：" + new String(body));
            }
        });
    }
}
