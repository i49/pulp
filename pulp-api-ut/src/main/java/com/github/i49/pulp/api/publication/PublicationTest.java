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
 * Unit tests for {@link Publication}.
 */
public class PublicationTest {

	private Publication publication;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
	}

	/* getNumberOfRenditions() */

	@Test
	public void getNumberOfRenditions_shouldReturnZero() {
		assertThat(publication.getNumberOfRenditions()).isEqualTo(0);
	}

	@Test
	public void getNumberOfRenditions_shouldReturnOne() {
		publication.addRendition();
		assertThat(publication.getNumberOfRenditions()).isEqualTo(1);
	}

	@Test
	public void getNumberOfRenditions_shouldReturnMultiple() {
		publication.addRendition();
		publication.addRendition("another/package.opf");
		assertThat(publication.getNumberOfRenditions()).isEqualTo(2);
	}
	
	/* getDefaultRendition() */
	
	@Test
	public void getDefaultRendition_shoudlReturnNull() {
		assertThat(publication.getDefaultRendition()).isNull();
	}

	@Test
	public void getDefaultRendition_shoudlReturnOnlyRendition() {
		Rendition r = publication.addRendition();
		assertThat(publication.getDefaultRendition()).isEqualTo(r);
	}
	@Test
	public void getDefaultRendition_shoudlReturnFirstRendition() {
		Rendition first = publication.addRendition();
		@SuppressWarnings("unused")
		Rendition second = publication.addRendition("another/package.opf");
		assertThat(publication.getDefaultRendition()).isEqualTo(first);
	}
	
	/* addRendition() */
	
	@Test
	public void addRendition_shouldAddRenditionAtDefaultLocation() {
		Rendition r = publication.addRendition();
		assertThat(r.getLocation()).hasToString("EPUB/package.opf");
	}

	/* addRendition(location) */

	@Test
	public void addRendition_shouldAddRenditionIfLocationIsValid() {
		Rendition r = publication.addRendition("path/to/package.opf");
		assertThat(r.getLocation()).hasToString("path/to/package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowExceptionIfLocationIsNull() {
		Throwable thrown = catchThrowable(()->{
			publication.addRendition(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void addRendition_shouldThrowExceptionIfLocationHasScheme() {
		Throwable thrown = catchThrowable(()->{
			publication.addRendition("http://example.org/EPUB/package.opf");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("http://example.org/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowExceptionIfLocationIsPathAbsolute() {
		Throwable thrown = catchThrowable(()->{
			publication.addRendition("/EPUB/package.opf");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowExceptionIfLocationContainsDotSegments() {
		Throwable thrown = catchThrowable(()->{
			publication.addRendition("../package.opf");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("../package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowExceptionIfRenditionAlreadyExists() {
		publication.addRendition();
		Throwable thrown = catchThrowable(()->{
			publication.addRendition();
		});
		assertThat(thrown).isInstanceOf(EpubException.class).hasMessageContaining("EPUB/package.opf");
	}

	/* iterator() */

	@Test
	public void iterator_shouldIterateRenditions() {
		Rendition r1 = publication.addRendition();
		Rendition r2 = publication.addRendition("another/package.opf");
		assertThat(publication.iterator()).containsExactly(r1, r2);
	}

	/* getAllResources() */
	
	@Test
	public void getAllResources_shouldReturnEmptySetByDefault() {
		publication.addRendition();
		assertThat(publication.getAllResources()).isEmpty();
	}
	
	@Test
	public void getAllResources_shouldReturnAddedResources() {
		Rendition rendition = publication.addRendition();
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest manifest = rendition.getManifest();
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(r1);
		PublicationResource r2 = factory.newBuilder("chapter2.xhtml").build();
		manifest.add(r2);
		PublicationResource r3 = factory.newBuilder("images/figure1.png").build();
		manifest.add(r3);
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r2, r3);
	}
	
	@Test
	public void getAllResources_shouldNotReturnRemovedResource() {
		Rendition rendition = publication.addRendition();
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest manifest = rendition.getManifest();
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(r1);
		PublicationResource r2 = factory.newBuilder("chapter2.xhtml").build();
		Manifest.Item itemToRemove = manifest.add(r2);
		PublicationResource r3 = factory.newBuilder("images/figure1.png").build();
		manifest.add(r3);
		// Removes only the second item.
		manifest.remove(itemToRemove);
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r3);
	}
	
	@Test
	public void getAllResources_shouldReturnResourcesOfAllRenditions() {
		Rendition rendition1 = publication.addRendition("first/package.opf");
		PublicationResourceBuilderFactory f1 = Epub.createResourceBuilderFactory(rendition1.getLocation());
		Rendition rendition2 = publication.addRendition("second/package.opf");
		PublicationResourceBuilderFactory f2 = Epub.createResourceBuilderFactory(rendition2.getLocation());
		
		PublicationResource r1 = f1.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = f1.newBuilder("chapter2.xhtml").build();
		rendition1.getManifest().add(r1);
		rendition1.getManifest().add(r2);
		
		PublicationResource r3 = f2.newBuilder("chapter1.xhtml").build();
		rendition2.getManifest().add(r3);
		// This resource is shared with rendition1.
		rendition2.getManifest().add(r2); 
		
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r2, r3);
	}
	
	@Test
	public void getAllResources_shouldReturnResourcesRemovedByOneRendition() {
		Rendition rendition1 = publication.addRendition("first/package.opf");
		PublicationResourceBuilderFactory f1 = Epub.createResourceBuilderFactory(rendition1.getLocation());
		Rendition rendition2 = publication.addRendition("second/package.opf");
		PublicationResourceBuilderFactory f2 = Epub.createResourceBuilderFactory(rendition2.getLocation());
		
		PublicationResource r1 = f1.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = f1.newBuilder("chapter2.xhtml").build();
		rendition1.getManifest().add(r1);
		Manifest.Item itemToRemove = rendition1.getManifest().add(r2);
		
		PublicationResource r3 = f2.newBuilder("chapter1.xhtml").build();
		rendition2.getManifest().add(r3);
		rendition2.getManifest().add(r2);
		
		rendition1.getManifest().remove(itemToRemove);
		
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r2, r3);
	}
	
	@Test
	public void getAllResources_shouldNotReturnResourcesRemovedByAllRenditions() {
		Rendition rendition1 = publication.addRendition("first/package.opf");
		PublicationResourceBuilderFactory f1 = Epub.createResourceBuilderFactory(rendition1.getLocation());
		Rendition rendition2 = publication.addRendition("second/package.opf");
		PublicationResourceBuilderFactory f2 = Epub.createResourceBuilderFactory(rendition2.getLocation());

		// rendition1
		Manifest m1 = rendition1.getManifest();
		PublicationResource r1 = f1.newBuilder("chapter1.xhtml").build();
		m1.add(r1);
		PublicationResource r2 = f1.newBuilder("chapter2.xhtml").build();
		Manifest.Item item1 = m1.add(r2);
		
		// rendition2
		Manifest m2 = rendition2.getManifest();
		Manifest.Item item2 = m2.add(r2);
		PublicationResource r3 = f2.newBuilder("chapter1.xhtml").build();
		m2.add(r3);
		
		// The resource is removed by all sharing renditions.
		m1.remove(item1);
		m2.remove(item2);
		
		assertThat(publication.getAllResources()).containsExactlyInAnyOrder(r1, r3);
	}
	
	/* containsResource() */
	
	@Test
	public void containsResource_shouldReturnTrueIfContainingTheResource() {
		Rendition rendition = publication.addRendition();
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest manifest = rendition.getManifest();
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(resource);
		
		assertThat(publication.containsResource("EPUB/chapter1.xhtml")).isTrue();
	}

	@Test
	public void containsResource_shouldReturnFalseIfNotContainingTheResource() {
		assertThat(publication.containsResource("EPUB/chapter1.xhtml")).isFalse();
	}
	
	/* getResourceAt() */

	@Test
	public void getResourceAt_shouldReturnResourceFound() {
		Rendition rendition = publication.addRendition();
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest manifest = rendition.getManifest();
		PublicationResource resource = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(resource);
		
		assertThat(publication.getResource("EPUB/chapter1.xhtml")).isSameAs(resource);
	}
	
	@Test
	public void getResourceAt_shouldThrowExceptionIfResourceNotFound() {
		String location = "EPUB/chapter1.xhtml";
		Throwable thrown = catchThrowable(()->{
			publication.getResource(location);
		});
		assertThat(thrown).isInstanceOf(NoSuchElementException.class)
			.hasMessageContaining(location);
	}
}
