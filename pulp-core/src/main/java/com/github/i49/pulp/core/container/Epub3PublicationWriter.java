package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.XmlService;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class Epub3PublicationWriter implements PublicationWriter {

	private final Archiver archiver;
	private boolean closed;
	private final XmlService xmlService;
	
	private static final String MIMETYPE = "application/epub+zip";
	private static final int BUFFER_SIZE = 128 * 1024;
	
	public Epub3PublicationWriter(OutputStream stream, XmlService xmlService) throws Exception {
		this.archiver = new ZipArchiver(stream);
		this.closed = false;
		this.xmlService = xmlService;
	}

	@Override
	public void write(Publication pub) {
		try {
			writeAll(pub);
		} catch (IOException e) {
			throw new EpubException(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		if (!this.closed) {
			try {
				this.archiver.close();
				this.closed = true;
			} catch (IOException e) {
				throw new EpubException(e.getMessage(), e);
			}
		}
	}
	
	private void writeAll(Publication publication) throws IOException {
		writeMimeType();
		writeContainerDocument(publication);
		writeAllRenditions(publication);
	}
	
	private void writeAllRenditions(Publication publication) throws IOException {
		for (Rendition rendition: publication) {
			writeRendition(rendition);
		}
	}
	
	private void writeRendition(Rendition rendition) throws IOException {
		writePackageDocument(rendition);
		writeAllResources(rendition);
	}
	
	private void writeMimeType() throws IOException {
		byte[] content = MIMETYPE.getBytes();
		archiver.appendRaw("mimetype", content);
	}
	
	private void writeContainerDocument(Publication publication) throws IOException {
		ContainerDocumentBuilder builder = new ContainerDocumentBuilder(xmlService); 
		Document document = builder.publication(publication).build();
		writeXmlDocument("META-INF/container.xml", document);
	}

	private void writePackageDocument(Rendition rendition) throws IOException {
		PackageDocumentBuilder builder = new PackageDocumentBuilder(xmlService); 
		Document document = builder.rendition(rendition).build();
		writeXmlDocument(rendition.getLocation(), document);
	}

	private void writeAllResources(Rendition rendition) throws IOException {
		for (Manifest.Item item: rendition.getManifest()) {
			writeResource(item);
		}
	}
	
	private void writeResource(Manifest.Item item) throws IOException {
		PublicationResource resource = item.getResource();
		String entryName = resource.getLocation().getPath();
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = resource.openContent(); OutputStream out = archiver.append(entryName)) {
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		}
	}
	
	private void writeXmlDocument(String entryName, Document document) throws IOException {
		try (OutputStream stream = archiver.append(entryName)) {
			xmlService.writeDocument(stream, document);
		}
	}
}
