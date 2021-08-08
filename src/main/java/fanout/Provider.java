package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * 广播模式消息生产者
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        /*获取连接对象*/
        Connection connection = RabbitMQUtils.getConnection();
        /*创建通道*/
        Channel channel = connection.createChannel();
        /* 广播 一条消息多个消费者同时消费*/
        channel.exchangeDeclare("logs","fanout");
        /*发布消息*/
        channel.basicPublish("logs","",null,"hello".getBytes());

    }
}
