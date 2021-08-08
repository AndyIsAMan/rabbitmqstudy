package topic;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * route 模式消费者1
 */
public class Coustomer1 {
    public static void main(String[] args) throws IOException {

        // 获取mq的连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 通过连接对象创建通道
        Channel channel = connection.createChannel();
        // 声明交换机
        // 参数1: 你要声明的交换机的名字
        // 参数2: 你要声明的交换机的类型
        channel.exchangeDeclare("topics", "topic");
        // 创建临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定队列与交换机并设置获取交换机中的动态路由
        // 参数1 :队列的名字
        // 参数2 : 交换机的名字
        // 参数3: 你设置的动态路由
        channel.queueBind(queue, "topics", "user.*");
        // 消费消息
        channel.basicConsume(queue, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: "+new String(body));
            }
        });
    }
}
