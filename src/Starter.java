/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This program demonstrates basic java game development in creating a SpaceInvaders type 2d video game
 * ICS4UE
 *Date: January 23 2019
 * @author Adeeb Mahmud
 */
import javax.swing.*;

//This class is what runs when the program is run. It creates a new GamePanel.
public class Starter {
 
    public static void main(String[] args) {
        
        JFrame window = new JFrame("Space Invaders");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(new GamePanel());
        window.pack();
        window.setVisible(true);
        
    }
    
}
