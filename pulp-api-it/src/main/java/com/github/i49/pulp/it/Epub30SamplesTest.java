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
import java.util.List;
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
import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.DublinCoreTerm;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;

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
			assertThat(m.getNumberOfProperties()).isEqualTo(14);
			
			assertThat(m.get(DublinCore.IDENTIFIER)).hasValue("urn:isbn:9781449328030");
			assertThat(m.get(DublinCore.TITLE)).hasValue("Accessible EPUB 3");
			assertThat(m.get(DublinCore.LANGUAGE)).hasValue("en");
			assertThat(m.get(DublinCore.CREATOR)).hasValue("Matt Garrish");
			assertThat(m.getList(DublinCore.CONTRIBUTOR)).containsExactly(
				"O’Reilly Production Services", "David Futato", "Robert Romano", 
				"Brian Sawyer", "Dan Fauxsmith", "Karen Montgomery"
				);
			assertThat(m.get(DublinCore.PUBLISHER)).hasValue("O’Reilly Media, Inc.");
			assertThat(m.get(DublinCore.RIGHTS)).hasValue("Copyright © 2012 O’Reilly Media, Inc");
			assertThat(m.get(DublinCore.DATE)).hasValue("2012-02-20T00:00:00Z");
			assertThat(m.get(DublinCoreTerm.MODIFIED)).hasValue("2012-10-24T15:30:00Z");
			
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

			Metadata m = r.getMetadata();
			assertThat(m.get(DublinCore.IDENTIFIER)).hasValue("code.google.com.epub-samples.cc-shared-culture");
			assertThat(m.get(DublinCore.TITLE)).hasValue("Creative Commons - A Shared Culture");
			assertThat(m.get(DublinCore.LANGUAGE)).hasValue("en-US");
			assertThat(m.get(DublinCore.CREATOR)).hasValue("Jesse Dylan");
			assertThat(m.get(DublinCore.PUBLISHER)).hasValue("Creative Commons");
			assertThat(m.get(DublinCore.CONTRIBUTOR)).hasValue("mgylling");
			assertThat(m.get(DublinCore.DESCRIPTION)).hasValue("Multiple video tests (see Navigation Document (toc) for details)");
			assertThat(m.get(DublinCore.RIGHTS)).hasValue("This work is licensed under a Creative Commons Attribution-Noncommercial-Share Alike (CC BY-NC-SA) license.");
			assertThat(m.get(DublinCoreTerm.MODIFIED)).hasValue("2012-01-20T12:47:00Z");
			
			Manifest mf = r.getManifest();
			assertThat(mf.getNumberOfItems()).isEqualTo(21);
	
			assertThat(mf.get("images/326261902_3fa36f548d.jpg").isCoverImage()).isTrue();
			assertThat(mf.get("xhtml/p20.xhtml").isScripted()).isTrue();
			assertThat(mf.get("xhtml/toc.xhtml").isNavigation()).isTrue();
	
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
	
			Metadata m = r.getMetadata();
			assertThat(m.getNumberOfProperties()).isEqualTo(12);
			assertThat(m.get(DublinCore.IDENTIFIER)).hasValue("http://www.gutenberg.org/ebooks/25545");
			
			List<Property> titles = m.getList(DublinCore.TITLE);
			assertThat(titles).hasSize(2);
			assertThat(titles.get(0)).hasValue("Children's Literature");
			assertThat(titles.get(1)).hasValue("A Textbook of Sources for Teachers and Teacher-Training Classes");
		
			assertThat(m.get(DublinCore.LANGUAGE)).hasValue("en");
			
			List<Property> creators = m.getList(DublinCore.CREATOR);
			assertThat(creators).hasSize(2);
			assertThat(creators.get(0)).hasValue("Charles Madison Curry");
			assertThat(creators.get(0)).hasNormalizedValue("Curry, Charles Madison");
			assertThat(creators.get(1)).hasValue("Erle Elsworth Clippinger");
			assertThat(creators.get(1)).hasNormalizedValue("Clippinger, Erle Elsworth");
		
			List<Property> subjects = m.getList(DublinCore.SUBJECT);
			assertThat(subjects).hasSize(2);
			assertThat(subjects.get(0)).hasValue("Children -- Books and reading");
			assertThat(subjects.get(1)).hasValue("Children's literature -- Study and teaching");
			
			assertThat(m.get(DublinCore.SOURCE)).hasValue("http://www.gutenberg.org/files/25545/25545-h/25545-h.htm");
			assertThat(m.get(DublinCore.RIGHTS)).hasValue("Public domain in the USA.");

			assertThat(m.get(DublinCore.DATE)).hasValue("2008-05-20T00:00:00Z");
			assertThat(m.get(DublinCoreTerm.MODIFIED)).hasValue("2010-02-17T04:39:13Z");

			Manifest mf = r.getManifest();
			assertThat(mf.getNumberOfItems()).isEqualTo(7);
	
			assertThat(mf.get("images/cover.png").isCoverImage()).isTrue();
			assertThat(mf.get("nav.xhtml").isScripted()).isTrue();
			assertThat(mf.get("nav.xhtml").isNavigation()).isTrue();
	
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
