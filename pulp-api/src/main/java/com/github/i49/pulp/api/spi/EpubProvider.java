package com.github.i49.pulp.api.spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationBuilder;
import com.github.i49.pulp.api.PublicationWriterFactory;

/**
 * Service provider for EPUB processing objects.
 */
public abstract class EpubProvider {

	private static class ThreadLocalLoader extends ThreadLocal<ServiceLoader<EpubProvider>> {
		@Override
		protected ServiceLoader<EpubProvider> initialValue() {
			return ServiceLoader.load(EpubProvider.class);
		}
	}
	
	private static final ThreadLocal<ServiceLoader<EpubProvider>> threadLoader = new ThreadLocalLoader();
	
	public static EpubProvider provider() {
		Iterator<EpubProvider> it = threadLoader.get().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		throw new EpubException("Implementation for EpubProvider was not found.");
	}
	
	/**
	 * Creates a publication.
	 * @return a publication.
	 */
	public abstract Publication createPublication(Consumer<PublicationBuilder> c);
	
	public abstract PublicationWriterFactory createWriterFactory();
}
