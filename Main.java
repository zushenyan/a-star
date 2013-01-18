package aStarAlgorithm;

import javax.swing.*;

import aStarAlgorithm.PathFinder.Node;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public class Main implements MouseListener, MouseMotionListener, KeyListener{
	private JFrame m_frame;
	private Canvas m_canvas;
	private MapOperator m_op;
	
	public Main(){
		this.m_canvas = new Canvas();
		this.m_canvas.setSize(
				MapOperator.SQUARE_WIDTH * MapOperator.WIDTH,
				MapOperator.SQUARE_WIDTH * MapOperator.HEIGHT);
		this.m_canvas.addMouseListener(this);
		this.m_canvas.addMouseMotionListener(this);
		this.m_canvas.addKeyListener(this);
		this.m_canvas.setIgnoreRepaint(true);
		
		this.m_frame = new JFrame("A-Star Algorithm Demonstration");
		this.m_frame.getContentPane().add(this.m_canvas);
		this.m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.m_frame.pack();
		this.m_frame.setResizable(false);
		this.m_frame.setIgnoreRepaint(true);
		this.m_frame.setVisible(true);
		
		this.m_canvas.createBufferStrategy(2);
		this.m_canvas.requestFocus();
		this.m_op = new MapOperator(this.m_canvas);
	}
	
	public void run(){
		while(true){
			this.m_op.draw();
			try {
				//do not use all system resource
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		this.m_op.keyboardEntering(e);
	}
	public void keyReleased(KeyEvent e) {	
	}
	public void keyTyped(KeyEvent e) {
	}
	public void mouseClicked(MouseEvent e) {		
	}
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		this.m_op.placeTile(e);
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		this.m_op.placeTile(e);
	}
	public void mouseMoved(MouseEvent e) {
	}
	
	public static void main(String args[]){
		new Main().run();
	}
}
