package aStarAlgorithm;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class TileObjectAbstract{
	private boolean m_isObstacle;
	
	public TileObjectAbstract(boolean isObstacle){
		this.setObstacle(isObstacle);
	}
	
	public void setObstacle(boolean isObstacle){
		this.m_isObstacle = isObstacle;
	}
	
	public boolean isObstacle(){
		return this.m_isObstacle;
	}
	
	public abstract void displayMyself(Graphics2D g2, int x, int y, int square);
}
