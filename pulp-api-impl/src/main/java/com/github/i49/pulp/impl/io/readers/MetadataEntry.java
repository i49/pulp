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

package com.github.i49.pulp.impl.io.readers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.vocabularies.Term;

/**
 * A metadata entry in publication document.
 */
class MetadataEntry {

	private final Term term;
	private final Element element;
	private List<MetadataEntry> refiners;
	
	MetadataEntry(Term term, Element element) {
		assert(term != null);
		assert(element != null);
		this.term = term;
		this.element = element;
	}
	
	Term getTerm() {
		return term;
	}
	
	Element getElement() {
		return element;
	}
	
	boolean hasId() {
		return getElement().hasAttribute("id");
	}
	
	String getId() {
		return getElement().getAttribute("id");
	}
	
	boolean isRefining() {
		return getElement().hasAttribute("refines");
	}
	
	String getRefiningTarget() {
		return getElement().getAttribute("refines").trim();
	}
	
	void refine(MetadataEntry entry) {
		assert(entry != null);
		entry.addRefiner(this);
	}
	
	/**
	 * Returns the value of this entry as a string.
	 * 
	 * @return the value of this entry.
	 */
	String getValue() {
		return getElement().getTextContent().trim();
	}
	
	/**
	 * Returns the scheme of the value.
	 * 
	 * @return the scheme of the value.
	 */
	String getScheme() {
		return getElement().getAttribute("scheme").trim();
	}
	
	/**
	 * Returns the refiners of this entry.
	 * 
	 * @return the refiners of this entry.
	 */
	Iterable<MetadataEntry> getRefiners() {
		if (this.refiners == null) {
			return Collections.emptyList();
		}
		return this.refiners;
	}
	
	private void addRefiner(MetadataEntry refiner) {
		assert(refiner != null);
		if (this.refiners == null) {
			this.refiners = new ArrayList<>();
		}
		this.refiners.add(refiner);
	}
	
	@Override
	public String toString() {
		return element.getTagName();
	}
}
