import java.io.*;

public class LinuxProcess {
	private String name;
	private boolean liveState;

	public LinuxProcess() {
	}

	public LinuxProcess(String name) {
		this.name = name;
		this.liveState = this.isProcessAlive();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLiveState(boolean liveState) {
		this.liveState = liveState;
	}

	public String getName(String name) {
		return this.name;
	}

	public boolean getLiveState() {
		return this.liveState;
	}

	public void killProcess() {
		this.startProcess("../shell/killProcess.sh " + this.name);
	}

	public boolean isProcessAlive() {
		InputStream in = null;
		try {
			Process process = Runtime.getRuntime()
					.exec("../shell/checkProcessLive.sh " + this.name);
			process.waitFor();
			in = process.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String result1 = read.readLine();
			String result2 = read.readLine();

			if (result2 == null || result1 == null)
				return false;
			try {
				int tmp = Integer.parseInt(result1);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void startProcess(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startSystemProcess(int commandsId) {
		try {
			Runtime.getRuntime().exec(Constant.sysCommands[commandsId]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
