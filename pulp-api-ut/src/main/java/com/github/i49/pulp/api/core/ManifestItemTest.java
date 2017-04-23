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
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.Rendition;

/**
 * Unit tests for {@link Manifest.Item}.
 */
public class ManifestItemTest {

	private PublicationResourceBuilderFactory factory;
	private Manifest manifest;

	@Before
	public void setUp() {
		Publication publication = Epub.createPublication();
		Rendition rendition = publication.addRendition("EPUB/package.opf");
		factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		manifest = rendition.getManifest();
	}

	/* getResource */

	@Test
	public void getResource_shouldReturnAssignedResource() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item.getResource()).isEqualTo(resource);
	}
	
	/* getLocation */
	
	@Test
	public void getLocation_shouldReturnLocalLocation() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(resource.getLocation()).isEqualTo(URI.create("EPUB/chapter1.xhtml"));
		assertThat(item.getLocation()).isEqualTo(URI.create("chapter1.xhtml"));
	}

	@Test
	public void getLocation_shouldReturnRemoteLocation() {
		String location = "http://example.org/chapter1.xhtml";
		PublicationResource resource = factory.newBuilder(location).build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item.getLocation()).isEqualTo(URI.create(location));
	}
	
	@Test
	public void getLocation_shouldReturnLocationOtherThanCurrentPackage() {
		PublicationResource resource = factory.newBuilder("../other/chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(resource.getLocation()).isEqualTo(URI.create("other/chapter1.xhtml"));
		assertThat(item.getLocation()).isEqualTo(URI.create("../other/chapter1.xhtml"));
	}
	
	/* isCoverImage */
	
	@Test
	public void isCoverImage_shouldReturnFalseByDefault() {
		PublicationResource resource = factory.newBuilder("cover.png").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item.isCoverImage()).isFalse();
	}

	@Test
	public void isCoverImage_shouldReturnTrueIfSpecifiedAsCoverImage() {
		PublicationResource resource = factory.newBuilder("cover.png").build();
		Manifest.Item item = manifest.add(resource).asCoverImage();
		assertThat(item.isCoverImage()).isTrue();
	}

	/* isNavigation */
	
	@Test
	public void isNavigation_shouldReturnFalseByDefault() {
		PublicationResource resource = factory.newBuilder("nav.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item.isNavigation()).isFalse();
	}

	@Test
	public void isNavigation_shouldReturnTrueIfSpecifiedAsNavigation() {
		PublicationResource resource = factory.newBuilder("nav.xhtml").build();
		Manifest.Item item = manifest.add(resource).asNavigation();
		assertThat(item.isNavigation()).isTrue();
	}

	/* isScripted */

	@Test
	public void isScripted_shouldReturnFalseByDefault() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item.isScripted()).isFalse();
	}

	@Test
	public void isScripted_shouldReturnTrueIfSpecifiedAsScripted() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource).scripted(true);
		assertThat(item.isScripted()).isTrue();
	}

	@Test
	public void isScripted_shouldReturnFalseIfSpecifiedAsNotScripted() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource).scripted(false);
		assertThat(item.isScripted()).isFalse();
	}
}
