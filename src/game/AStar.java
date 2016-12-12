package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import co.megadodo.mackycheese.framework.DW;
import co.megadodo.mackycheese.framework.Utils;
import game.MyGame.Cell;

public class AStar {
	public static enum Status {
		OPEN,CLOSED,UNKNOWN;
	}
	public static class pos {
		int x=-1,y=-1,f=-1,g=-1,h=-1,parentX=-1,parentY=-1;
		boolean visited = false;
		Status stat = Status.UNKNOWN;
		pos() {  this(0,0,-1,10,-1); }
		pos(int x, int y) {this(x,y,-1,10,-1); };
		pos(int x, int y, int g) {this(x,y,-1,g,-1); };
		pos(int x, int y, int f, int g) {this(x,y,f,g,-1); };
		pos(int x, int y, int f, int g, int h) {this.x = x;this.y=y;this.f=f;this.g=g;this.h=h;}
	}

	private AStar() {
	}
	
	public static int heuristic(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1-x2) + Math.abs(y1-y2)) * 10;
	}
	
	public static pos[][] grid;

	public static ArrayList<XY> findPath(Cell[][] cellGrid, int startX, int startY, int endX, int endY) {
		int w = cellGrid.length;
		int h = cellGrid[0].length;
		int sx=startX,sy=startY,ex=endX,ey=endY;
		ArrayList<pos> opened = new ArrayList<pos>();
		ArrayList<pos> closed = new ArrayList<pos>();
		grid = new pos[w][h];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				grid[i][j] = new pos(i,j,0,0,0);
			}
		}
		grid[sx][sy].f = 0;
		grid[sx][sy].g = 0;
		grid[sx][sy].h = 0;
		opened.add(grid[sx][sy]); // add the start
		
		boolean addedEnd = false;
		while(!opened.isEmpty() && !addedEnd) {
			int lowestFInd = 0;
			for(int i = 0; i < opened.size(); i++) {
				if(opened.get(i).f < opened.get(lowestFInd).f) {
					lowestFInd = i;
				}
			}
			int x = opened.get(lowestFInd).x;
			int y = opened.get(lowestFInd).y;
			pos p = grid[x][y];
			System.out.println(x + " " + y);
			opened.remove(lowestFInd);
			closed.add(p);
			grid[x][y].stat = Status.CLOSED;
			if(x == ex && y == ey) {
				addedEnd = true;
				break;
			}
			
			ArrayList<pos> potential = new ArrayList<pos>();
//			potential.add(new pos(x+1,y+1,14));
			potential.add(new pos(x+1,y  ,10));
//			potential.add(new pos(x+1,y-1,14));

			potential.add(new pos(x  ,y+1,10));
			potential.add(new pos(x  ,y-1,10));

//			potential.add(new pos(x-1,y+1,14));
			potential.add(new pos(x-1,y  ,10));
//			potential.add(new pos(x-1,y-1,14));
			
			for(pos potentialPos : potential) {
				if(potentialPos.x >= 0
			    && potentialPos.x < w
			    && potentialPos.y >= 0
			    && potentialPos.y < h
			    ) {
					if(cellGrid[potentialPos.x][potentialPos.y] != Cell.BARRIER
			        && grid[potentialPos.x][potentialPos.y].stat == Status.UNKNOWN)
					{
						int ppx = potentialPos.x;
						int ppy = potentialPos.y;
						grid[ppx][ppy].g += potentialPos.g;
						grid[ppx][ppy].h = heuristic(ppx,ppy,ex,ey);
						grid[ppx][ppy].f = grid[ppx][ppy].g + grid[ppx][ppy].h;
						grid[ppx][ppy].parentX = x;
						grid[ppx][ppy].parentY = y;
						grid[ppx][ppy].stat = Status.OPEN;
						opened.add(grid[ppx][ppy]);
					}
				}
			}
			
		}
		if(addedEnd)
		{
			ArrayList<XY> path = new ArrayList<XY>();
			int curX = ex;
			int curY = ey;
			while(curX != sx || curY != sy) {
				int nextCurX = grid[curX][curY].parentX;
				int nextCurY = grid[curX][curY].parentY;
				curX = nextCurX;
				curY = nextCurY;
				path.add(new XY(curX,curY));
			}
			return path;
		}
		return new ArrayList<XY>();
	}
	

}
