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

import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Metadata;
import com.github.i49.pulp.api.core.Spine;

/**
 * A builder interface for the parsers to build a publication.
 */
interface PublicationBuilder {
	
	Metadata getMetadateToBuild();
	
	/**
	 * Adds a publication resource to the current rendition.
	 * 
	 * @param href the resource location relative to the package document of the current rendition.
	 * @param mediaType the media type of the resource.
	 * @return added item of the rendition manifest.
	 */
	Manifest.Item addManifestItem(String href, String mediaType);
	
	/**
	 * Appends a page to the current rendition.
	 *  
	 * @param item the item that hold the reference to the publication resource presented on the page.
	 * @return added page of the rendition.
	 */
	Spine.Page addSpinePage(Manifest.Item item);
}
