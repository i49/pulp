package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.EpubException;

class XmlService {

	private final DocumentBuilder documentBuilder;
	private final Transformer transformer;
	
	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	public XmlService() {
		try {
			this.documentBuilder = createDocumentBuilder();
			this.transformer = createTransformer();
		} catch (ParserConfigurationException e) {
			throw new EpubException(e.getMessage(), e);
		} catch (TransformerConfigurationException e) {
			throw new EpubException(e.getMessage(), e);
		}
	}
	
	/**
	 * Creates an empty XML document.
	 * @return created XML document.
	 */
	public Document createDocument() {
		return documentBuilder.newDocument();
	}
	
	public Document readDocument(InputStream stream) {
		try {
			return documentBuilder.parse(stream);
		} catch (SAXException | IOException e) {
			throw new EpubException(e.getMessage(), e);
		}
	}
	
	public void writeDocument(OutputStream stream, Document document) {
		try {
			stream.write(XML_DECLARATION.getBytes());
			DOMSource source = new DOMSource(document);
			StreamResult target = new StreamResult(stream);
			this.transformer.transform(source, target);
		} catch (IOException e) {
			throw new EpubException(e.getMessage(), e);
		} catch (TransformerException e) {
			throw new EpubException(e.getMessage(), e);
		}
	}
	
	private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder();
	}

	private static Transformer createTransformer() throws TransformerConfigurationException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer t = factory.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		return t;
	}
}
