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
 * The publication types available for the type property in metadata.
 */
public enum StandardType implements Type {
	
	/** Identifies that the EPUB Publication represents a dictionary. */
	DICTIONARY("dictionary"),
	/** Identifies that the EPUB Publication contains a distributable object. */
	DISTRIBUTABLE_OBJECT("distributable-object"),
	/** Identifies that the EPUB Publication conforms to the EDUPUB Profile. */
	EDUCATION("education"),
	/** Identifies that the EPUB Publication represents an index. */
	INDEX("index"),
	/** Identifies that the EPUB Publication is only a preview of a work. */
	PREVIEW("preview"),
	/** Identifies that the EPUB Publication is a Scriptable Component. */
	SCRIPTABLE_COMPONENT("scriptable-component"),
	/** Identifies that the EPUB Publication contains the teacher's edition of a work. */
	TEACHER_EDITION("teacher-edition"),
	/** Identifies that the EPUB Publication contains a teacher's guide. */
	TEACHER_GUIDE("teacher-guide")
	;
	
	private final String value;
	
	/**
	 * Constructs this publication type.
	 * 
	 * @param value the value to be assigned for this property.
	 */
	private StandardType(String value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "type";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return value;
	}
}
