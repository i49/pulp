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
import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Date;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Modified;
import com.github.i49.pulp.api.metadata.Property;
import com.github.i49.pulp.cli.MetadataLoader;

public class MetadataLoaderTest {
	
	private Metadata m;
	
	@Before
	public void setUp() {
		Rendition rendition = Epub.createPublication().addRendition(null);
		m = rendition.getMetadata(); 
	}

	@Test
	public void load_shouldLoadMetadata() throws IOException {
		try (InputStream s = openResource("metadata.yaml")) {
			MetadataLoader loader = MetadataLoader.from(s);
			loader.load(m);
		}
		List<Property> list = m.getList(BasicTerm.IDENTIFIER);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValue()).isEqualTo("idpf.epub31.samples.moby-dick.xhtml");
		list = m.getList(BasicTerm.TITLE);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValue()).isEqualTo("Moby-Dick");
		list = m.getList(BasicTerm.LANGUAGE);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValue()).isEqualTo("en-US");
		list = m.getList(BasicTerm.CREATOR);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValue()).isEqualTo("Herman Melville");
		list = m.getList(BasicTerm.PUBLISHER);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValue()).isEqualTo("Harper & Brothers, Publishers");
		list = m.getList(BasicTerm.DATE);
		assertThat(list).hasSize(1);
		assertThat(((Date)list.get(0)).getDateTime()).isEqualTo(OffsetDateTime.of(1851, 11, 14, 0, 0, 0, 0, ZoneOffset.UTC));
		list = m.getList(BasicTerm.MODIFIED);
		assertThat(list).hasSize(1);
		assertThat(((Modified)list.get(0)).getDateTime()).isEqualTo(OffsetDateTime.of(2016, 2, 5, 14, 40, 0, 0, ZoneOffset.UTC));
	}
	
	private InputStream openResource(String name) {
		return getClass().getResourceAsStream(name);
	}
}
