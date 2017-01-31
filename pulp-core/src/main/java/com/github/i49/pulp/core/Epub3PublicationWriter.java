package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationWriter;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class Epub3PublicationWriter implements PublicationWriter {

	private static final String MIMETYPE = "application/epub+zip";
	private static final String DEFAULT_PACKAGE_DIR = "EPUB/";
	private static final String PACKAGE_DOCUMENT_NAME = "package.opf";

	private final Archiver archiver;
	private final XmlService xmlService;
	
	private String packageDir = DEFAULT_PACKAGE_DIR;
	
	public Epub3PublicationWriter(OutputStream stream, XmlService xmlService) throws Exception {
		this.archiver = new ZipArchiver(stream);
		this.xmlService = xmlService;
	}

	@Override
	public void write(Publication pub) throws Exception {
		writeAll(pub);
	}

	@Override
	public void close() throws IOException {
		this.archiver.close();
	}
	
	private void writeAll(Publication pub) throws Exception {
		writeMimeType();
		writeContainerXml();
		writePackageDocument(pub);
		writeAllResources(pub);
	}
	
	private void writeMimeType() throws IOException {
		byte[] content = MIMETYPE.getBytes();
		archiver.appendRaw("mimetype", content);
	}
	
	private void writeContainerXml() throws IOException, TransformerException {
		ContainerXmlBuilder builder = new ContainerXmlBuilder(xmlService.createDocument()); 
		Document document = builder.build(packageDir);
		writeXmlDocument("META-INF/container.xml", document);
	}

	private void writePackageDocument(Publication publication) throws IOException, TransformerException {
		PackageDocumentBuilder builder = new PackageDocumentBuilder(xmlService.createDocument(), publication); 
		Document document = builder.build();
		writeXmlDocument(this.packageDir + PACKAGE_DOCUMENT_NAME, document);
	}

	private void writeAllResources(Publication publication) throws IOException {
		for (PublicationResource resource: publication.getAllResources()) {
			writeResource(resource);
		}
	}
	
	private void writeResource(PublicationResource resource) throws IOException {
		String entryName = this.packageDir + resource.getName().toString();
		try (OutputStream out = archiver.append(entryName)) {
			resource.persist(out);
		}
	}
	
	private void writeXmlDocument(String entryName, Document document) throws IOException, TransformerException {
		try (OutputStream stream = archiver.append(entryName)) {
			xmlService.writeDocument(stream, document);
		}
	}
}
