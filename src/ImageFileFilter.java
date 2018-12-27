import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		String name = file.getName();
		return file.isDirectory() || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".jpg")
				|| name.toLowerCase().endsWith(".png");
	}

	@Override
	public String getDescription() {
		return "Image File (*.jpeg; *.jpg; *.png)";
	}
}
