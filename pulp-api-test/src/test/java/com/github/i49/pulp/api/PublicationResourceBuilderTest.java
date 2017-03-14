package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class PublicationResourceBuilderTest {

	private Publication publication;
	private PublicationResourceRegistry registry;
	
	@Before
	public void setUp() {
		publication = Epub.createPublication();
		registry = publication.getResourceRegistry();
	}
	
	@Test
	public void coreMediaType() {
		PublicationResourceBuilder builder = registry.builder("title.xhtml");
		PublicationResource r = builder.ofType(CoreMediaType.APPLICATION_XHTML_XML).build();
		assertThat(r.getMediaType()).isEqualTo(CoreMediaType.APPLICATION_XHTML_XML);
	}
	
	@Test
	public void guessedMediaType() {
		PublicationResourceBuilder builder = registry.builder("title.xhtml");
		PublicationResource r = builder.build();
		assertThat(r.getMediaType()).isEqualTo(CoreMediaType.APPLICATION_XHTML_XML);
	}

	@Test
	public void unknowndMediaType() {
		PublicationResourceBuilder builder = registry.builder("figure.unknown");
		assertThatThrownBy(()->{
			builder.build();
		}).isInstanceOf(EpubException.class).hasMessageContaining("figure.unknown");
	}
	
	@Test
	public void invalidMediaType() {
		PublicationResourceBuilder builder = registry.builder("title.xhtml");
		assertThatThrownBy(()->{
			builder.ofType("abc");
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("abc");
	}	
}