package WorkQuene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 工作队列模型的生产者
 * 在work模型下 如果不做任何设置 默认消费者是平均分配的 有几个消费者就就平均分配给几个消费者 TODO
 * 官网上也是这么定义的 work模型是平均消费消息的
 * work模型的缺点：
 *  因为work模型是平均分配的，所以如果有某个服务器慢的话 他们还是平均分配 慢的服务器慢慢的执行 快的服务器
 *  执行完就没事了 这样消息消费整体上来说就不好了
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 通过通道声明队列
        // queueDeclare的汉语意思是 **队列声明**
        channel.queueDeclare("work", true, false, false, null);
        for (int i = 1; i <= 100; i++) {
            // 生产消息
            // 参数2 是队列名称
            channel.basicPublish("", "work", null, (i + "hello work quene").getBytes(StandardCharsets.UTF_8));
        }
        //关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);

    }
}
