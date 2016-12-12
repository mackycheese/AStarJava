package co.megadodo.mackycheese.framework;

import java.awt.Font;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DebugWindow {

	private static DebugWindow dw;
	
	private JFrame window;
	private JLabel lbText;
	private String text;
	
	private static String logPrefix = "-";
	private static String logSuffix = "-";
	
	private DebugWindow() {
		window = new JFrame("Game Debug");
		window.setSize(300, 300);
		
		window.setAlwaysOnTop(true);
		
		text = "";
		lbText = new JLabel(convertToMultiline(text));
		lbText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		window.add(lbText);
		
		window.setVisible(true);
	}
	public static void reinit() {
		dw = new DebugWindow();
	}
	
	public static void log(String s) {
		dw.text += logPrefix + " ";
		dw.text += s;
		dw.text += " " + logSuffix;
		dw.text += "\n";
		dw.lbText.setText(convertToMultiline(dw.text));
	}
	
	private static String convertToMultiline(String orig)
	{
	    return "<html>" + orig.replaceAll("\n", "<br>");
	}
	
	public static String getLogPrefix() {
		return logPrefix;
	}
	
	public static String getLogSuffix() {
		return logSuffix;
	}
	
	public static void setLogPrefix(String s) {
		logPrefix = s;
	}
	
	public static void setLogSuffix(String s) {
		logSuffix = s;
	}
}
