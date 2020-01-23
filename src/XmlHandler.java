
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
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
    
    public XmlHandler() throws ParserConfigurationException, SAXException, IOException {
        
        filepath = "/home/adeeb/NetBeansProjects/XMLCreate.zip/src/timetable.xml";
        docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
        document = docBuilder.parse(filepath);
    }
    
    //Will write a new score to the xml document if it is within the top 5 scores
    
    public void createXML() throws ParserConfigurationException, SAXException, IOException {
        
        
        Node scores = document.getFirstChild();
        document.appendChild(this.writeScore("name", "score", document));
        Element score = document.createElement("score");
        
    }
    
    //Take in user score as a string
    //Call write score() on JText Field event listener at win screen
    public Element writeScore(String username, String roundScore, Document document) throws ParserConfigurationException{
        
        Text attributeUsername = document.createTextNode(username);
        Element attributeScore = document.createElement(roundScore); //Create school
        attributeScore.appendChild(attributeUsername); //Finally append it to school node
        
        return attributeScore;
    }
    
    //Will display the top 5 scores of the Xml document
    public static void listScores(){
    
    }
}
