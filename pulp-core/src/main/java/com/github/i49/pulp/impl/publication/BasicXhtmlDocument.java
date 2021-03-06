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

package com.github.i49.pulp.impl.publication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.i49.pulp.api.publication.CoreMediaType;
import com.github.i49.pulp.api.publication.XhtmlDocument;

class BasicXhtmlDocument extends BasicXmlDocument implements XhtmlDocument {

	public static final String NAMESPACE_URI = "http://www.w3.org/1999/xhtml";
	
	public BasicXhtmlDocument(PublicationResourceLocation location) {
		super(location, CoreMediaType.APPLICATION_XHTML_XML);
	}

	@Override
	public String getTitle() {
		String title = null;
		Document document = getDocument();
		if (document != null) {
			NodeList nodes = document.getElementsByTagNameNS(NAMESPACE_URI, "title");
			if (nodes.getLength() > 0) {
				Element element = (Element)nodes.item(0);
				title = element.getTextContent();
			}
		}
		return title;
	}
}
