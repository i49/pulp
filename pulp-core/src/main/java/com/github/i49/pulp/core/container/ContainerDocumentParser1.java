package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Elements.childElements;
import static com.github.i49.pulp.core.container.Elements.firstChildElement;
import static com.github.i49.pulp.core.container.XmlAssertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.StandardMediaType;

/**
 * Parser of EPUB Container Document version 1.0.
 */
class ContainerDocumentParser1 extends ContainerDocumentParser {

	private final List<Element> rootfiles = new ArrayList<>();
	
	public ContainerDocumentParser1(Element rootElement) {
		super(rootElement);
	}

	@Override
	public Iterator<Rendition> parseFor(Publication publication) {
		Element rootfiles = firstChildElement(rootElement, NAMESPACE_URI);
		assertOn(rootfiles).hasName("rootfiles", NAMESPACE_URI);
		for (Element rootfile: childElements(rootfiles, NAMESPACE_URI)) {
			assertOn(rootfile).hasName("rootfile", NAMESPACE_URI);
			parseRootFile(rootfile);
		}
		return new RenditionIterator(publication);
	}
	
	protected void parseRootFile(Element rootfile) {
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

		private Publication publication;
		private int nextIndex = 0;
		
		public RenditionIterator(Publication publication) {
			this.publication = publication;
		}
		
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
			return this.publication.addRendition(location);
		}
	}
}