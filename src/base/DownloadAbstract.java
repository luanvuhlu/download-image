package base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import setting.MainSetting;
import common.FileUtil;
import common.Message;
import entity.Image;
import entity.PathEntity;

public abstract class DownloadAbstract  {
	protected DownloadAbstract(MainSetting setting) {
		this.setting=setting;
	}
	protected DownloadAbstract(){
		this.index=0;
	}
	protected MainSetting setting;
	protected int index;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	protected boolean download(List<Image> imgs, String folder) throws Exception{
		File fd=new File(folder);
		FileUtil.isDirectOrCreate(fd);
		for(Image img:imgs){
			Message.printConsole("Đang lưu ảnh: "+img.getName()+" vào "+folder);
			FileUtil.saveImage(img, folder);
		}
		return true;
	}
	public void setSetting(MainSetting setting) {
		this.setting = setting;
	}
	public MainSetting getSetting() {
		return setting;
	}
	public List<Image> getImages(List<String> urls) throws Exception{
		List<Image> imgs=new ArrayList<Image>();
		Image img=null;
		for(String p:urls){
			Message.printConsole("Đang lấy thông tin ảnh từ "+p);
			img=FileUtil.getImage(p);
			imgs.add(img);
		}
		return imgs;
	}
	public void downloadDirectPage(PathEntity pEtt) throws Exception{
		List<Image> imgs=null;
		List<String> imgUrls=null;
		try{
			Message.printConsole("Đang lấy url ảnh từ path: "+pEtt.getUrl());
			imgUrls=getImageUrls(pEtt.getUrl());
			Message.printConsole("Đang lấy thông tin ảnh");
			imgs=getImages(imgUrls);
			Message.printConsole("Bắt đầu download: "+pEtt.getTitle());
			pEtt.setTitle(FileUtil.replaceSpecial(pEtt.getTitle()));
			Message.printConsole("Lưu ảnh vào "+pEtt.getTitle());
			download(imgs,setting.getFolder()+"/"+ pEtt.getTitle());
		}catch(Exception e){
			throw e;
		}
	}
	protected abstract String nextPage(String rootPath);
	protected abstract List<String> getImageUrls(String path) throws Exception ;
	protected abstract List<PathEntity> getPaths(String rootPath) throws Exception;
	protected abstract PathEntity getPathDetail(String path);
	protected int[] parsePageLimit() {
		if(setting.getPage().indexOf("-") < 0){
			try{
				int p1=Integer.parseInt(setting.getPage());
				return new int[]{p1, 0};
			}catch(Exception e){
				throw e;
			}
		}
		try{
			int p1=Integer.parseInt(setting.getPage().substring(0, setting.getPage().lastIndexOf("-")));
			int p2=Integer.parseInt(setting.getPage().substring(setting.getPage().lastIndexOf("-")+1));
			return new int[]{p1, p2};
		}catch(Exception e){
			throw e;
		}
	}
	public void run() throws Exception {
		
		try{
			Message.printConsole("Đang download từ path: "+setting.getPath()+" pages: "+setting.getPage());
			if(setting.isDirect()){
				PathEntity pEtt=getPathDetail(setting.getPath());
				downloadDirectPage(pEtt);
				return;
			}
			List<PathEntity> paths=null;
			int[] pages=parsePageLimit();
			if(pages==null){
				Message.printConsole("Giới hạn về phân trang không đúng");
				return;
			}
			if(pages[1] > 0 && pages[0] <= pages[1] || pages[1] == 0){
				index=pages[0]-1;
			}
			do{
				if("0".equals(setting.getPage())){
					paths=getPaths(setting.getPath());
				}
				if(pages[1] <= 0 || index < pages[1]){
					index++;
				}
				if(paths!=null){
					for(PathEntity pEtt:paths){
						try{
							downloadDirectPage(pEtt);
						}catch(Exception e){
							Message.printConsole("Có lỗi :"+e.getMessage());
							continue;
						}
					}
				}
				paths=getPaths(nextPage(setting.getPath()));
				
			}while(paths !=null && paths.size() > 0);
			Message.printConsole("***********DONE********");
		}catch(Exception e){
			throw e;
		}
	}
}
