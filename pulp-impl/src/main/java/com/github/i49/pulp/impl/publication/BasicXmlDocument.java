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

package com.github.i49.pulp.impl.publication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.pulp.api.MediaType;
import com.github.i49.pulp.api.XmlDocument;
import com.github.i49.pulp.impl.xml.DocumentSerializer;
import com.github.i49.pulp.impl.xml.XmlServices;

class BasicXmlDocument extends BasicPublicationResource implements XmlDocument {

	private Document document;
	
	public BasicXmlDocument(PublicationResourceLocation location, MediaType mediaType) {
		super(location, mediaType);
	}
	
	@Override
	public InputStream openContent() throws IOException {
		if (this.document == null) {
			return super.openContent();
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			writeDocument(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public Document getDocument() {
		if (this.document == null) {
			try {
				this.document = readDocument();
			} catch (IOException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.document;
	}
	
	@Override
	public void setDocument(Document document) {
		this.document = document;
	}
	
	protected Document readDocument() throws IOException, SAXException {
		try (InputStream in = super.openContent()) {
			DocumentBuilder builder = XmlServices.newBuilder();
			return builder.parse(in);
		}
	}
	
	protected void writeDocument(OutputStream out) throws IOException, TransformerException {
		DocumentSerializer serializer = XmlServices.newSerializer();
		serializer.serialize(out, this.document);
		out.flush();
	}
}
