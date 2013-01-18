package aStarAlgorithm;

import java.awt.Point;
import java.util.ArrayList;

public class MapTemp {
//	private ArrayList m_objectsOnMap;
	private TileObjectAbstract m_map[][];
	private int m_width;
	private int m_height;
	
//	public Map(){
//		this.m_objectsOnMap = new ArrayList();
//	}
	
	public MapTemp(int width, int height){
		this.initMap(width, height);
	}
	
//	public void initMap(){
//		this.m_objectsOnMap.clear();
//	}
	
	public void initMap(int width, int height){
		this.setWidth(width);
		this.setHeight(height);
		this.m_map = new TileObjectAbstract[this.getHeight()][this.getWidth()];
		
		for(int y = 0; y < this.getHeight(); y++){
			for(int x = 0; x < this.getWidth(); x++){
				this.m_map[y][x] = new TileAssemble.Empty();
			}
		}
	}
	
	public void setMap(TileObjectAbstract newMap[][]){
		this.m_map = newMap;
		this.setWidth(newMap[0].length);
		this.setHeight(newMap.length);
	}
	
	public void setTile(int x, int y, TileObjectAbstract mta){
		this.m_map[y][x] = mta;
	}
	
	public boolean contains(String className) throws ClassNotFoundException{
		for(int y = 0; y < this.getHeight(); y++){
			for(int x = 0; x < this.getWidth(); x++){
				if(Class.forName(className).isInstance(this.getTile(x, y))){
					return true;
				}
			}
		}
		return false;
	}
	
	public TileObjectAbstract getTile(int x, int y){
		return this.m_map[y][x];
	}
	
	public Point getTile(String className) throws ClassNotFoundException{
		Point p = null;
		for(int y = 0; y < this.getHeight(); y++){
			for(int x = 0; x < this.getWidth(); x++){
				if(Class.forName(className).isInstance(this.getTile(x, y))){
					return p = new Point(x, y);
				}
			}
		}
		return p;
	}
	
	public TileObjectAbstract[][] getMap(){
		return this.m_map;
	}
	
	protected void setWidth(int width){
		this.m_width = width;
	}
	
	protected void setHeight(int height){
		this.m_height = height;
	}
	
	public int getWidth(){
		return this.m_width;
	}
	
	public int getHeight(){
		return this.m_height;
	}

}
