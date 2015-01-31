package setting;

public class MainSetting {
	private String path;
	private String page;
	private String type;
	private boolean direct;
	private String folder;
	public MainSetting(String path, String page, String type, boolean directDownload) {
		this.page=page;
		this.path=path;
		this.type=type;
		this.direct=directDownload;
	}
	public MainSetting() {
		this.page="0";
	}
	public String getPage() {
		return page;
	}
	public String getPath() {
		return path;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isDirect() {
		return direct;
	}
	public void setDirect(boolean direct) {
		this.direct = direct;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFolder() {
		return folder;
	}
}
