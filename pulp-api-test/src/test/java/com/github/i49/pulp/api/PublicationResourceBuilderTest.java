package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

public class PublicationResourceBuilderTest {

	private static final URI BASE_URI = URI.create("EPUB/package.opf");
	
	private PublicationResourceBuilderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createResourceBuilderFactory(BASE_URI);
	}
	
	@Test
	public void build_shouldAdoptSpecifiedCoreMediaType() {
		PublicationResourceBuilder builder = factory.newBuilder("chapter1.xhtml");
		PublicationResource r = builder.ofType(CoreMediaType.APPLICATION_XHTML_XML).build();
		assertThat(r.getMediaType()).isEqualTo(CoreMediaType.APPLICATION_XHTML_XML);
	}
	
	@Test
	public void build_shouldGuessMediaType() {
		PublicationResourceBuilder builder = factory.newBuilder("chapter1.xhtml");
		PublicationResource r = builder.build();
		assertThat(r.getMediaType()).isEqualTo(CoreMediaType.APPLICATION_XHTML_XML);
	}

	@Test
	public void build_shouldThrowExceptionIfMediaTypeNotDetected() {
		PublicationResourceBuilder builder = factory.newBuilder("figure.unknown");
		assertThatThrownBy(()->{
			builder.build();
		}).isInstanceOf(EpubException.class).hasMessageContaining("figure.unknown");
	}
	
	@Test
	public void build_shouldThrowExceptionIfMediaTypeIsInvalid() {
		PublicationResourceBuilder builder = factory.newBuilder("chapter1.xhtml");
		assertThatThrownBy(()->{
			builder.ofType("abc");
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("abc");
	}	
}
