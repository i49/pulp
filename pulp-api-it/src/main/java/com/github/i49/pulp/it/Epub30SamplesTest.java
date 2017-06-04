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

import static com.github.i49.pulp.it.CustomAssertions.*;
import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.RelatorRole;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.Contributor;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Subject;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.TitleType;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabularies.rendering.Layout;
import com.github.i49.pulp.api.vocabularies.rendering.Orientation;
import com.github.i49.pulp.api.vocabularies.rendering.Spread;

/**
 * Tests of reading EPUB 3.0 samples.
 */
public class Epub30SamplesTest {
	
	private static final Logger log = Logger.getLogger(Epub30SamplesTest.class.getName()); 

	private static Path basePath;
	private static TermRegistry termRegistry;
	
	@BeforeClass
	public static void setUpOnce() {
		String samplesHome = System.getenv("EPUB3_SAMPLES_HOME");
		Assume.assumeNotNull(samplesHome);
		basePath = Paths.get(samplesHome).resolve("30");
		termRegistry = Epub.getPropertyTermRegistry();
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
			assertThat(m.size()).isEqualTo(14);
			
			Identifier identifier = m.find().identifier().get(0);
			assertThat(identifier.getValue()).isEqualTo("urn:isbn:9781449328030");
			assertThat(identifier.isPrimary()).isTrue();

			assertThat(m.find().title().get(0)).hasValue("Accessible EPUB 3");
			assertThat(m.find().language().get(0)).hasValue("en");
			assertThat(m.find().creator().get(0)).hasValue("Matt Garrish");
			assertThat(m.find().contributor()).extracting(Property::getValueAsString).containsExactly(
				"O’Reilly Production Services", 
				"David Futato", 
				"Robert Romano", 
				"Brian Sawyer", 
				"Dan Fauxsmith", 
				"Karen Montgomery"
				);
			assertThat(m.find().publisher().get(0)).hasValue("O’Reilly Media, Inc.");
			assertThat(m.find().rights().get(0)).hasValue("Copyright © 2012 O’Reilly Media, Inc");
			assertThat(m.find().date().get(0)).hasValue("2012-02-20T00:00:00Z");
			assertThat(m.find().modified().get(0)).hasValue("2012-10-24T15:30:00Z");
			
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
			assertThat(m.size()).isEqualTo(10);
			assertThat(m.find().identifier().get(0)).hasValue("code.google.com.epub-samples.cc-shared-culture");
			assertThat(m.find().title().get(0)).hasValue("Creative Commons - A Shared Culture");
			assertThat(m.find().language().get(0)).hasValue("en-US");
			assertThat(m.find().creator().get(0)).hasValue("Jesse Dylan");
			assertThat(m.find().publisher().get(0)).hasValue("Creative Commons");
			assertThat(m.find().contributor().get(0)).hasValue("mgylling");
			assertThat(m.find().description().get(0)).hasValue("Multiple video tests (see Navigation Document (toc) for details)");
			assertThat(m.find().rights().get(0)).hasValue("This work is licensed under a Creative Commons Attribution-Noncommercial-Share Alike (CC BY-NC-SA) license.");
			assertThat(m.find().modified().get(0)).hasValue("2012-01-20T12:47:00Z");

			URI cc = URI.create("http://creativecommons.org/ns#");
			Optional<Term> term = termRegistry.findTerm(cc, "attributionURL");
			assertThat(term).isNotEmpty();
			assertThat(m.find().propertyOf(term.get()).get(0)).hasValue("http://creativecommons.org/videos/a-shared-culture");
			
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
			assertThat(m.size()).isEqualTo(12);
			assertThat(m.find().identifier().get(0)).hasValue("http://www.gutenberg.org/ebooks/25545");
			
			List<Title> titles = m.find().title();
			assertThat(titles).hasSize(2);
			assertThat(titles.get(0)).hasValue("Children's Literature");
			assertThat(titles.get(0).getType()).hasValue(TitleType.MAIN);
			assertThat(titles.get(0).getDisplayOrder()).hasValue(1);
			assertThat(titles.get(1)).hasValue("A Textbook of Sources for Teachers and Teacher-Training Classes");
			assertThat(titles.get(1).getType()).hasValue(TitleType.SUBTITLE);
			assertThat(titles.get(1).getDisplayOrder()).hasValue(2);
		
			assertThat(m.find().language().get(0)).hasValue("en");
			
			List<Creator> creators = m.find().creator();
			assertThat(creators).hasSize(2);
			assertThat(creators.get(0)).hasValue("Charles Madison Curry");
			assertThat(creators.get(0)).hasNormalizedValue("Curry, Charles Madison");
			assertThat(creators.get(1)).hasValue("Erle Elsworth Clippinger");
			assertThat(creators.get(1)).hasNormalizedValue("Clippinger, Erle Elsworth");
		
			List<Subject> subjects = m.find().subject();
			assertThat(subjects).hasSize(2);
			assertThat(subjects.get(0)).hasValue("Children -- Books and reading");
			assertThat(subjects.get(1)).hasValue("Children's literature -- Study and teaching");
			
			assertThat(m.find().source().get(0)).hasValue("http://www.gutenberg.org/files/25545/25545-h/25545-h.htm");
			assertThat(m.find().rights().get(0)).hasValue("Public domain in the USA.");

			assertThat(m.find().date().get(0)).hasValue("2008-05-20T00:00:00Z");
			assertThat(m.find().modified().get(0)).hasValue("2010-02-17T04:39:13Z");

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

	@Test
	public void test_childrens_media_query() {
		read("childrens-media-query.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(12);
			
			List<Creator> creators = m.find().creator();
			assertThat(creators).hasSize(2);
			assertThat(creators.get(0).getValue()).isEqualTo("Thomas Crane");
			assertThat(creators.get(0).getNormalizedValue()).hasValue("Crane, Thomas");
			assertThat(creators.get(0).getRole()).hasValue(RelatorRole.AUTHOR);
			assertThat(creators.get(1).getValue()).isEqualTo("Ellen Elizabeth Houghton");
			assertThat(creators.get(1).getNormalizedValue()).hasValue("Houghton, Ellen Elizabeth");
			assertThat(creators.get(1).getRole()).hasValue(RelatorRole.ILLUSTRATOR);
			
			List<Contributor> contributors = m.find().contributor();
			assertThat(contributors).hasSize(2);
			assertThat(contributors.get(0).getValue()).isEqualTo("Liza Daly");
			assertThat(contributors.get(0).getRole()).hasValue(RelatorRole.MARKUP_EDITOR);
			assertThat(contributors.get(1).getValue()).isEqualTo("University of California Libraries");
			assertThat(contributors.get(1).getRole()).isEmpty();

			List<Publisher> publishers = m.find().publisher();
			assertThat(publishers).hasSize(1);
			assertThat(publishers.get(0).getValue()).isEqualTo("London ; Belfast ; New York : Marcus Ward & Co.");
			assertThat(publishers.get(0).getRole()).isEmpty();
		});
	}
	
	@Test
	public void test_cole_voyage_of_life() {
		read("cole-voyage-of-life.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(6);
		});	
	}
	
	@Test
	public void test_cole_voyage_of_life_tol() {
		read("cole-voyage-of-life-tol.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(10);
			
			Layout layout = m.find().rendering().layout().get(0);
			CustomAssertions.assertThat(layout).isSameAs(Layout.PRE_PAGINATED);
			Orientation orient = m.find().rendering().orientation().get(0);
			CustomAssertions.assertThat(orient).isSameAs(Orientation.LANDSCAPE);
			Spread spread = m.find().rendering().spread().get(0);
			CustomAssertions.assertThat(spread).isSameAs(Spread.NONE);
		});	
	}
	
	@Test
	public void test_epub30_spec() {
		read("epub30-spec.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(5);
		});	
	}
	
	@Test
	public void test_figure_gallery_bindings() {
		read("figure-gallery-bindings.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(8);
		});	
	}

	@Test
	public void test_georgia_cfi() {
		read("georgia-cfi.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(9);
			
			Creator creator = m.find().creator().get(0);
			assertThat(creator.getValue()).isEqualTo("Various");
			assertThat(creator.getRole()).hasValue(RelatorRole.AUTHOR);
			
			List<Title> titles = m.find().title();
			assertThat(titles).hasSize(4);
			assertThat(titles.get(0).getValue()).isEqualTo("Georgia");
			assertThat(titles.get(0).getType()).hasValue(TitleType.MAIN);
			assertThat(titles.get(0).getDisplayOrder()).hasValue(3);
			assertThat(titles.get(1).getValue()).isEqualTo("Encyclopaedia Britannica, 11th Edition, Volume 11, Slice 7 / Georgia");
			assertThat(titles.get(1).getType()).hasValue(TitleType.EXPANDED);
			assertThat(titles.get(2).getValue()).isEqualTo("Encyclopaedia Britannica");
			assertThat(titles.get(2).getType()).hasValue(TitleType.COLLECTION);
			assertThat(titles.get(2).getDisplayOrder()).hasValue(1);
			assertThat(titles.get(3).getValue()).isEqualTo("11th Edition");
			assertThat(titles.get(3).getType()).hasValue(TitleType.EDITION);
			assertThat(titles.get(3).getDisplayOrder()).hasValue(2);
			
			List<Property<?>> sources = m.find().propertyOf(DublinCoreTerm.SOURCE);
			assertThat(sources).hasSize(1);
			assertThat(sources.get(0).getValue()).isEqualTo("http://www.gutenberg.org/files/37523/37523-h/37523-h.htm");
		});	
	}
	
	@Test
	public void test_georgia_pls_ssml() {
		read("georgia-pls-ssml.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(9);
		});	
	}
	
	@Test
	public void test_GhV_oeb_page() {
		read("GhV-oeb-page.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(9);
			
			assertThat(m.find().language().get(0).getValue()).isSameAs(Locale.FRENCH);
			
			List<Contributor> contributors = m.find().contributor();
			assertThat(contributors).hasSize(1);
			assertThat(contributors.get(0).getValue()).isEqualTo("Vincent Gros");
			assertThat(contributors.get(0).getRole()).hasValue(RelatorRole.MARKUP_EDITOR);
			assertThat(contributors.get(0).getNormalizedValue()).hasValue("Gros, Vincent");
		});	
	}
	
	@Test
	public void test_haruko_ahl() {
		read("haruko-ahl.epub", p->{
			assertCommon(p);
			
			assertThat(p.getNumberOfRenditions()).isEqualTo(1);
	
			Rendition r = p.getDefaultRendition();
	
			Metadata m = r.getMetadata();
			assertThat(m.size()).isEqualTo(7);
			
			Language language = m.find().language().get(0);
			assertThat(language.getValue()).isEqualTo(Locale.forLanguageTag("ja-jp"));
			
			CustomAssertions.assertThat(m.find().rendering().layout().get(0)).isSameAs(Layout.PRE_PAGINATED);
			CustomAssertions.assertThat(m.find().rendering().orientation().get(0)).isSameAs(Orientation.PORTRAIT);
			CustomAssertions.assertThat(m.find().rendering().spread().get(0)).isSameAs(Spread.LANDSCAPE);
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
