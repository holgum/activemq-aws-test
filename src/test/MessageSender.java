package test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class MessageSender {
	
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    //private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
//	private static String url = "ssl://b-a7d64cec-e0d9-45b4-9fd5-905dfe0e703d-1.mq.us-east-1.amazonaws.com:61617";
//	private static String url = "tcp://localhost:30612";

	private static String url = "failover:(tcp://localhost:30612,tcp://localhost:30616)";
	
	
    // default broker URL is : tcp://localhost:61616"
	
    private static String subject = "TEST.QUEUE"; // Queue Name.You can create any/many queue names as per your requirement. 
     
    public static void main(String[] args) throws JMSException {        


        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection("admin","admin-pw");
        connection.start();
        System.out.println("** starting connection..");
    	
   	
         
        // MessageProducer is used for sending messages to the queue.
        Integer i = 0;
        while(true) {


            //Creating a non transactional session to send/receive JMS message.

        	Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);  
            System.out.println("** session created..");
 
            //Destination represents here our queue 'JCG_QUEUE' on the JMS server. 
            //The queue will be created automatically on the server.
            Destination destination = session.createQueue(subject); 
     
        	MessageProducer producer = session.createProducer(destination);
        
	        // We will send a small text message saying 'Hello World!!!' 
	        TextMessage message = session
	                .createTextMessage("Hello !!! Welcome to the world of ActiveMQ." + i.toString());
	         
	        // Here we are sending our message!
	        producer.send(message);
	        try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("JCG printing@@ '" + message.getText());
	        i = i + 1;
        
	        session.close();

        }
        //connection.close();
        
    }
    

}
