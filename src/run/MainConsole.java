package run;

import java.awt.EventQueue;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import setting.Cli;
import setting.MainSetting;
import base.DownloadAbstract;
import base.DownloadFactory;
import common.Message;

public class MainConsole {
	public static void main(String[] args) {
		MainSetting setting=new MainSetting();
		Options options = Cli.createOptions();
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine line = parser.parse(options, args);
			if(Cli.haveOpt(line, "h")){
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("h", options);
				return;
			}
			if(Cli.haveOpt(line, "g")){
				runGui();
				return;
			}
			setting.setPath(Cli.retNull(line, "u"));
			setting.setFolder(Cli.retNull(line, "f"));
			setting.setDirect(Cli.haveOpt(line, "d"));
			setting.setPage(Cli.retNull(line, "p"));
			setting.setType(Cli.retNull(line, "t"));
			run(setting);
			
		}catch(NullPointerException e){
			Message.printConsole("Tham số truyền vào không đúng: "+e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			Message.printConsole("Tham số truyền vào không đúng: "+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Message.printConsole("Đã có lỗi xảy ra: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void run(MainSetting setting) throws Exception {
		try{
			DownloadAbstract download =DownloadFactory.getDownloadManager("Earth");
			download.setSetting(setting);
			download.run();
		}catch(Exception e){
			throw e;
			
		}
		
	}
	private static void runGui(){
		EventQueue.invokeLater(new Runnable() {
			Main window=null;
			public void run() {
				try {
					window= new Main();
					window.setFrameVisable(true);
				} catch (Exception e) {
					if(window !=null)
					Message.showMessage(window.getFrmDownload(), "Đã có lỗi xảy ra: "+e.getMessage());
					throw e;
				}
			}
		});
	}
	
}
