package com.criterya;
import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Crunchify.com
 */

public class XmlCreator {

	public static void main(String[] args) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element mainRootElement = doc.createElementNS("www.criterya.com/orco/", "OrcoAds");
			doc.appendChild(mainRootElement);
			
			Element  recommendedVideos = doc.createElement("recommendedVideos");
			recommendedVideos.appendChild(getRecommendedVideo(doc, "Video1", "c:/temp/video1.avi"));
			recommendedVideos.appendChild(getRecommendedVideo(doc, "Video2", "c:/temp/video2.avi"));
			recommendedVideos.appendChild(getRecommendedVideo(doc, "Video3", "c:/temp/video3.avi"));
			mainRootElement.appendChild(recommendedVideos);
			
			Element adsVideos = doc.createElement("adsVideos");
			adsVideos.appendChild(getAdsVideo(doc, "c:/temp/ads/ads1.avi"));
			adsVideos.appendChild(getAdsVideo(doc, "c:/temp/ads/ads2.avi"));
			adsVideos.appendChild(getAdsVideo(doc, "c:/temp/ads/ads3.avi"));
			adsVideos.appendChild(getAdsVideo(doc, "c:/temp/ads/ads4.avi"));
			mainRootElement.appendChild(adsVideos);
			
			
			// output DOM XML to console 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			StreamResult console = new StreamResult(new FileOutputStream("salida.xml"));
			transformer.transform(source, console);

			System.out.println("\nXML DOM Created Successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Node getRecommendedVideo(Document doc, String id, String path){
		Element ads = doc.createElement("recVideo");
		ads.setAttribute("id", id);
		ads.setAttribute("path", path);
		return ads;
	}
	
	private static Node getAdsVideo(Document doc, String path){
		Element ads = doc.createElement("adsVideo");
		ads.setAttribute("path", path);
		return ads;
	}
}
