package t05_订阅模型direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-05-06 17:27
 * direct 消息订阅模式
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();
        //通过通道声明交换机
        channel.exchangeDeclare("mylogs_direct","direct");

        //发送消息
        //只有routingKey和生产者相同的消费者 才能  接受到他的消息
        String routingKey="AAA";
        channel.basicPublish("mylogs_direct",routingKey,null,"message in direct订阅模式".getBytes());

        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
