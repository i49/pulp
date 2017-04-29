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

package com.github.i49.pulp.impl.container;

import static com.github.i49.pulp.impl.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationWriter;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.publication.StandardMediaType;
import com.github.i49.pulp.impl.xml.DocumentSerializer;
import com.github.i49.pulp.impl.xml.XmlServices;

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
	public void write(Publication publication) {
		checkNotNull(publication, "publication");
		try {
			writeAll(publication);
		} catch (Exception e) {
			throw new EpubException(e.getMessage(), e);
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
		rendition.getMetadata().addMandatory();
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
