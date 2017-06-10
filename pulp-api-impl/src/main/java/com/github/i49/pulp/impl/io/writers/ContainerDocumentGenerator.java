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

package com.github.i49.pulp.impl.io.writers;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.publication.Publication;
import com.github.i49.pulp.api.publication.Rendition;
import com.github.i49.pulp.impl.io.containers.ContainerDocumentProcessor;
import com.github.i49.pulp.impl.publication.StandardMediaType;

/**
 * A generator for generating {@code ContainerDocument}.
 */
class ContainerDocumentGenerator implements ContainerDocumentProcessor {

	private static final String VERSION = "1.0";
	
	private final DocumentBuilder documentBuilder;
	private Publication publication;
	private Document document;
	
	ContainerDocumentGenerator(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

	/**
	 * Generates a container document.
	 *  
	 * @param publication the publication to be written.
	 * @return generated container document.
	 */
	Document generateDocument(Publication publication) {
		this.publication = publication;
		this.document = this.documentBuilder.newDocument();
		this.document.appendChild(container());
		return this.document;
	}
	
	/**
	 * Creates a container element at the document root.
	 * 
	 * @return created element.
	 */
	private Element container() {
		Element container = document.createElementNS(NAMESPACE_URI, "container");
		container.setAttribute("version", VERSION);
		container.appendChild(rootfiles());
		return container;
	}
	
	/**
	 * Creates a rootfiles element. 
	 * 
	 * @return created element.
	 */
	private Element rootfiles() {
		Element rootfiles = document.createElementNS(NAMESPACE_URI, "rootfiles");
		for (Rendition rendition: this.publication) {
			rootfiles.appendChild(rootfile(rendition));
		}
		return rootfiles;
	}
	
	/**
	 * Creates a rootfile element.
	 *  
	 * @return created element.
	 */
	private Element rootfile(Rendition rendition) {
		Element rootfile = document.createElementNS(NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", rendition.getLocation().getPath());
		rootfile.setAttribute("media-type", StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString());
		return rootfile;
	}
}