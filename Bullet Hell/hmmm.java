import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.util.ArrayList;

import java.awt.Color;

public class hmmm extends JPanel implements KeyListener, MouseListener{

	int score = 0;
	int wave = 6;

	BufferedImage image;
	BufferedImage empty;
	BufferedImage fill;
	BufferedImage add;
	BufferedImage heart;

    Ship player = new Ship(100, 100);
    BossShip boss = new BossShip(200,-1500);

	ArrayList<Bullets> bullets = new ArrayList<Bullets>();
	ArrayList<BasicBullets> ebullets = new ArrayList<BasicBullets>();
	ArrayList<EnemyShip> enemies = new ArrayList<EnemyShip>();

	int [] Bcords = new int[2];

    public hmmm() {

		enemies.add(new EnemyShip(1100, 800, 1));
		enemies.add(new EnemyShip(350, -50, 1));
		enemies.add(new EnemyShip(500, -50, 1));

        setFocusable(true);
        JFrame frame = new JFrame("hmmm");
        frame.setSize(1300, 700);
        frame.setResizable(false);

        frame.add(this);
        frame.setVisible(true);

        addKeyListener(this);
        addMouseListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            image = ImageIO.read(new File("spaceimage.jpg"));

        } catch (Exception e) {
        }

        while (true) {
			for (int i=enemies.size()-1; i>0; i--){
				if (enemies.get(i).getAlive() == false)
				  enemies.remove(i);
			}
			for (int i=bullets.size()-1; i>0; i--){
				if (bullets.get(i).getAct() == false)
				  bullets.remove(i);
			}
			for (int i=ebullets.size()-1; i>0; i--){
				if (ebullets.get(i).getAct() == false)
				  ebullets.remove(i);
			}

			if(enemies.size() == 1){
				if (wave < 7)
				{
				  wave++;
				  player.setSP(player.getSP()+2);
				  switch(wave){
				  	case 7: boss.setAlive(true);
				  			System.out.println("The Boss is alive");
				  			System.out.println(boss.getAlive());

					case 6: enemies.add(new EnemyShip(-2500, (int)(Math.random()*600), 0));
							enemies.add(new EnemyShip(-3000, (int)(Math.random()*600), 0));

					case 5: enemies.add(new EnemyShip((int)(Math.random()*900),-2000,1));
							enemies.add(new EnemyShip((int)(Math.random()*900),-1000,1));
							enemies.add(new EnemyShip(-1000, (int)(Math.random()*600), 0));
							enemies.add(new EnemyShip(-2000, (int)(Math.random()*600), 0));

					case 4: enemies.add(new EnemyShip(-1000, (int)(Math.random()*600), 0));
						    enemies.add(new EnemyShip((int)(Math.random()*900),-1000,1));

					case 3: enemies.add(new EnemyShip((int)(Math.random()*900),-100,1));
							enemies.add(new EnemyShip(-500, (int)(Math.random()*600), 0));

					case 2: enemies.add(new EnemyShip(-50, (int)(Math.random()*600), 0));
							enemies.add(new EnemyShip((int)(Math.random()*900),-50,1));

					case 1: enemies.add(new EnemyShip((int)(Math.random()*900),-50,1));
							enemies.add(new EnemyShip((int)(Math.random()*900),-50,1));
							break;
				  }
			  }
			}

            player.move();

			if (player.getInvin() == true){
			  player.setTimer(player.getTimer()+1);
			  if (player.getTimer() > 99){
			    player.setInvin(false);
			    player.setTimer(0);
			  }
			}

            for(EnemyShip e : enemies){
				if (e.getBorder().intersects(player.getBorder()))
					if(player.getInvin() == false){
                  	  player.setHp(player.getHp()-10);
                  	  player.setInvin(true);
                  	  if (player.getHp() <= 0)
                  	    player.setAlive(false);
					}

				if (e.getReload() == 250){
					Bcords = e.shoot();
					ebullets.add(new BasicBullets(Bcords[0],Bcords[1], 20, e.getType()));
					e.setReload(0);
				}
				else{
					e.setReload(e.getReload()+1);
					e.move();
				}
			}

			if (player.getShooting() == true){
				if (player.getReload() > player.getReloadCap()){
					Bcords = player.shoot();
					bullets.add(new Bullets(Bcords[0], Bcords[1], player.getbSize(), Bcords[2], player.getbDmg()));
					player.setReload(0);
				}
				player.setReload(player.getReload()+1);
			}
			else player.setReload(player.getReload()+1);

            for (BasicBullets bullet : ebullets){
				bullet.move();
	            Rectangle r = new Rectangle(bullet.getX(), bullet.getY(), 20, 20);

	            if (r.intersects(player.getBorder())){
					bullet.setAct(false);
					if(player.getInvin() == false){
                  	  player.setHp(player.getHp()-bullet.getDmg());
                  	  player.setInvin(true);
                  	  if (player.getHp() <= 0)
                  	    player.setAlive(false);
					}
				}

				if ((bullet.getX() < -10) && (bullet.getX() > 1010))
				  bullet.setAct(false);
				else if ((bullet.getY() < -10) && (bullet.getY() > 710))
				  bullet.setAct(false);
			}

            for (Bullets bullet : bullets){

				bullet.move(player.getsBulletSpeed());
	            Rectangle r = new Rectangle(bullet.getX(), bullet.getY(), bullet.getSize(), bullet.getSize());
				for (EnemyShip e: enemies){
	            	if (r.intersects(e.getBorder())){
					  bullet.setAct(false);
                  	  	e.setHp(e.getHp()-bullet.getDmg());
                  	  	if (e.getHp() <= 0)
                  	  	  e.setAlive(false);
				    }
				}
				if (r.intersects(boss.getBorder())){
				  bullet.setAct(false);
				  boss.setHp(boss.getHp()-bullet.getDmg());
				  if (boss.getHp() <= 0)
				    boss.setAlive(false);
				    System.out.println("false");
				}
				else boss.getHp();

				if ((bullet.getX() < -10) && (bullet.getX() > 1010))
				  bullet.setAct(false);
				else if ((bullet.getY() < -10) && (bullet.getY() > 710))
				  bullet.setAct(false);
			}

			for (Bullets bullet : bullets){
				for (BasicBullets ebullet : ebullets){
					Rectangle r = new Rectangle(bullet.getX(), bullet.getY(), bullet.getSize(), bullet.getSize());
					Rectangle er = new Rectangle(ebullet.getX(), ebullet.getY(), 20, 20);
					if (r.intersects(er)){
						bullet.setAct(false);
						ebullet.setAct(false);
					}
				}
			}

            if (boss.getAlive() == true)
            {
				if (boss.getTimer() >= 5){
					boss.move();
					boss.setTimer(0);
				}
             	else boss.setTimer(boss.getTimer()+1);

             	if (boss.getBorder().intersects(player.getBorder()))
             	  player.setHp(0);
             	System.out.println(boss.getY());
             	if (boss.getY() < 500)
             	  player.setHp(0);
			}

            repaint();

            try {

                Thread.sleep(5);

            } catch (Exception e) {
            }

        }

    }

    public static void main(String[] args) {
        hmmm game = new hmmm();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, null);
		if (player.getAlive() == true)
		{
        player.paint(g2d);
        for (BasicBullets bullet : ebullets){
			bullet.paint(g2d);
		}
        for (Bullets bullet : bullets){
			bullet.paint(g2d);
		}

        for (EnemyShip enemy : enemies){
			    if (enemy.getAlive())
			      enemy.paint(g2d);
			    else{
			      enemy.blowup(g2d);
			    }
		}

		if (boss.getAlive() == true)
		  boss.paint(g2d);
		else g2d.setColor(new Color(0,0,0));

        g2d.setColor(new Color(0,0,0));
        g2d.fillRect(1000,0,300,800);

        try{

			add = ImageIO.read(new File("AddUpgrade.png"));
			heart = ImageIO.read(new File("heart.png"));
			empty = ImageIO.read(new File("EmptyUpgrade.png"));
			fill = ImageIO.read(new File("FilledUpgrade.png"));

        } catch (Exception e) {
        }

        g2d.setColor(new Color(255,255,255));

        switch(player.getHp()){
			case 100: g2d.drawImage(heart, null, 1200, 30);
        	case  90: g2d.drawImage(heart, null, 1180, 30);
        	case  80: g2d.drawImage(heart, null, 1160, 30);
        	case  70: g2d.drawImage(heart, null, 1140, 30);
        	case  60: g2d.drawImage(heart, null, 1120, 30);
        	case  50: g2d.drawImage(heart, null, 1100, 30);
        	case  40: g2d.drawImage(heart, null, 1080, 30);
        	case  30: g2d.drawImage(heart, null, 1060, 30);
        	case  20: g2d.drawImage(heart, null, 1040, 30);
        	case  10: g2d.drawImage(heart, null, 1020, 30);
        			  break;
		}

        if (player.getSP() > 0)
          g2d.drawString("Skill Points Remaining: "+player.getSP(),1050,105);
        else
          g2d.drawString("Exp: ", 1050, 105);
        g2d.drawString("Stats",1125,70);
        g2d.drawString("Current Wave: "+(wave+1), 1100, 20);

        g2d.drawString("Health",1050,120);
        g2d.drawImage(empty, null, 1050, 140);
        g2d.drawImage(empty, null, 1075, 140);
        g2d.drawImage(empty, null, 1100, 140);
        g2d.drawImage(empty, null, 1125, 140);
        g2d.drawImage(empty, null, 1150, 140);
        g2d.drawImage(empty, null, 1175, 140);
        g2d.drawImage(empty, null, 1200, 140);
        g2d.drawImage(add, null, 1225, 140);

        switch(player.getsHealth()){
        	case 7: g2d.drawImage(fill, null, 1200, 140);
        	case 6: g2d.drawImage(fill, null, 1175, 140);
        	case 5: g2d.drawImage(fill, null, 1150, 140);
        	case 4: g2d.drawImage(fill, null, 1125, 140);
        	case 3: g2d.drawImage(fill, null, 1100, 140);
        	case 2: g2d.drawImage(fill, null, 1075, 140);
        	case 1: g2d.drawImage(fill, null, 1050, 140);
        			break;
		}

        g2d.drawString("Reload",1050,210);
        g2d.drawImage(empty, null, 1050, 230);
        g2d.drawImage(empty, null, 1075, 230);
        g2d.drawImage(empty, null, 1100, 230);
        g2d.drawImage(empty, null, 1125, 230);
        g2d.drawImage(empty, null, 1150, 230);
        g2d.drawImage(empty, null, 1175, 230);
        g2d.drawImage(empty, null, 1200, 230);
        g2d.drawImage(add, null, 1225, 230);

        switch(player.getsReload()){
        	case 7: g2d.drawImage(fill, null, 1200, 230);
        	case 6: g2d.drawImage(fill, null, 1175, 230);
        	case 5: g2d.drawImage(fill, null, 1150, 230);
        	case 4: g2d.drawImage(fill, null, 1125, 230);
        	case 3: g2d.drawImage(fill, null, 1100, 230);
        	case 2: g2d.drawImage(fill, null, 1075, 230);
        	case 1: g2d.drawImage(fill, null, 1050, 230);
        			break;
		}

        g2d.drawString("Damage",1050,310);
        g2d.drawImage(empty, null, 1050, 330);
        g2d.drawImage(empty, null, 1075, 330);
        g2d.drawImage(empty, null, 1100, 330);
        g2d.drawImage(empty, null, 1125, 330);
        g2d.drawImage(empty, null, 1150, 330);
        g2d.drawImage(empty, null, 1175, 330);
        g2d.drawImage(empty, null, 1200, 330);
        g2d.drawImage(add, null, 1225, 330);

        switch(player.getsDmg()){
        	case 7: g2d.drawImage(fill, null, 1200, 330);
        	case 6: g2d.drawImage(fill, null, 1175, 330);
        	case 5: g2d.drawImage(fill, null, 1150, 330);
        	case 4: g2d.drawImage(fill, null, 1125, 330);
        	case 3: g2d.drawImage(fill, null, 1100, 330);
        	case 2: g2d.drawImage(fill, null, 1075, 330);
        	case 1: g2d.drawImage(fill, null, 1050, 330);
        			break;
		}

        g2d.drawString("Bullet Speed",1050,410);
        g2d.drawImage(empty, null, 1050, 430);
        g2d.drawImage(empty, null, 1075, 430);
        g2d.drawImage(empty, null, 1100, 430);
        g2d.drawImage(empty, null, 1125, 430);
        g2d.drawImage(empty, null, 1150, 430);
        g2d.drawImage(empty, null, 1175, 430);
        g2d.drawImage(empty, null, 1200, 430);
        g2d.drawImage(add, null, 1225, 430);

        switch(player.getsBulletSpeed()){
        	case 7: g2d.drawImage(fill, null, 1200, 430);
        	case 6: g2d.drawImage(fill, null, 1175, 430);
        	case 5: g2d.drawImage(fill, null, 1150, 430);
        	case 4: g2d.drawImage(fill, null, 1125, 430);
        	case 3: g2d.drawImage(fill, null, 1100, 430);
        	case 2: g2d.drawImage(fill, null, 1075, 430);
        	case 1: g2d.drawImage(fill, null, 1050, 430);
        			break;
		}

        g2d.drawString("Speed",1050,510);
        g2d.drawImage(empty, null, 1050, 530);
        g2d.drawImage(empty, null, 1075, 530);
        g2d.drawImage(empty, null, 1100, 530);
        g2d.drawImage(empty, null, 1125, 530);
        g2d.drawImage(empty, null, 1150, 530);
        g2d.drawImage(empty, null, 1175, 530);
        g2d.drawImage(empty, null, 1200, 530);
        g2d.drawImage(add, null, 1225, 530);

        switch(player.getsSpeed()){
        	case 7: g2d.drawImage(fill, null, 1200, 530);
        	case 6: g2d.drawImage(fill, null, 1175, 530);
        	case 5: g2d.drawImage(fill, null, 1150, 530);
        	case 4: g2d.drawImage(fill, null, 1125, 530);
        	case 3: g2d.drawImage(fill, null, 1100, 530);
        	case 2: g2d.drawImage(fill, null, 1075, 530);
        	case 1: g2d.drawImage(fill, null, 1050, 530);
        			break;
		}
		}
		else
		{
			g2d.setColor(new Color(0,0,0));
			g2d.fillRect(0,0,1300,700);
			g2d.setColor(new Color(255,255,255));
			g2d.drawString("Game Over", 500, 200);
		}

    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
        //not used
    }

    public void mousePressed(MouseEvent e){
		player.mousePressed(e);
	}
    public void mouseReleased(MouseEvent e){
		player.mouseReleased(e);
	}
    public void mouseExited(MouseEvent e){

	}
    public void mouseEntered(MouseEvent e){

	}
    public void mouseClicked(MouseEvent e){
		player.mouseClicked(e);
	}


}


