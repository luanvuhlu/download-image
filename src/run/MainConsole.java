package run;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import setting.MainSetting;
import base.DownloadAbstract;
import base.DownloadFactory;
import common.Message;

public class MainConsole {
	public static void main(String[] args) {
		MainSetting setting=new MainSetting();
		Options options = MainConsole.createOptions();
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine line = parser.parse(options, args);
			if (line.hasOption('h')) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("h", options);
				return;
			}

			if (line.hasOption("p")) {

				String path = line.getOptionValue("p");
				if (path == null || "".equals(path.trim())) {
					System.out.println("Path is invalid !");
					return;
				}
				setting.setPath(path);
			}
			
			
		}catch(Exception e){
			Message.printConsole("Đã có lỗi xảy ra: "+e.getMessage());
		}
	}
	public static Options createOptions() {
		Options options = new Options();

		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("Help");
		Option help = OptionBuilder.create("h");
		options.addOption(help);

		OptionBuilder.withArgName("Path");
		OptionBuilder
				.withDescription("Path");
		OptionBuilder.hasArgs();
		Option ipDb = OptionBuilder.create("p");
		options.addOption(ipDb);

		OptionBuilder.withArgName("Folder");
		OptionBuilder
				.withDescription("Folder");
		OptionBuilder.hasArgs();
		Option portDb = OptionBuilder.create("f");
		options.addOption(portDb);

		OptionBuilder.withArgName("type");
		OptionBuilder.hasOptionalArgs(1);
		OptionBuilder
				.withDescription("Update specify object .\nObjects:e Earth");
		Option objectIndex = OptionBuilder.create("t");
		options.addOption(objectIndex);

		OptionBuilder.withArgName("n");
		OptionBuilder
				.withDescription("Page");
		OptionBuilder.hasArgs();
		Option usrn = OptionBuilder.create("n");
		options.addOption(usrn);
		
		OptionBuilder.withArgName("boolean");
		OptionBuilder
				.withDescription("Download direct");
		OptionBuilder.hasArgs();
		Option pswd = OptionBuilder.create("d");
		options.addOption(pswd);
		
		
		return options;
	}
	private void run(MainSetting setting) throws Exception {
		try{
//			MainSetting setting= getSetting();
//			if(setting==null){
//				return;
//			}
			setting.setDirect(false);
			setting.setPath("http://thiendia.com/diendan/forumdisplay.php?f=87");
			setting.setFolder("C:/Users/luanvu/Pictures/New folder");
			
			DownloadAbstract download =DownloadFactory.getDownloadManager("Earth");
			download.setSetting(setting);
			download.run();
			
		}catch(Exception e){
			throw e;
			
		}
		
	}
	
}
