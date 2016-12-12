package co.megadodo.mackycheese.framework;

import java.util.UUID;

import co.megadodo.mackycheese.framework.animation.SpriteManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Entity implements GameEvents {
	
	protected int frameNum = 0;
	
	protected EntityType type = EntityType.NO_COLLISIONS;
	
	protected int posX;
	protected int posY;
	
	protected int sizeX;
	protected int sizeY;
	
	protected int dirX;
	protected int dirY;
	
	protected int maxDirX;
	protected int maxDirY;
	
	protected UUID uuid;
	
	protected SpriteManager spriteManager = null;
	
	protected Game game;
	
	protected String name;
	
	protected ArrayList<Entity> collidedEnts = new ArrayList<Entity>();
	
	protected ArrayList<Character> keysDown = new ArrayList<Character>();
	
	public Entity(Game g, int posX, int posY, int sizeX, int sizeY, String name) {
		this.posX = posX;
		this.posY = posY;
		
		this.maxDirX = -1;
		this.maxDirY = -1;
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.uuid = UUID.randomUUID();
		
		this.name = name;
		
		this.game = g;
	}
	
	public boolean canMoveX(int movement) {
		for(int count = 0; count < this.game.entities.size(); count++) {
			this.posX += movement;
			Entity ent = this.game.entities.get(count);
			if(
					!(ent.equals(this)) 
					&& 
					this.getBounds().intersects(ent.getBounds())
					) {
				this.posX -= movement;
				return false;
			}
			this.posX -= movement;
		}
		return true;
	}
	
	public boolean canMoveY(int movement) {
		for(int count = 0; count < this.game.entities.size(); count++) {
			this.posY += movement;
			Entity ent = this.game.entities.get(count);
			if(
					!(ent.equals(this)) 
					&& 
					this.getBounds().intersects(ent.getBounds())
					) {
				this.posY -= movement;
				return false;
			}
			this.posY -= movement;
		}
		return true;
	}
	
	public SpriteManager getSpriteManager() {
		return spriteManager;
	}

	public void setSpriteManager(SpriteManager spriteManager) {
		this.spriteManager = spriteManager;
	}

	public String getName() {
		return name;
	}
	
	public int getFrameNum() {
		return frameNum;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getDirX() {
		return dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public ArrayList<Character> getKeysDown() {
		return keysDown;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	@Deprecated
	public Entity(Game g, int posX, int posY, String name) {
		this(g, posX, posY, 0, 0, name);
	}
	
	@Deprecated
	public Entity(Game g, int posX, int posY) {
		this(g, posX, posY, "");
	}
	
	public boolean collidesWith(Entity other) {
		/*
		 * if (X1+W1<X2 or X2+W2<X1 or Y1+H1<Y2 or Y2+H2<Y1):
    Intersection = Empty
else:
    Intersection = Not Empty
		 */
		return other.getBounds().intersects(this.getBounds());
	}
	
	public void checkVel() {
		if(this.maxDirX != -1) {
			int xSign = Utils.getSign(dirX);
			if(!Utils.insideRangeInclusive(-maxDirX, maxDirX, dirX)) {
				dirX = maxDirX * xSign;
			}
		}
		if(this.maxDirY != -1) {
			int xSign = Utils.getSign(dirY);
			if(!Utils.insideRangeInclusive(-maxDirY, maxDirY, dirY)) {
				dirY = maxDirY * xSign;
			}
		}
	}
	
	public void update() {
		collidedEnts = new ArrayList<Entity>();
		this.posX += this.dirX;
		this.posY += this.dirY;
		checkVel();
		if(this.posX < 0) this.posX = 0;
		if(this.posX+this.sizeX >= Game.getGameWidth()) this.posX = Game.getGameWidth()-this.sizeX;
		if(this.posY < 0) this.posY = 0;
		if(this.posY+this.sizeY >= Game.getGameHeight()) this.posY = Game.getGameHeight()-this.sizeY;
		frameNum++;
	}
	
	protected void draw(Image img, Graphics2D g2d) {
//		double scaleX = sizeX / img.getWidth(null);
//		double scaleY = sizeY / img.getHeight(null);
		
		g2d.drawImage(img, posX, posY, null);
	}
	
	public void draw(Graphics2D g2d) {
		if(GameSettings.debug) {
			g2d.setColor(Color.GREEN);
			g2d.drawRect(posX, posY, sizeX, sizeY);
		}
	}
	
	protected Image getImage(String name) {
		return Toolkit.getDefaultToolkit().getImage(name);
	}
	
	
	
	public ArrayList<Point> getPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(int x = posX; x < posX + sizeX; x++) {
			for(int y = posY; y < posY + sizeY; y++) {
				points.add(new Point(x, y));
			}
		}
		
		return points;
	}
	
	public boolean hasNotCollidedWith(Entity other)
	{
		return !(collidedEnts.contains(other));
	}
	
	public void collide(Entity other, Axis ax) {
		collidedEnts.add(other);
	}
	public Rectangle2D.Float getBounds() {
		Rectangle2D.Float bounds = new Rectangle2D.Float(posX, posY, sizeX, sizeY);
		return bounds;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Entity) {
			return uuid.equals(((Entity)o).uuid);
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == 'w') System.out.println("FORWARD");
		keysDown.add(e.getKeyChar());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keysDown.remove((Character)e.getKeyChar());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void kill() {
		game.entities.remove(this);
	}


}
