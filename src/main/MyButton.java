package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class MyButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subTopic;
	private String pubTopic;
	private int qos=0;
	private boolean retailed=false;
	private Icon iconOn ;
	private Icon iconOff ;
	private String msgOn;
	private String msgOff;
	private String name;

	private boolean buttonState=false;	//false -off true -on
	
public MyButton(){
		this.addActionListener(this);
		super.setPreferredSize(new Dimension(100,100));
		super.setBackground(Color.LIGHT_GRAY);
		super.setFocusable(false);
		super.setContentAreaFilled(false);
		//super.setBorderPainted(false);
		super.setOpaque(false);
		
		
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


	public String getMsgOff() {
		return msgOff;
	}


	public void setMsgOff(String msgOff) {
		this.msgOff = msgOff;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(buttonState){
			setIcon(iconOn);
			buttonState=!buttonState;
		}
		else{
			setIcon(iconOff);
			buttonState=!buttonState;
		}
	}
	
	public String toString(){
		return "name:"+name+ " pubTopic:"+ pubTopic+
				" subTopic:"+subTopic + " qos:"+qos  +
				" retailed:" + retailed +" msgOn:"+msgOn+
				" msgOff:"+ msgOff; 
	}

	
	
	
	
}
