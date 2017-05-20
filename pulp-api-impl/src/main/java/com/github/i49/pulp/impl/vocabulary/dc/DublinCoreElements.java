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
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Coverage;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Description;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Identifier.Builder;
import com.github.i49.pulp.api.vocabulary.dc.Identifier.Scheme;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.api.vocabulary.dc.Relation;
import com.github.i49.pulp.api.vocabulary.dc.Rights;
import com.github.i49.pulp.api.vocabulary.dc.Title;
import com.github.i49.pulp.api.vocabulary.dc.TitleType;
import com.github.i49.pulp.impl.vocabulary.DateProperty;
import com.github.i49.pulp.impl.vocabulary.LanguageProperty;
import com.github.i49.pulp.impl.vocabulary.MultiValueTextProperty;
import com.github.i49.pulp.impl.vocabulary.RelatorProperty;
import com.github.i49.pulp.impl.vocabulary.StringProperty;
import com.github.i49.pulp.impl.vocabulary.TextProperty;

/**
 * All elements provided by Dublin Core Metadata Element Set.
 */
public final class DublinCoreElements {
	
	private DublinCoreElements() {
	}
	
	public static Contributor.Builder contributor() {
		return new ContributorBuilder();
	}

	public static Coverage.Builder coverage() {
		return new CoverageBuilder();
	}

	public static Creator.Builder creator() {
		return new CreatorBuilder();
	}

	public static Date.Builder date() {
		return new DateBuilder();
	}

	public static Description.Builder description() {
		return new DescriptionBuilder();
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

	public static Relation.Builder relation() {
		return new RelationBuilder();
	}

	public static Rights.Builder rights() {
		return new RightsBuilder();
	}

	public static Title.Builder title() {
		return new TitleBuilder();
	}
	
	private static class DefaultContributor extends RelatorProperty implements Contributor {
		
		private DefaultContributor(ContributorBuilder b) {
			super(DublinCore.CONTRIBUTOR, b);
		}
	}

	private static class ContributorBuilder 
		extends RelatorProperty.Builder<Contributor, Contributor.Builder>
		implements Contributor.Builder {
	
		@Override
		protected Contributor build() {
			return new DefaultContributor(this);
		}
	}

	private static class DefaultCoverage extends TextProperty implements Coverage {
		
		private DefaultCoverage(CoverageBuilder b) {
			super(DublinCore.COVERAGE, b);
		}
	}

	private static class CoverageBuilder 
		extends TextProperty.Builder<Coverage, Coverage.Builder>
		implements Coverage.Builder {
	
		@Override
		protected Coverage build() {
			return new DefaultCoverage(this);
		}
	}

	private static class DefaultCreator extends RelatorProperty implements Creator {
		
		private DefaultCreator(CreatorBuilder b) {
			super(DublinCore.CREATOR, b);
		}
	}

	private static class CreatorBuilder 
		extends RelatorProperty.Builder<Creator, Creator.Builder>
		implements Creator.Builder {

		@Override
		protected Creator build() {
			return new DefaultCreator(this);
		}
	}

	private static class DefaultDate extends DateProperty implements Date {
		
		private DefaultDate(DateBuilder b) {
			super(DublinCore.DATE, b);
		}
	}
	
	private static class DateBuilder extends DateProperty.Builder<Date, Date.Builder>
		implements Date.Builder {
		
		@Override
		protected Date build() {
			return new DefaultDate(this);
		}
	}

	private static class DefaultDescription extends TextProperty implements Description {
		
		private DefaultDescription(DescriptionBuilder b) {
			super(DublinCore.DESCRIPTION, b);
		}
	}

	private static class DescriptionBuilder 
		extends TextProperty.Builder<Description, Description.Builder>
		implements Description.Builder {
	
		@Override
		protected Description build() {
			return new DefaultDescription(this);
		}
	}

	private static class DefaultIdentifier extends StringProperty implements Identifier {
		
		private final Scheme scheme;
		private final URI schemeURI;
		
		private DefaultIdentifier(IdentifierBuilder b) {
			super(DublinCore.IDENTIFIER, b);
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
	
	private static class DefaultLanguage extends LanguageProperty implements Language {

		private DefaultLanguage(LanguageBuilder b) {
			super(DublinCore.LANGUAGE, b);
		}
	}

	public static class LanguageBuilder 
		extends LanguageProperty.Builder<Language, Language.Builder>
		implements Language.Builder {
	
		@Override
		protected Language build() {
			return new DefaultLanguage(this);
		}
	}

	private static class DefaultPublisher extends RelatorProperty implements Publisher {
		
		private DefaultPublisher(PublisherBuilder b) {
			super(DublinCore.PUBLISHER, b);
		}
	}

	private static class PublisherBuilder 
		extends RelatorProperty.Builder<Publisher, Publisher.Builder>
		implements Publisher.Builder {
		
		@Override
		protected Publisher build() {
			return new DefaultPublisher(this);
		}
	}

	private static class DefaultRelation extends TextProperty implements Relation {
		
		private DefaultRelation(RelationBuilder b) {
			super(DublinCore.RELATION, b);
		}
	}

	private static class RelationBuilder 
		extends TextProperty.Builder<Relation, Relation.Builder>
		implements Relation.Builder {
	
		@Override
		protected Relation build() {
			return new DefaultRelation(this);
		}
	}

	private static class DefaultRights extends TextProperty implements Rights {
		
		private DefaultRights(RightsBuilder b) {
			super(DublinCore.RIGHTS, b);
		}
	}

	private static class RightsBuilder 
		extends TextProperty.Builder<Rights, Rights.Builder>
		implements Rights.Builder {
	
		@Override
		protected Rights build() {
			return new DefaultRights(this);
		}
	}
	
	private static class DefaultTitle extends MultiValueTextProperty implements Title {
		
		private final TitleType type;
		
		private DefaultTitle(TitleBuilder b) {
			super(DublinCore.TITLE, b);
			this.type = b.type;
		}
		
		@Override
		public Optional<TitleType> getType() {
			return Optional.ofNullable(type);
		}
	}

	private static class TitleBuilder 
		extends MultiValueTextProperty.Builder<Title, Title.Builder>
		implements Title.Builder {
		
		private TitleType type;
		
		@Override
		public Title.Builder ofType(TitleType type) {
			checkNotNull(type, "type");
			this.type = type;
			return this;
		}
		
		@Override
		protected Title build() {
			return new DefaultTitle(this);
		}
	}
}
