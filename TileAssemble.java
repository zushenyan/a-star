package aStarAlgorithm;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class TileAssemble {
	public static class Empty extends TileObjectAbstract{
		public Empty(){
			super(false);
		}
		public void displayMyself(Graphics2D g2, int x, int y, int square) {
			//do nothing
		}
	}
	
	public static class Block extends TileObjectAbstract{
		public Block(){
			super(true);
		}
		public void displayMyself(Graphics2D g2, int x, int y, int square) {
			g2.setColor(Color.GRAY);
			g2.fillRect(x * square, y * square, square, square);
		}
	}
	
	public static class Start extends TileObjectAbstract{
		public Start(){
			super(false);
		}
		public void displayMyself(Graphics2D g2, int x, int y, int square) {
			g2.setColor(Color.GREEN);
			g2.fillRect(x * square, y * square, square, square);
		}		
	}
	
	public static class Goal extends TileObjectAbstract{
		public Goal(){
			super(false);
		}
		public void displayMyself(Graphics2D g2, int x, int y, int square) {
			g2.setColor(Color.RED);
			g2.fillRect(x * square, y * square, square, square);
		}
	}
	
	public static class Path extends TileObjectAbstract{
		public Path(){
			super(false);
		}
		public void displayMyself(Graphics2D g2, int x, int y, int square) {
			g2.setColor(Color.BLUE);
			g2.fillOval(x * square, y * square, square, square);
		}
	}
}
