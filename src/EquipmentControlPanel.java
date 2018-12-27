import java.awt.event.*;
import javax.swing.*;

public class EquipmentControlPanel extends JFrame {
	JFrame equipmentControlFrame = null;

	public EquipmentControlPanel() {
		equipmentControlFrame = this;

		// Manually set the size of the window
		int windowWidth = 300;
		int windowHeight = 80 + 30;

		int screenWidth = new ScreenSize().getScreenWidth();
		int screenHeight = new ScreenSize().getScreenHeight();

		JLabel projectorLabel = new JLabel("Projector");
		JLabel screenLabel = new JLabel("Screen");
		JLabel blindLabel = new JLabel("Blind");

		JButton projectorOnButton = new JButton("ON");
		JButton projectorOffButton = new JButton("OFF");
		JButton screenUpButton = new JButton("UP");
		JButton screenDownButton = new JButton("DOWN");
		JButton blindOpenButton = new JButton("OPEN");
		JButton blindCloseButton = new JButton("CLOSE");

		projectorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		screenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		blindLabel.setHorizontalAlignment(SwingConstants.CENTER);

		projectorOnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Send the IR code(ON) to the projector");
			}
		});
		projectorOffButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Send the IR code(OFF) to the projector");
			}
		});
		screenUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Control the motor to pull up the screen");
			}
		});
		screenDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Control the motor to pull down the screen");
			}
		});
		blindOpenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Control the motor to open the blind");
			}
		});
		blindCloseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Control the motor to close the blind");
			}
		});

		projectorLabel.setBounds(10, 5, 80, 20);
		screenLabel.setBounds(110, 5, 80, 20);
		blindLabel.setBounds(210, 5, 80, 20);
		projectorOnButton.setBounds(10, 30, 80, 20);
		projectorOffButton.setBounds(10, 55, 80, 20);
		screenUpButton.setBounds(110, 30, 80, 20);
		screenDownButton.setBounds(110, 55, 80, 20);
		blindOpenButton.setBounds(210, 30, 80, 20);
		blindCloseButton.setBounds(210, 55, 80, 20);

		this.getContentPane().add(projectorLabel);
		this.getContentPane().add(screenLabel);
		this.getContentPane().add(blindLabel);
		this.getContentPane().add(projectorOnButton);
		this.getContentPane().add(projectorOffButton);
		this.getContentPane().add(screenUpButton);
		this.getContentPane().add(screenDownButton);
		this.getContentPane().add(blindOpenButton);
		this.getContentPane().add(blindCloseButton);

		this.setLayout(null);
		this.setVisible(true);

		// Set the size, location and etc. for the window
		this.setTitle("Equipment Control");

		ImageIcon warningImage = new ImageIcon(Constant.settingImagePath);
		this.setIconImage(warningImage.getImage());
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				equipmentControlFrame.dispose();
			}
		});

		// Set the size of the window
		this.setSize(windowWidth, windowHeight);

		// Set the window at the center of the screen
		//this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
		this.setLocationRelativeTo(null);
	}
}
