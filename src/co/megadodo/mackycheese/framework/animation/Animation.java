package co.megadodo.mackycheese.framework.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import co.megadodo.mackycheese.framework.Game;

public abstract class Animation {

	protected String name;
	protected boolean loop;
	protected ArrayList<Integer> frames = new ArrayList<Integer>();
	protected int curFrame = 0;
	protected int updatesForFrame = 1;
	protected boolean isDone = false;
	protected int scale = 1;
	
	public Animation(String _name, boolean _loop, int _updatesForFrame) {
		this.name = _name;
		this.loop = _loop;
		this.updatesForFrame = _updatesForFrame;
	}
	
	public int getScale() {
		return scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public ArrayList<Integer> getFrames() { return frames; }
	public void setFrames(int... _frames) {
		frames.clear();
		for(int i = 0; i < _frames.length; i++) {
			frames.add(_frames[i]);
		}
		this.curFrame = 0;
	}
	public void setFrames(ArrayList<Integer> _frames) {
		frames.clear();
		for(int i = 0; i < _frames.size(); i++){
			frames.add(_frames.get(i));
		}
		this.curFrame = 0;
	}
	
	public void setName(String _name) {
		this.name = _name;
	}
	
	public String getName() {
		return this.name;
	}

	public void draw(Game g, Graphics2D g2d, int x, int y, int w, int h) {
		this.drawFrame(g, frames.get(curFrame), g2d, x, y, w, h);
	}
	public void update(int gameFrames) {
		//if(gameFrames % updatesForFrame == 0)
		{
			if(gameFrames % updatesForFrame == 0)
			this.goToNextFrame();
		}
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void goToNextFrame() {
//		if(curFrame == frames.size()) {
//			curFrame = frames.get(0);
//		} else {
//			curFrame++;
//		}
		if(curFrame == frames.size()-1) {
			if(loop) {
				curFrame = 0;
			} else {
				curFrame = -1;
				isDone = true;
			}
		} else {
			curFrame++;
		}
	}

	public abstract void drawFrame(Game g, int frameIndex, Graphics2D g2d, int x, int y, int w, int h);
}
