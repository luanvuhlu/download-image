package common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import entity.Image;

public class FileUtil {
	public static final int CREATE_NEW=0;
	public static final int EXIST=1;
	public static boolean isFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return false;
		}
		if (!f.isFile()) {
			return false;
		}
		return false;
	}

	public static boolean isFile(File f) {
		if (!f.exists()) {
			return false;
		}
		return f.isFile();
	}

	public static boolean isEmptyFile(File f){
		if(!isFile(f)){
			return false;
		}
		return f.getTotalSpace() == 0;
	}
	
	public static int isDirectOrCreate(File f) throws Exception{
		if(f.isDirectory()){
			return EXIST;
		}
		try{
			f.mkdir();
		}catch(Exception e){
			throw new Exception("Không tạo được folder: "+f.getAbsolutePath()+" : "+e.getMessage());
		}
		return CREATE_NEW;
	}
	public static boolean saveImage(Image img, String folder)
			throws Exception {
		InputStream in=img.getIn();
		FileOutputStream fos = null;
		ByteArrayOutputStream out = null;
		File f=new File(folder+"/"+img.getName()+"."+img.getExtension());
		if(isFile(f)){
			if(!isEmptyFile(f)){
				int index=0;
				do{
					f=new File(folder+"/"+img.getName()+(index++)+"."+img.getExtension());
					Message.printConsole("Index: "+index);
				}while(isFile(f));
//				Message.printConsole("File đã tồn tại: "+folder+"/"+img.getName()+"."+img.getExtension());
//				return false;
			}
		}
		
//		isDirectOrCreate(new File(folder));
		try {
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			Message.printConsole("Đang lưu: "+folder+"/"+img.getName()+"."+img.getExtension());
			fos = new FileOutputStream(f);
			fos.write(response);
		} catch (IOException e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return true;
	}
	public static Image getImage(String src) throws Exception{
		URL url=null;
		try{
			url =new URL(src);
			int indexname = src.lastIndexOf("/");
			if (indexname == src.length()) {
				src = src.substring(1, indexname);
			}
			indexname = src.lastIndexOf("/");
			String name = src.substring(src.lastIndexOf("/")+1, src.lastIndexOf(".")-1);
			String extension=src.substring(src.lastIndexOf(".")+1);
			name=replaceSpecial(name);
			extension=replaceSpecial(extension);
			Image img=new Image();
			img.setIn(url.openStream());
			img.setName(name);
			img.setExtension(extension);
			return img;
		}catch(Exception e){
			throw new Exception("Lỗi tại: "+src+" : "+e.getMessage());
		}
	}
	public static String replaceSpecial(String s){
		return s.replaceAll("[<>:/\\\\|\"?*]", " ").trim();
	}
}
