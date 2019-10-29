import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.*;

public class Ship {

    private int xPos;
    private int yPos;
    private int right, left, up, down = 0;
    private boolean shooting;
    private int reload = 100;
    private int reloadCap = 99;
    private int [] ayye = new int[3];
    private int bSize=10;
    private int bDmg=10;

    private int sp = 3;
    private int sHealth;
    private int sReload;
    private int sDmg;
    private int sBulletSpeed;
    private int sSpeed;

    private int pointX;
    private int pointY;
    private int clickX;
    private int clickY;

    private int direction;
    private int diffX;
    private int diffY;

    private int hp = 30;
    private int timer = 0;
    private boolean invincible;
    private boolean alive = true;


    public Ship(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(29,229,222));
        g2d.fillOval(xPos, yPos, 30, 30);
        g2d.setColor(new Color(0,0,0));
        g2d.fillOval(xPos+5, yPos+5, 20, 20);

        PointerInfo a = MouseInfo.getPointerInfo();
        Point c = a.getLocation();
        pointX = (int)c.getX();
        pointY = (int)c.getY();

		diffX = Math.abs(pointX - xPos);
		diffY = Math.abs(pointY - yPos);

        if(diffX > diffY)
        {
          if (pointX > xPos+25)
          {
            pointX = xPos+25;
            direction = 1;
            pointY = yPos+12;
		  }
          if (pointX < xPos)
          {
            pointX = xPos;
            direction = 0;
            pointY = yPos+12;
		  }
		}

		else if (diffY > diffX)
		{
          if (pointY > yPos+25)
          {
            pointY = yPos+25;
            direction = 3;
			pointX = xPos+12;
		  }
          if (pointY < yPos)
          {
            pointY = yPos;
            direction = 2;
			pointX = xPos+12;
		  }
		}

		g2d.setColor(new Color(225,128,0));
        g2d.fillOval(pointX, pointY, 5, 5);

    }

    public void move() {

	    if (xPos < 1){
	      xPos += right;
	    }
	      else if (xPos > 968){
	        xPos += left;
	      }
	        else xPos += left + right;

		if (yPos < 1){
		  yPos += down;
	    }
		  else if (yPos > 640){
		    yPos += up;
		  }
		    else yPos += up + down;

    }

	public int[] shoot(){
		switch(direction){
			case 0: ayye[0] = xPos;
					ayye[1] = yPos+12;
					break;
			case 1: ayye[0] = xPos+25;
					ayye[1] = yPos+12;
					break;

			case 2: ayye[0] = xPos+12;
					ayye[1] = yPos;
					break;

			case 3: ayye[0] = xPos+12;
					ayye[1] = yPos+25;
					break;
		}

		ayye[2] = direction;
		return ayye;
	}

    public void keyPressed(KeyEvent e) {

        if ((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_S ))

            down = 1+sSpeed/2;

        if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D ))

            right = 1+sSpeed/2;

        if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_W ))

            up = -1-sSpeed/2;

        if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A ))

            left = -1-sSpeed/2;

    }

    public void keyReleased(KeyEvent e) {

        if ((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_S ))

            down = 0;

        if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D ))

            right = 0;

        if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_W ))

            up = 0;

        if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A ))

            left = 0;

    }

    public Rectangle getBorder() {
        return new Rectangle(xPos, yPos, 30, 30);
    }

	public void mousePressed(MouseEvent e){
		shooting = true;
	}

	public void mouseReleased(MouseEvent e){
		shooting = false;
	}

	public void mouseClicked(MouseEvent e){
        PointerInfo a = MouseInfo.getPointerInfo();
        Point c = a.getLocation();
        clickX = (int)c.getX();
        clickY = (int)c.getY();

        if ((clickX > 1234) && (clickX < 1256)){
			if ((clickY > 174) && (clickY < 186) && (sp > 0) && (sHealth < 7)){
				sp--;
				sHealth++;
				hp = hp + 10;
			}
			if ((clickY > 264) && (clickY < 276) && (sp > 0) && (sReload < 7)){
				sp--;
				sReload++;
				reloadCap-=5;
			}

			if ((clickY > 364) && (clickY < 376) && (sp > 0) && (sDmg < 7)){
				sp--;
				sDmg++;
				bDmg+=5;
				bSize+=2;
			}

			if ((clickY > 464) && (clickY < 476) && (sp > 0) && (sBulletSpeed < 7)){
				sp--;
				sBulletSpeed++;
			}

			if ((clickY > 564) && (clickY < 576) && (sp > 0) && (sSpeed < 7)){
				sp--;
				sSpeed++;
			}
		}

	}

	public boolean getShooting(){
		return shooting;
	}
    public int getReload(){
		return reload;
	}
	public void setReload(int r){
		reload = r;
	}

	public int getbSize(){
		return bSize;
	}
	public void setbSize(int b){
		bSize = b;
	}

	public int getHp(){
		return hp;
	}
	public void setHp(int h){
		hp = h;
	}

	public void setInvin(boolean i){
		invincible = i;
	}
	public boolean getInvin(){
		return invincible;
	}

	public int getTimer(){
		return timer;
	}
	public void setTimer(int t){
		timer = t;
	}

	public void setAlive(boolean a){
		alive = a;
	}

	public boolean getAlive(){
		return alive;
	}

	public int getSP(){
		return sp;
	}
	public void setSP(int s){
		sp = s;
	}
	public int getsHealth(){
		return sHealth;
	}
	public int getsReload(){
		return sReload;
	}
	public int getsDmg(){
		return sDmg;
	}
	public int getsBulletSpeed(){
		return sBulletSpeed;
	}
	public int getsSpeed(){
		return sSpeed;
	}
	public int getReloadCap(){
		return reloadCap;
	}
	public int getbDmg(){
		return bDmg;
	}

}

