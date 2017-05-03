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

import static com.github.i49.pulp.impl.xml.XmlAssertions.assertOn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.metadata.PropertyFactory;

/**
 * Parser for parsing a package document that contains the information about a EPUB rendition.
 */
interface PackageDocumentParser extends PackageDocumentProcessor {
	
	/**
	 * Probes the version of the specification.
	 * 
	 * @param document the XML document to probe.
	 * @return the version of the specification.
	 */
	static String probe(Document document) {
	
		Element rootElement = document.getDocumentElement();
		
		assertOn(rootElement)
		.hasName("package", NAMESPACE_URI)
		.hasNonEmptyAttribute("version");

		return rootElement.getAttribute("version");
	}
	
	/**
	 * Parses the package document.
	 * 
	 * @param document the package document.
	 * @param rendition the rendition to build.
	 * @param propertyFactory the factory for producing metadata properties.
	 * @param resourceFinder the finder to find the resources required by the rendition.
	 */
	void parse(Document document, Rendition rendition, PropertyFactory propertyFactory, RenditionResourceFinder resourceFinder);
}
