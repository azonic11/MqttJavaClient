package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class DashBoard implements ActionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container cp;
	JFrame frame;
	List<MyButton> buttonSet = new LinkedList<MyButton>();
	Properties properties;
	
	RightClickListener rightClickListener;
	public DashBoard() {
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
		JMenuItem jMenuItemAddButton = new JMenuItem("Add Button", KeyEvent.VK_A);
		JMenuItem jMenuItemEditMode = new JMenuItem("Edit mode", KeyEvent.VK_E);

		jMenuItemLoadLayout.addActionListener(this);
		jMenuItemSaveLayout.addActionListener(this);
		jMenuItemExit.addActionListener(this);
		jMenuItemSettings.addActionListener(this);
		jMenuItemAddButton.addActionListener(this);
		jMenuItemEditMode.addActionListener(this);

		jMenuItemLoadLayout.setActionCommand("Load");
		jMenuItemSaveLayout.setActionCommand("Save");

		barMenuFiles.add(jMenuItemLoadLayout);
		barMenuFiles.add(jMenuItemSaveLayout);
		barMenuFiles.addSeparator();
		barMenuFiles.add(jMenuItemExit);

		barMenuSettings.add(jMenuItemEditMode);
		barMenuSettings.add(jMenuItemAddButton);
		barMenuSettings.add(jMenuItemSettings);

		jMenuBar.add(barMenuFiles);
		jMenuBar.add(barMenuSettings);

		
		
		//trying to load last session board
		try {
			FileInputStream fis = new FileInputStream("C:/Users/" + System.getProperty("user.name") + "/temp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<MyButton> buttons = (List<MyButton>) ois.readObject();
			loadButtons(buttons);
			printButtons(buttons);

			ois.close();
			fis.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("can't load dash board");
		}

		
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				try {
					FileOutputStream fos = new FileOutputStream(
							"C:/Users/" + System.getProperty("user.name") + "/temp");
					ObjectOutputStream oop = new ObjectOutputStream(fos);
					oop.writeObject(buttonSet);
					oop.flush();
					oop.close();
					fos.close();

				} catch (IOException exc) {
					exc.printStackTrace();
					System.out.println(exc.getMessage());
					System.out.println("can't write saves");
				}

			}
		});

		frame.setJMenuBar(jMenuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Add Button") {
			AddButton addButton = new AddButton();

			int result = JOptionPane.showConfirmDialog(frame, // use your JFrame
																// here
					addButton.getPanel(), "Use a Panel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			System.out.println(result);

			if (result == 0) {
				cp.add(addButton.getButton());
				buttonSet.add(addButton.getButton());
				addButton.getButton().addMouseListener(rightClickListener.getLeasener());
				// new Settings().autoSaves(this);
				// System.out.println(addButton.getButton().toString());
			}

			cp.revalidate();

		}

		else if (e.getActionCommand() == "Save") {
			new Settings().save(this);
		}

		else if (e.getActionCommand() == "Load") {
			loadButtons(new Settings().load());
			printButtons(new Settings().load());
		}
		else if(e.getActionCommand()=="remove"){
			buttonSet.remove(rightClickListener.getSelectedButton());
			//buttonSet.remove(buttonSet.indexOf(rightClickListener.getSelectedButton()));
			System.out.println(rightClickListener.getSelectedButton().toString());
			printButtons(this.buttonSet);
			//printButtons(buttonSet);
		}
		else if(e.getActionCommand()=="edit"){
			AddButton addButton = new AddButton();

			int result = JOptionPane.showConfirmDialog(frame, // use your JFrame
																// here
					addButton.getPanelForSetup(rightClickListener.getSelectedButton()), "Use a Panel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			System.out.println(result);
		}

	}

	private void printButtons(List<MyButton> buttons) {
		if (buttons != null) {
			cp.removeAll();
			// this.buttonList=db.buttonList;
			Iterator<MyButton> i = buttons.iterator();
			MyButton button;
			while(i.hasNext()) {
				
				button = (MyButton)i.next();
				button.setUI(new ModifButtonUI());
				cp.add(button);
				
				//buttonSet.add(button);
				button.addMouseListener(rightClickListener.getLeasener());
				
				
			}
			cp.repaint();
			cp.revalidate();

		}

	}

	private void loadButtons(List<MyButton> buttons) {
		if (buttons != null) {
			//cp.removeAll();
			// this.buttonList=db.buttonList;
			Iterator<MyButton> i = buttons.iterator();
			MyButton button;
			while(i.hasNext()) {
				
				button = (MyButton)i.next();
				button.setUI(new ModifButtonUI());
				//cp.add(button);
				
				buttonSet.add(button);
				button.addMouseListener(rightClickListener.getLeasener());
				
				
			}
			//cp.repaint();
			//cp.revalidate();

		}

	}
}
