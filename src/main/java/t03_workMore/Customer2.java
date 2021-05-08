package t03_workMore;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-04-30 17:21
 */

public class Customer2 {

    public static void main(String[] args) throws IOException {
        System.out.println("customer2 init...........");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);    //设置通道一次性只发一条消息
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2：" + new String(body));
                //确认本次消息 已经 操作完成   让队列知道
                //参数： 1。确认哪条消息     2.是否开启批量确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
