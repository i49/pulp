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

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Manifest type.
 */
public class ManifestTest {

	private Publication publication;
	private PublicationResourceBuilderFactory factory;
	private Manifest manifest;
	
	@Before
	public void setUp() {
		publication = Epub.createPublication();
		Rendition rendition = publication.addRendition("EPUB/package.opf");
		factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		manifest = rendition.getManifest();
	}

	/* contains */
	
	@Test
	public void contains_shouldReturnTrueIfItemIsAdded() {
		Manifest.Item item = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		assertThat(manifest.contains(item)).isTrue();
	}

	@Test
	public void contains_shouldReturnFalseIfItemIsRemoved() {
		Manifest.Item item = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		manifest.remove(item);
		assertThat(manifest.contains(item)).isFalse();
	}
	
	@Test
	public void contains_shouldReturnFalseIfItemIsNull() {
		assertThat(manifest.contains(null)).isFalse();
	}

	/* get */
	
	@Test
	public void get_shouldReturnItemIfItemIsContained() {
		Manifest.Item item = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		assertThat(manifest.get("chapter1.xhtml")).isEqualTo(item);
	}
	
	@Test
	public void get_shouldThrowExceptionIfItemIsNotContained() {
		assertThatThrownBy(()->{
			@SuppressWarnings("unused")
			Manifest.Item item = manifest.get("chapter1.xhtml");
		}).isInstanceOf(NoSuchElementException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	/* find */

	@Test
	public void find_shouldReturnItemIfManifestContainsIt() {
		Manifest.Item item = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		Optional<Manifest.Item> found = manifest.find("chapter1.xhtml");
		assertThat(found).hasValue(item);
	}

	@Test
	public void find_shouldReturnEmptyIfManifestDoesNotContainIt() {
		Optional<Manifest.Item> found = manifest.find("chapter1.xhtml");
		assertThat(found).isEmpty();
	}

	@Test
	public void find_shouldReturnEmptyIfItemIsNull() {
		Optional<Manifest.Item> found = manifest.find(null);
		assertThat(found).isEmpty();
	}
	
	/* findCoverImage */
	
	@Test
	public void findCoverImage_shouldReturnItemIfFound() {
		PublicationResource r = factory.newBuilder("cover.png").build();
		Manifest.Item item = manifest.add(r).asCoverImage();
		Optional<Manifest.Item> found = manifest.findCoverImage();
		assertThat(found).hasValue(item);
	}

	@Test
	public void findCoverImage_shouldReturnNullIfNotFound() {
		Optional<Manifest.Item> found = manifest.findCoverImage();
		assertThat(found).isEmpty();
	}

	/* findNavigationDocument */
	
	@Test
	public void findNavigationDocument_shouldReturnItemIfFound() {
		PublicationResource r = factory.newBuilder("nav.xhtml").build();
		Manifest.Item item = manifest.add(r).asNavigation();
		Optional<Manifest.Item> found = manifest.findNavigationDocument();
		assertThat(found).hasValue(item);
	}

	@Test
	public void findNavigationDocument_shouldReturnNullfNotFound() {
		Optional<Manifest.Item> found = manifest.findNavigationDocument();
		assertThat(found).isEmpty();
	}
	
	/* getNumberOfItems */

	@Test
	public void getNumberOfItems_shouldReturnZeroByDefault() {
		assertThat(manifest.getNumberOfItems()).isEqualTo(0);
	}
	
	@Test
	public void getNumberOfItems_shouldReturnCorrectNumber() {
		manifest.add(factory.newBuilder("chapter1.xhtml").build());
		manifest.add(factory.newBuilder("chapter2.xhtml").build());
		assertThat(manifest.getNumberOfItems()).isEqualTo(2);
	}

	/* add */
	
	@Test
	public void add_shouldAddItem() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(item).isNotNull();
		assertThat(publication.getAllResources()).contains(resource);
	}
	
	@Test
	public void add_shouldThrowExceptionIfResourceAlreadyAdded() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(resource);
		assertThatThrownBy(()->{
			manifest.add(resource);
		}).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	@Test
	public void add_shouldThrowExceptionIfResourceAlreadyExists() {
		PublicationResource resource1 = factory.newBuilder("chapter1.xhtml").build();
		PublicationResource resource2 = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(resource1);
		assertThatThrownBy(()->{
			manifest.add(resource2);
		}).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	/* remove */
	
	@Test
	public void remove_shouldRemoveItem() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(manifest.contains(item)).isTrue();
		manifest.remove(item);
		assertThat(manifest.contains(item)).isFalse();
		assertThat(publication.getAllResources()).doesNotContain(resource);
	}
	
	@Test
	public void remove_shouldDoNothingIfItemIsNotAdded() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		manifest.remove(item);
		manifest.remove(item);
	}

	@Test
	public void remove_shouldDoNothingIfItemIsNull() {
		manifest.remove(null);
	}
	
	/* iterator */
	
	@Test
	public void iterator_shouldReturnIterator() {
		Manifest.Item item1 = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		Manifest.Item item2 = manifest.add(factory.newBuilder("chapter2.xhtml").build());
		assertThat(manifest.iterator()).containsExactlyInAnyOrder(item1, item2);
	}
}
