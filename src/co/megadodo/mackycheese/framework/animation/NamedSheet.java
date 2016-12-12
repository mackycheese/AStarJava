package co.megadodo.mackycheese.framework.animation;

import java.awt.Image;
import java.awt.Toolkit;

public class NamedSheet {

	protected Image img;
	protected String name;
	
	public NamedSheet(String imgName, String _name) {
		// TODO Auto-generated constructor stub
		img = Toolkit.getDefaultToolkit().getImage(imgName);
		name = _name;
	}
	
	public NamedSheet(Image img, String _name) {
		this.img = img;
		this.name = _name;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
