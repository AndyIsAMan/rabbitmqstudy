package WorkQuene;

import com.rabbitmq.client.*;
import helloword.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * work模型的消费者
 *
 */
public class Customer1 {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 获取通道
        final Channel channel = connection.createChannel();
        // 给通道绑定队列
        channel.queueDeclare("work", true, false, false, null);
        // 告诉通道每一次消费一个消息
        channel.basicQos(1);
        // 开始消费
        // 第二个参数 ： 消息自动确认  true 消费者自动向rabbitmq确认消息消费 false 手动确认消息
        // 他不管消费有没有消费完 只要拿到消息就自动告诉消息队列我消费完了 其实是没有消费完 他要慢慢消费
//        channel.basicConsume("work",true, new DefaultConsumer(channel) {
        channel.basicConsume("work",false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1 " + new String(body));
                // 模拟 这个服务器配置比较慢
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                // 手动确认消息
                // 参数1： 确认队列中的哪个具体消息
                // 参数2：是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        // 消费者不需要关闭消费通道，因为消费者要不断地监听消息通道
    }
}
