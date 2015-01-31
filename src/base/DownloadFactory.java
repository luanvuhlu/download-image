package base;

import downloadmanager.Earth;

public class DownloadFactory {
	private DownloadFactory(){}
	public static DownloadAbstract getDownloadManager(String type) throws Exception{
		if("earth".equals(type.toLowerCase())){
			return new Earth();
		}
		throw new Exception("The download manager type " + type + " is not recognized.");
	}
}
