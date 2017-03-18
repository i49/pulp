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

import static com.github.i49.pulp.api.EpubAssertions.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.i49.pulp.api.Spine.Page;

public class EpubSampleReaderTest {

	private static Path basePath;
	
	@BeforeClass
	public static void setUpOnce() {
		String samplesDir = System.getProperty("epub3.samples.dir");
		Path samplesPath = Paths.get(samplesDir);
		basePath = samplesPath.resolve("30");
	}
	
	private static boolean shouldCheckLength() {
		return false;
	}

	@Test
	public void read_accessible_epub_3() {
		Publication p = read("accessible_epub_3.epub");
		
		assertCommon(p);
		
		assertThat(p.getNumberOfRenditions()).isEqualTo(1);
		
		Rendition r = p.getDefaultRendition();
		
		Manifest m = r.getManifest();
		assertThat(m).hasItems(35);
		
		if (shouldCheckLength()) {
			assertThat(m)
			.hasResource("bk01-toc.xhtml", 4284)
			.hasResource("ch01.xhtml", 6862)
			.hasResource("ch01s02.xhtml", 6866)
			.hasResource("ch02.xhtml", 68916)
			.hasResource("ch02s02.xhtml", 12313)
			.hasResource("ch02s03.xhtml", 9874)
			.hasResource("ch03.xhtml", 15003)
			.hasResource("ch03s02.xhtml", 29099)
			.hasResource("ch03s03.xhtml", 43129)
			.hasResource("ch03s04.xhtml", 6954)
			.hasResource("ch03s05.xhtml", 34190)
			.hasResource("ch03s06.xhtml", 2389)
			.hasResource("ch04.xhtml", 4732)
			.hasResource("co01.xhtml", 467)
			.hasResource("cover.xhtml", 422)
			.hasResource("covers/9781449328030_lrg.jpg", 820269)
			.hasResource("css/epub.css", 20117)
			.hasResource("css/synth.css", 138)
			.hasResource("fonts/FreeSansBold.otf", 366460)
			.hasResource("fonts/FreeSerif.otf", 2184720)
			.hasResource("fonts/UbuntuMono-B.ttf", 191400)
			.hasResource("fonts/UbuntuMono-BI.ttf", 216208)
			.hasResource("fonts/UbuntuMono-R.ttf", 205748)
			.hasResource("fonts/UbuntuMono-RI.ttf", 210216)
			.hasResource("images/spi_global_ad.png", 302554)
			.hasResource("images/web/epub3_0401.png", 1131419)
			.hasResource("index.xhtml", 2896)
			.hasResource("lexicon/en.pls", 394)
			.hasResource("lexicon/fr.pls", 297)
			.hasResource("pr01.xhtml", 4302)
			.hasResource("pr01s02.xhtml", 1670)
			.hasResource("pr01s03.xhtml", 1624)
			.hasResource("pr01s04.xhtml", 1975)
			.hasResource("pr01s05.xhtml", 1211)
			.hasResource("spi-ad.xhtml", 387);
		}
		
		assertThat(m.get("covers/9781449328030_lrg.jpg").isCoverImage()).isTrue();
		assertThat(m.get("bk01-toc.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s).hasPages(22).linearExcept(0);
	}
	
	@Test
	public void read_cc_shared_culture() {
		Publication p = read("cc-shared-culture.epub");
		
		assertCommon(p);
		
		assertThat(p.getNumberOfRenditions()).isEqualTo(1);
		
		Rendition r = p.getDefaultRendition();
		
		Manifest m = r.getManifest();
		assertThat(m).hasItems(21);
		
		if (shouldCheckLength()) {
			assertThat(m)
			.hasResource("audio/asharedculture_soundtrack.mp3", 3265152)
			.hasResource("captions/cc-en.vtt", 5719)
			.hasResource("captions/cc-en.xml", 6704)
			.hasResource("captions/cc-fr.vtt", 6169)
			.hasResource("captions/cc-fr.xml", 7140)
			.hasResource("css/shared-culture.css", 2281)
			.hasResource("fonts/Quicksand_Bold_Oblique.otf", 38172)
			.hasResource("fonts/Quicksand_Light.otf", 35504)
			.hasResource("images/2565514353_2ae2073e14.jpg", 88094)
			.hasResource("images/326261902_3fa36f548d.jpg", 279551)
			.hasResource("script/shared.js", 863)
			.hasResource("video/shared-culture.mp4", 21784780)
			.hasResource("video/shared-culture.webm", 8330669)
			.hasResource("xhtml/cover.xhtml", 572)
			.hasResource("xhtml/p10.xhtml", 3021)
			.hasResource("xhtml/p20.xhtml", 2903)
			.hasResource("xhtml/p30.xhtml", 2320)
			.hasResource("xhtml/p40.xhtml", 2400)
			.hasResource("xhtml/p50.xhtml", 3042)
			.hasResource("xhtml/p60.xhtml", 6201)
			.hasResource("xhtml/toc.xhtml", 3162);
		}
		
		assertThat(m.get("images/326261902_3fa36f548d.jpg").isCoverImage()).isTrue();
		assertThat(m.get("xhtml/p20.xhtml").isScripted()).isTrue();
		assertThat(m.get("xhtml/toc.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s).hasPages(8).linearExcept(0);
	}
	
	@Test
	public void read_childrens_literature() {
		Publication p = read("childrens-literature.epub");
		
		assertCommon(p);
		
		assertThat(p.getNumberOfRenditions()).isEqualTo(1);

		Rendition r = p.getDefaultRendition();

		Manifest m = r.getManifest();
		assertThat(m).hasItems(7);
		
		if (shouldCheckLength()) {
			assertThat(m)
			.hasResource("cover.xhtml", 392)
			.hasResource("css/epub.css", 1473)
			.hasResource("css/nav.css", 570)
			.hasResource("images/cover.png", 41134)
			.hasResource("nav.xhtml", 12655)
			.hasResource("s04.xhtml", 342935)
			.hasResource("toc.ncx", 17140);
		}
		
		assertThat(m.get("images/cover.png").isCoverImage()).isTrue();
		assertThat(m.get("nav.xhtml").isScripted()).isTrue();
		assertThat(m.get("nav.xhtml").isNavigation()).isTrue();

		Spine s = r.getSpine();
		assertThat(s).hasPages(3).linearExcept();
	}
	
	protected Publication read(String filename) {
		Path filePath = basePath.resolve(filename);
		PublicationReader reader = Epub.createReader(filePath);
		return reader.read();
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
