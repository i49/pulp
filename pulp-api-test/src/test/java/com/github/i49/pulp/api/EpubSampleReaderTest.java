package com.github.i49.pulp.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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

	@Test
	public void read_accessible_epub_3() {
		Publication p = read("accessible_epub_3.epub");
		
		testCommon(p);
		
		assertThat(p.getNumberOfRenditions(), is(1));
		
		Rendition r = p.getDefaultRendition();
		
		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems(), is(35));

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages(), is(22));
		assertLinearExcept(s, 0);
	}
	
	@Test
	public void read_cc_shared_culture() {
		Publication p = read("cc-shared-culture.epub");
		
		testCommon(p);
		
		assertThat(p.getNumberOfRenditions(), is(1));
		
		Rendition r = p.getDefaultRendition();
		
		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems(), is(21));

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages(), is(8));
		assertLinearExcept(s, 0);
	}
	
	@Test
	public void read_childrens_literature() {
		Publication p = read("childrens-literature.epub");
		
		testCommon(p);
		
		assertThat(p.getNumberOfRenditions(), is(1));

		Rendition r = p.getDefaultRendition();

		Manifest m = r.getManifest();
		assertThat(m.getNumberOfItems(), is(7));

		Spine s = r.getSpine();
		assertThat(s.getNumberOfPages(), is(3));
		assertLinearExcept(s);
	}
	
	protected Publication read(String filename) {
		Path filePath = basePath.resolve(filename);
		PublicationReader reader = Epub.createReader(filePath);
		return reader.read();
	}
	
	protected void testCommon(Publication p) {
		assertThat(p, is(notNullValue()));
		
		Rendition r = p.getDefaultRendition();
		assertThat(r, is(notNullValue()));
		
		Manifest m = r.getManifest();
		assertThat(m, is(notNullValue()));

		for (Manifest.Item item: m) {
			assertThat(item, is(notNullValue()));
			assertThat(item.getLocation(), is(notNullValue()));
			assertThat(item.getResource(), is(notNullValue()));
			assertThat(item.getResource().getLocation(), is(notNullValue()));
			assertThat(item.getResource().getMediaType(), is(notNullValue()));
		}

		Spine s = r.getSpine();
		assertThat(s, is(notNullValue()));
		for (Page page: s) {
			assertThat(page, is(notNullValue()));
			assertThat(page.getItem(), is(notNullValue()));
		}
	}
	
	protected void assertLinearExcept(Spine spine, int... indices) {
		int next = 0;
		for (int i = 0; i < spine.getNumberOfPages(); i++) {
			Page page = spine.get(i);
			if (next < indices.length && i == indices[next]) {
				assertThat(page.isLinear(), is(false));
			} else {
				assertThat(page.isLinear(), is(true));
			}
		}
		
	}
}
