//package main;
//
//import java.awt.Container;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//
//public class ButtonListener implements ActionListener {
//	private DashBoard dashboard;
//
//	private Container cp;
//	private JFrame frame;
//	private List<MqttButton> buttonList = new LinkedList<MqttButton>();
//	private DashBoard dashBoard;
//	private Map<String, String> serverSettings = new HashMap<String, String>();
//	private RightClickListener rightClickListener;
//	private transient Client client;
//	private JLabel connectionStatus;
//	
//	public ButtonListener(DashBoard dash){
//		this.dashboard=dash;
//		this.frame=dash.getFrame();
//		this.cp=dash.getCp();
//		//this.buttonList=dash.getButtonList();
//		this.serverSettings=dash.getServerSettings();
//		this.rightClickListener=dash.getRightClickListener();
//		this.client=dash.getClient();
//		this.connectionStatus=dash.getConnectionStatus();
//	}
//	
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//
//		if (e.getActionCommand() == "Add Button") {
//			MqttButtonManager MqttButtonManager = new MqttButtonManager();
//			int result = JOptionPane.showConfirmDialog(frame, MqttButtonManager.getPanel(), "Use a Panel",
//								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//			if (result == 0) {
//				cp.add(MqttButtonManager.getButton());
//				buttonList.add(MqttButtonManager.getButton());
//				MqttButtonManager.getButton().addMouseListener(rightClickListener.getLeasener());
//			}
//			cp.revalidate();
//		
//		
//		}else if (e.getActionCommand() == "Settings") {
//			ConnectionSettings cs = new ConnectionSettings();
//			int result = JOptionPane.showConfirmDialog(frame, cs.getJPanel(serverSettings), "Use a Panel",
//							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//			if (result == 0) {
//				client = new Client(cs.getSettings(), dashBoard);
//				(new Thread(client)).start();
//				serverSettings = cs.getSettings();
//			}
//
//			
//		}else if (e.getActionCommand() == "Save") {
//			LayoutManager.saveLayout(dashBoard);
//		
//		
//		}else if (e.getActionCommand() == "Reconnect") {
//			client=new Client(serverSettings,dashBoard);
//			(new Thread(client)).start();
//
//			
//		}else if (e.getActionCommand() == "Load") {
//			dashBoard.setButtons(LayoutManager.loadLayout().getButtonList());
//
//			
//		}else if (e.getActionCommand() == "Remove") {
//			buttonList.remove(rightClickListener.getSelectedButton());
//			System.out.println(rightClickListener.getSelectedButton().toString());
//			dashBoard.setButtons(buttonList);
//
//	
//		}else if (e.getActionCommand() == "Exit") {
//			client.publicMsg("hello from button'exit'");
//
//			
//		}else if (e.getActionCommand() == "Edit") {
//			MqttButtonManager MqttButtonManager = new MqttButtonManager();
//			int result = JOptionPane.showConfirmDialog(frame,
//							MqttButtonManager.getPanelForSetup(rightClickListener.getSelectedButton()), "Use a Panel",
//								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//			if (result == 0) {
//				rightClickListener.getSelectedButton().setTitle(MqttButtonManager.getButton().getTitle());
//				rightClickListener.getSelectedButton().setIconOn(MqttButtonManager.getButton().getIconOn());
//				rightClickListener.getSelectedButton().setIconOff(MqttButtonManager.getButton().getIconOff());
//				rightClickListener.getSelectedButton().setMsgOff(MqttButtonManager.getButton().getMsgOff());
//				rightClickListener.getSelectedButton().setMsgOn(MqttButtonManager.getButton().getMsgOn());
//				rightClickListener.getSelectedButton().setPubTopic(MqttButtonManager.getButton().getPubTopic());
//				rightClickListener.getSelectedButton().setSubTopic(MqttButtonManager.getButton().getSubTopic());
//				rightClickListener.getSelectedButton().setQos(MqttButtonManager.getButton().getQos());
//				rightClickListener.getSelectedButton().setRetailed(MqttButtonManager.getButton().isRetailed());
//			}
//		}
//	}
//
//}
