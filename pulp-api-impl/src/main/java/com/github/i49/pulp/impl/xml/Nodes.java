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

package com.github.i49.pulp.impl.xml;

import java.util.Iterator;

import org.w3c.dom.Element;

public final class Nodes {

	public static Iterator<Element> children(Element element) {
		return new ElementIterator(element);
	}
	
	public static Iterator<Element> children(Element element, String namespaceURI) {
		return new ElementIterator(element, (Element child)->{
			if (namespaceURI == null) {
				return child == null;
			} else {
				return namespaceURI.equals(child.getNamespaceURI());
			}
		});
	}
	
	private Nodes() {
	}
}
