package co.megadodo.mackycheese.framework.animation;

import java.awt.Graphics2D;

import co.megadodo.mackycheese.framework.Game;

public class ImagedAnimation extends Animation {

	protected String imgBaseName, replace;
	
	public ImagedAnimation(String _name, boolean _loop, int _updatesForFrame, String imgBaseName, String replace) {
		super(_name, _loop, _updatesForFrame);
		this.imgBaseName = imgBaseName;
		this.replace = replace;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFrame(Game g, int frameIndex, Graphics2D g2d, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		String imgName = imgBaseName.replace(replace, Integer.toString(frameIndex));
	}

}
