package co.megadodo.mackycheese.framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	protected int frames = 0;
	protected ArrayList<Character> keysDown = new ArrayList<Character>();
	
	public int getFrames() {
		return frames;
	}
	
	public void close() throws Exception
	{
		System.out.println("game closed");
	}

	public void addEntity(Entity ent) {
		if(ent == null) {
			throw new NullPointerException("Cannot add a <null> entity");
		}
		entities.add(ent);
		
	}
	
	public ArrayList<Entity> getAllEntsWithName(String name) {
		ArrayList<Entity> ents = new ArrayList<Entity>();
		for(Entity a : entities) {
			if(a.getName() == name) {
				ents.add(a);
			}
		}
		return ents;
	}
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Entity getEntByName(String name) {
		for(int count = 0; count < entities.size(); count++) {
			if(entities.get(count).getName().equals(name)) return entities.get(count);
		}
		return null;
	}
	
	public static int getGameWidth() {
		return GameSettings.gameW;
	}
	
	public static int getGameHeight() {
		return GameSettings.gameH;
	}
	
	
	public static int getWindowWidth() {
		return GameSettings.windowW;
	}
	
	public static int getWindowHeight() {
		return GameSettings.windowH;
	}
	
	public void update() {
		this.frames++;
		for(int count = 0; count < entities.size(); count++) {
			entities.get(count).update();
			
		}
		if(entities.size() > 1) {
			SpaceManager.doCollisions(this, entities);
		}	
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, GameSettings.gameW, GameSettings.gameH);
		for(int count = 0; count < entities.size(); count++) {
			entities.get(count).draw(g2d);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if(!this.isFocusOwner()) this.requestFocus();
		
		update();
		g2d.clearRect(0, 0, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
		draw(g2d);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		for(Entity ent : this.entities) ent.keyPressed(e);
		
		keysDown.add(e.getKeyChar());
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {

		for(Entity ent : this.entities) ent.keyReleased(e);
		
		keysDown.remove((Character)e.getKeyChar());
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Entity ent : this.entities) ent.mouseDragged(e);
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Entity ent : this.entities) ent.mouseMoved(e);
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		for(Entity ent : this.entities) ent.mouseClicked(e);
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Entity ent : this.entities) ent.mouseReleased(e);
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
