package co.megadodo.mackycheese.framework;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameRunner {
	
	private String title;
	public static JFrame gameWindow;
	
	public GameRunner(String title) {
		this.title = title;
	}
	
	public JFrame run(Game gamePanel, JFrame frame)
	{
		
		
		
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.requestFocus();
		gamePanel.requestFocus();
		

		frame.setResizable(true);
		frame.setSize(Game.getWindowWidth(), Game.getWindowHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JFrame w = frame;
//		 w.setCursor( w.getToolkit().createCustomCursor( new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ), new java.awt.Point(), null ) );
		
		gamePanel.addMouseListener(gamePanel);
		gamePanel.addMouseMotionListener(gamePanel);
		gamePanel.addKeyListener(gamePanel);
		gamePanel.requestFocus();
		
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                try {
					gamePanel.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
                e.getWindow().dispose();
            }
        });
		
		gameWindow = frame;

		Timer t = new Timer(GameSettings.millisPerFrame, e-> {
			
			gamePanel.repaint();
		});
		t.start();
		
		
		return frame;
	}
	
	public JFrame run(Game gamePanel) {
		JFrame frame = new JFrame(this.title);
		return run(gamePanel, frame);
	}
}
