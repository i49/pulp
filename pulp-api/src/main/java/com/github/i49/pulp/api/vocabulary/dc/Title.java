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

import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.Multilingual;
import com.github.i49.pulp.api.vocabulary.Normalizable;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.Text;

/**
 * The title of the publication.
 */
public interface Title extends Text, Multilingual, Normalizable {

	@Override
	default Term getTerm() {
		return DublinCore.TITLE;
	}

	/**
	 * Returns the type of this title.
	 * 
	 * @return the type of this title.
	 */
	Optional<TitleType> getType();

	public interface Builder extends Text.Builder<Title, Builder>, 
		Multilingual.Builder<Builder>, Normalizable.Builder<Builder>  {
		
		Builder ofType(TitleType type);
	}
}
