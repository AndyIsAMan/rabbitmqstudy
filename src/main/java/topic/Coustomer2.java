package topic;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * 路由模式消费者2
 */
public class Coustomer2 {
    public static void main(String[] args) throws IOException {
        // 获取rabbitmq的连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 根据连接对象创建消息通道
        Channel channel = connection.createChannel();
        // 声明交换机
        channel.exchangeDeclare("topics", "topic");
        // 创建临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定路由与交换机并设置获取交换机中动态路由
        channel.queueBind(queue, "topics", "user.#");

        // 消费消息
        channel.basicConsume(queue, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: "+new String(body));
            }
        });
    }
}
