package co.megadodo.mackycheese.framework;

import java.util.ArrayList;

public class SpaceManagerOld {
	private SpaceManagerOld(){}
	public static Axis getAxisForEntities(Entity aEnt, Entity bEnt) {
		return null;
	}
	
	public static void collideList(ArrayList<Entity> entities) {
		if(entities.size() > 1) {
			if(entities.size() == 2) {
				Axis ax = getAxisForEntities(entities.get(0), entities.get(1));
				Entity a = entities.get(0);
				Entity b = entities.get(1);
//				entities.get(0).collide(entities.get(1), ax);
//				entities.get(1).collide(entities.get(0), ax);
				if(a.hasNotCollidedWith(b)) {
					a.collide(b, ax);
					b.collide(a, ax);
				}
				
			} else {
				for(int a = 0; a < entities.size(); a++) {
					for(int b = 0; b < entities.size(); b++) {
						Entity aEnt = entities.get(a);
						Entity bEnt = entities.get(b);
						ArrayList<Entity> collList = new ArrayList<Entity>();
						collList.add(aEnt);
						collList.add(bEnt);
						collideList(collList);
					}
				}
			}
		}
	}
	
	public static Space doCollisions(Game g, ArrayList<Entity> entities) {
		Space space = new Space();
		if(GameSettings.debug) {
			EntityBorder borderTop = new EntityBorder(g, 0, 0, Game.getGameWidth(), 1);
			EntityBorder borderBottom = new EntityBorder(g, 0, Game.getGameHeight()-1, Game.getGameWidth(), 1);
			EntityBorder borderLeft = new EntityBorder(g, 0, 0, 1, Game.getGameHeight());
			EntityBorder borderRight = new EntityBorder(g, Game.getGameWidth()-1, 0, 1, Game.getGameHeight());		
			entities.add(borderTop);
			entities.add(borderBottom);
			entities.add(borderLeft);
			entities.add(borderRight);
		}
		 
		space = initializeSpace(entities);
		
		for(int x = 0; x < Game.getGameWidth(); x++) {
			for(int y = 0; y < Game.getGameHeight(); y++) {
				if(space.get(x).get(y).size() > 1) {
					collideList(space.get(x).get(y));
				}
			}
		}
		
		return space;
	}
	public static Space initializeSpace(ArrayList<Entity> entities) {
		Space space = new Space();
		
		for(int x = 0; x < Game.getGameWidth(); x++) {
			ArrayList<ArrayList<Entity>> row = new ArrayList<>();
			for(int y = 0; y < Game.getGameHeight(); y++) {
				row.add(new ArrayList<Entity>());
			}
			space.add(row);
		}
		
		for(int count = 0; count < entities.size(); count++) {
			Entity ent = entities.get(count);
			//ArrayList<Point> parts = ent.getPoints();
			space.get(ent.getPosX()).get(ent.getPosY()).add(ent);
			//for(int i = 0; i < parts.size(); i++)
			//{
			//	Point p = parts.get(i);
			//	space.get(p.getX()).get(p.getY()).add(ent);
			//}
		}
		
		return space;
	}
}
