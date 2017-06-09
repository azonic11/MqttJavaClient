package main;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectionSettings {
	private JPanel root;
	private JTextField broker;
	private JTextField port;
	private JTextField clientID;
	private JTextField username;
	private JTextField password;
	private JCheckBox ssl;

	private Map<String,String> serverSettings=new HashMap<String,String>();
	
	public ConnectionSettings() {

		root = new JPanel();
		
		root.setPreferredSize(new Dimension(200,240));
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.setPreferredSize(new Dimension(200,240));
		
		optionsPanel.add(new JLabel("Broker: "));
		optionsPanel.add(broker=new JTextField());
		optionsPanel.add(new JLabel("Port: "));
		optionsPanel.add(port=new JTextField());
		optionsPanel.add(new JLabel("ssl: "));
		optionsPanel.add(ssl=new JCheckBox());
		optionsPanel.add(new JLabel("Client ID:"));
		optionsPanel.add(clientID=new JTextField());
		optionsPanel.add(new JLabel("User name: "));
		optionsPanel.add(username=new JTextField());
		optionsPanel.add(new JLabel("Password: "));
		optionsPanel.add(password=new JPasswordField());
		
		root.add(optionsPanel);
		
	}

	public JPanel getJPanel (){
		return root;
	}
	
	public JPanel getJPanel (Map<String,String> settings){
		setSettings(settings);
		return root;
	}
	
	public Map<String, String> getSettings(){
		if (clientID.getText().isEmpty())
			clientID.setText("javaClient"+ new Random().nextInt((1000 - 0) + 1) + 0);
			//System.out.println(new Random().nextInt((1000 - 0) + 1) + 0);
		serverSettings.put("broker", broker.getText());
		serverSettings.put("port", port.getText());
		serverSettings.put("ssl", String.valueOf(ssl.isSelected()));
		serverSettings.put("clientID", clientID.getText());
		serverSettings.put("username", username.getText());
		serverSettings.put("password", password.getText());
		return serverSettings;
	}
	
	public void setSettings(Map<String,String> settings){
		this.broker.setText(settings.get("broker"));
		this.clientID.setText(settings.get("clientID"));
		this.username.setText(settings.get("username"));
		this.password.setText(settings.get("password"));
		this.port.setText(settings.get("port"));
	}
}
