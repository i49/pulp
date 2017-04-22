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

import java.util.Locale;

/**
 * A builder for building a {@link Relator} property.
 * 
 * @param <P> the type of the property to build,
 * such as {@link Creator}, {@link Contributor}, or {@link Publisher}.
 */
public interface RelatorBuilder<P extends Relator> extends TextPropertyBuilder<P, RelatorBuilder<P>>{

	/**
	 * Optionally specifies the normalized form of the relator name.
	 * 
	 * @param value the name in the normalized form.
	 * @return this builder.
	 */
	RelatorBuilder<P> fileAs(String value);
	
	/**
	 * Optionally specifies the alternative representation of the relator name.
	 * 
	 * @param value the alternative representation of the relator.
	 * @param language the language used for the alternative representation.
	 * @return this builder.
	 */
	RelatorBuilder<P> alternative(String value, Locale language);
	
	/**
	 * Optionally specifies the role of the relator.
	 * <p>
	 * Note that specified role will be ignored if the type of the property being built is {@link Publisher}.
	 * </p>
	 * 
	 * @param role the role of the relator.
	 * @return this builder.
	 */
	RelatorBuilder<P> role(String role);
}
