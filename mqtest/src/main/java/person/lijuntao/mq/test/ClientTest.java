package person.lijuntao.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp.Factory;

public class ClientTest {
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
			for(int i=10;i<20;i++)
			channel.basicPublish("", "q1", null, ("hello world"+i).getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
