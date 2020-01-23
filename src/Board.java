/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adeeb
 */

/*
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class Board extends JPanel implements Runnable, MouseListener {
    
    boolean ingame = true;
    private Dimension d;
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 500;
    int x = 0;
    BufferedImage img;
    String message = "Click Board to Start";

    private Thread animator;

    Player player;
    Alien[] alien_array = new Alien[20]; //Alien array (Hoard of incoming aliens - 25 Aliens)
    
    public static ArrayList<Bullet> bullets;
    
    public Board() {
    
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
       
        player = new Player(BOARD_WIDTH/2, BOARD_HEIGHT-60, 5);
        bullets = new ArrayList<Bullet>();
        
        int alien_x = 10;
        int alien_y = 10;
    
        for(int i = 0; i < alien_array.length; i++) {
            
            //Controls creating a new row. 5 Aliens per row.
            if (i%5 == 0 && i != 0) {
                alien_x = 10; //Set x back to 10 and shift down the y axis for new row.
                alien_y += 40;
            }
            
            alien_array[i] = new Alien(alien_x,alien_y,10);
            alien_x += 40; //Adds spacing in between aliens for every alien generated
            
        }  
        
        
            if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
            
            }
                    
  
        setDoubleBuffered(true);
    }
    
    //Rendering units
    public void paint(Graphics g) {
        
        super.paint(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        //g.fillOval(x,y,r,r);

        //Player Sprite
        g.setColor(Color.GREEN);
        g.fillRect(player.x, player.y, 20, 20);
        
        if(player.moveRight == true) {
            player.x += player.speed;
        }
        
        if (player.moveLeft == true) {
            player.x -= player.speed;
        }
        
        for(int i = 0; i< alien_array.length; i++) {
            g.setColor(Color.red);
            g.fillRect(alien_array[i].x, alien_array[i].y, 30, 30);
        }
        
        moveAliens();
        
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(message, 10, d.height-60);
        
        for(int i =0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
        
        if (ingame) {
        
    // g.drawImage(img,0,0,200,200 ,null);
   
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public void moveAliens() {
        
        for(int i = 0; i < alien_array.length; i++) {
            
            //Right movement
            if(alien_array[i].moveRight == true) {
                alien_array[i].x += 5;
            }
            //Left movement
            if(alien_array[i].moveLeft == true) {
                alien_array[i].x -= 5;
            }
            
        }
        
        // DIRECTION CHANGE
        //margins of 10 on the board for direction change
        
        for(int i = 0; i < alien_array.length; i++) {
            
            if(alien_array[i].x > BOARD_WIDTH - 50) {

                for (Alien alien : alien_array) {
                    
                    alien.moveLeft = true;
                    alien.moveRight = false;
                    alien.y += 5; //Aliens move down after hitting the edge
                }

            }

            if(alien_array[i].x < 20) {

                for (Alien alien : alien_array) {
                    
                    alien.moveRight = true;
                    alien.moveLeft = false;
                    alien.y += 5; //Aliens move down after hitting the edge
                }
            }
         }
        
    }
    
    
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
             int key = e.getKeyCode();

             player.moveRight = false;
             player.moveLeft = false;
             
             if (key == 20 ) {
                
                player.setFiring(false);
                
            }
        }

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(key==39){

                player.moveRight = true;

            }

            if (key==37) {

                player.moveLeft = true;

            }
            
            //Spacebar shoot mechanic
            if (key == KeyEvent.VK_SPACE ) {
                
                player.setFiring(true);
                
            }

        }
        

    }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {

        }


    public void run() {

    long beforeTime, timeDiff, sleep;

    beforeTime = System.currentTimeMillis();
     int animationDelay = 30;
     long time = System.currentTimeMillis();
        while (true) {//infinite loop
         // spriteManager.update();
          repaint();
          try {
            time += animationDelay;
            Thread.sleep(Math.max(0,time - 
              System.currentTimeMillis()));
          }catch (InterruptedException e) {
            System.out.println(e);
          }//end catch
        }//end while loop

    }//end of run

}//end of class
*/