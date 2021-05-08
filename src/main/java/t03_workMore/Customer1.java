package t03_workMore;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-04-30 17:21
 */

public class Customer1 {

    public static void main(String[] args) throws IOException {
        System.out.println("customer1 init...........");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);    //设置通道一次性只发一条消息
        channel.queueDeclare("work", true, false, false, null);

        //将消息的自动确认 autoAck改为false  不然队列在分发消息时将消息给了这个消费者 但有可能他还没有消费完成 但是自动确认了  造成有误情况。
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //模拟中间操作  sleep 2s
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者-1：" + new String(body));
                //确认消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
