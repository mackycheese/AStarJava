package co.megadodo.mackycheese.framework.animation;

import java.awt.Graphics2D;

import co.megadodo.mackycheese.framework.Utils;

import co.megadodo.mackycheese.framework.Game;

public class SheetAnimation extends Animation {

	protected NamedSheet sheet;
	protected int spriteWidth;	// width of one sprite in pixels
	protected int spriteHeight;	// height of one sprite in pixels
	protected int spritesInRow;
	protected int spritesInCol;
	
	public SheetAnimation(int spriteWidth, int spriteHeight, int spritesInRow, int spritesInCol, NamedSheet _sheet, String _name, boolean _loop, int _updatesForFrame) {
		super(_name, _loop, _updatesForFrame);
		// TODO Auto-generated constructor stub
		sheet = new NamedSheet(_sheet.getImg(), _sheet.getName());
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.spritesInRow = spritesInRow;
		this.spritesInCol = spritesInCol;
	}

	@Override
	public void drawFrame(Game game, int frameIndex, Graphics2D g2d, int x, int y, int w, int h) {
		
		// 12 and 8x8
		// spriteX = 12 % 8
		// spriteY = 12 / 8
		// spriteX = 4
		// spriteY = 1
		
		// 3 and 3x2
		// spriteX = 3 % 3
		// spriteY = 3 / 3
		int spriteX = frameIndex % spritesInRow;
		int spriteY = frameIndex / spritesInRow;
		Utils.drawSectionOfImage(g2d, sheet.getImg(), x, y, w*scale, h*scale, spriteX * spriteWidth, spriteY * spriteHeight, spriteWidth, spriteHeight, game);
	}

}
