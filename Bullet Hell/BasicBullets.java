import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.awt.Color;

public class BasicBullets extends Bullets{

    private int xPos;
    private int yPos;
    private int d;
    private boolean act = true;

    public BasicBullets(int x, int y, int s, int d) {
		super(x,y,s, d, 10);
		xPos = x;
		yPos = y;
		this.d = d;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(255,0,0));
		g2d.fillRect(xPos,yPos,20,20);
    }

    public void move() {
		switch(d){
			case 0: xPos++;
					break;
			case 1: yPos++;
					break;
			case 2: xPos--;
					break;
		}
    }

	public boolean getAct(){
		return act;
	}

	public void setAct(boolean a){
		act=a;
	}

	public int getX(){
		return xPos;
	}

	public int getY(){
		return yPos;
	}

}

