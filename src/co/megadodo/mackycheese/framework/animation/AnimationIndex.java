package co.megadodo.mackycheese.framework.animation;

import java.util.ArrayList;

// TODO use later
public class AnimationIndex {
	
	protected ArrayList<Integer> indexes;

	public AnimationIndex(ArrayList<Integer> _indexes) {
		// TODO Auto-generated constructor stub
		this.indexes = _indexes;
	}
	
	public ArrayList<Integer> getIndexes() {
		return indexes; // "by reference" so people can change it
	}

}
