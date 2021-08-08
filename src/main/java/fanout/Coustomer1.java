package fanout;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * 消费者1 fanout 广播模式的消费者1
 */
public class Coustomer1 {

    public static void main(String[] args) throws IOException {
        /* 获取连接对象 */
        Connection connection = RabbitMQUtils.getConnection();
        /* 创建通道 */
        Channel channel = connection.createChannel();
        /* 绑定交换机 */
        channel.exchangeDeclare("logs", "fanout");
        /* 创建临时队列 */
        String queue = channel.queueDeclare().getQueue();
        /* 将临时队列绑定到交换机 */
        channel.queueBind(queue, "logs", "");
        /* 处理消息 */
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });
    }
}
