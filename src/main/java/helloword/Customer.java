package helloword;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
    public static void main(String[] args) throws IOException, TimeoutException  {
        // 创建连接工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置mq的连接主机
//        connectionFactory.setHost("192.168.211.130");
        // 设置mq的tcp通信ip
//        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
//        connectionFactory.setVirtualHost("/ems");
        // 设置虚拟主机的账号密码
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
        // 获取连接对象
//        Connection connection = connectionFactory.newConnection();

        // 通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 绑定队列
        channel.queueDeclare("hello", false, false, false, null);
        // 消费消息
        // 参数1： 消费哪个队列的消息 队列名称
        // 参数2： 开启消费自动确认机制
        // 参数3： 消费时回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            // 处理消息时的回调
            // 最后一个参数  消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("message===> " + new String(body));
            }
        });
        // 消费者不要关闭通道 不然会停止监听
        // 关闭通道
//        channel.close();
//        connection.close();
    // 通过工具类关闭流
//        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }


}
