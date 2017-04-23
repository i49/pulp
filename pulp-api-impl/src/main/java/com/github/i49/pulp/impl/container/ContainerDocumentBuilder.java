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

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.impl.publication.StandardMediaType;

/**
 * A builder class for building {@code ContainerDocument}.
 */
public class ContainerDocumentBuilder implements ContainerDocumentProcessor {

	private static final String VERSION = "1.0";
	
	private final DocumentBuilder documentBuilder;
	private Publication publication;
	private Document document;
	
	ContainerDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

	public Document build(Publication publication) {
		this.publication = publication;
		this.document = this.documentBuilder.newDocument();
		this.document.appendChild(container());
		return this.document;
	}
	
	/**
	 * Creates container element at the document root.
	 * @return created element.
	 */
	private Element container() {
		Element container = document.createElementNS(NAMESPACE_URI, "container");
		container.setAttribute("version", VERSION);
		container.appendChild(rootfiles());
		return container;
	}
	
	/**
	 * Creates rootfiles element. 
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
	 * Creates rootfile element. 
	 * @return created element.
	 */
	private Element rootfile(Rendition rendition) {
		Element rootfile = document.createElementNS(NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", rendition.getLocation().getPath());
		rootfile.setAttribute("media-type", StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString());
		return rootfile;
	}
}