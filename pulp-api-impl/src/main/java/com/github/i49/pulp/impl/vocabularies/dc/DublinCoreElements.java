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

package com.github.i49.pulp.impl.vocabularies.dc;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;
import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.Contributor;
import com.github.i49.pulp.api.vocabularies.dc.Coverage;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.Date;
import com.github.i49.pulp.api.vocabularies.dc.Description;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dc.Format;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Publisher;
import com.github.i49.pulp.api.vocabularies.dc.Relation;
import com.github.i49.pulp.api.vocabularies.dc.Rights;
import com.github.i49.pulp.api.vocabularies.dc.Source;
import com.github.i49.pulp.api.vocabularies.dc.Subject;
import com.github.i49.pulp.api.vocabularies.dc.SubjectAuthority;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dc.TitleType;
import com.github.i49.pulp.api.vocabularies.dc.Type;
import com.github.i49.pulp.api.vocabularies.dc.Identifier.Builder;
import com.github.i49.pulp.api.vocabularies.dc.IdentifierScheme;
import com.github.i49.pulp.impl.vocabularies.DateProperty;
import com.github.i49.pulp.impl.vocabularies.LanguageProperty;
import com.github.i49.pulp.impl.vocabularies.MultiValueTextProperty;
import com.github.i49.pulp.impl.vocabularies.RelatorProperty;
import com.github.i49.pulp.impl.vocabularies.StringProperty;
import com.github.i49.pulp.impl.vocabularies.TextProperty;

/**
 * All elements provided by Dublin Core Metadata Element Set.
 */
public final class DublinCoreElements {
	
	private DublinCoreElements() {
	}
	
	/**
	 * Creates a builder for {@link Contributor}.
	 * 
	 * @return newly created builder.
	 */
	public static Contributor.Builder contributor() {
		return new ContributorBuilder();
	}

	/**
	 * Creates a builder for {@link Coverage}.
	 * 
	 * @return newly created builder.
	 */
	public static Coverage.Builder coverage() {
		return new CoverageBuilder();
	}

	/**
	 * Creates a builder for {@link Creator}.
	 * 
	 * @return newly created builder.
	 */
	public static Creator.Builder creator() {
		return new CreatorBuilder();
	}

	/**
	 * Creates a builder for {@link Date}.
	 * 
	 * @return newly created builder.
	 */
	public static Date.Builder date() {
		return new DateBuilder();
	}

	/**
	 * Creates a builder for {@link Description}.
	 * 
	 * @return newly created builder.
	 */
	public static Description.Builder description() {
		return new DescriptionBuilder();
	}

	/**
	 * Creates a builder for {@link Format}.
	 * 
	 * @return newly created builder.
	 */
	public static Format.Builder format() {
		return new FormatBuilder();
	}

	/**
	 * Creates a builder for {@link Identifier}.
	 * 
	 * @return newly created builder.
	 */
	public static Identifier.Builder identifier() {
		return new IdentifierBuilder();
	}

	/**
	 * Creates a builder for {@link Language}.
	 * 
	 * @return newly created builder.
	 */
	public static Language.Builder language() {
		return new LanguageBuilder();
	}

	/**
	 * Creates a builder for {@link Publisher}.
	 * 
	 * @return newly created builder.
	 */
	public static Publisher.Builder publisher() {
		return new PublisherBuilder();
	}

	/**
	 * Creates a builder for {@link Relation}.
	 * 
	 * @return newly created builder.
	 */
	public static Relation.Builder relation() {
		return new RelationBuilder();
	}

	/**
	 * Creates a builder for {@link Rights}.
	 * 
	 * @return newly created builder.
	 */
	public static Rights.Builder rights() {
		return new RightsBuilder();
	}

	/**
	 * Creates a builder for {@link Source}.
	 * 
	 * @return newly created builder.
	 */
	public static Source.Builder source() {
		return new SourceBuilder();
	}

	/**
	 * Creates a builder for {@link Subject}.
	 * 
	 * @return newly created builder.
	 */
	public static Subject.Builder subject() {
		return new SubjectBuilder();
	}

	/**
	 * Creates a builder for {@link Title}.
	 * 
	 * @return newly created builder.
	 */
	public static Title.Builder title() {
		return new TitleBuilder();
	}
	
	/**
	 * Creates a builder for {@link Type}.
	 * 
	 * @return newly created builder.
	 */
	public static Type.Builder type() {
		return new TypeBuilder();
	}
	
	/**
	 * An implementation of {@link Contributor} property.
	 */
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
	
	/**
	 * An implementation of {@link Coverage} property.
	 */
	private static class DefaultCoverage extends TextProperty implements Coverage {
		
		private DefaultCoverage(CoverageBuilder b) {
			super(b);
		}
	}

	private static class CoverageBuilder 
		extends TextProperty.Builder<Coverage, Coverage.Builder>
		implements Coverage.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.COVERAGE;
		}

		@Override
		protected Coverage build() {
			return new DefaultCoverage(this);
		}
	}
	
	/**
	 * An implementation of {@link Creator} property.
	 */
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
	
	/**
	 * An implementation of {@link Date} property.
	 */
	private static class DefaultDate extends DateProperty implements Date {
		
		private DefaultDate(DateBuilder b) {
			super(b);
		}
	}
	
	private static class DateBuilder extends DateProperty.Builder<Date, Date.Builder>
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
	
	/**
	 * An implementation of {@link Description} property.
	 */
	private static class DefaultDescription extends TextProperty implements Description {
		
		private DefaultDescription(DescriptionBuilder b) {
			super(b);
		}
	}

	private static class DescriptionBuilder 
		extends TextProperty.Builder<Description, Description.Builder>
		implements Description.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.DESCRIPTION;
		}
		
		@Override
		protected Description build() {
			return new DefaultDescription(this);
		}
	}
	
	/**
	 * An implementation of {@link Format} property.
	 */
	private static class DefaultFormat extends StringProperty implements Format {
		
		private DefaultFormat(FormatBuilder b) {
			super(b);
		}
	}

	private static class FormatBuilder 
		extends StringProperty.Builder<Format, Format.Builder>
		implements Format.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.FORMAT;
		}

		@Override
		protected Format build() {
			return new DefaultFormat(this);
		}
	}
	
	/**
	 * An implementation of {@link Identifier} property.
	 */
	private static class DefaultIdentifier extends StringProperty implements Identifier {
		
		private final IdentifierScheme scheme;
		private final URI schemeURI;
		
		private DefaultIdentifier(IdentifierBuilder b) {
			super(b);
			this.scheme = b.scheme;
			this.schemeURI = b.schemeURI;
		}

		@Override
		public Optional<IdentifierScheme> getScheme() {
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
		
		private IdentifierScheme scheme;
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
		public Builder scheme(IdentifierScheme scheme) {
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
	
	/**
	 * An implementation of {@link Language} property.
	 */
	private static class DefaultLanguage extends LanguageProperty implements Language {

		private DefaultLanguage(LanguageBuilder b) {
			super(b);
		}
	}

	public static class LanguageBuilder 
		extends LanguageProperty.Builder<Language, Language.Builder>
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

	/**
	 * An implementation of {@link Publisher} property.
	 */
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
	
	/**
	 * An implementation of {@link Relation} property.
	 */
	private static class DefaultRelation extends TextProperty implements Relation {
		
		private DefaultRelation(RelationBuilder b) {
			super(b);
		}
	}

	private static class RelationBuilder 
		extends TextProperty.Builder<Relation, Relation.Builder>
		implements Relation.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.RELATION;
		}

		@Override
		protected Relation build() {
			return new DefaultRelation(this);
		}
	}

	/**
	 * An implementation of {@link Rights} property.
	 */
	private static class DefaultRights extends TextProperty implements Rights {
		
		private DefaultRights(RightsBuilder b) {
			super(b);
		}
	}

	private static class RightsBuilder 
		extends TextProperty.Builder<Rights, Rights.Builder>
		implements Rights.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.RIGHTS;
		}

		@Override
		protected Rights build() {
			return new DefaultRights(this);
		}
	}
	
	/**
	 * An implementation of {@link Source} property.
	 */
	private static class DefaultSource extends StringProperty implements Source {
		
		private final String scheme;
		
		private DefaultSource(SourceBuilder b) {
			super(b);
			this.scheme = b.scheme;
		}

		@Override
		public Optional<String> getScheme() {
			return Optional.ofNullable(scheme);
		}
	}

	private static class SourceBuilder 
		extends StringProperty.Builder<Source, Source.Builder>
		implements Source.Builder {
	
		private String scheme;
		
		@Override
		public Term getTerm() {
			return DublinCore.SOURCE;
		}

		@Override
		protected Source build() {
			return new DefaultSource(this);
		}

		@Override
		public Source.Builder scheme(String scheme) {
			checkNotBlank(scheme, "scheme");
			this.scheme = scheme;
			return self();
		}
	}
	
	/**
	 * An implementation of {@link Subject} property.
	 */
	private static class DefaultSubject extends StringProperty implements Subject {
		
		private final SubjectAuthority authority;
		private final URI scheme;
		private final String code;
		
		private DefaultSubject(SubjectBuilder b) {
			super(b);
			this.authority = b.authority;
			this.scheme = b.scheme;
			this.code = b.code;
		}

		@Override
		public Optional<SubjectAuthority> getAuthority() {
			return Optional.ofNullable(this.authority);
		}

		@Override
		public Optional<String> getCode() {
			return Optional.ofNullable(this.code);
		}

		@Override
		public Optional<URI> getScheme() {
			return Optional.ofNullable(this.scheme);
		}
	}

	private static class SubjectBuilder 
		extends StringProperty.Builder<Subject, Subject.Builder>
		implements Subject.Builder {
		
		private SubjectAuthority authority;
		private URI scheme;
		private String code;
	
		@Override
		public Term getTerm() {
			return DublinCore.SUBJECT;
		}

		@Override
		protected Subject build() {
			return new DefaultSubject(this);
		}

		@Override
		public Subject.Builder ofCode(SubjectAuthority authority, String code) {
			checkNotNull(authority, "authority");
			checkNotBlank(code, "code");
			this.authority = authority;
			this.scheme = null;
			this.code = code;
			return self();
		}

		@Override
		public Subject.Builder ofCode(URI scheme, String code) {
			checkNotNull(scheme, "scheme");
			checkNotBlank(code, "code");
			this.authority = null;
			this.scheme = scheme;
			this.code = code;
			return self();
		}
	}
	
	/**
	 * An implementation of {@link Title} property.
	 */
	private static class DefaultTitle extends MultiValueTextProperty implements Title {
		
		private final TitleType type;
		
		private DefaultTitle(TitleBuilder b) {
			super(b);
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
		public Term getTerm() {
			return DublinCore.TITLE;
		}

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
	
	/**
	 * An implementation of {@link Type} property.
	 */
	private static class DefaultType extends StringProperty implements Type {
		
		private DefaultType(TypeBuilder b) {
			super(b);
		}
	}

	private static class TypeBuilder 
		extends StringProperty.Builder<Type, Type.Builder>
		implements Type.Builder {
	
		@Override
		public Term getTerm() {
			return DublinCore.TYPE;
		}

		@Override
		protected Type build() {
			return new DefaultType(this);
		}
	}
}
