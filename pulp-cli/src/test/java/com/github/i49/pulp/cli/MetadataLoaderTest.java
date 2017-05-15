package com.github.i49.pulp.cli;

import static org.assertj.core.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.api.vocabulary.dc.Title;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;
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
		Identifier[] identifiers = m.find().identifier().asArray();
		assertThat(identifiers).hasSize(1);
		assertThat(identifiers[0].getValueAsString()).isEqualTo("idpf.epub31.samples.moby-dick.xhtml");
		Title[] title = m.find().title().asArray();
		assertThat(title).hasSize(1);
		assertThat(title[0].getValueAsString()).isEqualTo("Moby-Dick");
		Language[] languages = m.find().language().asArray();
		assertThat(languages).hasSize(1);
		assertThat(languages[0].getValueAsString()).isEqualTo("en-US");
		Creator[] creators = m.find().creator().asArray();
		assertThat(creators).hasSize(1);
		assertThat(creators[0].getValueAsString()).isEqualTo("Herman Melville");
		Publisher[] publishers = m.find().publisher().asArray();
		assertThat(publishers).hasSize(1);
		assertThat(publishers[0].getValueAsString()).isEqualTo("Harper & Brothers, Publishers");
		Date[] dates = m.find().date().asArray();
		assertThat(dates).hasSize(1);
		assertThat(dates[0].getValue()).isEqualTo(OffsetDateTime.of(1851, 11, 14, 0, 0, 0, 0, ZoneOffset.UTC));
		Modified[] modified = m.find().modified().asArray();
		assertThat(modified).hasSize(1);
		assertThat(modified[0].getValue()).isEqualTo(OffsetDateTime.of(2016, 2, 5, 14, 40, 0, 0, ZoneOffset.UTC));
	}
	
	private InputStream openResource(String name) {
		return getClass().getResourceAsStream(name);
	}
}
