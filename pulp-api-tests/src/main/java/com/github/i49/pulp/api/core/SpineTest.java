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

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine;

/**
 * Unit tests for {@link Spine}.
 */
public class SpineTest {

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
	
	/* getNumberOfPages() */
	
	@Test
	public void getNumberOfPages_shouldReturnZeroByDefault() {
		assertThat(spine.getNumberOfPages()).isEqualTo(0);
	}
	
	@Test
	public void getNumberOfPages_shouldReturnCorrectNumber() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		
		assertThat(spine.getNumberOfPages()).isEqualTo(2);
	}
	
	/* get() */
	
	@Test
	public void get_shouldReturnFirstPage() {
		Spine.Page thePage = spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		spine.append(newItem("charpter3.xhtml"));
		
		assertThat(spine.get(0)).isSameAs(thePage);
	}

	@Test
	public void get_shouldReturnLastPage() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		Spine.Page thePage = spine.append(newItem("charpter3.xhtml"));
		
		assertThat(spine.get(2)).isSameAs(thePage);
	}
	
	@Test
	public void get_shouldThrowIndexOutOfBoundsExceptionIfIndexIsNegative() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		spine.append(newItem("charpter3.xhtml"));
		
		assertThatThrownBy(()->{
			spine.get(-1);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	public void get_shouldThrowIndexOutOfBoundsExceptionIfIndexExceedsPages() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		spine.append(newItem("charpter3.xhtml"));
		
		assertThatThrownBy(()->{
			spine.get(3);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}
	
	/* append() */
	
	@Test 
	public void append_shouldAppendPageToBlankSpine() {
		Spine.Page page = spine.append(newItem("charpter1.xhtml"));
		
		assertThat(page).isNotNull();
		assertThat(spine.getNumberOfPages()).isEqualTo(1);
		assertThat(spine.get(0)).isSameAs(page);
	}

	@Test 
	public void append_shouldAppendPageLast() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		Spine.Page page = spine.append(newItem("charpter3.xhtml"));
		
		assertThat(page).isNotNull();
		assertThat(spine.getNumberOfPages()).isEqualTo(3);
		assertThat(spine.get(2)).isSameAs(page);
	}
	
	@Test
	public void append_shouldThrowExpceptionIfItemIsNull() {
		assertThatThrownBy(()->{
			spine.append(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void append_shouldThrowExceptionIfItemAlreadyAssigned() {
		Manifest.Item item = newItem("chapter1.xhtml");
		spine.append(item);
		
		assertThatThrownBy(()->{
			spine.append(item);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void append_shouldThrowExceptionIfItemNotInManifest() {
		Manifest.Item item = newItem("chapter1.xhtml");
		// Invalidates the item.
		manifest.remove(item);
		
		assertThatThrownBy(()->{
			spine.append(item);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* insert() */
	
	@Test 
	public void insert_shouldInsertPageToBlankSpine() {
		Spine.Page page = spine.insert(0, newItem("charpter1.xhtml"));
		
		assertThat(page).isNotNull();
		assertThat(spine.getNumberOfPages()).isEqualTo(1);
		assertThat(spine.get(0)).isSameAs(page);
	}

	@Test 
	public void insert_shouldInsertPage() {
		Spine.Page p1 = spine.append(newItem("charpter1.xhtml"));
		Spine.Page p3 = spine.append(newItem("charpter3.xhtml"));
		Spine.Page p2 = spine.insert(1, newItem("charpter2.xhtml"));
		
		assertThat(p1).isNotNull();
		assertThat(spine.getNumberOfPages()).isEqualTo(3);
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p2);
		assertThat(spine.get(2)).isSameAs(p3);
	}
	
	@Test
	public void insert_shouldThrowExpceptionIfItemIsNull() {
		assertThatThrownBy(()->{
			spine.insert(0, null);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void insert_shouldThrowExceptionIfIndexIsNegative() {
		assertThatThrownBy(()->{
			spine.insert(-1, newItem("chapter1.xhtml"));
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	public void insert_shouldThrowExceptionIfIndexExceedsPages() {
		assertThatThrownBy(()->{
			spine.insert(1, newItem("chapter1.xhtml"));
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}
	
	@Test
	public void insert_shouldThrowExceptionIfItemAlreadyAssigned() {
		Manifest.Item item = newItem("chapter1.xhtml");
		spine.append(item);

		assertThatThrownBy(()->{
			spine.insert(0, item);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void insert_shouldThrowExceptionIfItemNotInManifest() {
		Manifest.Item item = newItem("chapter1.xhtml");
		// Invalidates the item.
		manifest.remove(item);
		
		assertThatThrownBy(()->{
			spine.insert(0, item);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* remove() */
	
	@Test
	public void remove_shouldRemovePage() {
		Spine.Page p1 = spine.append(newItem("charpter1.xhtml"));
		Spine.Page p2 = spine.append(newItem("charpter2.xhtml"));
		Spine.Page p3 = spine.append(newItem("charpter3.xhtml"));
		
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p2);
		assertThat(spine.get(2)).isSameAs(p3);

		spine.remove(1);
		assertThat(spine.getNumberOfPages()).isEqualTo(2);
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p3);
	}
	
	@Test
	public void remove_shouldThrowExceptionIfIndexIsNegative() {
		assertThatThrownBy(()->{
			spine.remove(-1);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	public void remove_shouldThrowExceptionIfIndexExceedsPages() {
		Spine.Page p1 = spine.append(newItem("charpter1.xhtml"));
		Spine.Page p2 = spine.append(newItem("charpter2.xhtml"));
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p2);
		assertThat(spine.getNumberOfPages()).isEqualTo(2);

		assertThatThrownBy(()->{
			spine.remove(2);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}
	
	/* move() */
	
	@Test
	public void move_shouldMovePage() {
		Spine.Page p1 = spine.append(newItem("charpter1.xhtml"));
		Spine.Page p2 = spine.append(newItem("charpter2.xhtml"));
		Spine.Page p3 = spine.append(newItem("charpter3.xhtml"));
		
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p2);
		assertThat(spine.get(2)).isSameAs(p3);
		
		Spine.Page moved = spine.move(0, 2);
		assertThat(moved).isSameAs(p1);
		assertThat(spine.getNumberOfPages()).isEqualTo(3);
		assertThat(spine.get(0)).isSameAs(p2);
		assertThat(spine.get(1)).isSameAs(p3);
		assertThat(spine.get(2)).isSameAs(p1);
	}
	
	@Test
	public void move_shouldNotMovePageIfIndexIsSame() {
		Spine.Page p1 = spine.append(newItem("charpter1.xhtml"));
		Spine.Page p2 = spine.append(newItem("charpter2.xhtml"));
		Spine.Page p3 = spine.append(newItem("charpter3.xhtml"));
		
		// newIndex is same as index.
		Spine.Page moved = spine.move(1, 1);
		assertThat(moved).isSameAs(p2);
		assertThat(spine.getNumberOfPages()).isEqualTo(3);
		assertThat(spine.get(0)).isSameAs(p1);
		assertThat(spine.get(1)).isSameAs(p2);
		assertThat(spine.get(2)).isSameAs(p3);
	}
	
	@Test
	public void move_shouldThrowExceptionIfIndexIsNegative() {
		spine.append(newItem("charpter1.xhtml"));
		assertThatThrownBy(()->{
			spine.move(-1, 0);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	public void move_shouldThrowExceptionIfIndexExceedsPages() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		assertThatThrownBy(()->{
			spine.move(2, 0);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}
	
	@Test
	public void move_shouldThrowExceptionIfNewIndexIsNegative() {
		spine.append(newItem("charpter1.xhtml"));
		assertThatThrownBy(()->{
			spine.move(0, -1);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	public void move_shouldThrowExceptionIfNewIndexExceedsPages() {
		spine.append(newItem("charpter1.xhtml"));
		spine.append(newItem("charpter2.xhtml"));
		assertThatThrownBy(()->{
			spine.move(0, 2);
		}).isInstanceOf(IndexOutOfBoundsException.class);
	}
}
