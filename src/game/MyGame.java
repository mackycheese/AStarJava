package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import co.megadodo.mackycheese.framework.Game;
import co.megadodo.mackycheese.framework.GameRunner;
import co.megadodo.mackycheese.framework.Utils;

@SuppressWarnings("serial")
public class MyGame extends Game {
	public static final int GRID_SIZE = 64; // 16
	public static final int GRID_W = /*22*/22; // 90
	public static final int GRID_H = /*13*/13; // 53
	boolean moveEndPlaceMode = false;
	boolean moveStartPlaceMode = false;
//	boolean doDiagonal = true;
	
	enum dir {
		L,R,U,D;
		XY nextXY(XY cur) {
			switch(this) {
				case L: cur.setX(cur.getX()-1); break;
				case R: cur.setX(cur.getX()+1); break;
				case U: cur.setY(cur.getY()-1); break;
				case D: cur.setY(cur.getY()+1); break;
			}
			if(cur.getX() < 0) cur.setX(0);
			if(cur.getY() < 0) cur.setY(0);
			
			if(cur.getX() >=GRID_W) cur.setX(GRID_W-1);
			if(cur.getY() >=GRID_H) cur.setY(GRID_H-1);
			return cur;
		}
		static dir random() {
			return dir.values()[Utils.randInt(0,3)];
		}
	}
	void generateMaze() {
		int n = 5;
		generateMaze(0,0,GRID_W-1,GRID_H-1, 1, n);
//		generateMaze(endX,endY,GRID_W-1,GRID_H-1,1,n);
//		generateMaze(GRID_W/2,GRID_H/2,GRID_W-1,GRID_H-1,1,n);
		grid[startX][startY] = Cell.START;
		grid[endX][endY] = Cell.END;
	}
	
	void resetGrid() {
		for(int i = 0; i < GRID_W; i++) {
			for(int j = 0; j < GRID_H;j++) {
				grid[i][j] = Cell.EMPTY;
			}
		}
		grid[startX][startY] = Cell.START;
		grid[endX][endY] = Cell.END;
		AStar.grid = null;
	}
	
	void generateMaze(int x, int y, int w, int h, int counter, int maxCounter) {

		
		int bottomX = x+w;
		int bottomY = y+h;
		int middleX = (x+bottomX)/2;
		int middleY = (y+bottomY)/2;

		int n = Utils.randInt(x, bottomX);
		for(int i = x; i < bottomX; i++) {
			if(i != n && i != bottomX-n) {
				grid[i][y] = grid[i][bottomY] = Cell.BARRIER;
			}
		}
		n = Utils.randInt(y, bottomY);
		for(int i = y; i < bottomY; i++) {
			if(i != n && i != bottomY-n) {
				grid[x][i] = grid[bottomX][i] = Cell.BARRIER;
			}
		}
		if(counter < maxCounter) {
			counter++;
			generateMaze(x,y,w/2,h/2,counter,maxCounter);
			generateMaze(x,middleY,w/2,h/2,counter,maxCounter);
			generateMaze(middleX,y,w/2,h/2,counter,maxCounter);
			generateMaze(middleX,middleY,w/2,h/2,counter,maxCounter);
		}
	}
	boolean isValidPos(int x, int y) {
		return (x >= 0 && y >= 0 && x < GRID_W && y < GRID_H);
	}
	ArrayList<XY> getNeighbors(int x, int y) {
		ArrayList<XY> list = new ArrayList<XY>();
		if(isValidPos(x-1,y)) list.add(new XY(x-1,y));
		if(isValidPos(x+1,y)) list.add(new XY(x+1,y));
		if(isValidPos(x,y-1)) list.add(new XY(x,y-1));
		if(isValidPos(x,y+1)) list.add(new XY(x,y+1));
		
		return list;
	}
	
	int getNumOfWalls(XY xy) {
		int x = xy.getX();
		int y = xy.getY();
		ArrayList<XY> list = getNeighbors(x,y);
		int n = 0;
		for(XY c : list) {
			if(grid[c.getX()][c.getY()] == Cell.BARRIER) {
				n++;
			}
		}
		return n;
	}
	public static enum Cell {
		START,
		END,
		BARRIER,
		EMPTY;
	}
	class pos {
		public int x=0,y=0,f=0,g=0,h=0,parentX=0,parentY=0;
	}
	Cell[][] grid;
	ArrayList<pos> path = new ArrayList<pos>();
	int startX;
	int startY;
	int endX;
	int endY;

	public static void main(String[] args) {
		GameRunner runner = new GameRunner("Game");
		
		runner.run(new MyGame());
	}
	
	
	public MyGame() {
		startX = 1;
		startY = 1;
		
		endX = GRID_W-2;
		endY = GRID_H-2;
		
		grid = new Cell[GRID_W][GRID_H];
		resetGrid();
		Utils.seedRandom((long) (Math.random() * Long.MAX_VALUE));
		generateObstructions(GRID_W/2,GRID_H/2,1);
//		generateMaze();
	}
	void generateObstructions(int x, int y, int count) {
		int range = 1;
		int n = 4;
		int g = 4;
		
		int moveX = Utils.randInt(-range, range);
		int moveY = Utils.randInt(-range, range);
		if(x < 0 || y < 0 || x >= GRID_W || y >= GRID_H || (x==startX&&y==startY) || (x==endX&&y==endY)) return;
		grid[x][y] = Cell.BARRIER;
		if(count < n) {
			for(int i = 0; i < g; i++) {
				generateObstructions(Utils.randInt(1, GRID_W-1), Utils.randInt(1,  GRID_H-1), count+1);
			}
		}
	}
	boolean is1Down = false;
	boolean is2Down = false;
	
	public void update() {
		super.update();
	}

	public void drawPath(Graphics2D g2d) {
		int pathIndex = 1;
		for(pos p : path) {

			int pathLength = path.size();
			float c = pathIndex/((float)(pathLength));
			float n = (float)0;
//			g2d.setColor(new Color(0,1,1,c));
			g2d.setColor(Color.CYAN);
			g2d.fillRect(p.x*GRID_SIZE, p.y*GRID_SIZE, GRID_SIZE, GRID_SIZE);
			pathIndex++;
		}
	}

	
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if(moveEndPlaceMode) {

			for(int a = 0; a < grid.length; a++) {
				for(int b = 0; b < grid[0].length; b++) {
					if(grid[a][b] == Cell.END) {
						grid[a][b] = Cell.EMPTY;
					}
				}
			}
			grid[x][y] = Cell.END;
			if(endX != x || endY != y) {
				endX = x;
				endY = y;
			}
		} else if(moveStartPlaceMode) {

			for(int a = 0; a < grid.length; a++) {
				for(int b = 0; b < grid[0].length; b++) {
					if(grid[a][b] == Cell.START) {
						grid[a][b] = Cell.EMPTY;
					}
				}
			}
			grid[x][y] = Cell.START;
			if(startX != x || startY != y) {
				startX = x;
				startY = y;
			}
		}
		
		for(int x = 0; x < GRID_W; x++) {
			for(int y = 0; y < GRID_H; y++) {
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x*GRID_SIZE, y*GRID_SIZE, GRID_SIZE, GRID_SIZE);
				Color c = Color.GRAY;
				switch(grid[x][y]) {
					case START: c = Color.GREEN; break;
					case END: c = Color.RED; break;
					case BARRIER: c = Color.BLACK; break;
					case EMPTY: c = Color.GRAY;
				}
				g2d.setColor(c);
				g2d.fillRect(x*GRID_SIZE, y*GRID_SIZE, GRID_SIZE, GRID_SIZE);
				g2d.setColor(Color.WHITE);
				int offset = -20;
				g2d.drawString(x + ","+y, x*GRID_SIZE-offset, y*GRID_SIZE-offset);
			}
			/*
			 * 
			 */
		}
		drawPath(g2d);
//		drawText(g2d);
		drawParents(g2d);
		drawFGH(g2d);
		
	}
	
	public static void drawFGH(Graphics2D g2d) {
		if(AStar.grid == null) return;
		for(int x = 0; x < GRID_W; x++) {
			for(int y = 0; y < GRID_H; y++) {
				int f = AStar.grid[x][y].f;
				int g = AStar.grid[x][y].g;
				int h = AStar.grid[x][y].h;
				g2d.setColor(Color.BLACK);
				if(g>0)g2d.drawString("" + g, x*GRID_SIZE,y*GRID_SIZE);
				if(h>0)g2d.drawString("" + h, x*GRID_SIZE + GRID_SIZE-30, y*GRID_SIZE);
				if(f>0)g2d.drawString("" + f, x*GRID_SIZE, y*GRID_SIZE-50);
			}
		}
	}
	
	public static Shape createArrowShape(Point fromPt, Point toPt) {
	    Polygon arrowPolygon = new Polygon();
	    arrowPolygon.addPoint(-6,1);
	    arrowPolygon.addPoint(3,1);
	    arrowPolygon.addPoint(3,3);
	    arrowPolygon.addPoint(6,0);
	    arrowPolygon.addPoint(3,-3);
	    arrowPolygon.addPoint(3,-1);
	    arrowPolygon.addPoint(-6,-1);


	    Point midPoint = midpoint(fromPt, toPt);

	    double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

	    AffineTransform transform = new AffineTransform();
	    transform.translate(midPoint.x, midPoint.y);
	    double ptDistance = fromPt.distance(toPt);
	    double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
	    transform.scale(scale, scale);
	    transform.rotate(rotate);

	    return transform.createTransformedShape(arrowPolygon);
	}

	private static Point midpoint(Point p1, Point p2) {
	    return new Point((int)((p1.x + p2.x)/2.0), 
	                     (int)((p1.y + p2.y)/2.0));
	}
	
	void drawParents(Graphics2D g2d) {
		if(AStar.grid == null)return;
		for(int x = 0; x < AStar.grid.length; x++) {
			for(int y = 0; y < AStar.grid[x].length; y++) {
				int fromX = x*GRID_SIZE+GRID_SIZE/2;
				int fromY = y*GRID_SIZE+GRID_SIZE/2;
				int toX = AStar.grid[x][y].parentX*GRID_SIZE+GRID_SIZE/2;
				int toY = AStar.grid[x][y].parentY*GRID_SIZE+GRID_SIZE/2;
				if(fromX<=0||fromY<=0||toX<=0||toY<=0) continue;
				Shape shape = createArrowShape(new Point(toX, toY),new Point(fromX,fromY));
				g2d.draw(shape);
			}
		}
	}
	
	void drawText(Graphics2D g2d) {
		String lines[] = new String[] {
//				"Diagonal: " + doDiagonal,
				"Controls:",
				"ENTER : Calculate the path",
				"1     : Enter/exit a mode where all squares hovered over become barriers",
				"2     : Enter/exit a mode where all squares hovered over become empty",
				"q     : Enter/exit a mode to move the end square to where the mouse is",
				"e     : Enter/exit a mode to move the start square to where the mouse is",
				"SPACE : Generate a new maze",
				"C     : Clear path"
		};
		g2d.setColor(Color.RED);
		Font font = new Font("Monospaced", Font.PLAIN, 10);
		g2d.setFont(font);
		for(int i = 0; i < lines.length; i++) {
			String s = lines[i];
			int x = 0;
			int y = i * g2d.getFontMetrics().getHeight();
			g2d.drawString(s, x, y);
		}
	}

	public void mouseDragged(MouseEvent evt)
	{
	}
	int x = 0, y = 0;
	public void mouseMoved(MouseEvent evt) {
		x = evt.getX()/GRID_SIZE;
		y = evt.getY()/GRID_SIZE;
		if(is1Down) {
			if(grid[x][y] != Cell.START && grid[x][y] != Cell.END) grid[x][y] = Cell.BARRIER;
//			recalculate();
		}
		if(is2Down) {
			if(grid[x][y] != Cell.START && grid[x][y] != Cell.END) grid[x][y] = Cell.EMPTY;
//			recalculate();
		}

	}
	
	public void mouseReleased(MouseEvent evt) {
		int x = evt.getX()/GRID_SIZE;
		int y = evt.getY()/GRID_SIZE;
		
		if(grid[x][y] == Cell.EMPTY) {
			grid[x][y] = Cell.BARRIER;
		} else if(grid[x][y] == Cell.BARRIER) {
			grid[x][y] = Cell.EMPTY;
		}
	}
	
	
	public void recalculate() {
		ArrayList<XY> list = AStar.findPath(grid, startX, startY, endX, endY);
		this.path.clear();
		for(XY xy : list) {
			pos myPos = new pos();
			myPos.x = xy.getX();
			myPos.y = xy.getY();
			int x = myPos.x;
			int y = myPos.y;
			myPos.f = AStar.grid[x][y].f;
			myPos.g = AStar.grid[x][y].g;
			myPos.h = AStar.grid[x][y].h;
			this.path.add(myPos);
		}
	}
	
	public void keyPressed(KeyEvent evt) {

		if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		recalculate();
		else if(evt.getKeyChar() == 'q' && !moveStartPlaceMode){
			moveEndPlaceMode = !moveEndPlaceMode;
		} else if(evt.getKeyChar() == '1') {
			is1Down = !is1Down;
		} else if(evt.getKeyChar() == '2') {
			is2Down = !is2Down;
		} else if(evt.getKeyChar() == 'e' && !moveEndPlaceMode) {
			moveStartPlaceMode = !moveStartPlaceMode;
		} else if(evt.getKeyChar() == ' ') {
			resetGrid();
			Utils.seedRandom((long) (Math.random() * Long.MAX_VALUE));
			generateObstructions(GRID_W/2,GRID_H/2,1);
//			generateMaze();
			
		} else if(evt.getKeyChar() == 'c') {
			path.clear();
			
		} else if(evt.getKeyChar() == 'd') {
//			doDiagonal = !doDiagonal;
		} else if(evt.getKeyChar() == 'C') {
			for(int i = 0; i < GRID_W; i++) {
				for(int j = 0; j < GRID_H; j++) {
					grid[i][j] = Cell.EMPTY;
				}
			}
			grid[startX][startY] = Cell.START;
			grid[endX][endY] = Cell.END;
		}
	}
	
	void print(String s)
	{
		System.out.println(s);
	}
}
