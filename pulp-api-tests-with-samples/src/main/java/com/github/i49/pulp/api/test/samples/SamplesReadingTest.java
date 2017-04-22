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

package com.github.i49.pulp.api.test.samples;

import static com.github.i49.pulp.api.test.samples.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationReader;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine;
import com.github.i49.pulp.api.core.Spine.Page;

public class SamplesReadingTest {

	private static Path basePath;
	
	@BeforeClass
	public static void setUpOnce() {
		String samplesHome = System.getenv("EPUB3_SAMPLES_HOME");
		Assume.assumeNotNull(samplesHome);
		basePath = Paths.get(samplesHome).resolve("30");
	}

	protected Publication read(String filename) {
		Path filePath = basePath.resolve(filename);
		PublicationReader reader = Epub.createReader(filePath);
		return reader.read();
	}
	
	@Test
	public void read_accessible_epub_3() {
		Publication p = read("accessible_epub_3.epub");

		assertCommon(p);

		assertThat(p.getNumberOfRenditions()).isEqualTo(1);
		
		Rendition r = p.getDefaultRendition();
		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems()).isEqualTo(35);

		assertThat(m.get("covers/9781449328030_lrg.jpg").isCoverImage()).isTrue();
		assertThat(m.get("bk01-toc.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages()).isEqualTo(22);
		assertThat(s).linearExcept(0);
	}

	@Test
	public void read_cc_shared_culture() {
		Publication p = read("cc-shared-culture.epub");
		
		assertCommon(p);
		
		assertThat(p.getNumberOfRenditions()).isEqualTo(1);
		
		Rendition r = p.getDefaultRendition();
		
		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems()).isEqualTo(21);

		assertThat(m.get("images/326261902_3fa36f548d.jpg").isCoverImage()).isTrue();
		assertThat(m.get("xhtml/p20.xhtml").isScripted()).isTrue();
		assertThat(m.get("xhtml/toc.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages()).isEqualTo(8);
		assertThat(s).linearExcept(0);
	}
	
	@Test
	public void read_childrens_literature() {
		Publication p = read("childrens-literature.epub");
		
		assertCommon(p);
		
		assertThat(p.getNumberOfRenditions()).isEqualTo(1);

		Rendition r = p.getDefaultRendition();

		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems()).isEqualTo(7);

		assertThat(m.get("images/cover.png").isCoverImage()).isTrue();
		assertThat(m.get("nav.xhtml").isScripted()).isTrue();
		assertThat(m.get("nav.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages()).isEqualTo(3);
		assertThat(s).linearExcept();
	}
	
	protected void assertCommon(Publication p) {
		assertThat(p).isNotNull();
		
		Rendition r = p.getDefaultRendition();
		assertThat(r).isNotNull();
		
		Manifest m = r.getManifest();
		assertThat(m).isNotNull();

		for (Manifest.Item item: m) {
			assertThat(item).isNotNull();
			assertThat(item.getLocation()).isNotNull();
			assertThat(item.getResource()).isNotNull();
			assertThat(item.getResource().getLocation()).isNotNull();
			assertThat(item.getResource().getMediaType()).isNotNull();
		}

		Spine s = r.getSpine();
		assertThat(s).isNotNull();
		for (Page page: s) {
			assertThat(page).isNotNull();
			assertThat(page.getItem()).isNotNull();
		}
	}
}
