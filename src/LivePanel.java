import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class LivePanel extends JFrame {
	JFrame liveFrame = null;

	public LivePanel() {
		liveFrame = this;

		// Manually set the size of the window
		int windowWidth = 320;
		int windowHeight = 55 + 30;

		int screenWidth = new ScreenSize().getScreenWidth();
		int screenHeight = new ScreenSize().getScreenHeight();

		JLabel liveLabel = new JLabel("Live");
		JLabel addressLabel = new JLabel("Live Address");
		JLabel addressDisplayLabel = new JLabel();

		JButton liveControlButton = new JButton("OFF");

		NetInterface eth0 = new NetInterface("wlan0");

		liveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressDisplayLabel.setVerticalAlignment(SwingConstants.CENTER);

		addressDisplayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		LinuxProcess streamProcess = new LinuxProcess("gst-launch-1.0");

		if (streamProcess.getLiveState()) {
			liveControlButton.setText("OFF");
			addressDisplayLabel.setText("http://" + eth0.getIpv4Address() + "/live");
		} else {
			liveControlButton.setText("ON");
			addressDisplayLabel.setText("Not available");
		}

		liveControlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (streamProcess.isProcessAlive()) {
					streamProcess.killProcess();
					while (streamProcess.isProcessAlive())
						;
					liveControlButton.setText("ON");
					addressDisplayLabel.setText("Not available");
				} else {
					streamProcess.startProcess("../shell/startLive.sh " + eth0.getIpv4Address());
					while (!streamProcess.isProcessAlive())
						;
					liveControlButton.setText("OFF");
					addressDisplayLabel.setText("http://" + eth0.getIpv4Address() + "/live");
				}
			}
		});

		liveLabel.setBounds(10, 5, 80, 20);
		addressLabel.setBounds(100, 5, 200, 20);
		liveControlButton.setBounds(10, 30, 80, 20);
		addressDisplayLabel.setBounds(100, 30, 200, 20);

		this.getContentPane().add(liveLabel);
		this.getContentPane().add(addressLabel);
		this.getContentPane().add(liveControlButton);
		this.getContentPane().add(addressDisplayLabel);

		this.setLayout(null);
		this.setVisible(true);

		// Set the size, location and etc. for the window
		this.setTitle("Live Control");

		ImageIcon warningImage = new ImageIcon(Constant.settingImagePath);
		this.setIconImage(warningImage.getImage());
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				liveFrame.dispose();
			}
		});

		// Set the size of the window
		this.setSize(windowWidth, windowHeight);

		// Set the window at the center of the screen
		// this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
		this.setLocationRelativeTo(null);
	}
}
