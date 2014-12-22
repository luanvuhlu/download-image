package entity;

public class PathEntity {
	private String url;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public PathEntity() {
	}
	public PathEntity(String url, String title) {
		this.url = url;
		this.title = title;
	}
}
