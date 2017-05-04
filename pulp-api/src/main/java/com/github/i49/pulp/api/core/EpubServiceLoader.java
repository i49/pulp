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

import java.util.Iterator;
import java.util.ServiceLoader;

import com.github.i49.pulp.api.spi.EpubService;

/**
 * A helper class for loading {@link EpubService}.
 */
final class EpubServiceLoader {

	private static final ThreadLocal<ServiceLoader<EpubService>> threadLoader = new ThreadLocalLoader();
	
	/**
	 * Returns the service of {@link EpubService} type. 
	 * 
	 * @return found service.
	 * @throws EpubException if API implementation was not found.
	 */
	static EpubService getService() {
		Iterator<EpubService> it = threadLoader.get().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		throw new EpubException("Implementation of EpubServiceProvider was not found.");
	}
	
	/**
	 * Thread-local loader class of {@link EpubService}.
	 */
	private static class ThreadLocalLoader extends ThreadLocal<ServiceLoader<EpubService>> {
		@Override
		protected ServiceLoader<EpubService> initialValue() {
			return ServiceLoader.load(EpubService.class);
		}
	}
}
