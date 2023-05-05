package com.officeutils.noticectl.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.xml.DomUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Component
public class XSLUtils {

    @Autowired
    private Resource firstXmlResource;

    @Autowired
    private Resource secondXmlResource;

    private String firstTagName;
    private String secondTagName;

    private DocumentBuilder parser;

    public XSLUtils(String firstTagName, String secondTagName) {
        this.firstTagName = firstTagName;
        this.secondTagName = secondTagName;

        //Instantiate the parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            parser = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

/*     public void processXml_1() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        InputStream inputStream = context.getResource(firstXmlResource.getURL().toString()).getInputStream();
        Document firstDoc = DomUtils.parseXml(inputStream); 
        Document secondDoc = DomUtils.parseXml(context.getResource(secondXmlResource.getURL()).getInputStream());

        Element firstElement = (Element) firstDoc.getElementsByTagName(firstTagName).item(0);
        Element secondElement = (Element) secondDoc.getElementsByTagName(secondTagName).item(0);

        firstElement.appendChild(firstDoc.importNode(secondElement, true));

        FileWriter writer = new FileWriter(new File(secondXmlResource.getURL().toURI()));
        Source source = new DOMSource(firstDoc);
        StreamResult result = new StreamResult(writer);
        ((Transformer) context.getBean("transformer")).transform(source, result);
        writer.close();
    } */

    public void processXml() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        Document firstDoc = parser.parse(firstXmlResource.getInputStream());
        Document secondDoc = parser.parse(secondXmlResource.getInputStream());
    
        Element firstElement = (Element) firstDoc.getElementsByTagName(firstTagName).item(0);
        Element secondElement = (Element) secondDoc.getElementsByTagName(secondTagName).item(0);
    
        firstElement.appendChild(firstDoc.importNode(secondElement, true));
    
        FileWriter writer = new FileWriter(new File(secondXmlResource.getURL().toURI()));
        Source source = new DOMSource(firstDoc);
        StreamResult result = new StreamResult(writer);
        Transformer transformer = (Transformer) context.getBean("transformer");
        transformer.transform(source, result);
        writer.close();
    }
}
