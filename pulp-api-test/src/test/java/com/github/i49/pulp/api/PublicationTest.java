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

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Publication type.
 */
public class PublicationTest {

	private Publication publication;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
	}

	/* getNumberOfRenditions */

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
	
	/* getDefaultRendition */
	
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
	public void addRendition_shouldAddRenditionAtDefaultLocationIfLocationIsNull() {
		Rendition r = publication.addRendition(null);
		assertThat(r.getLocation()).hasToString("EPUB/package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowExceptionIfLocationHasScheme() {
		assertThatThrownBy(()->{
			publication.addRendition("http://example.org/EPUB/package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("http://example.org/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowExceptionIfLocationIsPathAbsolute() {
		assertThatThrownBy(()->{
			publication.addRendition("/EPUB/package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowExceptionIfLocationContainsDotSegments() {
		assertThatThrownBy(()->{
			publication.addRendition("../package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("../package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowExceptionIfRenditionAlreadyExists() {
		publication.addRendition();
		assertThatThrownBy(()->{
			publication.addRendition();
		}).isInstanceOf(EpubException.class).hasMessageContaining("EPUB/package.opf");
	}

	/* getAllResources */
	
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
		assertThat(publication.getAllResources()).containsOnly(r1, r2, r3);
	}

	/* iterator */

	@Test
	public void iterator_shouldIterateRenditions() {
		Rendition r1 = publication.addRendition();
		Rendition r2 = publication.addRendition("another/package.opf");
		assertThat(publication.iterator()).containsExactly(r1, r2);
	}
}
