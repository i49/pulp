package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.DocumentSerializer;
import com.github.i49.pulp.core.XmlServices;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class Epub3PublicationWriter implements PublicationWriter {

	private final ContainerSaver saver;
	private final DocumentBuilder documentBuilder;
	private final DocumentSerializer documentSerializer;
	
	private static final String MIMETYPE = "application/epub+zip";
	private static final int BUFFER_SIZE = 128 * 1024;
	
	public Epub3PublicationWriter(ContainerSaver saver) {
		this.saver = saver;
		this.documentBuilder = XmlServices.newBuilder();
		this.documentSerializer = XmlServices.newSerializer();
	}

	@Override
	public void write(Publication pub) {
		try {
			writeAll(pub);
		} catch (Exception e) {
			throw new EpubException(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		try {
			this.saver.close();
		} catch (IOException e) {
			// TODO:
			throw new EpubException(e.getMessage(), e);
		}
	}
	
	private void writeAll(Publication publication) throws Exception {
		writeMimeType();
		writeContainerDocument(publication);
		writeAllRenditions(publication);
	}
	
	private void writeAllRenditions(Publication publication) throws Exception {
		for (Rendition rendition: publication) {
			writeRendition(rendition);
		}
	}
	
	private void writeRendition(Rendition rendition) throws Exception {
		writePackageDocument(rendition);
		writeAllResources(rendition);
	}
	
	private void writeMimeType() throws Exception {
		byte[] content = MIMETYPE.getBytes();
		saver.save("mimetype", content);
	}
	
	private void writeContainerDocument(Publication publication) {
		ContainerDocumentBuilder builder = new ContainerDocumentBuilder(documentBuilder); 
		Document document = builder.publication(publication).build();
		writeXmlDocument("META-INF/container.xml", document);
	}

	private void writePackageDocument(Rendition rendition) {
		PackageDocumentBuilder builder = new PackageDocumentBuilder(documentBuilder); 
		Document document = builder.rendition(rendition).build();
		writeXmlDocument(rendition.getLocation().getPath(), document);
	}

	private void writeAllResources(Rendition rendition) throws Exception {
		for (Manifest.Item item: rendition.getManifest()) {
			writeResource(item);
		}
	}
	
	private void writeResource(Manifest.Item item) throws Exception {
		PublicationResource resource = item.getResource();
		String entryName = resource.getLocation().getPath();
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = resource.openContent()) {
			saver.save(entryName, out->{
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			});
		}
	}
	
	private void writeXmlDocument(String location, Document document) {
		try {
			saver.save(location, stream->{
				documentSerializer.serialize(stream, document);
			});
		} catch (Exception e) {
			// TODO:
			throw new EpubException("", e);
		}
	}
}
