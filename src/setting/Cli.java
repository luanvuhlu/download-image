package setting;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class Cli {
	public static String retNull(CommandLine line, String optName) throws NullPointerException{
		String str = line.getOptionValue(optName);
		if (str == null || "".equals(str.trim())) {
			throw new NullPointerException();
		}
		return str;
	}
	public static boolean haveOpt(CommandLine line, String optName){
		String str = line.getOptionValue(optName);
		if (str == null) {
			return false;
		}
		return true;
	}
	public static String retNum(CommandLine line, String optName){
		String str = line.getOptionValue(optName);
		if (str == null || "".equals(str.trim())) {
			throw new NullPointerException();
		}
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			throw e;
		}
		return str;
	}
	public static Options createOptions() {
		Options options = new Options();

		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("Help");
		Option help = OptionBuilder.create("h");
		options.addOption(help);

		OptionBuilder.withArgName("Url");
		OptionBuilder
				.withDescription("Url");
		OptionBuilder.hasArgs();
		Option ipDb = OptionBuilder.create("u");
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

		OptionBuilder.withArgName("number");
		OptionBuilder
				.withDescription("Page");
		OptionBuilder.hasArgs();
		Option usrn = OptionBuilder.create("p");
		options.addOption(usrn);
		
		OptionBuilder.withArgName("boolean");
		OptionBuilder
				.withDescription("Download direct");
		OptionBuilder.hasArg(false);
		Option pswd = OptionBuilder.create("d");
		options.addOption(pswd);
		
		OptionBuilder.withArgName("boolean");
		OptionBuilder
				.withDescription("Gui");
		OptionBuilder.hasArg(false);
		Option g = OptionBuilder.create("g");
		options.addOption(g);
		
		
		return options;
	}
}
