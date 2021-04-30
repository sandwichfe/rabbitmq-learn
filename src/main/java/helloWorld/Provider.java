package helloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author user
 * @Description
 * @create 2021-04-29 17:35
 */
public class Provider {

    //生产者 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
       /* //创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接rabbitmq的主机    ip
        connectionFactory.setHost("192.168.2.234");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");*/


        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取连接通道
        Channel channel = connection.createChannel();
        //将连接通道绑定对应消息队列
        //参数1： 队列名称  如果队列不存在 自动创建
        //参数2：用来定义队列特性是否需要持久化 ture持久化  false 不持久化
        //参数3：exclusive 是否独占队列
        //参数4：autoDeleted   是否在消费完成后自动删除队列
        //参数5： 额外的参数
        channel.queueDeclare("hello", false, false, false, null);
        //发布消息
        //参数: 参数1：交换机名称  参数2：队列名称   参数3：传递消息额外设置 参数4： 消息内容
        channel.basicPublish("", "hello", null, "hello rabbitmq11".getBytes());
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
