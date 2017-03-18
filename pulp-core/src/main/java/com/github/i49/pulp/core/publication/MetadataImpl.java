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

package com.github.i49.pulp.core.publication;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.i49.pulp.api.Metadata;

class MetadataImpl implements Metadata {

	private String identifier;
	private final List<String> titles = new ArrayList<>();
	private final List<Locale> languages = new ArrayList<>();
	
	private final List<String> creators = new ArrayList<>();
	private final List<String> publishers = new ArrayList<>();
	
	private OffsetDateTime date;
	private OffsetDateTime lastModified;
	
	public MetadataImpl() {
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public List<String> getTitles() {
		return titles;
	}
	
	@Override
	public List<Locale> getLanguages() {
		return languages;
	}
	
	@Override
	public List<String> getCreators() {
		return creators;
	}
	
	@Override
	public List<String> getPublishers() {
		return publishers;
	}
	
	@Override
	public OffsetDateTime getDate() {
		return date;
	}
	
	@Override
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	
	@Override
	public OffsetDateTime getLastModified() {
		return lastModified;
	}
	
	@Override
	public void setLastModified(OffsetDateTime lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (getIdentifier() != null) {
			b.append("Identifier: ").append(getIdentifier()).append("\n");
		}
		for (String title: getTitles()) {
			b.append("Title: ").append(title).append("\n");
		}
		for (Locale language: getLanguages()) {
			b.append("Language: ").append(language.toLanguageTag()).append("\n");
		}
		for (String creator : getCreators()) {
			b.append("Creator: ").append(creator).append("\n");
		}
		for (String publisher: getPublishers()) {
			b.append("Publisher: ").append(publisher).append("\n");
		}
		if (getDate() != null) {
			b.append("Date: ").append(getDate()).append("\n");
		}
		if (getLastModified() != null) {
			b.append("Last Modified: ").append(getLastModified()).append("\n");
		}
		return b.toString();
	}
}
