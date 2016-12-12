package co.megadodo.mackycheese.framework;

import java.util.ArrayList;

public class EntityType {
	
	public static EntityType NO_COLLISIONS = new EntityType("", new ArrayList<String>());
	
	protected String type;
	protected ArrayList<String> collidesWith;
	
	protected EntityType(String _type, ArrayList<String> _collidesWith) {
		this.type = _type;
		
		this.collidesWith = new ArrayList<String>();
		for(int count = 0; count < _collidesWith.size(); count++) {
			this.collidesWith.add(_collidesWith.get(count));
		}
	}
	
	protected EntityType() {
		this(NO_COLLISIONS.getType(), NO_COLLISIONS.getCollidesWith());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getCollidesWith() {
		return collidesWith;
	}

	public void setCollidesWith(ArrayList<String> collidesWith) {
		this.collidesWith = collidesWith;
	}
	
	public void addCollidableType(String str) {
		if(!(collidesWith.contains(str))) {
			collidesWith.add(str);
		}
	}
	
	public void removeCollidableType(String str) {
		collidesWith.remove(str);
	}
	
	public static EntityType createEntityType(String type, String... collidesWith) {
		ArrayList<String> asList = new ArrayList<String>();
		for(int count = 0; count < collidesWith.length; count++) {
			asList.add(collidesWith[count]);
		}
		return createEntityType(type, asList);
	}
	
	public static EntityType createEntityType(String type, ArrayList<String> collidesWith) {
		return new EntityType(type, collidesWith);
	}
	
	public boolean canCollideWith(EntityType other) {
		return this.collidesWith.contains(other.getType());
	}
	
	public boolean canOtherCollideWith(EntityType other) {
		return other.canCollideWith(this);
	}
	
}
