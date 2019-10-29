import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.awt.Color;

public class Bullets {

    private int xPos;
    private int yPos;
    private int size=10;
    private boolean active = true;
    private int direction;
    private int dmg = 10;


    public Bullets() {

        xPos = 0;
        yPos = 0;
        size = 0;
    }

    public Bullets(int x, int y, int s, int d, int dmg) {

        xPos = x;
        yPos = y;
        size = s;
        direction = d;
        this.dmg = dmg;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(50,230,128));
		g2d.fillRect(xPos,yPos,size,size);

    }

    public void move(int s) {
		switch(direction){
			case 0: xPos-=1+s/2;
					break;
			case 1: xPos+=1+s/2;
					break;
			case 2: yPos-=1+s/2;
					break;
			case 3: yPos+=1+s/2;
					break;
		}
    }

	public boolean getAct(){
		return active;
	}

	public void setAct(boolean a){
		active=a;
	}

	public void setDmg(int d){
		dmg = d;
	}

	public int getDmg(){
		return dmg;
	}

	public int getSize(){
		return size;
	}

	public int getX(){
		return xPos;
	}

	public int getY(){
		return yPos;
	}


}

