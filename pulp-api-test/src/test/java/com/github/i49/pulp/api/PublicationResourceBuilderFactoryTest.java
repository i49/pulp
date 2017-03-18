package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

public class PublicationResourceBuilderFactoryTest {

	private static final URI BASE_URI = URI.create("EPUB/package.opf");
	
	private PublicationResourceBuilderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createResourceBuilderFactory(BASE_URI);
	}

	@Test
	public void newBuilder_shouldCreateLocalResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldCreateRelativeResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("../other/images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldThrowExceptionIfLocalLocationIsInvalid() {
		assertThatThrownBy(()->{
			factory.newBuilder("../../images/cover.png");
		}).isInstanceOf(EpubException.class);
	}
	
	@Test
	public void newBuilder_shouldCreateRemoteResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("http://example.org/images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldThrowExceptionIfRemoteLocationIsOpaque() {
		assertThatThrownBy(()->{
			factory.newBuilder("http:./images/cover.png");
		}).isInstanceOf(EpubException.class);
	}
}
