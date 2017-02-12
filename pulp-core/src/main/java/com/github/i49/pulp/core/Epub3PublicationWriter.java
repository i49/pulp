package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.Rendition;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class Epub3PublicationWriter implements PublicationWriter {

	private final Archiver archiver;
	private boolean closed;
	private final XmlService xmlService;
	
	private String packageDir = DEFAULT_PACKAGE_DIR;

	private static final String MIMETYPE = "application/epub+zip";
	private static final String DEFAULT_PACKAGE_DIR = "EPUB/";
	private static final String PACKAGE_DOCUMENT_NAME = "package.opf";
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
		writeContainerDocument();
		writeRendition(publication.getDefaultRendition());
	}
	
	private void writeRendition(Rendition rendition) throws IOException {
		writePackageDocument(rendition);
		writeAllResources(rendition);
	}
	
	private void writeMimeType() throws IOException {
		byte[] content = MIMETYPE.getBytes();
		archiver.appendRaw("mimetype", content);
	}
	
	private void writeContainerDocument() throws IOException {
		ContainerDocumentBuilder builder = new ContainerDocumentBuilder(xmlService.createDocument()); 
		Document document = builder.build(this.packageDir);
		writeXmlDocument("META-INF/container.xml", document);
	}

	private void writePackageDocument(Rendition rendition) throws IOException {
		PackageDocumentBuilder builder = new PackageDocumentBuilder(xmlService.createDocument(), rendition); 
		Document document = builder.build();
		writeXmlDocument(this.packageDir + PACKAGE_DOCUMENT_NAME, document);
	}

	private void writeAllResources(Rendition rendition) throws IOException {
		for (Rendition.Item item: rendition.getItems()) {
			writeResource(item);
		}
	}
	
	private void writeResource(Rendition.Item item) throws IOException {
		String entryName = this.packageDir + item.getPath();
		PublicationResource resource = item.getResource();
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = resource.openStream(); OutputStream out = archiver.append(entryName)) {
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
