package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * Routing 路由模式的消息提供者
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 根据连接通道创建对象
        Channel channel = connection.createChannel();
        // 声明交换机  参数1:交换机名称 参数2:交换机类型 基于指令的Routing key转发
        channel.exchangeDeclare("logs_direct","direct");
        String key = "info";
        // 发布消息
        channel.basicPublish("logs_direct",key,null,("指定的route key"+key+"的消息").getBytes());

    }
}
