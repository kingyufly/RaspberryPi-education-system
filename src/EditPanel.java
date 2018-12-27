import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.*;


public class EditPanel extends JFrame {
	int JSONArrayIndex = -1;
	ImageIcon icon = null;
	JSONArray userAppJSONArray = null;

	JFrame editFrame = null;

	// Edit APP parameters (if appName is null, then it is to add a new APP)
	public EditPanel(UserDefinePanel userDefineFrame, JSONArray userAppJSONArray, String appName) {

		this.userAppJSONArray = userAppJSONArray;
		editFrame = this;

		JLabel nameLabel = new JLabel("APP Name:", SwingConstants.LEFT);
		JLabel commandLabel = new JLabel("Command:", SwingConstants.LEFT);
		JLabel iconLabel = new JLabel("Icon:", SwingConstants.LEFT);
		JLabel iconPreviewLabel = new JLabel("<html>Icon<br>Preview</html>", SwingConstants.LEFT);
		JLabel iconDisplayLabel = new JLabel();

		JButton iconSelectButton = new JButton("Select");
		JButton iconDeleteButton = new JButton("Delete");
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		JTextField nameInput = new JTextField();
		JTextField commandInput = new JTextField();

		Font font = new Font("Liberation Serif", Font.PLAIN, 14);

		// Manually set the size of the window
		int windowWidth = 266;
		int windowHeight = 288;

		int screenWidth = new ScreenSize().getScreenWidth();
		int screenHeight = new ScreenSize().getScreenHeight();

		nameLabel.setFont(font);
		nameInput.setFont(font);
		commandLabel.setFont(font);
		commandInput.setFont(font);
		iconLabel.setFont(font);
		iconSelectButton.setFont(font);
		iconDeleteButton.setFont(font);
		iconPreviewLabel.setFont(font);
		iconDisplayLabel.setFont(font);
		okButton.setFont(font);
		cancelButton.setFont(font);
		iconDisplayLabel.setBorder(BorderFactory.createLineBorder(Color.black));

		if (appName != null) {
			for (int i = 0; i < userAppJSONArray.length(); i++) {
				if (userAppJSONArray.getJSONObject(i).getString("name").equals(appName)) {
					JSONArrayIndex = i;
					break;
				}
			}

			nameInput.setText(userAppJSONArray.getJSONObject(JSONArrayIndex).getString("name"));
			commandInput.setText(userAppJSONArray.getJSONObject(JSONArrayIndex).getString("command"));

			if (("" + userAppJSONArray.getJSONObject(JSONArrayIndex).get("iconPath")).equals("")) {
				// no icon
				iconDisplayLabel.setText("NO ICON");
				iconDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
				iconDisplayLabel.setIcon(null);
			} else {
				// has icon
				icon = new ImageIcon("" + userAppJSONArray.getJSONObject(JSONArrayIndex).get("iconPath"));
				icon.setImage(icon.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
				iconDisplayLabel.setIcon(icon);
			}
		} else {
			nameInput.setText("");
			commandInput.setText("");
			iconDisplayLabel.setText("Add Icon to Preview");
			iconDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}

		iconSelectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				iconDisplayLabel.setText(null);
				JFileChooser imageFileChooser = new JFileChooser();
				ImageFileFilter imageFilter = new ImageFileFilter();
				imageFileChooser.addChoosableFileFilter(imageFilter);
				imageFileChooser.setFileFilter(imageFilter);

				int i = imageFileChooser.showOpenDialog(null); // open the dialog to choose the file
				if (i == imageFileChooser.APPROVE_OPTION) { // judge the status of the user's choice
					icon = new ImageIcon(imageFileChooser.getSelectedFile().getPath());
					icon.setImage(icon.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
					iconDisplayLabel.setIcon(icon);
				}

				// if the user does not choose any file, do nothing
			}
		});

		iconDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				iconDisplayLabel.setIcon(null);
				iconDisplayLabel.setText("NO ICON");
				iconDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// use a invisible JFrame to set the icon of the confirm dialog window
				ImageIcon warningIcon = new ImageIcon(Constant.warningImagePath);
				JFrame warningFrame = new JFrame();
				warningFrame.setIconImage(warningIcon.getImage());
				warningFrame.setVisible(false);

				if (nameInput.getText().equals("") || nameInput.getText() == null) { // empty app name
					JOptionPane.showConfirmDialog(warningFrame, "Please input the APP's name!\nTry again!", "Warning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
					nameInput.requestFocus();
				} else if (commandInput.getText().equals("") || commandInput.getText() == null) { // empty app
																									// command
					JOptionPane.showConfirmDialog(warningFrame, "Please input the APPs' command!\nTry again!",
							"Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
					commandInput.requestFocus();
				} else {
					if ((new Config().isAppNameDupBasic(nameInput.getText())
							|| new Config().isAppNameDupUser(nameInput.getText(), userAppJSONArray))
							&& ((appName == null) ? true : !nameInput.getText().equals(appName))) {
						// duplicate app name when adding/editing app (except the same as itself)
						JOptionPane.showConfirmDialog(warningFrame,
								"The APP's name is duplicate with the existing one!\n Please try again!", "Warning",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						nameInput.requestFocus();
					} else if ((new Config().isAppCommandDupBasic(commandInput.getText())
							|| new Config().isAppCommandDupUser(commandInput.getText(), userAppJSONArray))
							&& ((appName == null) ? true
									: !commandInput.getText().equals(
											userAppJSONArray.getJSONObject(JSONArrayIndex).getString("command")))) {
						// duplicate app command when adding/editing app (except the same as itself)
						JOptionPane.showConfirmDialog(warningFrame,
								"The APP's command is duplicate with the existing one!\n Please try again!", "Warning",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						commandInput.requestFocus();
					} else {
						if (appName != null) {
							// change the JSON parameters of the added APP
							userAppJSONArray.getJSONObject(JSONArrayIndex).put("name", nameInput.getText());
							userAppJSONArray.getJSONObject(JSONArrayIndex).put("command", commandInput.getText());
							if (iconDisplayLabel.getIcon() == null)
								userAppJSONArray.getJSONObject(JSONArrayIndex).put("iconPath", "");
							else
								userAppJSONArray.getJSONObject(JSONArrayIndex).put("iconPath",
										iconDisplayLabel.getIcon());
							editFrame.dispose();
						} else {
							// add new app to the user define JSON file
							Application newApp = new Application();
							newApp.setName(nameInput.getText());
							newApp.setCommand(commandInput.getText());
							if (iconDisplayLabel.getIcon() == null)
								newApp.setIconPath("");
							else
								newApp.setIconPath("" + iconDisplayLabel.getIcon());
							userAppJSONArray.put(new JSONObject(newApp));
							editFrame.dispose();
						}
						userDefineFrame.appDetialPanelRepaint();
					}
				}
				// release the JFrame after the user confirm the warning message
				warningFrame.dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editFrame.dispose();
			}
		});

		nameLabel.setBounds(5, 5, 70, 20);
		nameInput.setBounds(80, 5, 175, 20);
		commandLabel.setBounds(5, 30, 70, 20);
		commandInput.setBounds(80, 30, 175, 20);
		iconLabel.setBounds(5, 55, 70, 20);
		iconSelectButton.setBounds(80, 55, 80, 20);
		iconDeleteButton.setBounds(165, 55, 80, 20);
		iconPreviewLabel.setBounds(5, 134, 70, 40);
		iconDisplayLabel.setBounds(80, 90, 128, 128);
		okButton.setBounds(32, 233, 80, 20);
		cancelButton.setBounds(144, 233, 80, 20);

		this.getContentPane().add(nameLabel);
		this.getContentPane().add(nameInput);
		this.getContentPane().add(commandLabel);
		this.getContentPane().add(commandInput);
		this.getContentPane().add(iconLabel);
		this.getContentPane().add(iconSelectButton);
		this.getContentPane().add(iconDeleteButton);
		this.getContentPane().add(iconPreviewLabel);
		this.getContentPane().add(iconDisplayLabel);
		this.getContentPane().add(okButton);
		this.getContentPane().add(cancelButton);

		this.setLayout(null);
		this.setVisible(true);

		// Set the size, location and etc. for the window
		this.setTitle("Edit App");

		ImageIcon warningImage = new ImageIcon(Constant.settingImagePath);
		this.setIconImage(warningImage.getImage());
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				editFrame.dispose();
			}
		});

		this.setSize(windowWidth, windowHeight);

		// Set the window at the center of the screen
		//this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
	}
}
