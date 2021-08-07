package helloword.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq工具类
 */
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;

    // 静态代码块 在类加载的时候执行 只执行一次
    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.211.130");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
    }

    // 定义提供连接对象的方法
    public static Connection getConnection()  {
        try {
            // 连接工厂属于重量级工厂

            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭通道和关闭连接的方法
    public static void closeConnectionAndChannel(Channel channel, Connection connection){
        try {
            if (null != channel){
                channel.close();
            }
            if (null != connection){
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
