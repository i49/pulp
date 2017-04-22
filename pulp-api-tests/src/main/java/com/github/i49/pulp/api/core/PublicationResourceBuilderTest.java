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

package com.github.i49.pulp.api.core;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.CoreMediaType;
import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilder;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;

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
		Throwable thrown = catchThrowable(()->{
			builder.ofType("xyz");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("xyz");
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
	public void build_shouldFindContentSourceFromSinglePath() throws IOException {
		factory.setSourcePath(EpubPaths.get("valid-single-rendition/EPUB"));
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		String content = new String(resource.getContent(), StandardCharsets.UTF_8);
		assertThat(content).containsSequence("<h1>Chapter 1</h1>");
	}

	@Test
	public void build_shouldFindContentSourceFromSecondPath() throws IOException {
		Path path1 = EpubPaths.get("path/to/nonexistent");
		Path path2 = EpubPaths.get("valid-single-rendition/EPUB");
		factory.setSourcePath(path1, path2);
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		String content = new String(resource.getContent(), StandardCharsets.UTF_8);
		assertThat(content).containsSequence("<h1>Chapter 1</h1>");
	}
	
	@Test
	public void build_shouldThrowExceptionIfContentSourceNotFound() {
		factory.setSourcePath(EpubPaths.get("path/to/nonexistent"));
		Throwable thrown = catchThrowable(()->{
			factory.newBuilder("chapter1.xhtml").build();
		});
		assertThat(thrown).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	@Test
	public void build_shouldThrowExceptionIfMediaTypeNotDetected() {
		PublicationResourceBuilder builder = factory.newBuilder("figure.unknown");
		Throwable thrown = catchThrowable(()->{
			builder.build();
		});
		assertThat(thrown).isInstanceOf(EpubException.class).hasMessageContaining("figure.unknown");
	}
	
	@Test
	public void build_shouldThrowExceptionIfMediaTypeIsInvalid() {
		PublicationResourceBuilder builder = factory.newBuilder("chapter1.xhtml");
		Throwable thrown = catchThrowable(()->{
			builder.ofType("abc");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("abc");
	}	
}
