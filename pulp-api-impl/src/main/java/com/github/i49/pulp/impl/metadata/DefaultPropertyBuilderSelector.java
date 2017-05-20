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

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

import com.github.i49.pulp.api.metadata.PropertyBuilderSelector;
import com.github.i49.pulp.api.vocabularies.Generic;
import com.github.i49.pulp.api.vocabularies.GenericText;
import com.github.i49.pulp.api.vocabularies.PropertyBuilder;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.TypedProperty;
import com.github.i49.pulp.api.vocabularies.dc.Contributor;
import com.github.i49.pulp.api.vocabularies.dc.Coverage;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Date;
import com.github.i49.pulp.api.vocabularies.dc.Description;
import com.github.i49.pulp.api.vocabularies.dc.Format;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Relation;
import com.github.i49.pulp.api.vocabularies.dc.Rights;
import com.github.i49.pulp.api.vocabularies.dc.Source;
import com.github.i49.pulp.api.vocabularies.dc.Subject;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.Type;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;
import com.github.i49.pulp.impl.vocabularies.GenericProperty;
import com.github.i49.pulp.impl.vocabularies.GenericTextProperty;
import com.github.i49.pulp.impl.vocabularies.dc.DublinCoreElements;
import com.github.i49.pulp.impl.vocabularies.dcterms.DublinCoreTerms;

/**
 * The default implementation of {@link PropertyBuilderSelector}.
 */
class DefaultPropertyBuilderSelector implements PropertyBuilderSelector {
	
	private final PropertySet properties;
	
	DefaultPropertyBuilderSelector(PropertySet properties) {
		this.properties = properties;
	}
	
	@Override
	public Contributor.Builder contributor(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.contributor().value(value));
	}

	@Override
	public Coverage.Builder coverage(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.coverage().value(value));
	}

	@Override
	public Creator.Builder creator(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.creator().value(value));
	}

	@Override
	public Date.Builder date(OffsetDateTime value) {
		checkNotNull(value, "value");
		return add(DublinCoreElements.date().value(value));
	}

	@Override
	public Description.Builder description(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.description().value(value));
	}

	@Override
	public Format.Builder format(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.format().value(value));
}

	@Override
	public Identifier.Builder identifier() {
		UUID uuid = UUID.randomUUID();
		String value = "urn:uuid:" + uuid.toString();
		return identifier(value);
	}

	@Override
	public Identifier.Builder identifier(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.identifier().value(value));
	}

	@Override
	public Language.Builder language(Locale value) {
		checkNotNull(value, "value");
		return add(DublinCoreElements.language().value(value));
	}

	@Override
	public Language.Builder language(String value) {
		checkNotBlank(value, "value");
		Locale locale = new Locale.Builder().setLanguageTag(value).build();
		return language(locale);
	}
	
	@Override
	public Modified.Builder modified(OffsetDateTime value) {
		checkNotNull(value, "value");
		return add(DublinCoreTerms.modified().value(value));
	}

	@Override
	public Publisher.Builder publisher(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.publisher().value(value));
	}

	@Override
	public Relation.Builder relation(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.relation().value(value));
	}

	@Override
	public Rights.Builder rights(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.rights().value(value));
	}

	@Override
	public Source.Builder source(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.source().value(value));
	}

	@Override
	public Subject.Builder subject(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.subject().value(value));
	}

	@Override
	public Title.Builder title(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.title().value(value));
	}

	@Override
	public Type.Builder type(String value) {
		checkNotBlank(value, "value");
		return add(DublinCoreElements.type().value(value));
	}

	@Override
	public <V> Generic.Builder<V> generic(Term term, V value) {
		checkNotNull(term, "term");
		checkNotNull(value, "value");
		Generic.Builder<V> b = GenericProperty.builder(term);
		b.value(value);
		return add(b);
	}

	@Override
	public GenericText.Builder generic(Term term, String value) {
		checkNotNull(term, "term");
		checkNotBlank(value, "value");
		return add(GenericTextProperty.builder(term).value(value));
	}
	
	/**
	 * Adds deferred property to the property set.
	 * 
	 * @param builder the builder to build the final property.
	 * @return the builder.
	 */
	private <V, T extends TypedProperty<V>, R extends PropertyBuilder<V, T, R>> 
	R add(R builder) {
		Term term = builder.getTerm();
		DeferredProperty<V> deferred = DeferredProperty.of(term, builder);
		this.properties.add(deferred);
		return builder;
	}
}
