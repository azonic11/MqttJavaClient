package main;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.fusesource.mqtt.client.*;

public  class Client implements Runnable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient MQTT mqtt;
	private transient BlockingConnection connection;
	private String broker;
	private String subTopic;
	private String pubTopic;
	private String userName;
	private String password;
	private String clientID;
	private int qos;
	private int port;
	private boolean ssl;
	private DashBoard dashBoard;
	private boolean stop=false;

	public Client(Map<String, String> settings, DashBoard dashBoard) {
		
		this.dashBoard = dashBoard;
		mqtt = new MQTT();
		this.broker = settings.get("broker");
		this.userName = settings.get("username");
		this.password = settings.get("password");
		this.clientID = settings.get("clientID");
		this.ssl = Boolean.getBoolean(settings.get("ssl"));
	
		try{
			this.port = Integer.valueOf(settings.get("port"));
		}catch(Exception e){
			this.port=1;
			System.out.println(
		this.port = Integer.valueOf(settings.get("port")));
		}
	
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			
			
			System.out.println("connecting");
			dashBoard.setConnectionStatus("Connecting");
			
			mqtt.setHost(broker, port);
			mqtt.setUserName(userName);
			mqtt.setPassword(password);
			mqtt.setClientId(clientID);
			
			connection = mqtt.blockingConnection();
			connection.connect();
			connection.publish("greating", "Hello from java app".getBytes(), QoS.AT_LEAST_ONCE, false);
			dashBoard.setConnectionStatus("Connected");
			
			while (!stop) {
				System.out.println("Thread-"+Thread.currentThread().getId());
				System.out.println("Thread count "+Thread.activeCount());
				Message message = connection.receive();
				System.out.println(message.getTopic());
				byte[] payload = message.getPayload();
				// process the message then:
				String s = new String(payload);
				System.out.println("Text Decryted : " + s);
				message.ack();
				
				for(int i=0;i<dashBoard.getButtonList().size();i++){
					if((dashBoard.getButtonList().get(i).getSubTopic().equals(
							(String)message.getTopic().toString())))
					{
						if(dashBoard.getButtonList().get(i).getMsgOff().equals(s))
							dashBoard.getButtonList().get(i).setStateOff();
						
						else if(dashBoard.getButtonList().get(i).getMsgOn().equals(s))
							dashBoard.getButtonList().get(i).setStateOn();
					
					}
				}
			
				
			}
		} catch (Exception e) {
			dashBoard.setConnectionStatus("Disconected");
			System.out.println("connection failed");
			System.out.println(e.getMessage());
		}
	}

	public void publicMsg(String topic,String mesage) {
		try {
			connection.publish(topic, mesage.getBytes(), QoS.AT_LEAST_ONCE, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void subscribe(String s) {
		System.out.println(s);
		Topic[] topics = { new Topic(s, QoS.AT_LEAST_ONCE) };
		try {
			byte[] qoses = connection.subscribe(topics);
		} catch (Exception e) {
			System.out.println("not subs");
		}
	}
	
	
	public void stop() throws Exception{
		stop=true;
		connection.disconnect();
		
	}
	public boolean isConnected(){
		if(connection!=null)
			return connection.isConnected();
		else
			return false;
	}
}
