package com.github.i49.pulp.core.container;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.i49.pulp.api.Publication;

public class ContainerDocument {

	public static final String DEFAULT_NAMESPACE_URI = "urn:oasis:names:tc:opendocument:xmlns:container";
	
	private final Document document;
	
	private ContainerDocument(Document document) {
		this.document = document;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public void addRenditions(Publication publication) {
		for (Element rootFile: getRootFiles()) {
			String mediaType = rootFile.getAttribute("media-type");
			if (PackageDocument.MEDIA_TYPE.equals(mediaType)) {
				String location = rootFile.getAttribute("full-path");
				if (location != null) {
					publication.addRendition(location);
				}
			}
		}
	}
	
	private List<Element> getRootFiles() {
		List<Element> result = new ArrayList<>();
		Element container = document.getDocumentElement();
		NodeList children = container.getElementsByTagNameNS(DEFAULT_NAMESPACE_URI, "rootfiles");
		if (children.getLength() > 0) {
			Element rootfiles =  (Element)children.item(0);
			children = rootfiles.getElementsByTagNameNS(DEFAULT_NAMESPACE_URI, "rootfile");
			for (int i = 0; i < children.getLength(); i++) {
				result.add((Element)children.item(i));
			}
		}
		return result;
	}
	
	public static ContainerDocument create(Document document) {
		Element root = document.getDocumentElement();
		if (root.getNamespaceURI().equals(DEFAULT_NAMESPACE_URI) && root.getLocalName().equals("container")) {
			return new ContainerDocument(document);
		} else {
			return null;
		}
	}
	
	public static  ContainerDocument create(DocumentBuilder documentBuilder, Publication publication) {
		ContainerDocumentBuilder builder = new ContainerDocumentBuilder(documentBuilder);
		return builder.build(publication);
	}
}
