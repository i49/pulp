package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

public class RenditionTest {

	private Publication publication;
	private Rendition rendition;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
		rendition = publication.addRendition("EPUB/package.opf");
	}

	@Test
	public void getPublication_shouldReturnPublication() {
		assertThat(rendition.getPublication()).isEqualTo(publication);
	}
	
	@Test
	public void getLocation_shouldReturnLocation() {
		assertThat(rendition.getLocation()).isEqualTo(URI.create("EPUB/package.opf"));
	}

	@Test
	public void getMetadata_shouldReturnMetadata() {
		assertThat(rendition.getMetadata()).isNotNull();
	}

	@Test
	public void getManifest_shouldReturnManifest() {
		assertThat(rendition.getManifest()).isNotNull();
	}

	@Test
	public void getSpine_shouldReturnManifest() {
		assertThat(rendition.getSpine()).isNotNull();
	}
}
