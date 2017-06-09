package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class DashBoard implements ActionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cp;
	private JFrame frame;
	private List<MqttButton> buttonList = new LinkedList<MqttButton>();
	private DashBoard dashBoard;
	private Map<String, String> serverSettings = new HashMap<String, String>();
	private RightClickListener rightClickListener;
	private  transient Client client;
	private JLabel connectionStatus;
	private static Thread clientThread;
	// private Client mqttClient;

	@SuppressWarnings("deprecation")
	public DashBoard() {
		Thread clientThread = null;
		connectionStatus = new JLabel("Disconected   ");
		dashBoard = this;
		rightClickListener = new RightClickListener(this);
		System.out.println("JFrame starting");
		frame = new JFrame();
		cp = frame.getContentPane();
		cp.setBackground(new Color(40, 40, 40));

		frame.setPreferredSize(new Dimension(440, 400));
		frame.setMinimumSize(new Dimension(100, 170));

		cp.setLayout(new FlowLayout(0));

		JMenuBar jMenuBar = new JMenuBar();

		JMenu barMenuFiles = new JMenu("Files");
		JMenu barMenuSettings = new JMenu("Settings");

		JMenuItem jMenuItemLoadLayout = new JMenuItem("Load Layout", KeyEvent.VK_L);
		JMenuItem jMenuItemSaveLayout = new JMenuItem("SaveLayout", KeyEvent.VK_S);
		JMenuItem jMenuItemExit = new JMenuItem("Exit", KeyEvent.VK_X);
		JMenuItem jMenuItemSettings = new JMenuItem("Settings", KeyEvent.VK_S);
		JMenuItem jMenuItemMqttButtonManager = new JMenuItem("Add Button", KeyEvent.VK_A);
		

		jMenuItemLoadLayout.addActionListener(this);
		jMenuItemSaveLayout.addActionListener(this);
		jMenuItemExit.addActionListener(this);
		jMenuItemSettings.addActionListener(this);
		jMenuItemMqttButtonManager.addActionListener(this);
	
		jMenuItemLoadLayout.setActionCommand("Load");
		jMenuItemSaveLayout.setActionCommand("Save");
		jMenuItemExit.setActionCommand("Exit");
	

		barMenuFiles.add(jMenuItemLoadLayout);
		barMenuFiles.add(jMenuItemSaveLayout);
		barMenuFiles.addSeparator();
		barMenuFiles.add(jMenuItemExit);

		barMenuSettings.add(jMenuItemMqttButtonManager);
		barMenuSettings.add(jMenuItemSettings);


		jMenuBar.add(barMenuFiles);
		jMenuBar.add(barMenuSettings);
		jMenuBar.add(Box.createGlue());
		jMenuBar.add(connectionStatus);
		
		//saving on close
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try{
					LayoutManager.saveLastState(dashBoard);
				}catch(Exception exc){System.out.println("can't save state");}
			}
		});
		// trying to load last session board
		// Reading and setting las state of program
		
		try {
			serverSettings = LayoutManager.getLastState().serverSettings;
			if(!this.serverSettings.get("broker").isEmpty()){
				client = new Client(this.serverSettings, this);
				clientThread=new Thread(client);
				clientThread.start();
			}setButtons(LayoutManager.getLastState().getButtonList());
			
		} catch (Exception e) {
			System.out.println("can't load state");
		}


			
		
		frame.setJMenuBar(jMenuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Add Button") {
			MqttButtonManager MqttButtonManager = new MqttButtonManager();
			int result = JOptionPane.showConfirmDialog(frame, MqttButtonManager.getPanel(), "Use a Panel",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == 0) {
				cp.add(MqttButtonManager.getButton());
				buttonList.add(MqttButtonManager.getButton());
				MqttButtonManager.getButton().addMouseListener(rightClickListener.getLeasener());
				MqttButtonManager.getButton().setClient(client);
				
			}
			cp.revalidate();
		
		
		}else if (e.getActionCommand() == "Settings") {
			ConnectionSettings cs = new ConnectionSettings();
			int result = JOptionPane.showConfirmDialog(frame, cs.getJPanel(serverSettings), "Use a Panel",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == 0) {
				serverSettings = cs.getSettings();
				try {
					client.stop();
					this.clientThread.interrupt();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				client = new Client(this.serverSettings, this);
				clientThread=new Thread(client);
				clientThread.start();
				setButtons(LayoutManager.getLastState().getButtonList());
				
			}

			
		}else if (e.getActionCommand() == "Save") {
			LayoutManager.saveLayout(this);
		
		
		}else if (e.getActionCommand() == "Load") {
			
			try {
				if(client!=null)
					client.stop();
				if(clientThread!=null)
					this.clientThread.interrupt();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			DashBoard buff=LayoutManager.loadLayout();
			serverSettings = buff.serverSettings;
			client = new Client(this.serverSettings, this);
			clientThread=new Thread(client);
			clientThread.start();
			setButtons(buff.getButtonList());
			
		}else if (e.getActionCommand() == "Remove") {
			buttonList.remove(rightClickListener.getSelectedButton());
			System.out.println(rightClickListener.getSelectedButton().toString());
			setButtons(buttonList);

	
		}else if (e.getActionCommand() == "Exit") {
		

			
		}else if (e.getActionCommand() == "Edit") {
			MqttButtonManager MqttButtonManager = new MqttButtonManager();
			int result = JOptionPane.showConfirmDialog(frame,
							MqttButtonManager.getPanelForSetup(rightClickListener.getSelectedButton()), "Use a Panel",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == 0) {
				rightClickListener.getSelectedButton().setTitle(MqttButtonManager.getButton().getTitle());
				rightClickListener.getSelectedButton().setIconOn(MqttButtonManager.getButton().getIconOn());
				rightClickListener.getSelectedButton().setIconOff(MqttButtonManager.getButton().getIconOff());
				rightClickListener.getSelectedButton().setMsgOff(MqttButtonManager.getButton().getMsgOff());
				rightClickListener.getSelectedButton().setMsgOn(MqttButtonManager.getButton().getMsgOn());
				rightClickListener.getSelectedButton().setPubTopic(MqttButtonManager.getButton().getPubTopic());
				rightClickListener.getSelectedButton().setSubTopic(MqttButtonManager.getButton().getSubTopic());
				rightClickListener.getSelectedButton().setQos(MqttButtonManager.getButton().getQos());
				rightClickListener.getSelectedButton().setRetailed(MqttButtonManager.getButton().isRetailed());

			}

		}

	}

	private void setButtons(List<MqttButton> buttons) {

		if (buttons != null) {
			MqttButton button;

			if (buttons != this.getButtonList()) {
				buttonList = buttons;
			}

			cp.removeAll();
			Iterator<MqttButton> i2 = buttons.iterator();

			while (i2.hasNext()) {

				button = (MqttButton) i2.next();
				button.setUI(new ModifButtonUI());
				cp.add(button);
				button.addMouseListener(rightClickListener.getLeasener());
				button.setClient(client);

			}
			cp.repaint();
			cp.revalidate();

		}
	}

	public void setConnectionStatus(String s) {
		connectionStatus.setText(s + "   ");

	}

	public List<MqttButton> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<MqttButton> buttonList) {
		this.buttonList = buttonList;
	}


}
