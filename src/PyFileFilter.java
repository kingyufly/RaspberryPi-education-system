import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PyFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		String name = file.getName();
		return file.isDirectory() || name.toLowerCase().endsWith(".py");
	}

	@Override
	public String getDescription() {
		return "Python File (*.py)";
	}
}
