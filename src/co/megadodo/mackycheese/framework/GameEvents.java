package co.megadodo.mackycheese.framework;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface GameEvents {
	public default void mouseMoved(MouseEvent me) {}
	public default void mouseClicked(MouseEvent me) {}
	public default void mouseDragged(MouseEvent me) {}
	public default void mouseReleased(MouseEvent me) {}
	
	public default void keyPressed(KeyEvent ke) {}
	public default void keyReleased(KeyEvent ke) {}
	
}
