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
import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.DateProperty;
import com.github.i49.pulp.api.metadata.DublinCoreTerm;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Property;
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
		List<Property> list = m.getList(DublinCore.IDENTIFIER);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValueAsString()).isEqualTo("idpf.epub31.samples.moby-dick.xhtml");
		list = m.getList(DublinCore.TITLE);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValueAsString()).isEqualTo("Moby-Dick");
		list = m.getList(DublinCore.LANGUAGE);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValueAsString()).isEqualTo("en-US");
		list = m.getList(DublinCore.CREATOR);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValueAsString()).isEqualTo("Herman Melville");
		list = m.getList(DublinCore.PUBLISHER);
		assertThat(list).hasSize(1);
		assertThat(list.get(0).getValueAsString()).isEqualTo("Harper & Brothers, Publishers");
		list = m.getList(DublinCore.DATE);
		assertThat(list).hasSize(1);
		assertThat(((DateProperty)list.get(0)).getValue()).isEqualTo(OffsetDateTime.of(1851, 11, 14, 0, 0, 0, 0, ZoneOffset.UTC));
		list = m.getList(DublinCoreTerm.MODIFIED);
		assertThat(list).hasSize(1);
		assertThat(((DateProperty)list.get(0)).getValue()).isEqualTo(OffsetDateTime.of(2016, 2, 5, 14, 40, 0, 0, ZoneOffset.UTC));
	}
	
	private InputStream openResource(String name) {
		return getClass().getResourceAsStream(name);
	}
}
