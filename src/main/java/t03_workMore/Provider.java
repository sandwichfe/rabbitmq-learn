package t03_workMore;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-04-30 16:42
 * work  模式  的  provider与customer     能者多劳模式
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);      //durable   队列持久化
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("", "work", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + " hello work queue").getBytes());   //PERSISTENT_TEXT_PLAIN  消息持久化
        }
        //生产消息
        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

}
