package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class AddButton implements ActionListener {

	private JPanel root;
	private MyButton myButton;

	JLabel subTopic = new JLabel("Subscribe Topic: ");
	JLabel pubTopic = new JLabel("Publish Topic: ");
	JTextField subTopicText = new JTextField(15);
	JTextField pubTopicText = new JTextField(15);
	JLabel on = new JLabel("On: ");
	JLabel off = new JLabel("Off: ");
	JTextField onText = new JTextField(15);
	JTextField offText = new JTextField(15);
	JTextField titleText = new JTextField(15);
	JLabel title = new JLabel("Title:");

	private LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();

	// File representing the folder that you select using a FileChooser
	File dir;

	// array of supported extensions (use a List if you prefer)
	private static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp" };

	// filter to identify images based on their extensions
	private static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	public AddButton() {

		myButton = new MyButton();

		try {

			final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

			if (jarFile.isFile()) { // Run with JAR file
				final JarFile jar = new JarFile(jarFile);
				final Enumeration<JarEntry> entries = jar.entries(); // gives
																		// ALL
																		// entries
																		// in
																		// jar
				while (entries.hasMoreElements()) {
					final String name = entries.nextElement().getName();
					if (name.endsWith(".png")) { // filter according to the path
						System.out.println(name);

						BufferedImage buff = null;

						try {
							buff = ImageIO.read(ClassLoader.getSystemResource(name));
							if (buff != null)
								images.add(buff);
						} catch (Exception e) {
							System.out.println("load image exception");
							System.out.println(e.getMessage());
						}
					}
				}
				jar.close();
			} else { // Run with IDE

				dir = new File("Icons");
				if (dir.isDirectory()) { // make sure it's a directory
					for (final File f : dir.listFiles(IMAGE_FILTER)) {
						BufferedImage img = null;

						try {
							img = ImageIO.read(f);
							if (img != null)
								images.add(img);

						} catch (final IOException e) {
							System.out.println("Load image error");
						}
					}
				}
			}

		} catch (Exception e) {
		}

		JPanel firstStateOfButtonPanel = new JPanel();
		firstStateOfButtonPanel.setBorder(BorderFactory.createTitledBorder("Icon on"));
		firstStateOfButtonPanel.setLayout(new BoxLayout(firstStateOfButtonPanel, BoxLayout.Y_AXIS));
		firstStateOfButtonPanel.setBounds(0, 0, 150, 300);

		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setViewportBorder(new LineBorder(Color.BLACK));
		firstStateOfButtonPanel.add(jScrollPane);

		JPanel iconsPanel = new JPanel();
		iconsPanel.setLayout(new GridLayout(0, 1));

		for (int i = 0; i < images.size(); i++) {
			JButton firstStateIcon = new JButton();

			firstStateIcon.setPreferredSize(new Dimension(100, 100));
			firstStateIcon.addActionListener(this);
			firstStateIcon.setBackground(Color.darkGray);
			Image scaled = images.get(i).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			firstStateIcon.setIcon(new ImageIcon(scaled));
			firstStateIcon.setActionCommand("setButtonIcon");
			iconsPanel.add(firstStateIcon);
			firstStateIcon.setName("firstStateIcon");
			firstStateIcon.setFocusable(false);

		}
		jScrollPane.setViewportView(iconsPanel);

		JPanel secondStateOfButtonPanel = new JPanel();
		secondStateOfButtonPanel.setBorder(BorderFactory.createTitledBorder("Icon off"));
		secondStateOfButtonPanel.setLayout(new BoxLayout(secondStateOfButtonPanel, BoxLayout.Y_AXIS));
		secondStateOfButtonPanel.setBounds(150, 0, 150, 300);

		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane2.setViewportBorder(new LineBorder(Color.BLACK));
		secondStateOfButtonPanel.add(jScrollPane2);

		JPanel iconsPanel2 = new JPanel();
		iconsPanel2.setLayout(new GridLayout(0, 1));

		for (int i = 0; i < images.size(); i++) {
			JButton secondStateIcon = new JButton();

			secondStateIcon.setPreferredSize(new Dimension(100, 100));
			secondStateIcon.addActionListener(this);
			secondStateIcon.setBackground(Color.darkGray);
			Image scaled = images.get(i).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			secondStateIcon.setIcon(new ImageIcon(scaled));
			secondStateIcon.setActionCommand("setButtonIcon");
			iconsPanel2.add(secondStateIcon);
			secondStateIcon.setName("secondStateIcon");
			secondStateIcon.setFocusable(false);

		}
		jScrollPane2.setViewportView(iconsPanel2);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.add(subTopic);
		optionsPanel.add(subTopicText);
		optionsPanel.add(pubTopic);
		optionsPanel.add(pubTopicText);

		ButtonGroup group = new ButtonGroup();

		JRadioButton radioButton0 = new JRadioButton("QoS(0)");
		JRadioButton radioButton1 = new JRadioButton("QoS(1)");
		JRadioButton radioButton2 = new JRadioButton("QoS(2)");

		radioButton0.setSelected(true);

		radioButton0.setActionCommand("radio0");
		radioButton1.setActionCommand("radio1");
		radioButton2.setActionCommand("radio2");

		radioButton0.addActionListener(this);
		radioButton1.addActionListener(this);
		radioButton2.addActionListener(this);

		group.add(radioButton0);
		group.add(radioButton1);
		group.add(radioButton2);

		optionsPanel.add(radioButton0);
		optionsPanel.add(radioButton1);
		optionsPanel.add(radioButton2);

		JCheckBox retailed = new JCheckBox("Retailed ");
		optionsPanel.add(retailed);
		retailed.addActionListener(this);
		retailed.setActionCommand("retailed");

		optionsPanel.add(on);
		optionsPanel.add(onText);
		optionsPanel.add(off);
		optionsPanel.add(offText);

		optionsPanel.add(title);
		optionsPanel.add(titleText);

		optionsPanel.setBounds(300, 0, 150, 300);

		root = new JPanel();
		root.setPreferredSize(new Dimension(450, 300));
		root.setLayout(null);
		root.add(firstStateOfButtonPanel);
		root.add(secondStateOfButtonPanel);
		root.add(optionsPanel);
		// root.add(panel4);

	}

	public JPanel getPanel() {

		return root;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "radio0")
			myButton.setQos(0);
		else if (e.getActionCommand() == "radio1")
			myButton.setQos(1);
		else if (e.getActionCommand() == "radio2")
			myButton.setQos(2);

		else if (e.getActionCommand() == "retailed") {
			JCheckBox box = (JCheckBox) e.getSource();
			myButton.setRetailed(box.isSelected());
		}

		else if (e.getActionCommand() == "setButtonIcon") {

			JButton button = (JButton) e.getSource();

			try {
				List<Component> buttons = new ArrayList<Component>();
				buttons = getAllComponents(root);

				if (button.getName() == "secondStateIcon") {
					for (int i = 0; i < buttons.size(); i++) {
						try {
							if (buttons.get(i).getName() == "secondStateIcon")
								buttons.get(i).setBackground(Color.DARK_GRAY);
						} catch (Exception exc) {
						}
					}
					button.setBackground(Color.LIGHT_GRAY);
					myButton.setIconOff(button.getIcon());

				} else if (button.getName() == "firstStateIcon") {
					for (int i = 0; i < buttons.size(); i++) {
						try {
							if (buttons.get(i).getName() == "firstStateIcon")
								buttons.get(i).setBackground(Color.DARK_GRAY);
						} catch (Exception exc) {

						}
					}
					button.setBackground(Color.LIGHT_GRAY);
					myButton.setIconOn(button.getIcon());
				}

			} catch (Exception exc) {
			}

		}
	}

	public static List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	public MyButton getButton() {
		myButton.setSubTopic(subTopicText.getText());
		myButton.setPubTopic(pubTopicText.getText());
		myButton.setMsgOn(onText.getText());
		myButton.setMsgOff(offText.getText());
		myButton.setTitle(titleText.getText());

		return myButton;
	}

	public JPanel getPanelForSetup(MyButton b) {
		// TODO Auto-generated method stub
		titleText.setText(b.getTitle());
		offText.setText(b.getMsgOff());
		onText.setText(b.getMsgOn());
		pubTopicText.setText(b.getPubTopic());
		subTopicText.setText(b.getSubTopic());

		return root;
	}

}
