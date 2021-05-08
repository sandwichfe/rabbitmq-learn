package t04_广播模型fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;


/**
 * @author user
 * @Description
 * @create 2021-05-06 11:50
 * 广播
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        //获取连接对象
        Connection connection= RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();

        //为发送者 通道绑定交换机
        channel.exchangeDeclare("mylogs","fanout");   //交换机名称  以及交换机类型      名称没有则会被创建

        //发送消息
        channel.basicPublish("mylogs","",null,"fanout广播类型的 type message".getBytes());

        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
