public class ScreenSize {
	private int screenWidth;
	private int screenHeight;

	public int getScreenWidth() {
		setScreenWidth(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		setScreenHeight(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
}
