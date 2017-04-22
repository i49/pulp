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

package com.github.i49.pulp.impl.metadata;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.Contributor;
import com.github.i49.pulp.api.metadata.Coverage;
import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.Description;
import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.Format;
import com.github.i49.pulp.api.metadata.Identifier;
import com.github.i49.pulp.api.metadata.IdentifierScheme;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.Publisher;
import com.github.i49.pulp.api.metadata.Relation;
import com.github.i49.pulp.api.metadata.RelatorBuilder;
import com.github.i49.pulp.api.metadata.Rights;
import com.github.i49.pulp.api.metadata.Source;
import com.github.i49.pulp.api.metadata.Subject;
import com.github.i49.pulp.api.metadata.SubjectAuthority;
import com.github.i49.pulp.api.metadata.Title;
import com.github.i49.pulp.api.metadata.TitleBuilder;
import com.github.i49.pulp.api.metadata.Type;

/**
 * The single implementation of {@link PropertyFactory}.
 */
public class DefaultPropertyFactory implements PropertyFactory {

	@Override
	public RelatorBuilder<Contributor> getContributorBuilder(String name) {
		name = requireNonEmpty(name, "name");
		return DefaultContributor.builder(name);
	}

	@Override
	public RelatorBuilder<Creator> getCreatorBuilder(String name) {
		name = requireNonEmpty(name, "name");
		return DefaultCreator.builder(name);
	}

	@Override
	public RelatorBuilder<Publisher> getPublisherBuilder(String name) {
		name = requireNonEmpty(name, "name");
		return DefaultPublisher.builder(name);
	}

	@Override
	public TitleBuilder getTitleBuilder(String value) {
		value = requireNonEmpty(value, "value");
		return DefaultTitle.builder(value);
	}

	@Override
	public Contributor newContributor(String name) {
		return getContributorBuilder(name).build();
	}

	@Override
	public Contributor newContributor(String name, Locale language) {
		return getContributorBuilder(name).language(language).build();
	}

	@Override
	public Coverage newCoverage(String text) {
		text = requireNonEmpty(text, "text");
		return new DefaultCoverage(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		return new DefaultCoverage(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language, Direction direction) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		requireNonNull(direction, "direction");
		return new DefaultCoverage(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Creator newCreator(String name) {
		return getCreatorBuilder(name).build();
	}

	@Override
	public Creator newCreator(String name, Locale language) {
		return getCreatorBuilder(name).language(language).build();
	}

	@Override
	public Description newDescription(String text) {
		text = requireNonEmpty(text, "text");
		return new DefaultDescription(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		return new DefaultDescription(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language, Direction direction) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		requireNonNull(direction, "direction");
		return new DefaultDescription(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Format newFormat(String value) {
		value = requireNonEmpty(value, "value");
		return new DefaultFormat(value);
	}

	@Override
	public Identifier newIdentifier() {
		return DefaultIdentifier.ofRandomUUID();
	}

	@Override
	public Identifier newIdentifier(String value) {
		value = requireNonEmpty(value, "value");
		return new DefaultIdentifier(value);
	}

	@Override
	public Identifier newIdentifier(String value, IdentifierScheme scheme) {
		value = requireNonEmpty(value, "value");
		requireNonNull(scheme, "scheme");
		return new DefaultIdentifier(value, scheme);
	}

	@Override
	public Identifier newIdentifier(String value, URI schemeURI) {
		value = requireNonEmpty(value, "value");
		requireNonNull(schemeURI, "schemeURI");
		return new DefaultIdentifier(value, schemeURI);
	}

	@Override
	public Publisher newPublisher(String name) {
		return getPublisherBuilder(name).build();
	}

	@Override
	public Publisher newPublisher(String name, Locale language) {
		return getPublisherBuilder(name).language(language).build();
	}

	@Override
	public Relation newRelation(String text) {
		text = requireNonEmpty(text, "text");
		return new DefaultRelation(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		return new DefaultRelation(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language, Direction direction) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		requireNonNull(direction, "direction");
		return new DefaultRelation(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Rights newRights(String text) {
		text = requireNonEmpty(text, "text");
		return new DefaultRights(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		return new DefaultRights(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language, Direction direction) {
		text = requireNonEmpty(text, "text");
		requireNonNull(language, "language");
		requireNonNull(direction, "direction");
		return new DefaultRights(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Source newSource(String value) {
		value = requireNonEmpty(value, "value");
		return new DefaultSource(value, Optional.empty());
	}

	@Override
	public Source newSource(String value, String scheme) {
		value = requireNonEmpty(value, "value");
		requireNonNull(scheme, "scheme");
		return new DefaultSource(value, Optional.of(scheme));
	}

	@Override
	public Subject newSubject(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject newSubject(String value, SubjectAuthority authority, String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject newSubject(String value, URI scheme, String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Title newTitle(String value) {
		return getTitleBuilder(value).build();
	}

	@Override
	public Title newTitle(String value, Locale language) {
		return getTitleBuilder(value).language(language).build();
	}

	@Override
	public Type newType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null");
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	private static <T> T requireNonNull(T object, String name) {
		if (object == null) {
			throw new IllegalArgumentException("\"" + name + "\" must not be null");
		}
		return object;
	}

	private static String requireNonEmpty(String value, String name) {
		requireNonNull(value, name);
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			throw new IllegalArgumentException("\"" + name + "\" must not be blank");
		}
		return trimmed;
	}
}
