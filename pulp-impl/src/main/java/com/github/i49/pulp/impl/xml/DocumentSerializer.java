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

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * A class for serializing XML document into byte stream.
 * Note that an instance of this class is not thread-safe.
 */
public class DocumentSerializer {

	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	private final Transformer transformer;
	
	public DocumentSerializer(Transformer transformer) {
		this.transformer = transformer;
	}

	public void serialize(OutputStream stream, Document document) throws IOException, TransformerException {
		stream.write(XML_DECLARATION.getBytes());
		DOMSource source = new DOMSource(document);
		StreamResult target = new StreamResult(stream);
		this.transformer.transform(source, target);
	}
}
