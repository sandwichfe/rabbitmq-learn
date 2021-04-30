package helloWorld;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author lww
 * @Description
 * @create 2021-04-30 9:34
 */
public class Customer {
    public static void main(String[] args) throws IOException, TimeoutException {
  /*      //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.2.234");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");  //虚拟主机
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");*/
        //创建连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //通道绑定对象
        channel.queueDeclare("hello", false, false, false, null);
        //消费消息
        //参数一: 消费哪个队列的名称，执行要消费的队列
        //参数二：开启消息的自动确认机制
        //参数三：消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override     //最后一个参数：消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("the message:" + new String(body));
            }
        });
        //这里不建议取关闭他们   因为关闭了线程就结束了  执行不到上面的回调函数了
        //channel.close();
        //connection.close();
    }
}
