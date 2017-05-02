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

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.impl.base.Messages;

class ContainerDocumentParserFactory {

	public ContainerDocumentParser newParser(Document document) {
		
		Element rootElement = document.getDocumentElement();
		
		assertOn(rootElement)
		.hasName("container", ContainerDocumentParser.NAMESPACE_URI)
		.hasNonEmptyAttribute("version");

		ContainerDocumentParser parser = null;
		String version = rootElement.getAttribute("version");
		if ("1.0".equals(version)) {
			parser = new ContainerDocumentParser1();
		} else {
			throw new EpubException(Messages.XML_DOCUMENT_VERSION_UNSUPPORTED(version));
		}
		
		return parser;
	}
}