
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import org.w3c.dom.Node;

import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adeeb
 */
public class XmlHandler {
    
    String filepath;
    Document document;
    DocumentBuilder docBuilder;
    DocumentBuilderFactory docFactory;
    
    
    public XmlHandler() {
        
    }
    
    //Will write a new score to the xml document if it is within the top 5 scores
    
    //Take in user score as a string
    //Call write score() on JText Field event listener at win screen
    public void writeScore(String username, String roundScore) {
        try {
            filepath = "/home/adeeb/NetBeansProjects/SpaceInvaders/src/score.xml";
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.parse(filepath);

            Node scores = document.getFirstChild();
            Node score = document.getElementsByTagName("scores").item(0);

            //Appends score to scores
            scores.appendChild(score); 

            //Appends name and value to score
            score.appendChild(document.createTextNode(username));
            score.appendChild(document.createTextNode(roundScore));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
        }
        
        catch (UnsupportedEncodingException e) {
            System.out.println(
             "This VM does not support the Latin-1 character set."
            );
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
        catch (ParserConfigurationException | TransformerException | SAXException pce) {
           }
    }
    
    //Will display the top 5 scores of the Xml document
    public static void listScores(){
    
    }
}
