package workquene;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * work模型消费者2
 */
public class Custonmer2 {

    public static void main(String[] args) throws IOException {
        // 通过RabbitMqUtils获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 创建通道对象
        final Channel channel = connection.createChannel();
        // 给通道绑定对队列
        channel.queueDeclare("work", true, false, false, null);
        // 每次只能消费一个消息
        channel.basicQos(1);
        // 开始消费
        // 第二个参数的意思是是否确认消息
        channel.basicConsume("work", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 ：" + new String(body));
                // 手动确认消息消费
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
