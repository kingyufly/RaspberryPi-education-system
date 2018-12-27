import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import org.json.*;

public class MainPanel extends JFrame {
	JButton userDefineButton = null;
	JButton equipmentControlButton = null;
	JButton liveButton = null;
	JButton sysButton = null;
	JLabel userDefineLabel = null;
	JLabel equipmentControlLabel = null;
	JLabel liveLabel = null;
	JLabel sysLabel = null;

	JButton exitButton = null;

	JFrame userDefineFrame = null;
	JFrame equipmentControlFrame = null;
	JFrame liveFrame = null;
	JFrame sysControlFrame = null;

	JSONArray basicAppJSONArray = null;
	JSONArray userAppJSONArray = null;
	ArrayList<JButton> userAppButton = null;
	ArrayList<JLabel> userAppLabel = null;

	int screenWidth = -1;
	int screenHeight = -1;

	int buttonSize = -1;
	int buttonHorMaxNum = -1;
	int buttonHorMaxInterval = -1;

	int labelWidth = -1;
	int labelHeight = -1;

	int buttonVerMaxInterval = -1;

	int fontSize = -1;

	Font labelFont = null;

	JFrame mainPanel = null;

	public MainPanel() {
		mainPanel = this;

		basicAppJSONArray = new Config().getBasicAppJSONInfo();

		userAppButton = new ArrayList<JButton>();
		userAppLabel = new ArrayList<JLabel>();

		if (basicAppJSONArray == null) {
			this.setIconImage(new ImageIcon(Constant.warningImagePath).getImage());
			JOptionPane.showConfirmDialog(this, "The basic config is missing! \nPlease contact to the administrator!",
					"Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		screenWidth = new ScreenSize().getScreenWidth();
		screenHeight = new ScreenSize().getScreenHeight();

		buttonSize = (screenHeight - 9 * 5) / 7 / 7 * 6;
		buttonHorMaxNum = (screenWidth - 5) / (buttonSize + 5);
		buttonHorMaxInterval = (screenWidth - buttonSize * buttonHorMaxNum) / (buttonHorMaxNum + 1);

		labelWidth = buttonSize;
		labelHeight = (screenHeight - 9 * 5) / 7 / 7;

		buttonVerMaxInterval = (screenHeight - (buttonSize + labelHeight) * 7) / (7 + 1);

		fontSize = (int) Math.ceil((float) screenWidth / 1920 * 16);

		labelFont = new Font("Liberation Serif", Font.BOLD, fontSize);

		// Set basic apps' UI

		for (int i = 0; i < basicAppJSONArray.length(); i++) {
			JSONObject appJSON = basicAppJSONArray.getJSONObject(i);

			// Set buttons
			JButton appButton = new JButton();
			ImageIcon appIcon = new ImageIcon(appJSON.getString("iconPath"));
			appIcon.setImage(appIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));
			appButton.setIcon(appIcon);

			if (appJSON.getString("name").equals("Java")) { // run java program
				appButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (userDefineFrame != null) {
							userDefineFrame.dispose();
							if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
								((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
						}
						if (sysControlFrame != null) {
							sysControlFrame.dispose();
							((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
						}
						if (equipmentControlFrame != null) {
							equipmentControlFrame.dispose();
						}
						if (liveFrame != null) {
							liveFrame.dispose();
						}

						JFileChooser jarFileChooser = new JFileChooser();
						JarFileFilter jarFilter = new JarFileFilter();
						jarFileChooser.addChoosableFileFilter(jarFilter);
						jarFileChooser.setFileFilter(jarFilter);

						int i = jarFileChooser.showOpenDialog(getParent()); // open the dialog to choose the file
						String jarFilePath = "";
						if (i == jarFileChooser.APPROVE_OPTION) // judge the status of the user's choice
							jarFilePath = jarFileChooser.getSelectedFile().getPath();
						// if the user does not choose any file, do nothing

						if (!jarFilePath.equals("") && (jarFilePath != null))
							new LinuxProcess()
									.startProcess("/home/kyf/Program/jdk-9.0.1/jre-9.0.1/bin/java -jar " + jarFilePath);
					}
				});
			} else if (appJSON.getString("name").equals("Python")) { // run python program
				appButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (userDefineFrame != null) {
							userDefineFrame.dispose();
							if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
								((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
						}
						if (sysControlFrame != null) {
							sysControlFrame.dispose();
							((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
						}
						if (equipmentControlFrame != null) {
							equipmentControlFrame.dispose();
						}
						if (liveFrame != null) {
							liveFrame.dispose();
						}

						JFileChooser pyFileChooser = new JFileChooser();
						JarFileFilter pyFilter = new JarFileFilter();
						pyFileChooser.addChoosableFileFilter(pyFilter);
						pyFileChooser.setFileFilter(pyFilter);

						int i = pyFileChooser.showOpenDialog(getParent()); // open the dialog to choose the file
						String pyFilePath = "";
						if (i == pyFileChooser.APPROVE_OPTION) // judge the status of the user's choice
							pyFilePath = pyFileChooser.getSelectedFile().getPath();
						// if the user does not choose any file, do nothing

						if (!pyFilePath.equals("") && (pyFilePath != null))
							new LinuxProcess().startProcess("python " + pyFilePath);
					}
				});
			} else { // other app
				appButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (userDefineFrame != null) {
							userDefineFrame.dispose();
							if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
								((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
						}
						if (sysControlFrame != null) {
							sysControlFrame.dispose();
							((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
						}
						if (equipmentControlFrame != null) {
							equipmentControlFrame.dispose();
						}
						if (liveFrame != null) {
							liveFrame.dispose();
						}
						new LinuxProcess().startProcess(appJSON.getString("command"));
					}
				});
			}
			appButton.setBounds(i / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
					i % 7 * (buttonSize + labelHeight + buttonVerMaxInterval) + buttonVerMaxInterval, buttonSize,
					buttonSize);

			// Set labels
			JLabel appLabel = new JLabel(appJSON.getString("name"));
			appLabel.setFont(labelFont);
			appLabel.setVerticalAlignment(SwingConstants.CENTER);
			appLabel.setHorizontalAlignment(SwingConstants.CENTER);
			appLabel.setBounds(appButton.getBounds().x, appButton.getBounds().y + buttonSize, labelWidth, labelHeight);

			this.getContentPane().add(appButton);
			this.getContentPane().add(appLabel);
		}

		userDefineButton = new JButton();
		equipmentControlButton = new JButton();
		liveButton = new JButton();
		sysButton = new JButton();
		exitButton = new JButton("exit");

		// The maximum resolution of the image is 256*256

		ImageIcon userDefineImage = new ImageIcon(Constant.settingImagePath);
		ImageIcon equipmentControlImage = new ImageIcon(Constant.equipmentImagePath);
		ImageIcon liveImage = new ImageIcon(Constant.liveImagePath);
		ImageIcon sysImage = new ImageIcon(Constant.sysImagePath);

		// Scale the image to the size of the button
		userDefineImage
				.setImage(userDefineImage.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));
		equipmentControlImage.setImage(
				equipmentControlImage.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));
		liveImage.setImage(liveImage.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));
		sysImage.setImage(sysImage.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));

		userDefineButton.setIcon(userDefineImage);
		equipmentControlButton.setIcon(equipmentControlImage);
		liveButton.setIcon(liveImage);
		sysButton.setIcon(sysImage);

		userDefineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
				userDefineFrame = new UserDefinePanel((MainPanel) mainPanel);
			}
		});
		equipmentControlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
				equipmentControlFrame = new EquipmentControlPanel();
			}
		});
		liveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
				liveFrame = new LivePanel();
			}
		});
		sysButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
				sysControlFrame = new SystemControlPanel();
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		userDefineButton.setBounds(
				(7 * buttonHorMaxNum - 4) / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
				(7 * buttonHorMaxNum - 4) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval)
						+ buttonVerMaxInterval,
				buttonSize, buttonSize);
		equipmentControlButton.setBounds(
				(7 * buttonHorMaxNum - 3) / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
				(7 * buttonHorMaxNum - 3) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval)
						+ buttonVerMaxInterval,
				buttonSize, buttonSize);
		liveButton.setBounds((7 * buttonHorMaxNum - 2) / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
				(7 * buttonHorMaxNum - 2) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval)
						+ buttonVerMaxInterval,
				buttonSize, buttonSize);
		sysButton.setBounds((7 * buttonHorMaxNum - 1) / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
				(7 * buttonHorMaxNum - 1) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval)
						+ buttonVerMaxInterval,
				buttonSize, buttonSize);
		exitButton.setBounds((7 * buttonHorMaxNum - 5) / 7 * (buttonSize + buttonHorMaxInterval) + buttonHorMaxInterval,
				(7 * buttonHorMaxNum - 5) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval)
						+ buttonVerMaxInterval,
				buttonSize, buttonSize);

		userDefineLabel = new JLabel("Edit Apps");
		userDefineLabel.setVerticalAlignment(SwingConstants.CENTER);
		userDefineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		equipmentControlLabel = new JLabel("Equip Control");
		equipmentControlLabel.setVerticalAlignment(SwingConstants.CENTER);
		equipmentControlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		liveLabel = new JLabel("Live");
		liveLabel.setVerticalAlignment(SwingConstants.CENTER);
		liveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sysLabel = new JLabel("SHUT DOWN");
		sysLabel.setVerticalAlignment(SwingConstants.CENTER);
		sysLabel.setHorizontalAlignment(SwingConstants.CENTER);

		userDefineLabel.setFont(labelFont);
		equipmentControlLabel.setFont(labelFont);
		liveLabel.setFont(labelFont);
		sysLabel.setFont(labelFont);

		userDefineLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
			}
		});
		equipmentControlLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
			}
		});
		liveLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
			}
		});
		sysLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
			}
		});

		userDefineLabel.setBounds(userDefineButton.getBounds().x, userDefineButton.getBounds().y + buttonSize,
				labelWidth, labelHeight);
		equipmentControlLabel.setBounds(equipmentControlButton.getBounds().x,
				equipmentControlButton.getBounds().y + buttonSize, labelWidth, labelHeight);
		liveLabel.setBounds(liveButton.getBounds().x, liveButton.getBounds().y + buttonSize, labelWidth, labelHeight);
		sysLabel.setBounds(sysButton.getBounds().x, sysButton.getBounds().y + buttonSize, labelWidth, labelHeight);

		this.getContentPane().add(userDefineButton);
		this.getContentPane().add(equipmentControlButton);
		this.getContentPane().add(liveButton);
		this.getContentPane().add(sysButton);
		this.getContentPane().add(userDefineLabel);
		this.getContentPane().add(equipmentControlLabel);
		this.getContentPane().add(liveLabel);
		this.getContentPane().add(sysLabel);

		this.getContentPane().add(exitButton);

		// Add user defined Apps
		((MainPanel) mainPanel).userButtonRepaint();

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (userDefineFrame != null) {
					userDefineFrame.dispose();
					if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
						((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
				}
				if (sysControlFrame != null) {
					sysControlFrame.dispose();
					((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
				}
				if (equipmentControlFrame != null) {
					equipmentControlFrame.dispose();
				}
				if (liveFrame != null) {
					liveFrame.dispose();
				}
			}
		});
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setBackground(Color.black);
		this.setUndecorated(true);

		this.setVisible(true);

		// Set the main frame to full size of the screen
		// this.setUndecorated(true);
		// GraphicsEnvironment currentEnvironment =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// GraphicsDevice currentDevice = currentEnvironment.getDefaultScreenDevice();
		// currentDevice.setFullScreenWindow(this);
	}

	public void userButtonRepaint() {
		for (int i = 0; i < userAppButton.size(); i++) {
			mainPanel.getContentPane().remove(userAppButton.get(i));
			mainPanel.getContentPane().remove(userAppLabel.get(i));
		}

		userAppButton.clear();
		userAppLabel.clear();

		// Add user defined apps'(if any) to the main panel
		userAppJSONArray = new Config().getUserAppJSONInfo();
		if (userAppJSONArray == null) {
			mainPanel.repaint();
		} else {
			// Set user defined apps' UI

			for (int i = 0; i < userAppJSONArray.length(); i++) {
				if (i < (buttonHorMaxNum * 7 - 12)) {
					JSONObject appJSON = userAppJSONArray.getJSONObject(i);

					// Set buttons
					JButton appButton = new JButton();
					ImageIcon appIcon = new ImageIcon(appJSON.getString("iconPath"));
					appIcon.setImage(appIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT));
					appButton.setIcon(appIcon);

					appButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (userDefineFrame != null) {
								userDefineFrame.dispose();
								if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
									((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
							}
							if (sysControlFrame != null) {
								sysControlFrame.dispose();
								((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
							}
							if (equipmentControlFrame != null) {
								equipmentControlFrame.dispose();
							}
							if (liveFrame != null) {
								liveFrame.dispose();
							}
							new LinuxProcess().startProcess(appJSON.getString("command"));
						}
					});
					appButton.setBounds(
							((i + basicAppJSONArray.length()) / 7 * (buttonSize + buttonHorMaxInterval))
									+ buttonHorMaxInterval,
							((i + basicAppJSONArray.length()) % 7 * (buttonSize + labelHeight + buttonVerMaxInterval))
									+ buttonVerMaxInterval,
							buttonSize, buttonSize);

					// Set labels
					JLabel appLabel = new JLabel(appJSON.getString("name"));
					appLabel.setFont(labelFont);
					appLabel.setVerticalAlignment(SwingConstants.CENTER);
					appLabel.setHorizontalAlignment(SwingConstants.CENTER);
					appLabel.setBounds(appButton.getBounds().x, appButton.getBounds().y + buttonSize, labelWidth,
							labelHeight);

					appLabel.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if (userDefineFrame != null) {
								userDefineFrame.dispose();
								if (((UserDefinePanel) userDefineFrame).getEditFrame() != null)
									((UserDefinePanel) userDefineFrame).getEditFrame().dispose();
							}
							if (sysControlFrame != null) {
								sysControlFrame.dispose();
								((SystemControlPanel) sysControlFrame).getCountdownThread().interrupt();
							}
							if (equipmentControlFrame != null) {
								equipmentControlFrame.dispose();
							}
							if (liveFrame != null) {
								liveFrame.dispose();
							}
						}
					});

					mainPanel.getContentPane().add(appButton);
					mainPanel.getContentPane().add(appLabel);
					mainPanel.repaint();

					userAppButton.add(appButton);
					userAppLabel.add(appLabel);
				} else
					;
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainPanel();
			}
		});
	}
}
