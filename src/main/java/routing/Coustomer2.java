package routing;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * Routing 模式 路由模式消费者2
 */
public class Coustomer2 {

    public static void main(String[] args) throws IOException {
        // 获取mq的连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 根据连接对象创建通道信息
        Channel channel = connection.createChannel();
        // 声明交换机
        channel.exchangeDeclare("logs_direct", "direct");
        // 创建临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定队列和交换机
        channel.queueBind(queue, "logs_direct", "error");
        // 消费消息
        channel.basicConsume(queue, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 " + new String(body));
            }
        });
    }
}
