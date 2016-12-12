package co.megadodo.mackycheese.framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DW {

	private DW() {}
	private static boolean init = false;
	
	private static void checkInit() {
		if(!init) {
			init = true;
			DebugWindow.reinit();
		}
	}
	
	/**
	 * Logs time in the format mm:ss
	 * @param prefix
	 * @param suffix
	 */
	public static void logTime(String prefix, String suffix) {
		checkInit();
		DateFormat df = new SimpleDateFormat("mm:ss");
		Date dateobj = new Date();
		log(prefix + df.format(dateobj) + suffix);
	}
	
	
	/**
	 * Logs time in the format minute:second:millis
	 * @param prefix
	 * @param suffix
	 */
	public static void logDetailedTime(String prefix, String suffix) {
		checkInit();
		DateFormat df = new SimpleDateFormat("mm:ss");
		Date dateobj = new Date();
		log(prefix + df.format(dateobj) + ":" + System.currentTimeMillis() + suffix);
	}
	
	public static void time(String prefix, String suffix) {
		checkInit();
		logTime(prefix, suffix);
	}
	
	public static void dTime(String prefix, String suffix) {
		checkInit();
		logDetailedTime(prefix, suffix);
	}
	
	public static void log(String s) {
		checkInit();
		DebugWindow.log(s);
	}
	
	public static String prefix() {
		checkInit();
		return DebugWindow.getLogPrefix();
	}
	
	public static String suffix() {
		checkInit();
		return DebugWindow.getLogSuffix();
	}
	
	public static void setPrefix(String s) {
		checkInit();
		DebugWindow.setLogPrefix(s);
	}
	
	public static void setSuffix(String s) {
		checkInit();
		DebugWindow.setLogSuffix(s);
	}
	
	public static void reinit() {
		checkInit();
	}
	
	

}
