package com.github.i49.pulp.api;

import org.junit.Test;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;

public class XhtmlDocumentTest {
	
	private XhtmlDocument resource;

	@Before
	public void setUp() throws URISyntaxException {
		PublicationResourceFactory f = Epub.createResourceFactory();
		URI uri = getClass().getResource("sample.xhtml").toURI();
		this.resource = (XhtmlDocument)f.createResource("sample.xhtml", CoreMediaType.APPLICATION_XHTML_XML, uri);
	}
	
	@Test
	public void testGetTitle() {
		assertThat(resource.getTitle(), equalTo("Hello World"));
	}
}
