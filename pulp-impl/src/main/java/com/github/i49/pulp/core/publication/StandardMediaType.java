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

package com.github.i49.pulp.core.publication;

import com.github.i49.pulp.api.MediaType;

/**
 * Media types defined by IDPF.
 */
public enum StandardMediaType implements MediaType {
	
	APPLICATION_EPUB_ZIP("application", "epub+zip"),
	APPLICATION_OEBPS_PACKAGE_XML("application", "oebps-package+xml")
	;

	private final String type;
	private final String subtype;
	
	private StandardMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSubtype() {
		return subtype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getType() + "/" + getSubtype();
	}
}
