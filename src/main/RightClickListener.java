package main;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class RightClickListener extends JPopupMenu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuItem remove;
	private JMenuItem edit;

	private JPopupMenu menu;
	private JTextComponent jTextComponent;
	private transient MouseAdapter mouseListener;
	private MqttButton button;

	public RightClickListener(ActionListener listener) {
		menu = new JPopupMenu();

		remove = new JMenuItem("Remove");
		remove.setActionCommand("Remove");
		remove.addActionListener(listener);
		edit = new JMenuItem("Edit");
		edit.setActionCommand("Edit");
		edit.addActionListener(listener);

		menu.add(remove);
		menu.add(edit);
		
		mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {
					menu.show(e.getComponent(), 0, 0);

					menu.setLocation(e.getLocationOnScreen().x, e.getLocationOnScreen().y);
					button = (MqttButton) e.getComponent();
				}
			}
		};

	}

	public MouseAdapter getLeasener() {
		return mouseListener;
	}

	public MqttButton getSelectedButton() {
		return button;
	}
}