import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import org.json.*;

public class UserDefinePanel extends JFrame {
	JPanel appDetialPanel = null;
	JScrollPane appScrollPanel = null;
	JSONArray userAppJSONArray = null;
	ArrayList<JPanel> appDetialPanelArray = null;
	ArrayList<ArrayList<JLabel>> appDetialLabelArray = null;

	int windowWidth = 0;
	int windowHeight = 0;

	int panelFlag = 0;
	int i = 0;

	JPopupMenu rightMenu = null;

	Font plainFont = null;
	Font boldFont = null;

	UserDefinePanel userDefineFrame = null;

	private JFrame editFrame = null;

	public JFrame getEditFrame() {
		return this.editFrame;
	}

	public UserDefinePanel(MainPanel mainPanel) {
		userDefineFrame = this;

		JLabel infoLabel = new JLabel("Right Click the APP detials to edit the APP");
		ArrayList<JLabel> itemLabelArray = new ArrayList<JLabel>();

		appDetialPanel = new JPanel();
		appScrollPanel = new JScrollPane(appDetialPanel);

		userAppJSONArray = new Config().getUserAppJSONInfo();
		if (userAppJSONArray == null)
			userAppJSONArray = new JSONArray();
		
		appDetialPanelArray = new ArrayList<JPanel>();
		appDetialLabelArray = new ArrayList<ArrayList<JLabel>>();

		JButton addButton = new JButton();
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

		JMenuItem edit = new JMenuItem("Edit");
		JMenuItem remove = new JMenuItem("Remove");
		JMenuItem moveUp = new JMenuItem("Move Up");
		JMenuItem moveDown = new JMenuItem("Move Down");

		rightMenu = new JPopupMenu();

		plainFont = new Font("Liberation Serif", Font.PLAIN, 14);
		boldFont = new Font("Liberation Serif", Font.BOLD, 14);

		// Manually set the size of the window
		windowWidth = 610;
		windowHeight = 200;

		int screenWidth = new ScreenSize().getScreenWidth();
		int screenHeight = new ScreenSize().getScreenHeight();

		infoLabel.setFont(boldFont);
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		itemLabelArray.add(new JLabel("ID"));
		itemLabelArray.add(new JLabel("Name"));
		itemLabelArray.add(new JLabel("Command"));
		itemLabelArray.add(new JLabel("Icon Path"));
		for (i = 0; i < itemLabelArray.size(); i++) {
			itemLabelArray.get(i).setFont(plainFont);
			itemLabelArray.get(i).setHorizontalAlignment(SwingConstants.CENTER);
			itemLabelArray.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		// rightMenu
		edit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (editFrame != null) 
					editFrame.dispose();
				if (e.getButton() == e.BUTTON1) {
					editFrame = new EditPanel(userDefineFrame, userAppJSONArray,
							userAppJSONArray.getJSONObject(panelFlag).getString("name"));
				} else
					;
			}
		});

		remove.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (editFrame != null) 
					editFrame.dispose();
				if (e.getButton() == e.BUTTON1) {
					userAppJSONArray.remove(panelFlag);
					userDefineFrame.appDetialPanelRepaint();
				} else
					;
			}
		});

		moveUp.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (editFrame != null) 
					editFrame.dispose();
				if (e.getButton() == e.BUTTON1) {
					if (panelFlag == 0 || (panelFlag == 0 && panelFlag == (userAppJSONArray.length() - 1))) {
						;
					} else {
						int index = panelFlag;
						int previousIndex = --panelFlag;

						JSONArray temp = new JSONArray();
						for (int i = 0; i < previousIndex; i++) {
							temp.put(userAppJSONArray.getJSONObject(i));
						}
						temp.put(userAppJSONArray.getJSONObject(index));

						temp.put(userAppJSONArray.getJSONObject(previousIndex));

						for (int i = ++index; i < userAppJSONArray.length(); i++) {
							temp.put(userAppJSONArray.getJSONObject(i));
						}
						userAppJSONArray = temp;
						userDefineFrame.appDetialPanelRepaint();
					}
				} else
					;
			}
		});
		moveDown.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (editFrame != null) 
					editFrame.dispose();
				if (e.getButton() == e.BUTTON1) {
					if (panelFlag == (userAppJSONArray.length() - 1)
							|| (panelFlag == 0 && panelFlag == (userAppJSONArray.length() - 1))) {
						;
					} else {
						int index = panelFlag;
						int nextIndex = ++panelFlag;

						JSONArray temp = new JSONArray();
						for (int i = 0; i < index; i++) {
							temp.put(userAppJSONArray.getJSONObject(i));
						}
						temp.put(userAppJSONArray.getJSONObject(nextIndex));

						temp.put(userAppJSONArray.getJSONObject(index));

						for (int i = ++nextIndex; i < userAppJSONArray.length(); i++) {
							temp.put(userAppJSONArray.getJSONObject(i));
						}
						userAppJSONArray = temp;
						userDefineFrame.appDetialPanelRepaint();
					}
				} else
					;
			}
		});

		rightMenu.add(edit);
		rightMenu.add(remove);
		rightMenu.add(moveUp);
		rightMenu.add(moveDown);

		ImageIcon addIcon = new ImageIcon(Constant.addImagePath);
		addIcon.setImage(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		addButton.setIcon(addIcon);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (editFrame != null) 
					editFrame.dispose();
				editFrame = new EditPanel(userDefineFrame, userAppJSONArray, null);
			}
		});

		saveButton.setFont(plainFont);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (editFrame != null) 
					editFrame.dispose();
				try {
					userAppJSONArray.getJSONObject(0);
					new FileUtil().writeFile(userAppJSONArray.toString(), Config.userFilePath, true);
				} catch (JSONException e) {
					new FileUtil().writeFile("", Config.userFilePath, true);
				}
				userDefineFrame.dispose();
				mainPanel.userButtonRepaint();
			}
		});

		cancelButton.setFont(plainFont);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (editFrame != null) 
					editFrame.dispose();
				userDefineFrame.dispose();
			}
		});

		infoLabel.setBounds(0, 0, windowWidth, 20);
		itemLabelArray.get(0).setBounds(0, 20, 30, 20);
		itemLabelArray.get(1).setBounds(30, 20, 100, 20);
		itemLabelArray.get(2).setBounds(130, 20, 300, 20);
		itemLabelArray.get(3).setBounds(430, 20, 180, 20);

		this.appDetialPanelRepaint();

		addButton.setBounds(10, 145, 20, 20);
		saveButton.setBounds(420, 145, 80, 20);
		cancelButton.setBounds(510, 145, 80, 20);

		this.getContentPane().add(infoLabel);
		this.getContentPane().add(itemLabelArray.get(0));
		this.getContentPane().add(itemLabelArray.get(1));
		this.getContentPane().add(itemLabelArray.get(2));
		this.getContentPane().add(itemLabelArray.get(3));
		this.getContentPane().add(appScrollPanel);
		this.getContentPane().add(addButton);
		this.getContentPane().add(saveButton);
		this.getContentPane().add(cancelButton);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (editFrame != null) 
					editFrame.dispose();
			}
		});
		
		this.setLayout(null);
		this.setVisible(true);
		this.setTitle("App settings");

		ImageIcon settingImage = new ImageIcon(Constant.settingImagePath);
		this.setIconImage(settingImage.getImage());
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				userDefineFrame.dispose();
				if (editFrame != null)
					editFrame.dispose();
			}
		});

		// Set the size of the window
		this.setSize(windowWidth, windowHeight);

		// Set the window at the center of the screen
		// this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
	}

	public void appDetialPanelRepaint() {
		appDetialLabelArray.clear();
		appDetialPanelArray.clear();
		appDetialPanel.removeAll();

		for (i = 0; i < userAppJSONArray.length(); i++) {
			ArrayList<JLabel> appItemLabelArray = new ArrayList<JLabel>();
			JPanel appPanel = new JPanel();
			appItemLabelArray.add(new JLabel("" + (i + 1)));

			appItemLabelArray.add(new JLabel(userAppJSONArray.getJSONObject(i).getString("name")));
			appItemLabelArray.add(new JLabel(userAppJSONArray.getJSONObject(i).getString("command")));
			appItemLabelArray.add(new JLabel("" + userAppJSONArray.getJSONObject(i).get("iconPath")));

			for (int j = 0; j < appItemLabelArray.size(); j++) {
				appItemLabelArray.get(j).setFont(plainFont);
				switch (j) {
				case 0: {
					appItemLabelArray.get(j).setHorizontalAlignment(SwingConstants.CENTER);
					break;
				}
				case 1: {
					appItemLabelArray.get(j).setHorizontalAlignment(SwingConstants.CENTER);
					break;
				}
				case 2: {
					appItemLabelArray.get(j).setHorizontalAlignment(SwingConstants.LEFT);
					break;
				}
				case 3: {
					appItemLabelArray.get(j).setHorizontalAlignment(SwingConstants.LEFT);
					break;
				}
				default: {
					appItemLabelArray.get(j).setHorizontalAlignment(SwingConstants.LEFT);
					break;
				}
				}

				appItemLabelArray.get(j).setBorder(BorderFactory.createLineBorder(Color.BLACK));
				appPanel.add(appItemLabelArray.get(j));
			}

			appPanel.addMouseListener(new PopupListener(rightMenu, appPanel));

			appDetialLabelArray.add(appItemLabelArray);
			appDetialPanelArray.add(appPanel);
		}

		for (int i = 0; i < appDetialPanelArray.size(); i++) {
			appDetialPanel.add(appDetialPanelArray.get(i));
			appDetialPanelArray.get(i).setBounds(0, i * 20, 580, 20);

			appDetialLabelArray.get(i).get(0).setBounds(0, 0, 30, 20);
			appDetialLabelArray.get(i).get(1).setBounds(30, 0, 100, 20);
			appDetialLabelArray.get(i).get(2).setBounds(130, 0, 300, 20);
			appDetialLabelArray.get(i).get(3).setBounds(430, 0, 150, 20);

			appDetialPanelArray.get(i).setLayout(null);
		}
		appDetialPanel.setLayout(null);
		appDetialPanel.setPreferredSize(new Dimension(580, userAppJSONArray.length() * 20));
		appScrollPanel.setBounds(0, 40, windowWidth, 100);
		appScrollPanel.updateUI();
	}

	public class PopupListener extends MouseAdapter {
		JPopupMenu menu;
		JPanel appPanel;

		public PopupListener(JPopupMenu menu, JPanel appPanel) {
			this.menu = menu;
			this.appPanel = appPanel;
		}

		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		private void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				panelFlag = appDetialPanelArray.indexOf(appPanel);
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
