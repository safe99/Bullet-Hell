import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.awt.Color;

public class BossShip extends Ship {

    private int xPos;
    private int yPos;
    private int R = 255, G = 255, B = 255;
    int [] ayye = new int[2];
    private int reload = 250;
    private boolean alive = false;
    private int hp = 1000;
    private int timer;
    private int right, left, up, down = 0;

    public BossShip(int x, int y) {
		super(x,y);
        xPos = x;
        yPos = y;
    }

    public void paint(Graphics g) {
        if (hp <= 600){
			R=255;
			G=128;
			B=0;
		}
		if (hp <= 300){
			R=228;
			G=77;
			B=77;
		}
        Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(R,G,B));
        g2d.fillOval(xPos, yPos, 500, 500);
        g2d.setColor(new Color(0,0,0));
        g2d.fillOval(xPos+50, yPos+50, 400, 400);

    }

    public void move() {
		yPos++;
    }

	public void blowup(Graphics g){
		Graphics g2d = (Graphics2D) g;
		g2d.setColor(new Color(255,255,255));
		g2d.fillOval(xPos, yPos, 30, 30);
		g2d.setColor(new Color(0,0,0));
		g2d.fillOval(xPos, yPos, 30, 30);
	}

    public Rectangle getBorder() {
        return new Rectangle(xPos, yPos, 500, 500);
    }

	public boolean getAlive(){
		return alive;
	}

	public int getHp(){
		return hp;
	}

	public void setAlive(boolean a){
		alive = a;
	}

	public void setHp(int h){
		hp = h;
	}

	public int getTimer(){
		return timer;
	}

	public void setTimer(int t){
		timer = t;
	}

	public int getY(){
		return yPos;
	}

}

