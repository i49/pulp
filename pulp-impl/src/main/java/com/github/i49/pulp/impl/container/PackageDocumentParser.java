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

import static com.github.i49.pulp.impl.xml.XmlAssertions.*;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.impl.Messages;

/**
 * Parser interface for parsing a package document.
 */
abstract class PackageDocumentParser implements PackageDocumentProcessor {
	
	protected final Element rootElement;
	protected final PublicationBuilder builder;
	
	protected PackageDocumentParser(Element rootElement, PublicationBuilder builder) {
		this.rootElement = rootElement;
		this.builder = builder;
	}
	
	/**
	 * Parses a Package Document which describes a rendition.
	 * 
	 * @param rendition the rendition to build.
	 */
	public abstract void parse();
	
	/**
	 * Creates a new parser.
	 * 
	 * @param rootElement the root element of the document.
	 * @param builder the builder for building a publication.
	 * @return newly created parser.
	 */
	public static PackageDocumentParser create(Element rootElement, PublicationBuilder builder) {
		
		assertOn(rootElement)
			.hasName("package", NAMESPACE_URI)
			.hasNonEmptyAttribute("version");

		PackageDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("3.0".equals(version)) {
			parser = new PackageDocumentParser3(rootElement, builder);
		} else {
			throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
		}
		return parser;
	}
}
