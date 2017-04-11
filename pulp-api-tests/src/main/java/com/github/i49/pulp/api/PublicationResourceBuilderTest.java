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

package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PublicationResourceBuilder}.
 */
public class PublicationResourceBuilderTest {

	private static final URI BASE_URI = URI.create("EPUB/package.opf");
	
	private PublicationResourceBuilderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createResourceBuilderFactory(BASE_URI);
	}
	
	/* ofType() */

	@Test
	public void ofType_shouldThrowExceptionIfMediaTypeInvalid() {
		PublicationResourceBuilder builder = factory.newBuilder("chapter1.xhtml");
		assertThatThrownBy(()->{
			builder.ofType("xyz");
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("xyz");
	}
	
	/* empty() */
	
	@Test
	public void empty_shouldProvideEmptyContent() throws IOException {
		PublicationResourceBuilder builder = factory.newBuilder("stylesheet.css");
		PublicationResource r = builder.empty().build();
		assertThat(r.getContent()).hasSize(0);
	}
	
	/* source(byte[]) */
	
	@Test
	public void source_shouldProvideByteArrayContent() throws IOException {
		byte[] content = "h1 {}".getBytes();
		PublicationResourceBuilder builder = factory.newBuilder("stylesheet.css");
		PublicationResource r = builder.source(content).build();
		assertThat(r.getContent()).containsExactly(content);		
	}
	
	/* build() */
	
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
