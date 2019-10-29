import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.awt.Color;

public class PowerUps {

	int x;
	int y;
	int score;

	public PowerUps(){

		x=(int) (Math.random()*540);
		y=(int) (Math.random()*260);
	}

	public void paint(Graphics g){

		Graphics g2d = (Graphics2D) g;
        g2d.setColor(new Color(238,70,70));
        g2d.fillOval(x, y, 20, 20);
        g2d.setColor(new Color(245,235,235));
        g2d.fillRect(x+1, y+5, 17, 9);

	}

	public void changeLocation(){
		x=(int) (Math.random()*540);
		y=(int) (Math.random()*260);
	}

	public Rectangle hitbox(){
		Rectangle r = new Rectangle(x,y,20,20);
		return (r);
	}

}