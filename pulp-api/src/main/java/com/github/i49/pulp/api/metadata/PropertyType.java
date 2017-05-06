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

/**
 * Types allowed for metadata property.
 */
public enum PropertyType {
	/** date type represented by {@link DateProperty}. */
	DATE,
	/** generic type represented by {@link GenericProperty}. */
	GENERIC,
	/** identifier type represented by {@link IdentifierProperty}. */
	IDENTIFIER,
	/** language type represented by {@link LanguageProperty}. */
	LANGUAGE,
	/** relator type represented by {@link RelatorProperty}. */
	RELATOR,
	/** simple type represented by {@link SimpleProperty}. */
	SIMPLE,
	/** source type represented by {@link SourceProperty}. */
	SOURCE,
	/** subject type represented by {@link SubjectProperty}. */
	SUBJECT,
	/** text type represented by {@link TextProperty}. */
	TEXT,
	/** title type represented by {@link TitleProperty}. */
	TITLE
}
