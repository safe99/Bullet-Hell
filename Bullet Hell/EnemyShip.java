import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.awt.Color;

public class EnemyShip extends Ship {

    private int xPos;
    private int yPos;
    private int R;
    private int G;
    private int B;
    int [] ayye = new int[2];
    private int reload = 250;
    private boolean alive = true;
    private boolean turn = false;
    private int hp = 100;
    private int right, left, up, down = 0;
    private int type;

    public EnemyShip(int x, int y, int c) {
		super(x,y);
        xPos = x;
        yPos = y;
        type = c;
		switch(c){
			case 0: R=255;
					G=51;
					B=184;
					break;
			case 1: R=255;
					G=255;
					B=0;
					break;
		}
    }

    public void paint(Graphics g) {
        if (hp <= 60){
			R=255;
			G=128;
			B=0;
		}
		if (hp <= 30){
			R=228;
			G=77;
			B=77;
		}

        Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(R,G,B));
        g2d.fillOval(xPos, yPos, 30, 30);
        g2d.setColor(new Color(0,0,0));
        g2d.fillOval(xPos+5, yPos+5, 20, 20);

    }

    public void move() {
		switch(type){
			case 0: if (xPos < 10){
					  xPos++;
					  break;
				    }

					if (yPos > 639)
					  turn = true;
					else if (yPos < 5)
					  turn = false;
					if (turn)
					  yPos--;
					else if (turn == false)
					  yPos++;
					break;

			case 1:	if (yPos < 100){
					  yPos++;
					  break;
				    }

					if (xPos > 967)
		  			  turn = true;
					else if (xPos < 10)
					  turn = false;
					if (turn)
					  xPos--;
					else if (turn == false)
					  xPos++;

					break;
		}
    }

	public int[] shoot(){
		ayye[0]=xPos+5;
		ayye[1]=yPos+10;
		return ayye;
	}

	public void blowup(Graphics g){
		Graphics g2d = (Graphics2D) g;
		g2d.setColor(new Color(255,255,255));
		g2d.fillOval(xPos, yPos, 30, 30);
		g2d.setColor(new Color(0,0,0));
		g2d.fillOval(xPos, yPos, 30, 30);
	}

    public Rectangle getBorder() {
        return new Rectangle(xPos, yPos, 30, 30);
    }

    public int getReload(){
		return reload;
	}

	public boolean getAlive(){
		return alive;
	}

	public int getHp(){
		return hp;
	}

	public int getType(){
		return type;
	}

	public void setReload(int r){
		reload = r;
	}

	public void setAlive(boolean a){
		alive = a;
	}

	public void setHp(int h){
		hp = h;
	}

}

