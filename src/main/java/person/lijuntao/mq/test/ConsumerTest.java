package person.lijuntao.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp.Factory;

public class ConsumerTest {
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		//factory.setPort(5672);
		//factory.setUsername("lijuntao");
		//factory.setPassword("123456");
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare();
			String queueName = "q1";
			//String string = channel.basicConsume(queueName, null);
			//System.out.println(string);
			Consumer consumer = new DefaultConsumer(channel) {
			      @Override
			      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			          throws IOException {
			        String message = new String(body, "UTF-8");
			        System.out.println(" [x] Received '" + message + "'");
			      }
			    };
			channel.basicConsume(queueName, true, consumer);
			//channel.basicPublish("hello", "world", null, new byte[]{1,2,3,4,5,6,7,8,9,0});
			//while(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
