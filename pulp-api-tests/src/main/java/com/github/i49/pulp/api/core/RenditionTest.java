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

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;

/**
 * Unit tests for {@link Rendition}.
 */
public class RenditionTest {

	private Publication publication;
	private Rendition rendition;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
		rendition = publication.addRendition("EPUB/package.opf");
	}
	
	/* getPublication() */

	@Test
	public void getPublication_shouldReturnPublication() {
		assertThat(rendition.getPublication()).isEqualTo(publication);
	}

	/* getLocation() */
	
	@Test
	public void getLocation_shouldReturnLocation() {
		assertThat(rendition.getLocation()).isEqualTo(URI.create("EPUB/package.opf"));
	}
	
	/* resolve() */
	
	@Test
	public void resolve_shouldResolveSiblingLocation() {
		URI resolved = rendition.resolve("chapter1.xhtml");
		assertThat(resolved).isEqualTo(URI.create("EPUB/chapter1.xhtml"));
	}

	@Test
	public void resolve_shouldResolveParentLocation() {
		URI resolved = rendition.resolve("../other/chapter1.xhtml");
		assertThat(resolved).isEqualTo(URI.create("other/chapter1.xhtml"));
	}
	
	/* getMetadata() */
	
	@Test
	public void getMetadata_shouldReturnMetadata() {
		assertThat(rendition.getMetadata()).isNotNull();
	}

	/* getManifest() */
	
	@Test
	public void getManifest_shouldReturnManifest() {
		assertThat(rendition.getManifest()).isNotNull();
	}

	/* getSpine() */
	
	@Test
	public void getSpine_shouldReturnManifest() {
		assertThat(rendition.getSpine()).isNotNull();
	}
}
