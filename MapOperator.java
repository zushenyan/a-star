package aStarAlgorithm;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import aStarAlgorithm.PathFinder.Node;

public class MapOperator {
	public static final int WIDTH = 15;
	public static final int HEIGHT = 15;
	public static final int SQUARE_WIDTH = 40;
	
	public static final int MODE_START = 1;
	public static final int MODE_GOAL = 2;
	public static final int MODE_BLOCK = 3;
	
	private static final String TIPS[] = new String[]{
		"1 - place Start",
		"2 - place Goal",
		"3 - place Block",
		"R - reset the map",
		"P - toggle displaying path",
		"Mouse Right Button - clear block",
		"Enter - start finding goal"
	};
	
	private boolean m_displayTip = false;
	private boolean m_displayPath = true;
	private int m_placeMode = MapOperator.MODE_BLOCK;
	
	private Map2D m_map;
	private PathFinder m_finder;
	private LinkedList m_path;
	
	private Canvas m_canvas;
	
	public MapOperator(Canvas canvas){
		this.m_map = new Map2D(MapOperator.WIDTH, MapOperator.HEIGHT);
		this.m_finder = new PathFinder();
		this.m_canvas = canvas;
	}
	
	public void draw(){
		BufferStrategy bs = this.m_canvas.getBufferStrategy();
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		
		this.drawBackground(g2);
		this.drawMap(g2);
		this.drawPath(g2);
		this.drawGrid(g2);
		this.drawTip(g2);
		this.drawDirections(g2);
		
		g2.dispose();
		if(!bs.contentsLost()){
			bs.show();
		}
	}
	
	private void drawBackground(Graphics2D g2){
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.SQUARE_WIDTH * this.WIDTH, this.SQUARE_WIDTH * this.HEIGHT);
	}
	
	private void drawMap(Graphics2D g2){
		for(int y = 0; y < this.m_map.getHeight(); y++){
			for(int x = 0; x < this.m_map.getWidth(); x++){
				this.m_map.getTile(x, y).displayMyself(g2, x, y, this.SQUARE_WIDTH);
			}
		}
	}
	
	private void drawPath(Graphics2D g2){
		if(this.m_path != null && this.m_displayPath){
			g2.setColor(Color.BLUE);
			for(int index = 0; index < this.m_path.size(); index++){
				PathFinder.Node node = (Node) this.m_path.get(index);
				g2.fillOval(
						node.getLocation().x * this.SQUARE_WIDTH, 
						node.getLocation().y * this.SQUARE_WIDTH, 
						this.SQUARE_WIDTH, this.SQUARE_WIDTH);
			}
		}
	}
	
	private void drawGrid(Graphics2D g2){
		g2.setColor(Color.LIGHT_GRAY);
		for(int x = 0; x < this.m_map.getHeight(); x++){
			for(int y = 0; y < this.m_map.getWidth(); y++){
				g2.drawRect(x * this.SQUARE_WIDTH, y * this.SQUARE_WIDTH, this.SQUARE_WIDTH, this.SQUARE_WIDTH);
			}
		}
	}
	
	private void drawTip(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.drawString("Press space for showing or hiding directions", 5, 20);
	}
	
	private void drawDirections(Graphics2D g2){
		if(this.m_displayTip){
			int x = this.m_canvas.getWidth() / 8;
			int y = this.m_canvas.getHeight() / 8;
			int width = (int) (this.m_canvas.getWidth() / 1.32);
			int height = (int) (this.m_canvas.getHeight() / 1.32);
			int size = 20;
			g2.setColor(Color.WHITE);
			g2.fillRect(x, y, width, height);
			g2.setColor(Color.BLACK);
			g2.drawRect(x, y, width, height);
			
			g2.setFont(new Font("Serial", Font.PLAIN, size));
			
			for(int index = 0; index < this.TIPS.length; index++){
				Color color = this.m_placeMode == (index + 1) ? Color.ORANGE : Color.BLACK;
				
				if(MapOperator.TIPS[index].contains("path")){
					color = this.m_displayPath ? Color.ORANGE : Color.BLACK;
				}
				
				g2.setColor(color);
				g2.drawString(this.TIPS[index], x + 15, (index + 1) * size * 2 + y);
			}
			
			g2.setColor(Color.BLACK);
			g2.drawString("Program by zushen-yan 2009/7 ^_^", x + 15, (MapOperator.TIPS.length + 4) * size * 2 + y);
		}
	}
	
	public void placeTile(MouseEvent e){
		int indexX = e.getX() / this.SQUARE_WIDTH;
		int indexY = e.getY() / this.SQUARE_WIDTH;
		
		try{
			if(e.getButton() == MouseEvent.BUTTON3){
				this.m_map.setTile(indexX, indexY, new TileAssemble.Empty());
			}
			else if(e.getButton() == MouseEvent.BUTTON1){
				if(this.m_placeMode == MapOperator.MODE_GOAL &&
						!this.m_map.contains(TileAssemble.Goal.class.getName())){
					this.m_map.setTile(indexX, indexY, new TileAssemble.Goal());
				}
				else if(this.m_placeMode == MapOperator.MODE_START &&
						!this.m_map.contains(TileAssemble.Start.class.getName())){
					this.m_map.setTile(indexX, indexY, new TileAssemble.Start());
				}
				else if(this.m_placeMode == MapOperator.MODE_BLOCK){
					this.m_map.setTile(indexX, indexY, new TileAssemble.Block());
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException ex){
			//do nothing
		} 
		catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(this.m_canvas, ex.getMessage());
		}
	}
	
	public void keyboardEntering(KeyEvent e){
		try{
			if(e.getKeyCode() == e.VK_SPACE){
				this.m_displayTip = !this.m_displayTip;
			}
			else if(e.getKeyCode() == e.VK_ENTER){
				this.m_path = this.m_finder.findPath(this.m_map);
				if(this.m_path == null){
					JOptionPane.showMessageDialog(this.m_canvas, "Path not found!");
				}
			}
			else if(e.getKeyCode() == e.VK_R){
				this.m_map.initMap(this.WIDTH, this.HEIGHT);
				this.m_path = null;
			}
			else if(e.getKeyCode() == e.VK_P){
				this.m_displayPath = !this.m_displayPath;
			}
			else if(e.getKeyCode() == e.VK_ESCAPE){
				System.exit(0);
			}
			else if(e.getKeyCode() == e.VK_1){
				this.m_placeMode = MapOperator.MODE_START;
			}
			else if(e.getKeyCode() == e.VK_2){
				this.m_placeMode = MapOperator.MODE_GOAL;
			}
			else if(e.getKeyCode() == e.VK_3){
				this.m_placeMode = MapOperator.MODE_BLOCK;
			}
		}
		catch(NullPointerException ex){
			JOptionPane.showMessageDialog(this.m_canvas, "place one START tile and one GOAL tile!");
		}
		catch(ClassNotFoundException ex){
			JOptionPane.showConfirmDialog(this.m_canvas, ex.getMessage());
		}
	}
}
