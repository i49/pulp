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

import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link PropertyFactory}.
 * Only one instance can be created for this class.
 */
public class DefaultPropertyFactory implements PropertyFactory {

/*	
	@Override
	public GenericProperty createGenericProperty(Term term, String value) {
		checkNotNull(term, "term");
		checkNotBlank(value, "value");
		if (term.getType() != PropertyType.GENERIC) {
			throw new IllegalArgumentException(
					Messages.METADATA_PROPERTY_TYPE_MISMATCHED(PropertyType.GENERIC, term));
		}
		return new DefaultGenericProperty(term, value);
	}
	
	public RelatorProperty newContributor(String value) {
		checkNotBlank(value, "value");
		return new DefaultRelatorProperty(DublinCore.CONTRIBUTOR, value);
	}

	@Override
	public TextProperty newCoverage(String text) {
		checkNotBlank(text, "text");
		return new DefaultTextProperty<TextProperty>(DublinCore.COVERAGE, text);
	}

	@Override
	public RelatorProperty newCreator(String value) {
		checkNotBlank(value, "value");
		return new DefaultRelatorProperty(DublinCore.CREATOR, value);
	}

	@Override
	public DateProperty newDate(OffsetDateTime value) {
		checkNotNull(value, "value");
		return new DefaultDateProperty(DublinCore.DATE, value);
	}
	
	@Override
	public TextProperty newDescription(String text) {
		checkNotBlank(text, "text");
		return new DefaultTextProperty<TextProperty>(DublinCore.DESCRIPTION, text);
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
	public RelatorProperty newPublisher(String value) {
		checkNotBlank(value, "value");
		return new DefaultRelatorProperty(DublinCore.PUBLISHER, value);
	}

	@Override
	public TextProperty newRelation(String text) {
		checkNotBlank(text, "text");
		return new DefaultTextProperty<TextProperty>(DublinCore.RELATION, text);
	}

	@Override
	public TextProperty newRights(String text) {
		checkNotBlank(text, "text");
		return new DefaultTextProperty<TextProperty>(DublinCore.RIGHTS, text);
	}

	@Override
	public SourceProperty newSource(String value) {
		checkNotBlank(value, "value");
		return new DefaultSourceProperty(DublinCore.SOURCE, value);
	}

	@Override
	public SubjectProperty newSubject(String value) {
		checkNotBlank(value, "value");
		return new DefaultSubjectProperty(DublinCore.SUBJECT, value);
	}

	@Override
	public TitleProperty newTitle(String value) {
		checkNotBlank(value, "value");
		return new DefaultTitleProperty(DublinCore.TITLE, value);
	}

	@Override
	public SimpleProperty newType(String value) {
		checkNotBlank(value, "value");
		return new DefaultSimpleProperty(DublinCore.TYPE, value);
	}
*/
}
