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

import com.github.i49.pulp.api.metadata.PropertyBuilderSelector;
import com.github.i49.pulp.api.vocabulary.Generic;
import com.github.i49.pulp.api.vocabulary.GenericText;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Coverage;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Description;
import com.github.i49.pulp.api.vocabulary.dc.Format;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.api.vocabulary.dc.Relation;
import com.github.i49.pulp.api.vocabulary.dc.Rights;
import com.github.i49.pulp.api.vocabulary.dc.Source;
import com.github.i49.pulp.api.vocabulary.dc.Subject;
import com.github.i49.pulp.api.vocabulary.dc.Title;
import com.github.i49.pulp.api.vocabulary.dc.Type;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;

/**
 * The default implementation of {@link PropertyBuilderSelector}.
 */
class DefaultPropertyBuilderSelector implements PropertyBuilderSelector {
	
	private final PropertyMap propertyMap;
	
	DefaultPropertyBuilderSelector(PropertyMap propertyMap) {
		this.propertyMap = propertyMap;
	}
	
	@Override
	public Contributor.Builder contributor(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coverage.Builder coverage(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Creator.Builder creator(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date.Builder date(OffsetDateTime value) {
		checkNotNull(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description.Builder description(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Format.Builder format(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identifier.Builder identifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identifier.Builder identifier(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language.Builder language(Locale value) {
		checkNotNull(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language.Builder language(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Modified.Builder modifiled(OffsetDateTime value) {
		checkNotNull(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publisher.Builder publisher(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation.Builder relation(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rights.Builder rights(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Source.Builder source(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject.Builder subject(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Title.Builder title(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type.Builder type(String value) {
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Generic.Builder<V> generic(Term term, V value) {
		checkNotNull(term, "term");
		checkNotNull(value, "value");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericText.Builder generic(Term term, String value) {
		checkNotNull(term, "term");
		checkNotBlank(value, "value");
		// TODO Auto-generated method stub
		return null;
	}
}
