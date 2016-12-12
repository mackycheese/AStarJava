

package co.megadodo.mackycheese.framework;

import java.util.ArrayList;


public class SpaceManager {
	private SpaceManager() {}
	
	public static void doCollisions(Game g, ArrayList<Entity> entities) {
//		EntityBorder borderTop = new EntityBorder(g, 0, 0, Game.getGameWidth(), 1);
//		EntityBorder borderBottom = new EntityBorder(g, 0, Game.getGameHeight()-1, Game.getGameWidth(), 1);
//		EntityBorder borderLeft = new EntityBorder(g, 0, 0, 1, Game.getGameHeight());
//		EntityBorder borderRight = new EntityBorder(g, Game.getGameWidth()-1, 0, 1, Game.getGameHeight());		
//		entities.add(borderTop);
//		entities.add(borderBottom);
//		entities.add(borderLeft);
//		entities.add(borderRight);
		for(int count = 0; count < entities.size(); count++) {
			for(int count2 = 0; count2 < entities.size(); count2++) {
				if(count != count2) { // if we are not colliding with myself
					Entity entA = entities.get(count);
					Entity entB = entities.get(count2);
					Axis ax = null;
					if(
							/*entA.hasNotCollidedWith(entB) && */
							entA.getBounds().intersects(entB.getBounds())) {
						if(entA.getType().canCollideWith(entB.getType())) {
							entA.collide(entB, ax);
						}
						if(entB.getType().canCollideWith(entA.getType())) {
							entB.collide(entA, ax);
						}
					}
				}
			}
		}
	}
}
