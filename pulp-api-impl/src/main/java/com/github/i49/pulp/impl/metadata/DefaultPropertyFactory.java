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

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.github.i49.pulp.api.metadata.Contributor;
import com.github.i49.pulp.api.metadata.Coverage;
import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.DateProperty;
import com.github.i49.pulp.api.metadata.Description;
import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.DublinCore;
import com.github.i49.pulp.api.metadata.DublinCoreTerm;
import com.github.i49.pulp.api.metadata.SimpleProperty;
import com.github.i49.pulp.api.metadata.GenericProperty;
import com.github.i49.pulp.api.metadata.IdentifierProperty;
import com.github.i49.pulp.api.metadata.LanguageProperty;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.PropertyType;
import com.github.i49.pulp.api.metadata.PublicationType;
import com.github.i49.pulp.api.metadata.Publisher;
import com.github.i49.pulp.api.metadata.Relation;
import com.github.i49.pulp.api.metadata.Relator;
import com.github.i49.pulp.api.metadata.Rights;
import com.github.i49.pulp.api.metadata.Source;
import com.github.i49.pulp.api.metadata.Subject;
import com.github.i49.pulp.api.metadata.SubjectAuthority;
import com.github.i49.pulp.api.metadata.Term;
import com.github.i49.pulp.api.metadata.Title;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link PropertyFactory}.
 * Only one instance can be created for this class.
 */
public class DefaultPropertyFactory implements PropertyFactory {
	
	// Predefined special publication types.
	private static final Map<String, PublicationType> PREDEFINED_TYPES = new HashMap<>();
	
	static {
		for (PublicationType type: PublicationType.values()) {
			PREDEFINED_TYPES.put(type.getValue(), type);
		}
	}

	@Override
	public <V> GenericProperty<V> createGenericProperty(Term term, V value) {
		checkNotNull(term, "term");
		checkNotNull(value, "value");
		if (term.getType() != PropertyType.GENERIC) {
			throw new IllegalArgumentException(
					Messages.METADATA_PROPERTY_TYPE_MISMATCHED(PropertyType.GENERIC, term));
		}
		return new DefaultGenericProperty<V>(term, value);
	}
	
	@Override
	public Relator.Builder<Contributor> getContributorBuilder(String name) {
		checkNotBlank(name, "name");
		return DefaultContributor.builder(name);
	}

	@Override
	public Relator.Builder<Creator> getCreatorBuilder(String name) {
		checkNotBlank(name, "name");
		return DefaultCreator.builder(name);
	}

	@Override
	public Relator.Builder<Publisher> getPublisherBuilder(String name) {
		checkNotBlank(name, "name");
		return DefaultPublisher.builder(name);
	}

	@Override
	public Title.Builder getTitleBuilder(String value) {
		checkNotBlank(value, "value");
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
		checkNotBlank(text, "text");
		return new DefaultCoverage(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		return new DefaultCoverage(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language, Direction direction) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		checkNotNull(direction, "direction");
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
	public DateProperty newDate(OffsetDateTime value) {
		checkNotNull(value, "value");
		return new DefaultDateProperty(DublinCore.DATE, value);
	}
	
	@Override
	public Description newDescription(String text) {
		checkNotBlank(text, "text");
		return new DefaultDescription(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		return new DefaultDescription(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language, Direction direction) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		checkNotNull(direction, "direction");
		return new DefaultDescription(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public SimpleProperty newFormat(String value) {
		checkNotBlank(value, "value");
		return new DefaultSimpleProperty(DublinCore.FORMAT, value);
	}

	@Override
	public IdentifierProperty newIdentifier() {
		return DefaultIdentifierProperty.ofRandomUUID();
	}

	@Override
	public IdentifierProperty newIdentifier(String value) {
		checkNotBlank(value, "value");
		return new DefaultIdentifierProperty(value);
	}

	@Override
	public LanguageProperty newLanguage(String languageTag) {
		checkNotBlank(languageTag, "languageTag");
		languageTag = languageTag.trim();
		Locale locale = new Locale.Builder().setLanguageTag(languageTag).build();
		return newLanguage(locale);
	}

	@Override
	public LanguageProperty newLanguage(Locale language) {
		checkNotNull(language, "language");
		return new DefaultLanguageProperty(language);
	}
	
	@Override
	public DateProperty newModified(OffsetDateTime value) {
		checkNotNull(value, "value");
		return new DefaultDateProperty(DublinCoreTerm.MODIFIED, value);
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
		checkNotBlank(text, "text");
		return new DefaultRelation(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		return new DefaultRelation(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language, Direction direction) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		checkNotNull(direction, "direction");
		return new DefaultRelation(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Rights newRights(String text) {
		checkNotBlank(text, "text");
		return new DefaultRights(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		return new DefaultRights(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language, Direction direction) {
		checkNotBlank(text, "text");
		checkNotNull(language, "language");
		checkNotNull(direction, "direction");
		return new DefaultRights(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Source newSource(String value) {
		checkNotBlank(value, "value");
		return new DefaultSource(value, Optional.empty());
	}

	@Override
	public Source newSource(String value, String scheme) {
		checkNotBlank(value, "value");
		checkNotNull(scheme, "scheme");
		return new DefaultSource(value, Optional.of(scheme));
	}

	@Override
	public Subject newSubject(String value) {
		checkNotBlank(value, "value");
		return new DefaultSubject(value);
	}

	@Override
	public Subject newSubject(String value, SubjectAuthority authority, String code) {
		checkNotBlank(value, "value");
		checkNotNull(authority, "authority");
		checkNotBlank(code, "code");
		return new DefaultSubject(value, authority, code);
	}

	@Override
	public Subject newSubject(String value, URI scheme, String code) {
		checkNotBlank(value, "value");
		checkNotNull(scheme, "scheme");
		checkNotBlank(code, "code");
		return new DefaultSubject(value, scheme, code);
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
	public SimpleProperty newType(String value) {
		checkNotBlank(value, "value");
		SimpleProperty type = PREDEFINED_TYPES.get(value);
		if (type != null) {
			return type;
		}
		return new DefaultSimpleProperty(DublinCore.TYPE, value);
	}
}
