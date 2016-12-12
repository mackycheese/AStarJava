package co.megadodo.mackycheese.framework.animation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import co.megadodo.mackycheese.framework.Game;

public class SpriteManager {
	
	protected ArrayList<Animation> anims = new ArrayList<Animation>();
	
	protected int curAnim = -1;

	public SpriteManager() {
		
	}

	public void draw(Game g, Graphics2D g2d, int x, int y, int w, int h) {
		if(curAnim != -1) {
			anims.get(curAnim).draw(g, g2d, x, y, w, h);
		}
	}
	
	public void update(int gameTicks) {
		if(curAnim != -1) {
			anims.get(curAnim).update(gameTicks);
		}
	}
	
	public String getCurAnim() {
		if(curAnim == -1) return null;
		return anims.get(curAnim).getName();
	}
	
	public void startAnim(String animName) {
		boolean found = false;
		int count = 0;
		while(count < anims.size() && !found) {
			if(anims.get(count).getName().equals(animName)) {
				found = true;
			} else {
				count++;
			}
		}
		if(!found && !(animName.equals("-1"))) {
			throw new RuntimeException("Invalid animation name " + animName + ". Cannot run specified animation.");
		} else if(animName.equals("-1")) {
			curAnim = -1;
		} else {
			curAnim = count;
		}
	}
	
	public void addAnimation(Animation anim) {
		this.addAnim(anim);
	}
	
	public void addAnim(Animation anim) {
		this.anims.add(anim);
	}
}
