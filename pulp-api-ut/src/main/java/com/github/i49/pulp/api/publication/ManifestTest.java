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

package com.github.i49.pulp.api.publication;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.publication.Epub;
import com.github.i49.pulp.api.publication.EpubException;
import com.github.i49.pulp.api.publication.Manifest;
import com.github.i49.pulp.api.publication.Publication;
import com.github.i49.pulp.api.publication.PublicationResource;
import com.github.i49.pulp.api.publication.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.publication.Rendition;

/**
 * Unit tests for {@link Manifest}.
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

	/* getNumberOfItems() */

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
	
	/* contains(Item) */
	
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
	public void contains_shouldThrowExceptionIfItemIsNull() {
		Throwable thrown = catchThrowable(()->{
			Manifest.Item item = null;
			manifest.contains(item);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	/* contains(String) */

	@Test
	public void contains_shouldReturnTrueIfItemExistsAtLocation() {
		String location = "chapter1.xhtml";
		manifest.add(factory.newBuilder(location).build());
		assertThat(manifest.contains(location)).isTrue();
	}

	@Test
	public void contains_shouldReturnFalseIfItemDoesNotExistAtLocation() {
		String location = "chapter1.xhtml";
		Manifest.Item item = manifest.add(factory.newBuilder(location).build());
		manifest.remove(item);
		assertThat(manifest.contains(location)).isFalse();
	}
	
	@Test
	public void contains_shouldThrowExceptionIfLocationIsNull() {
		Throwable thrown = catchThrowable(()->{
			String location = null;
			manifest.contains(location);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	/* get() */
	
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
	
	/* add() */
	
	@Test
	public void add_shouldAddOneItem() {
		PublicationResource r = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(r);
	
		assertThat(item).isNotNull();
		assertThat(manifest.getNumberOfItems()).isEqualTo(1);
		assertThat(publication.getAllResources()).contains(r);
	}
	
	@Test
	public void add_canAddMultipleItems() {
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = factory.newBuilder("chapter2.xhtml").build();
		PublicationResource r3 = factory.newBuilder("images/figure.png").build();
		Manifest.Item i1 = manifest.add(r1);
		Manifest.Item i2 = manifest.add(r2);
		Manifest.Item i3 = manifest.add(r3);
		
		assertThat(i1).isNotNull();
		assertThat(i2).isNotNull();
		assertThat(i3).isNotNull();
		assertThat(manifest.getNumberOfItems()).isEqualTo(3);
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r2, r3);
	}
	
	@Test
	public void add_shouldThrowExceptionIfManifestAlreadyHasTheResource() {
		PublicationResource theResource = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(theResource);
		assertThatThrownBy(()->{
			manifest.add(theResource);
		}).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	@Test
	public void add_shouldThrowExceptionIfResourceAlreadyExists() {
		// Two resources have the same location.
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(r1);
		assertThatThrownBy(()->{
			manifest.add(r2);
		}).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
	
	/* remove() */
	
	@Test
	public void remove_shouldRemoveItem() {
		PublicationResource r = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item i = manifest.add(r);
		assertThat(manifest.contains(i)).isTrue();
		boolean result = manifest.remove(i);
		
		assertThat(result).isTrue();
		assertThat(manifest.contains(i)).isFalse();
		assertThat(publication.getAllResources()).doesNotContain(r);
	}
	
	@Test
	public void remove_shouldNotRemoveOtherItems() {
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = factory.newBuilder("chapter2.xhtml").build();
		PublicationResource r3 = factory.newBuilder("chapter3.xhtml").build();
		Manifest.Item i1 = manifest.add(r1);
		Manifest.Item i2 = manifest.add(r2);
		Manifest.Item i3 = manifest.add(r3);

		// Removes only the second item.
		boolean result = manifest.remove(i2);
		
		assertThat(result).isTrue();
		assertThat(manifest.contains(i1)).isTrue();
		assertThat(manifest.contains(i2)).isFalse();
		assertThat(manifest.contains(i3)).isTrue();
		
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r3);
	}
	
	@Test
	public void remove_shouldDoNothingIfItemIsNotAdded() {
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(resource);
		assertThat(manifest.remove(item)).isTrue();
		assertThat(manifest.remove(item)).isFalse();
	}

	@Test
	public void remove_shouldThrowIllegalArgumentExceptionIfItemIsNull() {
		assertThatThrownBy(()->{
			manifest.remove(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* iterator() */
	
	@Test
	public void iterator_shouldReturnIterator() {
		Manifest.Item item1 = manifest.add(factory.newBuilder("chapter1.xhtml").build());
		Manifest.Item item2 = manifest.add(factory.newBuilder("chapter2.xhtml").build());
		assertThat(manifest.iterator()).containsExactlyInAnyOrder(item1, item2);
	}
	
	/* hasCoverImage() */
	
	@Test
	public void hasCoverImage_returnTrueIfContainingCoverImage() {
		PublicationResource r = factory.newBuilder("cover.png").build();
		manifest.add(r).asCoverImage();
		assertThat(manifest.hasCoverImage()).isTrue();
	}
	
	@Test
	public void hasCoverImage_returnFalseIfNotContainingCoverImage() {
		assertThat(manifest.hasCoverImage()).isFalse();
	}

	/* getCoverImage() */
	
	@Test
	public void getCoverImage_shouldReturnItemIfFound() {
		PublicationResource r = factory.newBuilder("cover.png").build();
		Manifest.Item item = manifest.add(r).asCoverImage();
		Manifest.Item found = manifest.getCoverImage();
		assertThat(found).isSameAs(item);
	}

	@Test
	public void getCoverImage_shouldReturnNullIfNotFound() {
		Throwable thrown = catchThrowable(()->{
			manifest.getCoverImage();
		});
		assertThat(thrown).isInstanceOf(NoSuchElementException.class);
	}

	/* hasNavigationDocument() */
	
	@Test
	public void hasNavigationDocument_returnTrueIfContainingNavigationDocument() {
		PublicationResource r = factory.newBuilder("nav.xhtml").build();
		manifest.add(r).asNavigation();
		assertThat(manifest.hasNavigationDocument()).isTrue();
	}

	@Test
	public void hasNavigationDocument_returnFalseIfNotContainingNavigationDocument() {
		assertThat(manifest.hasNavigationDocument()).isFalse();
	}
	
	/* getNavigationDocument() */
	
	@Test
	public void getNavigationDocument_shouldReturnItemIfFound() {
		PublicationResource r = factory.newBuilder("nav.xhtml").build();
		Manifest.Item item = manifest.add(r).asNavigation();
		Manifest.Item found = manifest.getNavigationDocument();
		assertThat(found).isSameAs(item);
	}

	@Test
	public void findNavigationDocument_shouldReturnNullfNotFound() {
		Throwable thrown = catchThrowable(()->{
			manifest.getNavigationDocument();
		});
		assertThat(thrown).isInstanceOf(NoSuchElementException.class);
	}
}
