import java.io.File;
import javax.swing.filechooser.FileFilter;

public class JarFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		String name = file.getName();
		return file.isDirectory() || name.toLowerCase().endsWith(".jar");
	}

	@Override
	public String getDescription() {
		return "JAR File (*.jar)";
	}
}
