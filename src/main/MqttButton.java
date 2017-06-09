package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

class MqttButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String subTopic;
	private String pubTopic;
	private int qos = 0;
	private boolean retailed = false;
	private Icon iconOn;
	private Icon iconOff;
	private String msgOn;
	private String msgOff;
	private String name;
	private MqttButton thisButton;
	private boolean buttonState = true; // false -off true -on
	private Client client;

	public MqttButton() {
		super();
		this.addActionListener(this);
		super.setPreferredSize(new Dimension(115, 125));
		super.setBackground(Color.DARK_GRAY);

		thisButton = this;
		thisButton.setForeground(Color.darkGray);
		thisButton.setBorderPainted(false);
		thisButton.setFocusPainted(false);
		thisButton.setHorizontalTextPosition(SwingConstants.CENTER);
		thisButton.setVerticalTextPosition(SwingConstants.TOP);
		thisButton.setVerticalAlignment(SwingConstants.TOP);

		ModifButtonUI modButtonUI = new ModifButtonUI();
		this.setUI(modButtonUI);
		this.setForeground(Color.BLACK);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubTopic() {
		return subTopic;
	}

	public void setSubTopic(String subTopic) {
		this.subTopic = subTopic;
	}

	public String getPubTopic() {
		return pubTopic;
	}

	public void setPubTopic(String pubTopic) {
		this.pubTopic = pubTopic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		thisButton.setText(title);
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public boolean isRetailed() {
		return retailed;
	}

	public void setRetailed(boolean retailed) {
		this.retailed = retailed;
	}

	public Icon getIconOn() {
		return iconOn;
	}

	public void setIconOn(Icon icon) {
		this.iconOn = icon;
		super.setIcon(icon);

	}

	public Icon getIconOff() {
		return iconOff;
	}

	public void setIconOff(Icon icon) {
		this.iconOff = icon;
		super.setIcon(icon);
	}

	public String getMsgOn() {
		return msgOn;
	}

	public void setMsgOn(String msgOn) {
		this.msgOn = msgOn;
	}

	public void setClient(Client client) {
		this.client=client;
		
		while(!client.isConnected()){
			System.out.println("waiting");
			client.subscribe(subTopic);
		}
	}

	public String getMsgOff() {
		return msgOff;
	}

	public void setMsgOff(String msgOff) {
		this.msgOff = msgOff;
	}
	
	public void setStateOff(){
		setIcon(iconOff);
		buttonState=true;
	}
	
	public void setStateOn(){
		setIcon(iconOn);
		buttonState=false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
		if (buttonState) {
			setIcon(iconOn);
			if(!(this.pubTopic.isEmpty()||this.msgOn.isEmpty()))
				client.publicMsg(this.pubTopic, this.msgOn);
			buttonState = !buttonState;
		} else {
			setIcon(iconOff);
			if(!(this.pubTopic.isEmpty()||this.msgOn.isEmpty()))
				client.publicMsg(this.pubTopic, this.msgOff);
			buttonState = !buttonState;
		}
		}catch(Exception exc){
			System.out.println("Error send");
		}

	}

	public String toString() {
		return "name:" + name + " pubTopic:" + pubTopic + " subTopic:" + subTopic + " qos:" + qos + " retailed:"
				+ retailed + " msgOn:" + msgOn + " msgOff:" + msgOff + " title: " + title;
	}

}
