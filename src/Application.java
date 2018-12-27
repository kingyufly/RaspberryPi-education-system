public class Application {
	private String name = "";
	private String iconPath = "";
	private String command = "";

	public String getName() {
		return name;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String getCommand() {
		return command;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String toString() {
		return "Application [name=" + name + ", iconPath=" + iconPath + ", command=" + command + "]";
	}
}
