package com.github.i49.pulp.api;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Factory class for creating EPUB processing objects. 
 */
public final class Epub {

	/**
	 * Creates an empty publication.
	 * @return an empty publication.
	 */
	public static Publication createPublication() {
		return getProvider().createPublication();
	}
	
	/**
	 * Creates a {@link PublicationWriter} instance that writes a publication to the given stream.
	 * @param stream the stream to which the writer will writes a publication.
	 * @return created publication writer.
	 */
	public static PublicationWriter createWriter(OutputStream stream) {
		return createWriterFactory().createWriter(stream);
	}
	
	/**
	 * Creates a {@link PublicationWriterFactory} instance that can create one or more {@link PublicationWriter}s.
	 * @return created publication writer factory.
	 */
	public static PublicationWriterFactory createWriterFactory() {
		return getProvider().createWriterFactory();
	}

	private static class ThreadLocalLoader extends ThreadLocal<ServiceLoader<EpubProvider>> {
		@Override
		protected ServiceLoader<EpubProvider> initialValue() {
			return ServiceLoader.load(EpubProvider.class);
		}
	}
	
	private static final ThreadLocal<ServiceLoader<EpubProvider>> threadLoader = new ThreadLocalLoader();
	
	public static EpubProvider getProvider() {
		Iterator<EpubProvider> it = threadLoader.get().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		throw new EpubException("Implementation for EpubProvider was not found.");
	}
	
	private Epub() {
	}
}
