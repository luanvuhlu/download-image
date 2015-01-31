package downloadmanager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.Message;
import base.DownloadAbstract;
import entity.PathEntity;


public class Earth extends DownloadAbstract {
	public Earth() {
		super();
	}
	@Override
	public List<String> getImageUrls(String path) throws Exception{
		List<String> imgs=new ArrayList<String>();
		try{
			Document doc = Jsoup.connect(path).get();
			 Elements imgTags = doc.select("img");
			 for(Element imgTag:imgTags){
				 if("".equals(imgTag.attr("onload")) || !"NcodeImageResizer.createOn(this);".equals(imgTag.attr("onload"))){
					 continue;
				 }
				 Message.printConsole("Lấy được url: "+imgTag.attr("src"));
				 imgs.add(imgTag.attr("src"));
			 }
		}catch(IOException e){
			throw e;
		}
		return imgs;
	}
	@Override
	protected PathEntity getPathDetail(String path) {
		String replaceStr=" - lauxanh phim sex online | anh sex viet nam | hinh sexviet, phim cap 3  nguoi lon";
		try{
		Document doc = Jsoup.connect(path).get();
		 Elements tags = doc.select("title");
		 return new PathEntity( path, tags.get(0).html().replace(replaceStr, "")); 
		}catch(Exception e){
			return new PathEntity( path,"New folder");
		}
		 
	}
	@Override
	public List<PathEntity> getPaths(String rootPath) throws Exception {
		Message.printConsole("Đang lấy thông tin từ: "+rootPath);
		if(rootPath==null || "".equals(rootPath)){
			return null;
		}
		String root="http://thiendia.com/diendan/";
		List<PathEntity> paths=new ArrayList<PathEntity>();
		PathEntity ett=null;
		try{
			 Document doc = Jsoup.connect(rootPath).get();
			 Elements aTags = doc.select("a");
			 for(Element aTag:aTags){
				 if("".equals(aTag.attr("id")) || !aTag.attr("id").startsWith("thread_title_")){
					 continue;
				 }
				 ett=new PathEntity(root+aTag.attr("href"), aTag.html());
				 paths.add(ett);
			 }
			}catch(IOException e){
				System.out.println("Kết thúc download với index: "+this.index);
				return null;
			}
			catch(Exception e){
				throw e;
			}
		return paths;
	}
	protected String nextPage(String rootPath){
		return rootPath+"&page="+index;
	}

}
