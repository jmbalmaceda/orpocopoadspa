package com.criterya;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;

public class XmlReader {
	private String role1 = null;
	private String role2 = null;
	private String role3 = null;
	private String role4 = null;
	private ArrayList<String> rolev;
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
	public boolean readXML(String xml) {
        rolev = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            role1 = getTextValue(role1, doc, "role1");
            if (role1 != null) {
                if (!role1.isEmpty())
                    rolev.add(role1);
            }
            role2 = getTextValue(role2, doc, "role2");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }
            role3 = getTextValue(role3, doc, "role3");
            if (role3 != null) {
                if (!role3.isEmpty())
                    rolev.add(role3);
            }
            role4 = getTextValue(role4, doc, "role4");
            if ( role4 != null) {
                if (!role4.isEmpty())
                    rolev.add(role4);
            }
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
	
	
	public static void main(String[] args) {
		new XmlReader().readXML("salida.xml");
	}
}
