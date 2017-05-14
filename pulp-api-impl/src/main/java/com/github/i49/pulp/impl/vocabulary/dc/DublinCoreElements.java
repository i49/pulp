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

package com.github.i49.pulp.impl.vocabulary.dc;

import java.time.OffsetDateTime;
import java.util.Locale;

import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.impl.vocabulary.AbstractProperty;
import com.github.i49.pulp.impl.vocabulary.AbstractPropertyBuilder;
import com.github.i49.pulp.impl.vocabulary.AbstractRelator;

/**
 *
 */
public class DublinCoreElements {
	
	public Creator.Builder creator(String value) {
		return new CreatorBuilder().value(value);
	}

	public Contributor.Builder contributor(String value) {
		return new ContributorBuilder().value(value);
	}

	public Date.Builder date(OffsetDateTime value) {
		return new DateBuilder().value(value);
	}

	public Language.Builder langauge(Locale value) {
		return new LanguageBuilder().value(value);
	}
	
	private <T extends Property> T add(T p) {
		return p;
	}
	
	private static class DefaultContributor extends AbstractRelator implements Contributor {
		
		private DefaultContributor(ContributorBuilder b) {
			super(b);
		}
	}

	private class ContributorBuilder 
		extends AbstractRelator.Builder<Contributor, Contributor.Builder>
		implements Contributor.Builder {
	
		@Override
		public Contributor result() {
			return add(new DefaultContributor(this));
		}
	}

	private static class DefaultCreator extends AbstractRelator implements Creator {
		
		private DefaultCreator(CreatorBuilder b) {
			super(b);
		}
	}

	private class CreatorBuilder 
		extends AbstractRelator.Builder<Creator, Creator.Builder>
		implements Creator.Builder {
	
		@Override
		public Creator result() {
			return add(new DefaultCreator(this));
		}
	}

	private static class DefaultDate extends AbstractProperty<OffsetDateTime> implements Date {
		
		private DefaultDate(DateBuilder b) {
			super(b.getValue());
		}
	}
	
	private class DateBuilder 
		extends AbstractPropertyBuilder<OffsetDateTime, Date, Date.Builder>
		implements Date.Builder {

		@Override
		public Date result() {
			return add(new DefaultDate(this));
		}
	}

	private static class DefaultLanguage extends AbstractProperty<Locale> implements Language {

		private DefaultLanguage(LanguageBuilder b) {
			super(b.getValue());
		}
	}

	public class LanguageBuilder 
		extends AbstractPropertyBuilder<Locale, Language, Language.Builder>
		implements Language.Builder {
	
		@Override
		public Language result() {
			return add(new DefaultLanguage(this));
		}
	}
}
