
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.Dictionary;
import java.util.Hashtable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//SCORES FROM XML DATA

public class WinScreen {
    
    public String username;
    private String filepath;
    private Document document;
    private DocumentBuilder docBuilder;
    private DocumentBuilderFactory docFactory;
    private Dictionary scoreDictionary;
    private Score[] scoreArray;
    
    
    public void draw(Graphics2D g) {
        
        //username = JOptionPane.showInputDialog("Enter your name");
        //import xml
        
        //looping forever
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("YOU WIN!", GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-100);
        Font fnt1 = new Font("arial", Font.BOLD, 10);
        g.drawString("HIGH SCORES", GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2);
        
        System.out.print(username);
        /*if(newHigh) {
            g.drawString("NEW HIGH SCORE!", GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2);
        }*/
        //Ask for a name submission input field
        //XML DATA FORMATTING
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            filepath = "src/scores.xml";
            document = docBuilder.parse(filepath);
            document.getDocumentElement().normalize();
            
            scoreDictionary = new Hashtable<String, String>();
            
            NodeList scores = document.getElementsByTagName("score");
            
            
            scoreArray = new Score[scores.getLength()];
            
            for (int i = 0; i < scores.getLength(); i ++) {
                
                
                Node data = scores.item(i);
                Element elementData = (Element)data;
                
                System.out.println(elementData.getAttribute("name"));
                Score score = new Score(elementData.getAttribute("name"), Integer.parseInt(elementData.getAttribute("value")));
                
                scoreArray[i] = score;
            }
            //SORT
            for ( int i = 0; i < scoreArray.length; i ++) {
                
                for(int j = 0; j < scoreArray.length-1; j ++ ) {
                    
                    if(scoreArray[j].score < scoreArray[j+1].score){
                        Score temp = scoreArray[j]; 
                        scoreArray[j] = scoreArray[j+1];
                        scoreArray[j+1] = temp;
                    }
                }
            } 
            
            // Print Scores
            if(scoreArray.length < 5) {
                
                for(int i = 0; i < scoreArray.length; i++) {
                
                    g.drawString(scoreArray[i].name + ": " + scoreArray[i].score, GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2+40 + i*35);
                    
                }
                
            }
            
            //Print top 5 scores
            else {
            
                for(int i = 0; i < 5; i++) {
                
                    g.drawString(scoreArray[i].name + ": " + scoreArray[i].score, GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2+50 + i*35);
                    
                }
            }
            
        }
        
        catch (UnsupportedEncodingException e) {
            System.out.println(
             "This VM does not support the Latin-1 character set."
            );
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
        catch (ParserConfigurationException | SAXException pce) {
           } 
        }
        
    }

