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

package com.github.i49.pulp.api.metadata;

import java.util.Locale;

/**
 * A metadata property representing an instance of a name given to the EPUB publication.
 * 
 * @see <a href="http://dublincore.org/documents/dces/#title">Dublin Core Metadata Element Set, Version 1.1</a> 
 */
public interface Title extends TextTypeProperty, AlternativeProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Term getTerm() {
		return DublinCore.TITLE;
	}

	/**
	 * A builder for building an instance of {@link Title} property.
	 */
	public interface Builder extends TextTypeProperty.Builder<Title, Builder> {

		/**
		 * Optionally specifies the normalized form of the title.
		 * 
		 * @param value the title in the normalized form.
		 * @return this builder.
		 */
		Builder fileAs(String value);
		
		/**
		 * Optionally specifies the alternative representation of the title.
		 * 
		 * @param value the alternative representation of the title.
		 * @param language the language used for the alternative representation.
		 * @return this builder.
		 */
		Builder alternative(String value, Locale language);
	}
}
