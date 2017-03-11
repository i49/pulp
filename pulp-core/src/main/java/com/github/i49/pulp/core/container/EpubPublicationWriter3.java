package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Message.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.DocumentSerializer;
import com.github.i49.pulp.core.StandardMediaType;
import com.github.i49.pulp.core.XmlServices;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class EpubPublicationWriter3 implements PublicationWriter {

	private final WriteableContainer container;
	private final DocumentBuilder documentBuilder;
	private final DocumentSerializer documentSerializer;
	
	private static final int BUFFER_SIZE = 128 * 1024;
	
	public EpubPublicationWriter3(WriteableContainer saver) {
		this.container = saver;
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
			this.container.close();
		} catch (IOException e) {
			throw new EpubException(CONTAINER_NOT_CLOSEABLE.format(container.getPath()), e);
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
		byte[] content = StandardMediaType.APPLICATION_EPUB_ZIP.toString().getBytes();
		container.writeItem(AbstractContainer.MIMETYPE_LOCATION, content);
	}
	
	private void writeContainerDocument(Publication publication) {
		ContainerDocumentBuilder builder = new ContainerDocumentBuilder(documentBuilder);
		Document document = builder.build(publication);
		writeXmlDocument(AbstractContainer.CONTAINER_DOCUMENT_LOCATION, document);
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
	
	private void writeResource(Manifest.Item item) throws IOException {
		PublicationResource resource = item.getResource();
		String location = resource.getLocation().getPath();
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = resource.openContent(); OutputStream out = container.openItemToWrite(location)) {
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		}
	}
	
	private void writeXmlDocument(String location, Document document) {
		try (OutputStream out = container.openItemToWrite(location)) {
			documentSerializer.serialize(out, document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
