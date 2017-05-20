package com.github.i49.pulp.cli;

import static org.assertj.core.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Date;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;
import com.github.i49.pulp.cli.MetadataLoader;

public class MetadataLoaderTest {
	
	private Metadata m;
	
	@Before
	public void setUp() {
		Rendition rendition = Epub.createPublication().addRendition();
		m = rendition.getMetadata(); 
	}

	@Test
	public void load_shouldLoadMetadata() throws IOException {
		try (InputStream s = openResource("metadata.yaml")) {
			MetadataLoader loader = MetadataLoader.from(s);
			loader.load(m);
		}
		List<Identifier> identifiers = m.find().identifier();
		assertThat(identifiers).hasSize(1);
		assertThat(identifiers.get(0).getValueAsString()).isEqualTo("idpf.epub31.samples.moby-dick.xhtml");
		List<Title> titles = m.find().title();
		assertThat(titles).hasSize(1);
		assertThat(titles.get(0).getValueAsString()).isEqualTo("Moby-Dick");
		List<Language> languages = m.find().language();
		assertThat(languages).hasSize(1);
		assertThat(languages.get(0).getValueAsString()).isEqualTo("en-US");
		List<Creator> creators = m.find().creator();
		assertThat(creators).hasSize(1);
		assertThat(creators.get(0).getValueAsString()).isEqualTo("Herman Melville");
		List<Publisher> publishers = m.find().publisher();
		assertThat(publishers).hasSize(1);
		assertThat(publishers.get(0).getValueAsString()).isEqualTo("Harper & Brothers, Publishers");
		List<Date> dates = m.find().date();
		assertThat(dates).hasSize(1);
		assertThat(dates.get(0).getValue()).isEqualTo(OffsetDateTime.of(1851, 11, 14, 0, 0, 0, 0, ZoneOffset.UTC));
		List<Modified> modified = m.find().modified();
		assertThat(modified).hasSize(1);
		assertThat(modified.get(0).getValue()).isEqualTo(OffsetDateTime.of(2016, 2, 5, 14, 40, 0, 0, ZoneOffset.UTC));
	}
	
	private InputStream openResource(String name) {
		return getClass().getResourceAsStream(name);
	}
}
