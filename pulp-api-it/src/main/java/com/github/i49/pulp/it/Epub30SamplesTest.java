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

package com.github.i49.pulp.it;

import static com.github.i49.pulp.it.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.logging.Logger;

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
import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Metadata;

/**
 * Tests of reading EPUB 3.0 samples.
 */
public class Epub30SamplesTest {
	
	private static final Logger log = Logger.getLogger(Epub30SamplesTest.class.getName()); 

	private static Path basePath;
	
	@BeforeClass
	public static void setUpOnce() {
		String samplesHome = System.getenv("EPUB3_SAMPLES_HOME");
		Assume.assumeNotNull(samplesHome);
		basePath = Paths.get(samplesHome).resolve("30");
	}
	
	protected void read(String filename, Consumer<Publication> consumer) {
		Path path = basePath.resolve(filename);
		if (Files.exists(path)) {
			PublicationReader reader = Epub.createReader(path);
			Publication p = reader.read();
			consumer.accept(p);
		} else {
			log.info("Skipped nonexistent epub sample: " + filename);
		}
	}
	
	@Test
	public void test_accessible_epub_3() {
		read("accessible_epub_3.epub", p->{

			assertCommon(p);
	
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
			
			Rendition r = p.getDefaultRendition();
			
			Metadata m = r.getMetadata();
			assertThat(m.get(BasicTerm.IDENTIFIER).getValue()).isEqualTo("urn:isbn:9781449328030");
			assertThat(m.get(BasicTerm.TITLE).getValue()).isEqualTo("Accessible EPUB 3");
			assertThat(m.get(BasicTerm.LANGUAGE).getValue()).isEqualTo("en");
			
			Manifest mf = r.getManifest();
			assertThat(mf.getNumberOfItems()).isEqualTo(35);
	
			assertThat(mf.get("covers/9781449328030_lrg.jpg").isCoverImage()).isTrue();
			assertThat(mf.get("bk01-toc.xhtml").isNavigation()).isTrue();
	
			Spine s = r.getSpine();
			assertThat(s.getNumberOfPages()).isEqualTo(22);
			assertThat(s).linearExcept(0);
		});
	}

	@Test
	public void test_cc_shared_culture() {
		read("cc-shared-culture.epub", p->{
			
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
		});
	}
	
	@Test
	public void test_childrens_literature() {
		read("childrens-literature.epub", p->{
			
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
		});
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
