package co.megadodo.mackycheese.framework;

public class EntityBorder extends Entity {

	public EntityBorder(Game g, int posX, int posY, int sizeX, int sizeY) {
		super(g, posX, posY, sizeX, sizeY, "border");
		// TODO Auto-generated constructor stub
	}
	
	public void collideWith(Entity other, Axis ax) {
		if(!(other instanceof EntityBorder)) {
			if(this.posX < other.posX) {
				other.dirX = 0;
				other.dirY = 0;
			}
		}
	}

}
