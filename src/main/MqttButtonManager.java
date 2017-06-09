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
import javax.swing.plaf.metal.MetalScrollButton;

public class MqttButtonManager implements ActionListener {

	private JPanel root;
	private MqttButton MqttButton;

	private JLabel subTopic = new JLabel("Subscribe Topic: ");
	private JLabel pubTopic = new JLabel("Publish Topic: ");
	private JTextField subTopicText = new JTextField(15);
	private JTextField pubTopicText = new JTextField(15);
	private JLabel on = new JLabel("On: ");
	private JLabel off = new JLabel("Off: ");
	private JTextField onText = new JTextField(15);
	private JTextField offText = new JTextField(15);
	private JTextField titleText = new JTextField(15);
	private JLabel title = new JLabel("Title:");
	private JCheckBox retailed;
	private LinkedList<BufferedImage> imagesOn = new LinkedList<BufferedImage>();
	private LinkedList<BufferedImage> imagesOff = new LinkedList<BufferedImage>();
	private LinkedList<String> imagesOnName = new LinkedList<String>();
	private LinkedList<String> imagesOffName = new LinkedList<String>();

	private JButton IconOff;
	private JButton IconOn;

	private JRadioButton radioButton0 = new JRadioButton("QoS(0)");
	private JRadioButton radioButton1 = new JRadioButton("QoS(1)");
	private JRadioButton radioButton2 = new JRadioButton("QoS(2)");
	// File representing the folder that you select using a FileChooser
	private File dir;

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

	
	public MqttButtonManager() {
		MqttButton = new MqttButton();
		loadAllIcons();
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

		for (int i = 0; i < imagesOn.size(); i++) {
			IconOn = new JButton();

			IconOn.setPreferredSize(new Dimension(100, 100));
			IconOn.addActionListener(this);
			IconOn.setBackground(Color.darkGray);
			Image scaled = imagesOn.get(i).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			ImageIcon buff = new ImageIcon(scaled);
			buff.setDescription(imagesOnName.get(i));
			IconOn.setIcon(buff);
			IconOn.setActionCommand("setButtonIcon");
			iconsPanel.add(IconOn);
			IconOn.setName("IconOn");
			IconOn.setFocusable(false);

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

		for (int i = 0; i < imagesOff.size(); i++) {
			IconOff = new JButton();

			IconOff.setPreferredSize(new Dimension(100, 100));
			IconOff.addActionListener(this);
			IconOff.setBackground(Color.darkGray);
			Image scaled = imagesOff.get(i).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			ImageIcon buff = new ImageIcon(scaled);
			buff.setDescription(imagesOffName.get(i));
			IconOff.setIcon(buff);
			IconOff.setActionCommand("setButtonIcon");
			iconsPanel2.add(IconOff);
			IconOff.setName("IconOff");
			IconOff.setFocusable(false);

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

		radioButton0 = new JRadioButton("QoS(0)");
		radioButton1 = new JRadioButton("QoS(1)");
		radioButton2 = new JRadioButton("QoS(2)");

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

		retailed = new JCheckBox("Retailed ");
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

	
	
	private void loadAllIcons() {
		try {
			final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

			if (jarFile.isFile()) { // Run with JAR file
				final JarFile jar = new JarFile(jarFile);
				final Enumeration<JarEntry> entries = jar.entries(); 
				while (entries.hasMoreElements()) {
					final String name = entries.nextElement().getName();

					if (name.endsWith(".png")) { // filter according to the path
						BufferedImage buff = null;
						try {
							buff = ImageIO.read(ClassLoader.getSystemResource(name));
							// System.out.println(name.charAt(name.length()-5));

							if (buff != null) {
								if (name.charAt(name.length() - 5) == '1') {
									imagesOn.add(buff);
									imagesOnName.add(name);
								} else if (name.charAt(name.length() - 5) == '0') {
									imagesOff.add(buff);
									imagesOffName.add(name);
								}

							}
						} catch (Exception e) {
							System.out.println("load image exception");
							System.out.println(e.getMessage());
						}
					}
				}
				jar.close();
			}else{
				dir = new File("Icons");
				if (dir.isDirectory()) { // make sure it's a directory
					for (final File f : dir.listFiles(IMAGE_FILTER)) {
						BufferedImage img = null;

						try {
							img = ImageIO.read(f);

							if (img != null) {
								if (f.getName().charAt(f.getName().length() - 5) == '1') {
									imagesOn.add(img);
									imagesOnName.add(f.getName());
								} else if (f.getName().charAt(f.getName().length() - 5) == '0') {
									imagesOff.add(img);
									imagesOffName.add(f.getName());
								}
							}

						} catch (final IOException e) {
							System.out.println(e.getMessage());
							System.out.println("Load image error");
						}
					}
				}
			}

		} catch (Exception e) {System.out.println(e.getMessage());}

	}

	public JPanel getPanel() {
		return root;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "radio0")
			MqttButton.setQos(0);
		else if (e.getActionCommand() == "radio1")
			MqttButton.setQos(1);
		else if (e.getActionCommand() == "radio2")
			MqttButton.setQos(2);

		else if (e.getActionCommand() == "retailed") {
			JCheckBox box = (JCheckBox) e.getSource();
			MqttButton.setRetailed(box.isSelected());
		}

		else if (e.getActionCommand() == "setButtonIcon") {

			JButton button = (JButton) e.getSource();

			try {
				List<Component> buttons = new ArrayList<Component>();
				buttons = getAllComponents(root);

				if (button.getName() == "IconOff") {

					for (int i = 0; i < buttons.size(); i++) {
						try {
							if (buttons.get(i).getName() == "IconOff")
								buttons.get(i).setBackground(Color.DARK_GRAY);
						} catch (Exception exc) {
						}

					}
					button.setBackground(Color.LIGHT_GRAY);
					MqttButton.setIconOff(button.getIcon());

				} else if (button.getName() == "IconOn") {
					for (int i = 0; i < buttons.size(); i++) {
						try {
							if (buttons.get(i).getName() == "IconOn")
								buttons.get(i).setBackground(Color.DARK_GRAY);
						} catch (Exception exc) {
							System.out.println(exc.getMessage());
						}
					}
					button.setBackground(Color.LIGHT_GRAY);
					MqttButton.setIconOn(button.getIcon());
				}

			} catch (Exception exc) {
				System.out.println(exc.getMessage());
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

	public MqttButton getButton() {
		MqttButton.setSubTopic(subTopicText.getText());
		MqttButton.setPubTopic(pubTopicText.getText());
		MqttButton.setMsgOn(onText.getText());
		MqttButton.setMsgOff(offText.getText());
		MqttButton.setTitle(titleText.getText());

		return MqttButton;
	}

	public JPanel getPanelForSetup(MqttButton b) {

		titleText.setText(b.getTitle());
		offText.setText(b.getMsgOff());
		onText.setText(b.getMsgOn());
		pubTopicText.setText(b.getPubTopic());
		subTopicText.setText(b.getSubTopic());
		retailed.setSelected(b.isRetailed());
		int Qos = b.getQos();
		if (Qos == 0)
			radioButton0.setSelected(true);
		else if (Qos == 1)
			radioButton1.setSelected(true);
		else
			radioButton2.setSelected(true);

		try {
			List<Component> AllComponents = new ArrayList<Component>();
			AllComponents = getAllComponents(root);

			ImageIcon buffOnFromOutside = (ImageIcon) b.getIconOn();
			ImageIcon buffOnFromInside;
			ImageIcon buffOffFromOutside = (ImageIcon) b.getIconOff();
			ImageIcon buffOffFromInside;
			JButton buff;

			for (int i = 0; i < AllComponents.size(); i++) {

				if (!(AllComponents.get(i) instanceof MetalScrollButton))
					if (AllComponents.get(i) instanceof JButton) {
						buff = (JButton) AllComponents.get(i);
						buffOnFromInside = (ImageIcon) buff.getIcon();
						buffOffFromInside = (ImageIcon) buff.getIcon();

						if (buffOnFromInside.getDescription().equals((String) buffOnFromOutside.getDescription())) {
							AllComponents.get(i).setBackground(Color.LIGHT_GRAY);
							MqttButton.setIconOn(buffOnFromOutside);

						}
						if (buffOffFromInside.getDescription().equals((String) buffOffFromOutside.getDescription())) {
							AllComponents.get(i).setBackground(Color.LIGHT_GRAY);
							MqttButton.setIconOff(buffOffFromOutside);
						}

					}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		;

		return root;
	}

}
