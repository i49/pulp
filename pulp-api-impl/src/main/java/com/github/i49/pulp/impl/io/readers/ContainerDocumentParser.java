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

import static com.github.i49.pulp.impl.xml.XmlAssertions.assertOn;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.impl.io.containers.ContainerDocumentProcessor;

interface ContainerDocumentParser extends ContainerDocumentProcessor {

	static String probe(Document document) {
	
		Element rootElement = document.getDocumentElement();
		
		assertOn(rootElement)
		.hasName("container", NAMESPACE_URI)
		.hasNonEmptyAttribute("version");

		return rootElement.getAttribute("version");
	}
	
	Iterator<Rendition> parse(Document document, Publication publication);
}
