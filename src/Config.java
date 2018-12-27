import org.json.*;

public class Config {
	public final static String basicFilePath = "../config/config_basic.json";
	public final static String userFilePath = "../config/config_user.json";

	public JSONArray getBasicAppJSONInfo() {
		FileUtil fu = new FileUtil();
		if (fu.isFileExist(basicFilePath)) {
			return new JSONArray(fu.readFileByLine(basicFilePath));
		}
		return null;
	}

	public JSONArray getUserAppJSONInfo() {
		FileUtil fu = new FileUtil();
		if (fu.isFileExist(userFilePath)) {
			try {
				return new JSONArray(fu.readFileByLine(userFilePath));
			} catch (JSONException e) {
				return null;
			}
		}
		return null;
	}

	public boolean isAppNameDupBasic(String appName) {
		JSONArray basicAppJSONArray = new JSONArray(new FileUtil().readFileByLine(basicFilePath));
		for (int i = 0; i < basicAppJSONArray.length(); i++) {
			if (basicAppJSONArray.getJSONObject(i).getString("name").equals(appName))
				return true;
		}
		return false;
	}

	public boolean isAppNameDupUser(String appName, JSONArray userAppJSONArray) {
		for (int i = 0; i < userAppJSONArray.length(); i++) {
			if (userAppJSONArray.getJSONObject(i).getString("name").equals(appName))
				return true;
		}
		return false;
	}

	public boolean isAppCommandDupBasic(String appCommand) {
		JSONArray basicAppJSONArray = new JSONArray(new FileUtil().readFileByLine(basicFilePath));
		for (int i = 0; i < basicAppJSONArray.length(); i++) {
			if (basicAppJSONArray.getJSONObject(i).getString("command").equals(appCommand))
				return true;
		}
		return false;
	}

	public boolean isAppCommandDupUser(String appCommand, JSONArray userAppJSONArray) {
		for (int i = 0; i < userAppJSONArray.length(); i++) {
			if (userAppJSONArray.getJSONObject(i).getString("command").equals(appCommand))
				return true;
		}
		return false;
	}

	// not used
	public String getAppNameDupSource(String appName) {
		JSONArray basicAppJSONArray = new JSONArray(new FileUtil().readFileByLine(basicFilePath));
		for (int i = 0; i < basicAppJSONArray.length(); i++) {
			if (basicAppJSONArray.getJSONObject(i).getString("name").equals(appName))
				return basicAppJSONArray.getJSONObject(i).getString("name");
		}

		// The user defined APP config file may not exist, so check the file existence
		// before check the user's config
		String userFileJSONStr = new FileUtil().readFileByLine(userFilePath);
		if (userFileJSONStr == null)
			return null;
		else {
			JSONArray userAppJSONArray = new JSONArray(new FileUtil().readFileByLine(userFilePath));
			for (int i = 0; i < userAppJSONArray.length(); i++) {
				if (userAppJSONArray.getJSONObject(i).getString("name").equals(appName))
					return userAppJSONArray.getJSONObject(i).getString("name");
			}
			return null;
		}
	}

	// not used
	public String getAppCommandDupSource(String appCommand) {
		JSONArray basicAppJSONArray = new JSONArray(new FileUtil().readFileByLine(basicFilePath));
		for (int i = 0; i < basicAppJSONArray.length(); i++) {
			if (basicAppJSONArray.getJSONObject(i).getString("command").equals(appCommand))
				return basicAppJSONArray.getJSONObject(i).getString("command");
		}

		// The user defined APP config file may not exist, so check the file existence
		// before check the user's config
		String userFileJSONStr = new FileUtil().readFileByLine(userFilePath);
		if (userFileJSONStr == null)
			return null;
		else {
			JSONArray userAppJSONArray = new JSONArray(new FileUtil().readFileByLine(userFilePath));
			for (int i = 0; i < userAppJSONArray.length(); i++) {
				if (userAppJSONArray.getJSONObject(i).getString("command").equals(appCommand))
					return userAppJSONArray.getJSONObject(i).getString("command");
			}
			return null;
		}
	}
}
