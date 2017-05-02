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

package com.github.i49.pulp.impl.container;

import org.w3c.dom.Document;

import com.github.i49.pulp.api.core.Rendition;

/**
 * Parser for parsing a package document.
 */
abstract class PackageDocumentParser implements PackageDocumentProcessor {
	
	private Rendition rendition;
	private RenditionResourceFinder resourceFinder;
	
	public void setRenditionToBuild(Rendition rendition, RenditionResourceFinder resourceFinder) {
		this.rendition = rendition;
		this.resourceFinder = resourceFinder;
	}
	
	public abstract void parse(Document document);

	protected Rendition getRendition() {
		assert(rendition != null);
		return rendition;
	}
	
	protected RenditionResourceFinder getResourceFinder() {
		assert(resourceFinder != null);
		return resourceFinder;
	}
}
