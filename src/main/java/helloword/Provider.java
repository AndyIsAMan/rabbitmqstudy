package helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import helloword.utils.RabbitMQUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;


public class Provider {

    // 生产消息
    @Test
    public void testSendMessage() throws Exception {

        // 创建mq的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置mq的连接主机
//        connectionFactory.setHost("192.168.211.130");
        // 设置端口号
//        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
//        connectionFactory.setVirtualHost("/ems");
        // 设置访问虚拟主机的用户名密码
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
        // 获取连接对象
//        Connection connection = connectionFactory.newConnection();

        // 通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 通过连接获取连接通道对象
        Channel channel = connection.createChannel();
        //  通道绑定对应的消息队列
        // 参数1：队列的名称 在不存在的情况下会自动创建
        // 参数2：用来定义队列的特性是否需要持久化 true 代表持久化 false代表不持久化
        // 参数3： exclusive 是否独占队列 当前队列只允许当前的连接可以用 其他的队列不可以用 true代表独占队列 false代表不独占
        // 参数4： autoDelete 是否在消费完成后自动删除队列 true 自动删除 false 不自动删除
        // 参数5： 额外参数 附加参数
        channel.queueDeclare("hello", false, false, false, null);
        // 发布消息
        // 参数1： 交换机名称 参数2：队列名称 参数3：传递消息的额外设置 参数4： 消息的具体内容
        for (int i = 0; i < 100; i++) {
            String message ="第" +i+"条消息";
            channel.basicPublish("", "hello", null, message.getBytes(StandardCharsets.UTF_8));

        }
        // 关闭通道
//        channel.close();
//        connection.close();
        // 通过工具类关闭流
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
