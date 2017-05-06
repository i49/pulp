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
import java.util.Optional;

/**
 * A person or organization who relates to the EPUB publication.
 * This property can be used for
 * {@link DublinCore#CONTRIBUTOR}, {@link DublinCore#CREATOR},
 * {@link DublinCore#PUBLISHER} and so on.
 */
public interface RelatorProperty 
	extends TextProperty, Multilingual<RelatorProperty>, Normalizable<RelatorProperty> {

	/**
	 * Returns the role of this relator.
	 * 
	 * @return the role of this relator.
	 */
	Optional<String> getRole();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	RelatorProperty resetDirection();

	/**
	 * {@inheritDoc}
	 */
	@Override
	RelatorProperty resetLanguage();

	/**
	 * Clears the role assigned.
	 * 
	 * @return this property.
	 */
	RelatorProperty resetRole();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	RelatorProperty setDirection(Direction direction);

	/**
	 * {@inheritDoc}
	 */
	@Override
	RelatorProperty setLanguage(Locale language);
	
	/**
	 * Assigns the role of this relator.
	 * 
	 * @param role the role of this relator.
	 * @return this property.
	 */
	RelatorProperty setRole(String role);

	/**
	 * {@inheritDoc}
	 */
	@Override
	RelatorProperty setValue(String value);
}
