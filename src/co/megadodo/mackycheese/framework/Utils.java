package co.megadodo.mackycheese.framework;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

public final class Utils {
	private Utils() {}
	
	private static boolean randSeeded = false;
	private static Random rand = null;
	
	public static void seedRandom(long seed) {
		rand = new Random(seed);
		randSeeded = true;
	}
	
	private static void error(String msg) {
		throw new RuntimeException(msg);
	}
	
	public static int getSign(int a) { return (int) Math.signum(a); }
	public static int getAbs(int a) { return Math.abs(a); }
	
	public static boolean insideRangeExclusive(int min, int max, int val) { return val > min && val < max; }
	public static boolean insideRangeInclusive(int min, int max, int val) { return val >= min && val <= max; }
	
	public static int randInt(int min, int max) {

		if(!randSeeded) error("Random not seeded");

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static double randDouble(double min, double max)
	{
		if(!randSeeded) error("Random not seeded");
		
		double randNum = rand.nextDouble();
		return randNum;
	}
	
	public static int constrain(int min, int max, int val) {
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
	
	public static void drawSectionOfImage(Graphics2D g2d, Image img, int dx, int dy, int dw, int dh, int sx, int sy, int sw, int sh, ImageObserver o) {
		g2d.drawImage(img, dx, dy, dx+dw, dy+dh, sx, sy, sx+sw, sy+sh, o);
	}
}
