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
 * Unit tests for {@link Spine.Page}.
 */
public class SpinePageTest {

	private Publication publication;
	private PublicationResourceBuilderFactory factory;
	private Manifest manifest;
	private Spine spine;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
		Rendition rendition = publication.addRendition("EPUB/package.opf");
		factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		manifest = rendition.getManifest();
		spine = rendition.getSpine();
	}
	
	/**
	 * Helper method to add new publication resource to the manifest.
	 * @param location the relative location of the resource.
	 * @return the item of the manifest.
	 */
	private Manifest.Item newItem(String location) {
		PublicationResource resource = factory.newBuilder(location).build();
		return manifest.add(resource);
	}
	
	/* getItem() */
	
	@Test
	public void getItem_shouldReturnAssignedItem() {
		Manifest.Item item = newItem("chapter1.xhtml");
		Spine.Page page = spine.append(item);
		
		assertThat(page.getItem()).isSameAs(item);
	}
	
	/* isLinear() and linear() */
	
	@Test
	public void isLinear_shouldReturnTrueByDefault() {
		Spine.Page page = spine.append(newItem("chapter1.xhtml"));
		assertThat(page.isLinear()).isTrue();
	}

	@Test
	public void isLinear_shouldReturnTrue() {
		Spine.Page page = spine.append(newItem("chapter1.xhtml")).linear(true);
		assertThat(page.isLinear()).isTrue();
	}

	@Test
	public void isLinear_shouldReturnFalse() {
		Spine.Page page = spine.append(newItem("chapter1.xhtml")).linear(false);
		assertThat(page.isLinear()).isFalse();
	}
}
