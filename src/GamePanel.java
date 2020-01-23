/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeeb Mahmud
 */

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
    
    //FIELDS
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static boolean fireChance;
    public static int score;
    public static boolean gameOver;
    
    public String stringScore = Integer.toString(score);
    
    private Thread thread;
    public static boolean running;
    private boolean doneRunning = true;
    private BufferedImage image;
    private Graphics2D g;
    
    private int FPS = 30;
    private double averageFPS;
    
    private Player player;
    
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Bullet> alienBullets;
    public static ArrayList<Alien> aliens;
    public static ArrayList<Civilian> civilians;
    
    public int aliensPerRow;
    public int aliensPerColumn;
    public int layer;
    public int randomAlienIndex;
    
    public long elapsedTime;
    public long gameStartTime;
    public long gameEndTime;
    
    //JPanels for game Screens
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private WinScreen winScreen;
    
    public boolean newHigh;
    public int highscore = 0;
  
    public XmlHandler xmlHandler = new XmlHandler();
    //public static String xmlFilePath = "/home/adeeb/NetBeansProjects/SpaceInvaders/src/scores.xml";
    
    public enum STATE {
        START,
        GAME,
        GAME_OVER,
        WIN,
        SCORES,
        STASUS
    };
    
    public STATE State = STATE.START;
    
    //CONSTRUCTOR
    public GamePanel() {
        
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        
    }
    
    //FUNCTIONS
    public void addNotify() {
        super.addNotify();
        
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }
    
    long startTime;
    long URDTimeMillis;
    long waitTime;
    long targetTime = 1000/FPS;
    //init creates a new instance of the game. It will be called when the player wants to play again and at the start of the program.
    private void init() {
        
        System.out.print("init");
        State = STATE.GAME;

        score = 3000;

        // UNITS
        player = new Player();
        aliens = new ArrayList<Alien>();
        civilians = new ArrayList<Civilian>();
 
        // BULLETS
        bullets = new ArrayList<Bullet>();
        alienBullets = new ArrayList<Bullet>();
        
        aliensPerRow = 2;
        aliensPerColumn = 2;
        layer = 1;
        
        fireChance = false;
        
        for(int i = 0; i < 10; i ++ ) {
            //Creates an array of Aliens
            civilians.add(new Civilian());
        }
        
        // ALIEN SPAWN FORMATTING ( 3 ROWS OF 5)
        
        for(int i = 0; i < aliensPerColumn*aliensPerRow; i ++ ) {
            //Creates an array of Aliens
            aliens.add(new Alien());
        }
        
        for (int i = 0; i < aliensPerColumn*aliensPerRow; i++) {
            
            
            // The first alien has an x of 10
            if(i == 0) {
                
               aliens.get(i).x = 10;
               
            }
            
            // Beginning of new row. Y needs to change
            else if (i%aliensPerRow == 0) {
                
                aliens.get(i).x = 10; //Set x back to 10 and shift down the y axis for new row.
                
                
                //Update Y value for next 5 aliens
                for(int j = 0; j <= aliensPerRow - 1; j ++) {
                    aliens.get(i+j).y += 40*layer;
                }
                layer += 1;
            }
            
            //Creation of a single row
            else {
                //Alien X will be 40 more than the previous Alien X. (Creates spacing of 40 units)
                 aliens.get(i).x += aliens.get(i-1).x + 40; 
                 
            }
        }
        System.out.println("end of init state: " + State.name());
        doneRunning = false;

    }
    
    //This will run on the very first time. The state is set to start. Every other execution of the program will run init to reset variables
    public void run() {
        
        System.out.print("run");
        State = STATE.START;
        
        
        startScreen = new StartScreen();
        gameOverScreen = new GameOverScreen();
        winScreen = new WinScreen();
             
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        g = (Graphics2D) image.getGraphics();
        
        setFocusable(true);
        requestFocus();
        gameStartTime = System.nanoTime();
        running = true;
        
        //GAME LOOP
        while(running) {
            
            System.out.println(State.name()); //RANDOM PRINT STATEMENT THAT MUST EXIST TO MAKE GAME FUNCTION PROPERLY.
            
            if (State == STATE.GAME) {
                gameEndTime = System.nanoTime();
                elapsedTime = (gameEndTime - gameStartTime) / 1000000000;

                fireChance = false;
                randomAlienIndex = (int) (Math.random() * aliens.size());

                //Every 4 seconds, aliens have 33% chance to shoot
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {

                   
                    public void run() {
                        
                        int chance;
                        chance = (int) (Math.random() * 2) + 1;

                        if (chance == 1) {

                            fireChance = true;

                        }

                        for (int i = 0; i < civilians.size(); i++) {

                            civilians.get(i).directionChange();

                        }

                    }

                }, 0, 3000);

                startTime = System.nanoTime();

                gameUpdate();
                gameRender();
                gameDraw();

                URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - URDTimeMillis;
                try {
                    Thread.sleep(waitTime);

                } catch (Exception e) {
                }

                
            } 
            
            
            else if(State == STATE.START) {
                
                gameRender();
                gameDraw();
                
            }
            
            //Stasus state is used to prevent win and game over screens from rendering multiple times. They only need to be rendered once.
            if(State != STATE.START && State != STATE.GAME) {
                
                State = STATE.STASUS;
                
                
            }
            
        }
        //If the game is not running... render and draw once because it will inevitably be either startwin or game over screen
        
        System.out.println("not running");
    }
    
    //Runs 30 times per second
    private void gameUpdate() {
        //STATE UPDATES
        
        if(!doneRunning) {
            
            //GAME OVER
            if (((!player.isAlive) || (civilians.isEmpty()))) {
                
                State = STATE.GAME_OVER;
                doneRunning = true;
                gameEndTime = System.nanoTime();
                

            }

            //WIN CONCDITION
            if (aliens.isEmpty()) {
                
                score += 1000; //If you win you get bonus points
                State = STATE.WIN;
                doneRunning = true;
                gameEndTime = System.nanoTime();
                
                xmlHandler.writeScore(winScreen.username, stringScore); //Null pointer 
                
                /*if(score > highscore) {
                    newHigh = true;
                }*/
            }
        }
        
        if(State == STATE.GAME) {
            // update will constantly update the game state.
            player.update();
            //Score update: Start at a score of 1000 and decrement by factor of 4 times the elapsed time.
            score -= elapsedTime/4;
            
            //Bullet update
            for(int i =0; i < bullets.size(); i++ ) {
                boolean remove = bullets.get(i).update();
                if(remove) {
                    bullets.remove(i);
                    i--;
                }
            }

            //Alien Bullet Update
            for(int i =0; i < alienBullets.size(); i++ ) {
                boolean remove = alienBullets.get(i).update();
                if(remove) {
                    alienBullets.remove(i);
                    i--;
                }
            }

            //Alien update
            for(int i = 0; i < aliens.size(); i ++ ) {

                aliens.get(i).update();

            }

            if(fireChance) {
                
                aliens.get(randomAlienIndex).setFiring(true); //Sets firing to be true for a random alien in the swarm.

            }

            //Civilian update
            for(int i = 0; i < civilians.size(); i ++ ) {
                civilians.get(i).update();
            }


            //bullet-alien collision
            for(int i = 0; i < bullets.size(); i ++ ) {
                Bullet b = bullets.get(i);

                double bulletX = b.getX();
                double bulletY = b.getY();
                double bulletR = b.getR();

                for(int j = 0; j < aliens.size(); j ++) {

                    Alien a = aliens.get(j);

                    //ALIEN FIELDS
                    double alienX = a.getX();
                    double alienY = a.getY();
                    double alienR = a.getR();

                    double dx = bulletX - alienX;
                    double dy = bulletY - alienY;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if(dist < bulletR + alienR) {
                        a.hit();
                        bullets.remove(i);
                        i--;
                        break;
                    }
                }
            }

           //Checking for dead aliens
           for(int i = 0; i < aliens.size(); i ++) {
               
               if(aliens.get(i).isAlive == false) {
                   aliens.remove(i);
                   i--;
                }
           }

           //alien bullet-civilian collision
           for(int i = 0; i < alienBullets.size(); i ++ ) {
                Bullet ab = alienBullets.get(i);

                double bulletX = ab.getX();
                double bulletY = ab.getY();
                double bulletR = ab.getR();

                for(int j = 0; j < civilians.size(); j ++) {

                    Civilian c = civilians.get(j);

                    //ALIEN FIELDS
                    double civilianX = c.getX();
                    double civilianY = c.getY();
                    double civilianR = c.getR();

                    double dx = bulletX - civilianX;
                    double dy = bulletY - civilianY;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if(dist < bulletR + civilianR) {
                        c.hit();
                        alienBullets.remove(i);
                        i--;
                        break;
                    }
                }
            }

           //alien bullet-player collision
           for(int i = 0; i < alienBullets.size(); i ++ ) {
                Bullet ab = alienBullets.get(i);

                double bulletX = ab.getX();
                double bulletY = ab.getY();
                double bulletR = ab.getR();

                //PLAYER FIELDS
                double playerX = player.getX();
                double playerY = player.getY();
                double playerR = player.getR();

                double dx = bulletX - playerX;
                double dy = bulletY - playerY;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist < bulletR + playerR) {
                    player.hit();
                    alienBullets.remove(i);
                    i--;
                    break;
                }

            }

           //Check dead civilians
           for(int i = 0; i < civilians.size(); i ++) {

               if(civilians.get(i).isAlive == false) {
                   civilians.remove(i);
                   i--;
                }
           }
        }
    }
    
    private void gameRender() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        
           
        switch (State) {
            case GAME:
                player.draw(g);
                for(int i = 0; i < aliens.size(); i ++) {
                    aliens.get(i).draw(g);
                }   for(int i = 0; i < bullets.size(); i ++) {
                    bullets.get(i).draw(g);
                }   for(int i = 0; i < alienBullets.size(); i ++) {
                    alienBullets.get(i).draw(g);
                }   for(int i = 0; i < civilians.size(); i ++) {
                    civilians.get(i).draw(g);
                }   break;
            case START:
                startScreen.draw(g);
                break;
            case GAME_OVER:
                gameOverScreen.draw(g);
                //running = false;
                break;
            case WIN:
                winScreen.draw(g);
                //running = false;
                break;
        }
        
    }
    
    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.setColor(Color.WHITE);
        
        if(State == STATE.GAME) {
            g2.drawString("SCORE: " + score, 10, 20);
            g2.drawString("LIVES: " + player.lives, 440, 20);
        }
        
        g2.dispose();
       
    }
    
    public void keyTyped(KeyEvent key) {}
    
    public void keyPressed(KeyEvent key) {
        
        int keyCode = key.getKeyCode();
        
        if(keyCode == KeyEvent.VK_LEFT) {
            
            player.setMoveLeft(true);
        }
        
        if(keyCode == KeyEvent.VK_RIGHT) {
            
            player.setMoveRight(true);
        }
        
        if(keyCode == KeyEvent.VK_SPACE) {
            
            player.setFiring(true);
        }
        
    }
    
    public void keyReleased(KeyEvent key) {
        
        int keyCode = key.getKeyCode();
        
        if(keyCode == KeyEvent.VK_LEFT) {
            player.setMoveLeft(false);
        }
        
        if(keyCode == KeyEvent.VK_RIGHT) {
            player.setMoveRight(false);
        }
        
        if(keyCode == KeyEvent.VK_SPACE) {
            player.setFiring(false);
        }
        
        if(keyCode == KeyEvent.VK_ENTER ) {
            if (State == STATE.GAME_OVER || State == STATE.WIN || State == STATE.START || State == STATE.STASUS) {
                System.out.println("Key Pressed");
                
                    System.out.print("second if reached");
                    //this.requestFocusInWindow();
                    init(); //Starts a new instance of the game
                    //this.requestFocusInWindow();
                    System.out.print(State.name());
                
            }
            
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
