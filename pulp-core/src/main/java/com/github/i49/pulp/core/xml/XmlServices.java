package com.github.i49.pulp.core.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.core.Messages;
import com.github.i49.pulp.core.publication.DocumentSerializer;

/**
 * Utility class for creating XML services.
 */
public final class XmlServices {

	// DocumentBuilderFactory for each thread.
	private static final ThreadLocal<DocumentBuilderFactory> builderFactory = 
			ThreadLocal.withInitial(XmlServices::createBuilderFactory);

	// TransformerFactory for each thread.
	private static final ThreadLocal<TransformerFactory> transformerFactory = 
			ThreadLocal.withInitial(TransformerFactory::newInstance);
	
	/**
	 * Creates an new instance of {@link DocumentBuilder}.
	 * @return created instance of {@link DocumentBuilder}, which is not thread-safe.
	 * @exception EpubException if a configuration error occurs.
	 */
	public static DocumentBuilder newBuilder() {
		try {
			return builderFactory.get().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new EpubException(Messages.XML_PARSER_MISCONFIGURED(), e);
		}
	}
	
	/**
	 * Creates an new instance of {@link DocumentSerializer}.
	 * @return created instance of {@link DocumentSerializer}, which is not thread-safe.
	 * @exception EpubException if a configuration error occurs.
	 */
	public static DocumentSerializer newSerializer() {
		try {
			Transformer t = transformerFactory.get().newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.METHOD, "xml");
			return new DocumentSerializer(t);
		} catch (TransformerConfigurationException e) {
			throw new EpubException(Messages.XML_TRANSFORMER_MISCONFIGURED(), e);
		}
	}

	private static DocumentBuilderFactory createBuilderFactory() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		return builderFactory;
	}

	private XmlServices() {
	}
}
