package t05_订阅模型direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author user
 * @Description
 * @create 2021-05-06 17:07
 * 广播模式下的消费者         每一个消费者都收到了消息
 */
public class Customer2 {

    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //通道
        Channel channel = connection.createChannel();

        //将通道与生产者的交换机绑定
        channel.exchangeDeclare("mylogs_direct", "direct");

        //当前消费者的临时队列  在fanout模式下 每个消费者都有自己对应的临时队列
        String queue = channel.queueDeclare().getQueue();

        //将生产者的交换机与消费者的队列绑定起来
        //以及routingKey
        String routingKey = "BBB";
        channel.queueBind(queue, "mylogs_direct", routingKey);
        System.out.println("与生产者routingKey不同所以消费不到");
        //消费信息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2" + new String(body));
            }
        });
    }
}
