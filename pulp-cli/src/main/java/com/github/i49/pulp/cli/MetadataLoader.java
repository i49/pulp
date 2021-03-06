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

package com.github.i49.pulp.cli;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.i49.pulp.api.metadata.Metadata;

/**
 * A loader class to load publication metadata from YAML files.
 */
class MetadataLoader {
	
	private final InputStream stream;
	
	private MetadataLoader(InputStream stream) {
		this.stream = stream;
	}
	
	/**
	 * Creates an instance of this class.
	 * @param path the path of the file describing metadata.
	 * @return created instance. 
	 * @throws IOException if the file specified is not found.
	 */
	public static MetadataLoader from(Path path) throws IOException {
		return from(Files.newInputStream(path));
	}
	
	public static MetadataLoader from(InputStream stream) {
		return new MetadataLoader(stream);
	}
	
	public void load(Metadata m) throws IOException {
		m.clear();
		try (InputStream s = stream) {
			loadMetadata(m);
		}
		m.fillMissingProperties();
	}

	private void loadMetadata(Metadata m) {
		Yaml yaml = new Yaml();
		Map<?, ?> map = yaml.loadAs(this.stream, Map.class);
		if (map == null) {
			return;
		}
		parseIdentifiers(m, getEntries(map, "identifier"));
		parseTitles(m, getEntries(map, "title"));
		parseLanguages(m, getEntries(map, "language"));
		parseCreators(m, getEntries(map, "creator"));
		parsePublishers(m, getEntries(map, "publisher"));
		parseDate(m, getDateTime(map, "date"));
		parseModified(m, getDateTime(map, "modified"));
	}
	
	private void parseIdentifiers(Metadata m, List<Entry> entries) {
		for (Entry entry: entries) {
			m.add().identifier(entry.getValue());
		}
	}

	private void parseTitles(Metadata m, List<Entry> entries) {
		for (Entry entry: entries) {
			m.add().title(entry.getValue());
		}
	}

	private void parseLanguages(Metadata m, List<Entry> entries) {
		for (Entry entry: entries) {
			m.add().language(entry.getValue());
		}
	}

	private void parseCreators(Metadata m, List<Entry> entries) {
		for (Entry entry: entries) {
			m.add().creator(entry.getValue());
		}
	}

	private void parsePublishers(Metadata m, List<Entry> entries) {
		for (Entry entry: entries) {
			m.add().publisher(entry.getValue());
		}
	}

	private void parseDate(Metadata m, OffsetDateTime value) {
		if (value != null) {
			m.add().date(value);
		}
	}

	private void parseModified(Metadata m, OffsetDateTime value) {
		if (value != null) {
			m.add().modified(value);
		}
	}
	
	private List<Entry> getEntries(Map<?, ?> map, String key) {
		List<Entry> entries = new ArrayList<>();
		Object value = map.get(key);
		if (value == null) {
			return entries;
		}
		if (value instanceof String) {
			entries.add(new Entry(0, (String)value));
		} else if (value instanceof List) {
			List<?> list = (List<?>)value;
			for (int i = 0; i < list.size(); i++) {
				Object item = list.get(i);
				if (item instanceof String) {
					entries.add(new Entry(i, (String)item));
				}
			}
		}
		return entries;
	}

	private OffsetDateTime getDateTime(Map<?, ?> map, String key) {
		java.util.Date date = (java.util.Date)map.get(key);
		if (date == null) {
			return null;
		}
		return OffsetDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
	}
	
	private static class Entry {
		
		private final int index;
		private final String value;
		
		public Entry(int index, String value) {
			this.index = index;
			this.value = value;
		}
		
		@SuppressWarnings("unused")
		public boolean isFirst() {
			return index == 0;
		}

		public String getValue() {
			return value;
		}
	}
}
