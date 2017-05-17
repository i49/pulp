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

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Identifier.Builder;
import com.github.i49.pulp.api.vocabulary.dc.Identifier.Scheme;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.impl.vocabulary.BaseProperty;
import com.github.i49.pulp.impl.vocabulary.RelatorProperty;
import com.github.i49.pulp.impl.vocabulary.StringProperty;

/**
 * Elements defined by Dublin Core Metadata Element Set.
 */
public class DublinCoreElements {
	
	public static Creator.Builder creator() {
		return new CreatorBuilder();
	}

	public static Contributor.Builder contributor() {
		return new ContributorBuilder();
	}

	public static Date.Builder date() {
		return new DateBuilder();
	}

	public static Identifier.Builder identifier() {
		return new IdentifierBuilder();
	}

	public static Language.Builder language() {
		return new LanguageBuilder();
	}

	public static Publisher.Builder publisher() {
		return new PublisherBuilder();
	}
	
	private static class DefaultContributor extends RelatorProperty implements Contributor {
		
		private DefaultContributor(ContributorBuilder b) {
			super(b);
		}
	}

	private static class ContributorBuilder 
		extends RelatorProperty.Builder<Contributor, Contributor.Builder>
		implements Contributor.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.CONTRIBUTOR;
		}

		@Override
		protected Contributor build() {
			return new DefaultContributor(this);
		}
	}

	private static class DefaultCreator extends RelatorProperty implements Creator {
		
		private DefaultCreator(CreatorBuilder b) {
			super(b);
		}
	}

	private static class CreatorBuilder 
		extends RelatorProperty.Builder<Creator, Creator.Builder>
		implements Creator.Builder {

		@Override
		public Term getTerm() {
			return DublinCore.CREATOR;
		}
	
		@Override
		protected Creator build() {
			return new DefaultCreator(this);
		}
	}

	private static class DefaultDate extends BaseProperty<OffsetDateTime> implements Date {
		
		private DefaultDate(DateBuilder b) {
			super(b);
		}
	}
	
	private static class DateBuilder 
		extends BaseProperty.Builder<OffsetDateTime, Date, Date.Builder>
		implements Date.Builder {
		
		@Override
		public Term getTerm() {
			return DublinCore.DATE;
		}

		@Override
		protected Date build() {
			return new DefaultDate(this);
		}
	}

	private static class DefaultIdentifier extends StringProperty implements Identifier {
		
		private final Scheme scheme;
		private final URI schemeURI;
		
		private DefaultIdentifier(IdentifierBuilder b) {
			super(b);
			this.scheme = b.scheme;
			this.schemeURI = b.schemeURI;
		}

		@Override
		public Optional<Scheme> getScheme() {
			return Optional.ofNullable(scheme);
		}

		@Override
		public Optional<URI> getSchemeURI() {
			return Optional.ofNullable(schemeURI);
		}
	}
	
	private static class IdentifierBuilder 
		extends StringProperty.Builder<Identifier, Identifier.Builder>
		implements Identifier.Builder {
		
		private Scheme scheme;
		private URI schemeURI;
		
		@Override
		public Term getTerm() {
			return DublinCore.IDENTIFIER;
		}

		@Override
		protected Identifier build() {
			return new DefaultIdentifier(this);
		}

		@Override
		public Builder scheme(Scheme scheme) {
			checkNotNull(scheme, "scheme");
			this.scheme = scheme;
			return self();
		}

		@Override
		public Builder scheme(URI schemeURI) {
			checkNotNull(schemeURI, "schemeURI");
			this.schemeURI = schemeURI;
			return self();
		}
	}
	
	private static class DefaultLanguage extends BaseProperty<Locale> implements Language {

		private DefaultLanguage(LanguageBuilder b) {
			super(b);
		}
	}

	public static class LanguageBuilder 
		extends BaseProperty.Builder<Locale, Language, Language.Builder>
		implements Language.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.LANGUAGE;
		}
		
		@Override
		protected Language build() {
			return new DefaultLanguage(this);
		}
	}

	private static class DefaultPublisher extends RelatorProperty implements Publisher {
		
		private DefaultPublisher(PublisherBuilder b) {
			super(b);
		}
	}

	private static class PublisherBuilder 
		extends RelatorProperty.Builder<Publisher, Publisher.Builder>
		implements Publisher.Builder {
		
		@Override
		public Term getTerm() {
			return DublinCore.PUBLISHER;
		}
	
		@Override
		protected Publisher build() {
			return new DefaultPublisher(this);
		}
	}
}
