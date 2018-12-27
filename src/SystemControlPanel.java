import java.awt.event.*;
import javax.swing.*;

public class SystemControlPanel extends JFrame {
	private Thread countdownThread = null;
	JFrame sysControlFrame = null;

	public Thread getCountdownThread() {
		return this.countdownThread;
	}

	public SystemControlPanel() {
		sysControlFrame = this;

		// int windowWidth = screenWidth / 3;
		// int windowHeight = screenHeight / 3;

		// Manually set the size of the window
		int windowWidth = 480;
		int windowHeight = 140;

		int screenWidth = new ScreenSize().getScreenWidth();
		int screenHeight = new ScreenSize().getScreenHeight();

		JButton rebootButton = new JButton("Restart");
		JButton cancelButton = new JButton("Cancel");
		JButton shutdownButton = new JButton("Shutdown");

		JLabel noticeLabel = new JLabel(
				"<html><font size=5><b>Shutdown the system now?</b></font><br><font size=4>This system will be automatically shut down in 60 seconds</font></html>");

		rebootButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				countdownThread.interrupt();
				new LinuxProcess().startSystemProcess(Constant.REBOOT);
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				countdownThread.interrupt();
				sysControlFrame.dispose();
			}
		});
		shutdownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				countdownThread.interrupt();
				new LinuxProcess().startSystemProcess(Constant.SHUTDOWN);
			}
		});

		noticeLabel.setBounds(5, 5, 475, 50);
		rebootButton.setBounds(37, 65, 110, 40);
		cancelButton.setBounds(185, 65, 110, 40);
		shutdownButton.setBounds(322, 65, 110, 40);

		this.getContentPane().add(noticeLabel);
		this.getContentPane().add(rebootButton);
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(shutdownButton);

		this.setLayout(null);
		this.setVisible(true);

		// Set the size, location and etc. for the window
		this.setTitle("WARNING");

		ImageIcon warningImage = new ImageIcon(Constant.warningImagePath);
		this.setIconImage(warningImage.getImage());
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				countdownThread.interrupt();
				sysControlFrame.dispose();
			}
		});
		// Set the size of the window
		this.setSize(windowWidth, windowHeight);

		// Set the window at the center of the screen
		// this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
		this.setLocationRelativeTo(null);

		countdownThread = new Thread(new CountdownThread());
		countdownThread.start();
	}

	public class CountdownThread implements Runnable {
		@Override
		public void run() {
			int secPassedby = 0;

			while (secPassedby < 60) {
				secPassedby++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
			new LinuxProcess().startSystemProcess(Constant.SHUTDOWN);
		}
	}
}
