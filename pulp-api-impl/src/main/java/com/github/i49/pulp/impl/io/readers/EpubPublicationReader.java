/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.impl.io.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.EpubParsingException;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationReader;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilder;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.spi.EpubService;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.AbstractContainer;
import com.github.i49.pulp.impl.io.containers.ReadableContainer;
import com.github.i49.pulp.impl.xml.XmlServices;

/**
 * An implementation of {@link PublicationReader}.
 */
public class EpubPublicationReader implements PublicationReader, RenditionResourceFinder {

	private final ReadableContainer container;
	private final EpubService service;
	private final DocumentBuilder documentBuilder;

	private String currentLocation;
	
	private Publication publication;
	private Rendition currentRendition;
	private PublicationResourceBuilderFactory currentResourceFactory;
	
	public EpubPublicationReader(ReadableContainer loader, EpubService service) {
		this.container = loader;
		this.service = service;
		this.documentBuilder = XmlServices.newBuilder();
	}

	@Override
	public Publication read() {
		try {
			return parseAll();
		} catch (Exception e) {
			throw new EpubParsingException(e.getMessage(), e, currentLocation, container.getPath());
		}
	}

	@Override
	public void close() {
		try {
			this.container.close();
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_IO_FAILURE(container.getPath()), e);
		}
	}
	
	protected Publication parseAll() throws IOException, SAXException {
		this.publication = createPublication();
		Iterator<Rendition> it = parseContainerDocument(publication);
		while (it.hasNext()) {
			buildRendition(it.next());
		}
		return this.publication;
	}
	
	protected Publication createPublication() {
		return service.createPublication();
	}
	
	protected Iterator<Rendition> parseContainerDocument(Publication publication) throws IOException, SAXException {
		String location = AbstractContainer.CONTAINER_DOCUMENT_LOCATION;
		Document document = readXmlDocument(location);
		ContainerDocumentParser parser = createContainerDocumentParser(document);
		return parser.parse(document, publication);
	}
	
	protected ContainerDocumentParser createContainerDocumentParser(Document document) {
		String version = ContainerDocumentParser.probe(document);
		if ("1.0".equals(version)) {
			return new ContainerDocumentParser1();
		}
		throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
	}
	
	protected void buildRendition(Rendition rendition) throws IOException, SAXException {
		this.currentRendition = rendition;
		this.currentResourceFactory = this.service.createResourceBuilderFactory(rendition.getLocation());
		String location = rendition.getLocation().getPath();
		Document document = readXmlDocument(location);
		PackageDocumentParser parser = createPackageDocumentParser(document);
		parser.parse(document, rendition, this.service, this);
	}
	
	protected PackageDocumentParser createPackageDocumentParser(Document document) {
		String version = PackageDocumentParser.probe(document);
		if ("3.0".equals(version)) {
			return new PackageDocumentParser3();
		}
		throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
	}
	
	private Document readXmlDocument(String location) throws IOException, SAXException {
		setCurrentLocation(location);
		try (InputStream in = container.openItemToRead(location)) {
			Document document = documentBuilder.parse(in); 
			document.setDocumentURI(location);
			return document;
		}
	}

	private void setCurrentLocation(String location) {
		this.currentLocation = location;
	}
	
	@Override
	public PublicationResource findResource(String href, String mediaType) {
		URI location = this.currentRendition.resolve(href);
		String path = location.getPath();
		if (this.publication.containsResource(path)) {
			return this.publication.getResource(path);
		} else {
			return buildResource(location, href, mediaType);
		}
	}
	
	private PublicationResource buildResource(URI location, String href, String mediaType) {
		PublicationResourceBuilder builder = this.currentResourceFactory.newBuilder(href);
		builder.ofType(mediaType);
		if (location.isAbsolute()) {
			builder.source(location);
		} else {
			String path = location.getPath();
			if (this.container.contains(path)) {
				builder.source(this.container.getContentSource(path));
			} else {
				throw new EpubException(Messages.RESOURCE_MISSING(location));
			}
		}
		return builder.build();
	}
}
