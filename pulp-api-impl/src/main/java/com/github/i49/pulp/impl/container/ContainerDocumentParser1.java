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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.impl.publication.StandardMediaType;
import com.github.i49.pulp.impl.xml.Nodes;

/**
 * Parser of EPUB Container Document version 1.0.
 */
class ContainerDocumentParser1 extends ContainerDocumentParser {

	private final List<Element> rootfiles = new ArrayList<>();
	
	@Override
	public Iterator<Rendition> parse(Document document) {
		Element rootElement = document.getDocumentElement();
		assertOn(rootElement).contains("rootfiles");
		Iterator<Element> it = Nodes.children(rootElement, NAMESPACE_URI);
		parseRootfiles(it.next());
		return new RenditionIterator();
	}
		
	protected void parseRootfiles(Element rootfiles) {
		assertOn(rootfiles).hasName("rootfiles", NAMESPACE_URI).contains("rootfile");
		Iterator<Element> it = Nodes.children(rootfiles, NAMESPACE_URI);
		while (it.hasNext()) {
			parseRootfile(it.next());
		}
	}
	
	protected void parseRootfile(Element rootfile) {
		assertOn(rootfile).hasName("rootfile", NAMESPACE_URI)
		                  .hasNonEmptyAttribute("media-type")
		                  .hasNonEmptyAttribute("full-path");
		String mediaType = rootfile.getAttribute("media-type");
		String expectedMediaType = StandardMediaType.APPLICATION_OEBPS_PACKAGE_XML.toString();
		if (expectedMediaType.equals(mediaType)) {
			String location = rootfile.getAttribute("full-path");
			if (location != null) {
				this.rootfiles.add(rootfile);
			}
		}
	}
	
	protected class RenditionIterator implements Iterator<Rendition> {

		private int nextIndex = 0;
		
		@Override
		public boolean hasNext() {
			return this.nextIndex < rootfiles.size();
		}

		@Override
		public Rendition next() {
			if (this.nextIndex >= rootfiles.size()) {
				throw new NoSuchElementException();
			}
			Element e = rootfiles.get(this.nextIndex++);	
			return createRendition(e);
		}
		
		protected Rendition createRendition(Element rootfile) {
			String location = rootfile.getAttribute("full-path");
			return getPublication().addRendition(location);
		}
	}
}