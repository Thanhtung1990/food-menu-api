package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RabbitMQUtil {

    public ConnectionFactory rabbit_ConnectionFactory(){

        // read data from rabbitMQConfig file

        PropertiesCache propertiesCache = new PropertiesCache("rabbitMQConfig");

        // get data from properties file
        String rabbitmq_hostName = propertiesCache.getProperty("rabbitmq_hostName");
        int rabbitmq_portNumber = Integer.parseInt(propertiesCache.getProperty("rabbitmq_portNumber"));
        String rabbitmq_virtualHost = propertiesCache.getProperty("rabbitmq_virtualHost");
        String rabbitmq_userName = propertiesCache.getProperty("rabbitmq_userName");
        String rabbitmq_password = propertiesCache.getProperty("rabbitmq_password");

        System.out.println("Create a ConnectionFactory");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(rabbitmq_virtualHost);
        factory.setHost(rabbitmq_hostName);
        factory.setPort(rabbitmq_portNumber);
        factory.setUsername(rabbitmq_userName);
        factory.setPassword(rabbitmq_password);

        System.out.println("Create a Connection");
        System.out.println("Create a Channel");

        return factory;

    }


    public void Producer(){

        String QUEUE_NAME = "queue";
        try (Connection connection = rabbit_ConnectionFactory().newConnection();
             Channel channel = connection.createChannel() ) {
            System.out.println("Create a queue " + QUEUE_NAME);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println("Start sending messages ... ");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
                String message;
                do {
                    System.out.print("Enter message: ");
                    message = br.readLine().trim();
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println(" [x] Sent: '" + message + "'");
                } while (!message.equalsIgnoreCase("close"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println("Close connection and free resources");
        }

    }

    public void Consumer(){}
}
