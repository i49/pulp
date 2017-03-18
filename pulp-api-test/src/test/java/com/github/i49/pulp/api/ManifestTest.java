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

public class ManifestTest {

	private PublicationResourceBuilderFactory factory;
	private Manifest manifest;
	
	@Before
	public void setUp() {
		Publication publication = Epub.createPublication();
		Rendition rendition = publication.addRendition("EPUB/package.opf");
		factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		manifest = rendition.getManifest();
	}
	
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
	
	@Test
	public void add_shouldAddItem() {
		PublicationResource r = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item item = manifest.add(r);
		assertThat(item).isNotNull();
	}
	
	@Test
	public void add_shouldReturnSameItem() {
		PublicationResource r = factory.newBuilder("chapter1.xhtml").build();
		Manifest.Item first = manifest.add(r);
		Manifest.Item second = manifest.add(r);
		assertThat(first).isEqualTo(second);
	}
	
	@Test
	public void add_shouldThrowExceptionIfResourceAlreadyExists() {
		PublicationResource r1 = factory.newBuilder("chapter1.xhtml").build();
		PublicationResource r2 = factory.newBuilder("chapter1.xhtml").build();
		manifest.add(r1);
		assertThatThrownBy(()->{
			manifest.add(r2);
		}).isInstanceOf(EpubException.class).hasMessageContaining("chapter1.xhtml");
	}
}
