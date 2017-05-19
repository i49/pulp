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

package com.github.i49.pulp.api.vocabulary.dc;

/**
 * Types of titles.
 * Six basic types of titles are defined by EPUB 3.0 specification.
 */
public enum TitleType {
	/** The title that reading systems should normally display. */
	MAIN,
	/** A secondary title that augments the main title. */
	SUBTITLE,
	/** Shortened version of the main title. */
	SHORT,
	/** A title given to a set to which the given publication is a member. */
	COLLECTION,
	/** A designation that indicates substantive changes from one to the next. */
	EDITION,
	/** A fully expressed title. */
	EXTENDED
}
