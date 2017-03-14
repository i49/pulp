package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class PublicationTest {

	private Publication publication;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
	}
	
	@Test
	public void defaultRenditionLocation() {
		Rendition r = publication.addRendition();
		assertThat(r.getLocation().toString()).isEqualTo("EPUB/package.opf");
	}

	@Test
	public void customRenditionLocation() {
		Rendition r = publication.addRendition("OEBPS/package.opf");
		assertThat(r.getLocation().toString()).isEqualTo("OEBPS/package.opf");
	}
	
	@Test
	public void invalidRenditionLocation() {
		assertThatThrownBy(()->{
			publication.addRendition("http://example.org");
		}).isInstanceOf(EpubException.class).hasMessageContaining("http://example.org");
	}
}
