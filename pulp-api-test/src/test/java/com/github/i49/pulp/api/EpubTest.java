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

import java.net.URI;

import org.junit.Test;

/**
 * Unit tests for {@link Epub}.
 */
public class EpubTest {

	/* createPublication() */
	
	@Test
	public void createPublication_shouldCreateEmptyPublication() {
		Publication p = Epub.createPublication();
		assertThat(p.getNumberOfRenditions()).isEqualTo(0);
		assertThat(p.getDefaultRendition()).isNull();
	}
	
	/* createResourceBuilderFactory() */
	
	@Test
	public void createResourceBuilderFactory_shouldCreateValidResourceBuilderFactory() {
		URI baseURI = URI.create("EPUB/package.opf");
		PublicationResourceBuilderFactory f = Epub.createResourceBuilderFactory(baseURI);
		assertThat(f.getBaseURI()).isEqualTo(URI.create("EPUB/package.opf"));
	}
	
	@Test
	public void createResourceBuilderFactory_shouldThrowExceptionIfBaseURIIsNull() {
		assertThatThrownBy(()->{
			Epub.createResourceBuilderFactory(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void createResourceBuilderFactory_shouldThrowExceptionIfBaseURIIsRemote() {
		URI baseURI = URI.create("http://example.org/package.opf");
		assertThatThrownBy(()->{
			Epub.createResourceBuilderFactory(baseURI);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("http://example.org/package.opf");
	}

	@Test
	public void createResourceBuilderFactory_shouldThrowExceptionIfBaseURIIsNotPathRootless() {
		URI baseURI = URI.create("/EPUB/package.opf");
		assertThatThrownBy(()->{
			Epub.createResourceBuilderFactory(baseURI);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("/EPUB/package.opf");
	}
}
