package com.flopbox.util;

import java.io.StringWriter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.net.ftp.FTPFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <h1>Class XMLUtils</h1>
 *
 * Cette classe permet de gérer le format XML
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 * @see javax.xml.parsers.DocumentBuilder;
 */
public class XMLUtils {

	/**
	 * Cette methode permet de construire un document XML
	 *
	 * @throws ParserConfigurationException
	 */
	public static Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		return doc;
	}

	/**
	 * Cette methode permet de lister le contenu d'un dossier
	 *
	 * @param root     le chemin du dossier
	 * @param file     le contenu du dossier
	 * @param uniqueID l'identifiant unique du fichier
	 */
	public static void extract_contentXML(Element root, FTPFile file, String uniqueID) {
		Element fileElement;
		if (file.getType() == 1)
			fileElement = root.getOwnerDocument().createElement("folder");
		else {
			fileElement = root.getOwnerDocument().createElement("file");
		}
		fileElement.setAttribute("id", uniqueID);
		fileElement.setAttribute("name", file.getName());
		fileElement.setAttribute("size", String.valueOf(file.getSize()));
		fileElement.setAttribute("date", file.getTimestamp().getTime().toString());
		root.appendChild(fileElement);
	}

	/**
	 * Cette methode permet de lister le contenu d'un dossier
	 *
	 * @param files
	 * @param root
	 * @throws ParserConfigurationException
	 */
	public static String listContentXML(FTPFile[] files, String root) throws ParserConfigurationException {
		Document doc = buildDocument();
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);
		for (FTPFile file : files) {
			String uniqueID = FTPUtils.generateUniqueID(root, file);
			extract_contentXML(rootElement, file, uniqueID);
		}
		return xmlToString(doc);
	}

	/**
	 * Cette methode permet de lister le contenu des resultat de la recherche
	 *
	 * @param res la liste des resultats
	 * @throws ParserConfigurationException
	 */
	public static Object mapToXML(Map<String, List<String>> res) throws ParserConfigurationException {
		Document doc = buildDocument();
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);
		for (String key : res.keySet()) {
			Element server = doc.createElement("server");
			server.setAttribute("id", key);
			for (String file : res.get(key)) {
				Element fileElement = doc.createElement("file");
				String path = file.replace("//", "/");
				String uniqueID = Base64.getEncoder().encodeToString(path.getBytes());
				fileElement.setAttribute("id", uniqueID);
				fileElement.setAttribute("name", path);
				server.appendChild(fileElement);
			}
			rootElement.appendChild(server);
		}
		return xmlToString(doc);
	}

	/**
	 * Cette methode permet de transformer un document XML en String
	 *
	 * @param doc
	 */
	public static String xmlToString(Document doc) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			return writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (Exception e) {
			return null;
		}
	}
}