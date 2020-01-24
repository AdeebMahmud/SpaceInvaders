/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeeb Mahmud
 */

// IMPORTS
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    //FIELDS
    
    //JFrame dimensions
    public static int WIDTH = 500; 
    public static int HEIGHT = 500;
    
    private Thread thread;
    public static boolean running; //This variable is used to control the Game Loop. Once run, the Game Loop is always true.
    private boolean doneRunning = true;
    private BufferedImage image;
    private Graphics2D g;
    private int FPS = 30;
    
    // entity declarations
    private Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Bullet> alienBullets;
    public static ArrayList<Alien> aliens;
    public static ArrayList<Civilian> civilians;
    
    // game elements
    
    public static int score; //this global variable keeps track of the player score
    public long elapsedTime; //used to keep track of time in addition to adding a time factor to the score
    public long gameStartTime;
    public long gameEndTime;
    long startTime; //These variables are used to control game FPS to allow for better game flow and control
    long URDTimeMillis;
    long waitTime;
    long targetTime = 1000/FPS;
    
    // Alien generation and behaviour elements
    public static boolean fireChance; // This boolean will be used in the Game Loop when randomizing alien fire
    public int aliensPerRow;
    public int aliensPerColumn;
    public int layer;
    public int randomAlienIndex;
    
    //JPanels for game screens
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private WinScreen winScreen;
    
    public XmlHandler xmlHandler = new XmlHandler();
    
    // Game state control
    // Game state is used to control which screens are to be rendered at a given point in time during the execution of the Game Loop
    public enum STATE {
        START, //Start Screen
        GAME, // In Game
        GAME_OVER, // Game Over Screen
        WIN, // Win Screen
        STASUS // Used as a temporary state to prevent screens from rendering more than they need to be (ie; Win Screen needs only to be rendered once)
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
    
    
    //The init function reinitializes game variables, effectively creating a new instance of the game.
    //It will be called when the player wants to play again and at the start of the program.
    private void init() {
        
        State = STATE.GAME; //The Game State is set to GAME
        
        score = 3000; //The player score will start at 3000 and decrement as time goes by. Killing aliens increases score.

        //INITIALIZING VARIABLES
        
        // Game units
        player = new Player();
        aliens = new ArrayList<Alien>();
        civilians = new ArrayList<Civilian>();
 
        // bullets
        bullets = new ArrayList<Bullet>();
        alienBullets = new ArrayList<Bullet>();
        
        // Alien generation specifications.
        aliensPerRow = 6;
        aliensPerColumn = 3;
        layer = 1;
        
        fireChance = false; // fireChance is set false at the beginning of the game, to be set true randomly every so often. See Game Loop.
        
        // Initializing the number of civilians (displayed as blue circles at the bottom of the screen)
        for(int i = 0; i < 10; i ++ ) {
            //Creates an array of Aliens
            civilians.add(new Civilian());
        }
        
        // Alien Spawn Formatting (3 ROWS OF 5). This number can be edited by changing values of the aliensPerRow and aliensPerColumn variables.
        
        // Adds type Alien(s) to an array list.
        for(int i = 0; i < aliensPerColumn*aliensPerRow; i ++ ) {
            //Creates an array of Aliens
            aliens.add(new Alien());
        }
        
        // Alien spacing
        for (int i = 0; i < aliensPerColumn*aliensPerRow; i++) {
            
            
            // The first alien has an x-coordinate of 10
            if(i == 0) {
                
               aliens.get(i).x = 10;
               
            }
            
            // At the beginning of a new row, the Y-coordinate for the aliens in that row must change.
            // using the modulo operator to set a new row every multiple of the desired amount of rows.
            else if (i%aliensPerRow == 0) {
                
                aliens.get(i).x = 10; //Set x-coordinate back to 10 and shift down the y axis for new row.
                
                //Update Y value for upcoming aliens in the respective row
                for(int j = 0; j <= aliensPerRow - 1; j ++) {
                    aliens.get(i+j).y += 40*layer;
                }
                layer += 1; //Use layer to control the y-level of the row of aliens
            }
            
            //Creation of a single row
            // Recursively get the x-coordinate of the next alien in the row by adding 40 from the previous alien
            else {
                //Alien x-coordinate will be 40 more than the previous Alien X. (Creates spacing of 40 units)
                 aliens.get(i).x += aliens.get(i-1).x + 40; 
                 
            }
        }
        
        doneRunning = false; //Used to control whether or not the game is ready to be re-initialized

    }
    
    //This will run on the very first time. The state is set to start. Every other execution of the program will run init to reset variables
    public void run() {
        
        // The first time the program is run, the Game State should be start and the player will be greeted by the StartScreen.
        State = STATE.START;
        
        // Initialize Game Screens
        startScreen = new StartScreen();
        gameOverScreen = new GameOverScreen();
        winScreen = new WinScreen();
        
        // Used to display the Start Screen
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        setFocusable(true);
        requestFocus();
        
        gameStartTime = System.nanoTime();
        running = true; // Once running, the GameLoop never ends until the entire program is exited
        
        //GAME LOOP
        while(running) {
            
            System.out.println(State.name()); //RANDOM PRINT STATEMENT THAT MUST EXIST TO MAKE GAME FUNCTION PROPERLY.
            
            if (State == STATE.GAME) {
                
                gameEndTime = System.nanoTime();
                elapsedTime = (gameEndTime - gameStartTime) / 1000000000;
                
                // ALIEN RANDOM FIRE MECHANIC
                
                fireChance = false;
                randomAlienIndex = (int) (Math.random() * aliens.size());

                //Every 3 seconds, aliens have a a window of opportunity to shoot
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    
                    // This function is called once every 3 seconds
                    public void run() {
                        
                        int chance;
                        // Create a random number between 1 and 2
                        chance = (int) (Math.random() * 2) + 1;
                        
                        // If that number is 1, the alien may shoot
                        if (chance == 1) {

                            fireChance = true;

                        }
                        
                        // For efficiency purposes, civilian movement is controlled in the same environment.
                        // Every 3 seconds, civilians get the opportunity to change their direction.
                        for (int i = 0; i < civilians.size(); i++) {

                            civilians.get(i).directionChange();

                        }

                    }

                }, 0, 3000);

                startTime = System.nanoTime();
                
                // MAIN LOOP
                
                // While the Game Loop is active, three primary functions are called.
                
                gameUpdate(); // Controls all entity updates including movement and controls Game state
                gameRender(); // Controls rendering the updated values performed by gameUpdate()
                gameDraw(); // Uses Graphics2d to draw to the Jframe
                
                // FPS CONTROL
                
                // FPS is controlled for improved game stability
                URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - URDTimeMillis;
                
                try {
                    
                    Thread.sleep(waitTime);

                } 
                catch (Exception e) {
                }

            } 
            
            //Used in the first time of running the program. Start screen must be rendered and drawn.
            else if(State == STATE.START) {
                
                gameRender();
                gameDraw();
                
            }
            
            //Stasus state is used to prevent win and game over screens from rendering multiple times. They only need to be rendered once.
            // The game is temporarely put in "stasus" until ENTER is pressed to play again and initialize a new game
            if(State != STATE.START && State != STATE.GAME) {
                
                State = STATE.STASUS;
                
            }
        }
    }
    
    //Runs 30 times per second
    private void gameUpdate() {
        
        //STATE UPDATES
        
        //If the game is already running, the user should not be able to create a new instance of the game.
        if(!doneRunning) {
            
            //GAME OVER
            // The game is over if the player loses all 3 of their lives OR if all civilians die
            if (((!player.isAlive) || (civilians.isEmpty()))) {
                
                State = STATE.GAME_OVER; //State change to GameOver
                doneRunning = true; // Game has ended, doneRunning can be set to true again.
                gameEndTime = System.nanoTime(); //Record the gameEndtime for updating score time factor

            }

            //WIN CONCDITION
            // The player has won if there are no more aliens.
            if (aliens.isEmpty()) {
                
                String username;
                
                score += 1000; //If you win you get bonus points
                State = STATE.WIN; // State update to win allowing for the WinScreen to be rendered.
                doneRunning = true; //The game has ended, doneRunning can be set to true again.
                gameEndTime = System.nanoTime(); //Record the gameEndtime for updating score time factor
                username = JOptionPane.showInputDialog("You won! Enter your name!"); // Reads user input to get the player's name
                xmlHandler.writeScore(username, Integer.toString(score)); // Call on the xmlHandler to keep record of a newly submited score.
               
            }
        }
        
        // If the game is running, continue to update player, Civilians, Aliens, and bullets
        
        if(State == STATE.GAME) {
            
            // Update Player
            player.update();
            
            // Score update: Start at a score of 1000 and decrement by factor of 4 times the elapsed time.
            // There is no particular reason for these factors, they are in effect random parameters
            score -= elapsedTime/4;
            
            //Bullet update
            // Iterate through every bullet in the bullets ArrayList. If the bullet is not in play, remove it. See bullets.update()
            for(int i =0; i < bullets.size(); i++ ) {
                boolean remove = bullets.get(i).update();
                if(remove) {
                    bullets.remove(i);
                    i--;
                }
            }

            //Alien Bullet Update
            // Iterate through every bullet in the alienBullets ArrayList. If the bullet is not in play, remove it. See alienBullets.update()
            for(int i =0; i < alienBullets.size(); i++ ) {
                boolean remove = alienBullets.get(i).update();
                if(remove) {
                    alienBullets.remove(i);
                    i--;
                }
            }

            //Alien update
            // Iterate through each Alien in aliens and update them all
            for(int i = 0; i < aliens.size(); i ++ ) {

                aliens.get(i).update();

            }
            
            // If the alien gets the chance to fire, setFiring to true and make a random Alien in aliens shoot an alienBullet
            if(fireChance) {
                
                aliens.get(randomAlienIndex).setFiring(true); //Sets firing to be true for a random alien in the swarm.

            }

            //Civilian update
            // Iterate through each Civilian in civilians and update them all
            for(int i = 0; i < civilians.size(); i ++ ) {
                
                civilians.get(i).update();
                
            }

            // COLLISIONS
            // All collisions utilize the hit() function to update an entity's isAlive boolean.
            
            // bullet-alien collision
            // If the x and y coordinates overlap (factoring in radius as a hitbox), remove both entities from their respective ArrayLists) 
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
                        a.hit(); //Alien is hit. update the isAlive property.
                        bullets.remove(i);
                        i--;
                        break; //Break because there is nothing more to calculate. The entities have been removed
                    }
                }
            }

           // Checking for dead aliens
           // If the alien is dead, remove them from aliens
           for(int i = 0; i < aliens.size(); i ++) {
               
               if(aliens.get(i).isAlive == false) {
                   aliens.remove(i);
                   i--;
                }
           }

           // alien bullet-civilian collision
           for(int i = 0; i < alienBullets.size(); i ++ ) {
                Bullet ab = alienBullets.get(i);

                double bulletX = ab.getX();
                double bulletY = ab.getY();
                double bulletR = ab.getR();

                for(int j = 0; j < civilians.size(); j ++) {

                    Civilian c = civilians.get(j);

                    //CIVILIAN FIELDS
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
           // Same as above
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
        // Renders the high level Jframe in the architecture
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        
        //RENDERING BASED ON GAME STATE
        // This switch statement simply takes the state as its condition and renders screens accordingly
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
                break;
            case WIN:
                winScreen.draw(g);
                break;
        }
    }
    
    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.setColor(Color.WHITE);
        
        // Display the score and lives in the JFrame once the game starts
        if(State == STATE.GAME) {
            g2.drawString("SCORE: " + score, 10, 20);
            g2.drawString("LIVES: " + player.lives, 440, 20);
        }
        
        g2.dispose();
       
    }
    
    // EVENT LISTENERS
    
    public void keyTyped(KeyEvent key) {}
    
    public void keyPressed(KeyEvent key) {
        
        int keyCode = key.getKeyCode();
        
        if(keyCode == KeyEvent.VK_LEFT) {
            
            player.setMoveLeft(true); // Updates the player movement. See setMoveLeft() in Player.java
        }
        
        if(keyCode == KeyEvent.VK_RIGHT) {
            
            player.setMoveRight(true); // Updates the player movement. See setMoveLeft() in Player.java
        }
        
        if(keyCode == KeyEvent.VK_SPACE) {
            
            player.setFiring(true); // Updates the player firing boolean. See setFiring() in Player.java
        }
        
    }
    
    // keyReleased is used to ensure that the player must hold down arrow keys to move.
    public void keyReleased(KeyEvent key) {
        
        int keyCode = key.getKeyCode();
        
        if(keyCode == KeyEvent.VK_LEFT) {
            player.setMoveLeft(false); // Updates the player movement. See setMoveLeft() in Player.java
        }
        
        if(keyCode == KeyEvent.VK_RIGHT) {
            player.setMoveRight(false); // Updates the player movement. See setMoveLeft() in Player.java
        }
        
        if(keyCode == KeyEvent.VK_SPACE) { // Updates the player firing boolean. See setFiring() in Player.java
            player.setFiring(false);
        }
        
        // If enter is hit and released anytime when the game is not in progress, start a new instance of the game to play again
        if(keyCode == KeyEvent.VK_ENTER ) {
            if (State == STATE.GAME_OVER || State == STATE.WIN || State == STATE.START || State == STATE.STASUS) {
                
                    init(); //Starts a new instance of the game
                
            }
            
        }
    }
}
