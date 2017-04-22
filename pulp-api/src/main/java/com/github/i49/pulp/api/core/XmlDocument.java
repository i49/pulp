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

package com.github.i49.pulp.api.core;

import org.w3c.dom.Document;

/**
 * A publication resource which content is an XML document.
 */
public interface XmlDocument extends PublicationResource {

	/**
	 * Returns the content of this resource as an XML document.
	 * @return an XML document.
	 */
	Document getDocument();
	
	/**
	 * Assigns an XML document to this resource.
	 * @param document the XML document to assign to this resource.
	 */
	void setDocument(Document document);
}