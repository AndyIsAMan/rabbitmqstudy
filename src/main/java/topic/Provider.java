package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Topic 模式 消息提供者
 *
 *  统配符
 * 		* (star) can substitute for exactly one word.    匹配不多不少恰好1个词
 * 		# (hash) can substitute for zero or more words.  匹配一个或多个词
 *
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 通过连接对象获取通道
        Channel channel = connection.createChannel();
        // 声明交换机和交换机的类型 topic 使用动态路由 (通配符方式)
        channel.exchangeDeclare("topics", "topic");
        // 动态路由key
        String routekey = "user.save";
        // 发布消息
        channel.basicPublish("topics", routekey,null, ("这是路由中的动态订阅模型, route key: [" + routekey + "]").getBytes(StandardCharsets.UTF_8));

    }
}
