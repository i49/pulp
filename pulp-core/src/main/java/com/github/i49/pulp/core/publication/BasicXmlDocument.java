package com.github.i49.pulp.core.publication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.MediaType;
import com.github.i49.pulp.api.XmlDocument;
import com.github.i49.pulp.core.xml.DocumentSerializer;
import com.github.i49.pulp.core.xml.XmlServices;

class BasicXmlDocument extends BasicPublicationResource implements XmlDocument {

	private Document document;
	
	public BasicXmlDocument(PublicationResourceLocation location, MediaType mediaType) {
		super(location, mediaType);
	}
	
	@Override
	public InputStream openContent() throws IOException {
		if (this.document == null) {
			return super.openContent();
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			writeDocument(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public Document getDocument() {
		if (this.document == null) {
			try {
				this.document = readDocument();
			} catch (IOException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.document;
	}
	
	@Override
	public void setDocument(Document document) {
		this.document = document;
	}
	
	protected Document readDocument() throws IOException, SAXException {
		try (InputStream in = super.openContent()) {
			DocumentBuilder builder = XmlServices.newBuilder();
			return builder.parse(in);
		}
	}
	
	protected void writeDocument(OutputStream out) throws IOException, TransformerException {
		DocumentSerializer serializer = XmlServices.newSerializer();
		serializer.serialize(out, this.document);
		out.flush();
	}
}
