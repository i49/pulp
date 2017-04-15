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

import com.github.i49.pulp.api.metadata.Contributor;
import com.github.i49.pulp.api.metadata.Coverage;
import com.github.i49.pulp.api.metadata.Creator;
import com.github.i49.pulp.api.metadata.Description;
import com.github.i49.pulp.api.metadata.Direction;
import com.github.i49.pulp.api.metadata.Format;
import com.github.i49.pulp.api.metadata.Identifier;
import com.github.i49.pulp.api.metadata.IdentifierScheme;
import com.github.i49.pulp.api.metadata.MetadataPropertyFactory;
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
 * An implementation of {@link MetadataPropertyFactory}.
 */
public class MetadataPropertyFactoryImpl implements MetadataPropertyFactory {

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coverage newCoverage(String text, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coverage newCoverage(String text, Locale language, Direction direction) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description newDescription(String text, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description newDescription(String text, Locale language, Direction direction) {
		// TODO Auto-generated method stub
		return null;
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
		value = validatePropertyValue(value);
		return IdentifierImpl.of(value);
	}

	@Override
	public Identifier newIdentifier(String value, IdentifierScheme scheme) {
		if (scheme == null) {
			throw new IllegalArgumentException("\"scheme\" must not be null");
		}
		value = validatePropertyValue(value);
		return IdentifierImpl.of(value, scheme);
	}

	@Override
	public Identifier newIdentifier(String value, URI schemeURI) {
		if (schemeURI == null) {
			throw new IllegalArgumentException("\"schemeURI\" must not be null");
		}
		value = validatePropertyValue(value);
		return IdentifierImpl.of(value, schemeURI);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation newRelation(String text, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation newRelation(String text, Locale language, Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rights newRights(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rights newRights(String text, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rights newRights(String text, Locale language, Direction direction) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Title newTitle(String value, Locale language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type newType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null");
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String validatePropertyValue(String value) {
		if (value == null) {
			throw new IllegalArgumentException("\"value\" must not be null");
		}
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			throw new IllegalArgumentException("\"value\" must not be blank");
		}
		return trimmed;
	}
}
