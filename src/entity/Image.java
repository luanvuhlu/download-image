package entity;

import java.io.InputStream;

public class Image {
	private String name;
	private InputStream in;
	private String extension;
	public String getExtension() {
		return extension;
	}
	public InputStream getIn() {
		return in;
	}
	public String getName() {
		return name;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
