import java.io.*;

public class FileUtil {
	public String readFileByLine(String filePath) {
		String str = "";
		File file = null;
		BufferedReader bReader = null;
		FileReader fReader = null;

		try {
			file = new File(filePath);
			if (file.exists()) {
				fReader = new FileReader(file);
				bReader = new BufferedReader(fReader);
				String tempStr = null;

				while ((tempStr = bReader.readLine()) != null) {
					str += tempStr;
				}
				bReader.close();
				fReader.close();
				return str;
			} else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bReader != null || fReader != null) {
				try {
					bReader.close();
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void writeFile(String str, String filePath, boolean overwriteFlag) {
		File file = null;
		FileWriter fWriter = null;
		BufferedWriter bWriter = null;

		try {
			file = new File(filePath);
			fWriter = new FileWriter(file, !overwriteFlag);
			bWriter = new BufferedWriter(fWriter);
			bWriter.write(str);
			bWriter.flush();
			bWriter.close();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fWriter != null || bWriter != null) {
				try {
					fWriter.close();
					bWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
}
