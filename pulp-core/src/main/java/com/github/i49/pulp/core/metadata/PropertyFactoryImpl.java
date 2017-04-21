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

package com.github.i49.pulp.core.metadata;

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
 * An implementation of {@link PropertyFactory}.
 */
public class PropertyFactoryImpl implements PropertyFactory {

	@Override
	public RelatorBuilder<Contributor> builderForContributor(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelatorBuilder<Creator> builderForCreator(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelatorBuilder<Publisher> builderForPublisher(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TitleBuilder builderForTitle(String value) {
		assertNotNull(value, "value");
		value = validatePropertyValue(value);
		return TitleImpl.builder(value);
	}

	@Override
	public Contributor newContributor(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contributor newContributor(String name, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coverage newCoverage(String text) {
		assertNotNull(text, "text");
		text = validatePropertyValue(text);
		return new CoverageImpl(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		text = validatePropertyValue(text);
		return new CoverageImpl(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Coverage newCoverage(String text, Locale language, Direction direction) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		assertNotNull(direction, "direction");
		text = validatePropertyValue(text);
		return new CoverageImpl(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Creator newCreator(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Creator newCreator(String name, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description newDescription(String text) {
		assertNotNull(text, "text");
		text = validatePropertyValue(text);
		return new DescriptionImpl(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		text = validatePropertyValue(text);
		return new DescriptionImpl(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Description newDescription(String text, Locale language, Direction direction) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		assertNotNull(direction, "direction");
		text = validatePropertyValue(text);
		return new DescriptionImpl(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Format newFormat(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identifier newIdentifier() {
		return IdentifierImpl.ofRandomUUID();
	}

	@Override
	public Identifier newIdentifier(String value) {
		assertNotNull(value, "value");
		value = validatePropertyValue(value);
		return new IdentifierImpl(value);
	}

	@Override
	public Identifier newIdentifier(String value, IdentifierScheme scheme) {
		assertNotNull(value, "value");
		assertNotNull(scheme, "scheme");
		value = validatePropertyValue(value);
		return new IdentifierImpl(value, scheme);
	}

	@Override
	public Identifier newIdentifier(String value, URI schemeURI) {
		assertNotNull(value, "value");
		assertNotNull(schemeURI, "schemeURI");
		value = validatePropertyValue(value);
		return new IdentifierImpl(value, schemeURI);
	}

	@Override
	public Publisher newPublisher(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publisher newPublisher(String name, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation newRelation(String text) {
		assertNotNull(text, "text");
		text = validatePropertyValue(text);
		return new RelationImpl(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		text = validatePropertyValue(text);
		return new RelationImpl(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Relation newRelation(String text, Locale language, Direction direction) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		assertNotNull(direction, "direction");
		text = validatePropertyValue(text);
		return new RelationImpl(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Rights newRights(String text) {
		assertNotNull(text, "text");
		text = validatePropertyValue(text);
		return new RightsImpl(text, Optional.empty(), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		text = validatePropertyValue(text);
		return new RightsImpl(text, Optional.of(language), Optional.empty());
	}

	@Override
	public Rights newRights(String text, Locale language, Direction direction) {
		assertNotNull(text, "text");
		assertNotNull(language, "language");
		assertNotNull(direction, "direction");
		text = validatePropertyValue(text);
		return new RightsImpl(text, Optional.of(language), Optional.of(direction));
	}

	@Override
	public Source newSource(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Source newSource(String value, String scheme) {
		// TODO Auto-generated method stub
		return null;
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
		return builderForTitle(value).build();
	}

	@Override
	public Title newTitle(String value, Locale language) {
		return builderForTitle(value).language(language).build();
	}

	@Override
	public Type newType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null");
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	private static void assertNotNull(Object parameter, String name) {
		if (parameter == null) {
			throw new IllegalArgumentException("\" + name + \" must not be null");
		}
	}
	
	private static String validatePropertyValue(String value) {
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			throw new IllegalArgumentException("property value must not be blank");
		}
		return trimmed;
	}
}
