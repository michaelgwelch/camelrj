package welch.subscriber;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.Basic.Consume;



public class ReceiveImages {

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setVirtualHost("images");;
		factory.setUsername("guest");;
		factory.setPassword("guest");;
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, "completed_images", "");
		System.out.println(" [*] Waiting for images");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			 public void handleDelivery(String consumerTag, Envelope envelope,
                     AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}

		};
		
		channel.basicConsume(queueName, true, consumer);
	}

}
/*
var factory = new ConnectionFactory() { HostName = "localhost", VirtualHost="images",
UserName="guest", Password="guest"};
using (var connection = factory.CreateConnection())
using (var channel = connection.CreateModel())
{
//channel.ExchangeDeclare(exchange: "logs", type: "fanout");

var queueName = channel.QueueDeclare().QueueName;
channel.QueueBind(queue: queueName,
				  exchange: "completed_images",
				  routingKey: "");

Console.WriteLine(" [*] Waiting for logs.");

var consumer = new EventingBasicConsumer(channel);
consumer.Received += (model, ea) =>
{
	var body = ea.Body;
	var message = Encoding.UTF8.GetString(body);
	Console.WriteLine(" [x] {0}", message);

};
channel.BasicConsume(queue: queueName,
					 noAck: true,
					 consumer: consumer);

Console.WriteLine(" Press [enter] to exit.");
Console.ReadLine();
}
*/