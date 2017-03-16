package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class PublicationTest {

	private Publication publication;

	@Before
	public void setUp() {
		publication = Epub.createPublication();
	}
	
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
	
	@Test
	public void iterator_shouldIterateRenditions() {
		Rendition r1 = publication.addRendition();
		Rendition r2 = publication.addRendition("another/package.opf");
		assertThat(publication.iterator()).containsExactly(r1, r2);
	}
	
	@Test
	public void addRendition_shouldAddDefaultRendition() {
		Rendition r = publication.addRendition();
		assertThat(r.getLocation().toString()).isEqualTo("EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldAddRenditionIfLocationIsValid() {
		Rendition r = publication.addRendition("path/to/package.opf");
		assertThat(r.getLocation().toString()).isEqualTo("path/to/package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowIfLocationHasScheme() {
		assertThatThrownBy(()->{
			publication.addRendition("http://example.org/EPUB/package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("http://example.org/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowIfLocationIsPathAbsolute() {
		assertThatThrownBy(()->{
			publication.addRendition("/EPUB/package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("/EPUB/package.opf");
	}

	@Test
	public void addRendition_shouldThrowIfLocationContainsDotSegments() {
		assertThatThrownBy(()->{
			publication.addRendition("../package.opf");
		}).isInstanceOf(EpubException.class).hasMessageContaining("../package.opf");
	}
	
	@Test
	public void addRendition_shouldThrowIfRenditionAlreadyExists() {
		assertThatThrownBy(()->{
			publication.addRendition();
			publication.addRendition();
		}).isInstanceOf(EpubException.class).hasMessageContaining("EPUB/package.opf");
	}
}
